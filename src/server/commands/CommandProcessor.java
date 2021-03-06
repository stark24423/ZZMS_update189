/*
 This file is part of the OdinMS Maple Story Server
 Copyright (C) 2008 ~ 2010 Patrick Huy <patrick.huy@frz.cc> 
 Matthias Butz <matze@odinms.de>
 Jan Christian Meyer <vimes@odinms.de>

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License version 3
 as published by the Free Software Foundation. You may not use, modify
 or distribute this program under any other version of the
 GNU Affero General Public License.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU Affero General Public License for more details.

 You should have received a copy of the GNU Affero General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package server.commands;

import client.MapleCharacter;
import client.MapleClient;
import database.DatabaseConnection;
import handling.channel.ChannelServer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import tools.FileoutputUtil;

public class CommandProcessor {

    private final static HashMap<String, MapleCommand> commands = new HashMap<>();
    private final static HashMap<Integer, ArrayList<String>> commandList = new HashMap<>();

    static {

        Class<?>[] CommandFiles = {
            PlayerCommand.class,
            InternCommand.class,
            GMCommand.class,
            SuperGMCommand.class,
            AdminCommand.class
        };

        for (Class<?> clasz : CommandFiles) {
            try {
                PlayerGMRank rankNeeded = (PlayerGMRank) clasz.getMethod("getPlayerLevelRequired", new Class<?>[]{}).invoke(null, (Object[]) null);
                Class<?>[] a = clasz.getDeclaredClasses();
                ArrayList<String> cL = new ArrayList<>();
                for (Class<?> c : a) {
                    try {
                        if (!Modifier.isAbstract(c.getModifiers()) && !c.isSynthetic()) {
                            Object o = c.newInstance();
                            boolean enabled;
                            try {
                                enabled = c.getDeclaredField("enabled").getBoolean(c.getDeclaredField("enabled"));
                            } catch (NoSuchFieldException ex) {
                                enabled = true; //Enable all coded commands by default.
                            }
                            if (o instanceof CommandExecute && enabled) {
                                for (int i = 0; i < rankNeeded.getCommandPrefix().length; i++) {
                                    cL.add(new StringBuilder().append(rankNeeded.getCommandPrefix()[i]).append(c.getSimpleName().toLowerCase()).toString());
                                    commands.put(rankNeeded.getCommandPrefix()[i] + c.getSimpleName().toLowerCase(), new MapleCommand((CommandExecute) o, rankNeeded.getLevel()));
                                    if (rankNeeded.getCommandPrefix()[i] != PlayerGMRank.GM.getCommandPrefix()[i] && rankNeeded.getCommandPrefix()[i] != PlayerGMRank.NORMAL.getCommandPrefix()[i]) { //add it again for GM
                                        commands.put(String.valueOf(rankNeeded.getCommandPrefix()[i]) + c.getSimpleName().toLowerCase(), new MapleCommand((CommandExecute) o, PlayerGMRank.GM.getLevel()));
                                    }
                                }
                            }
                        }
                    } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException ex) {
                        FileoutputUtil.outputFileError(FileoutputUtil.CommandEx_Log, ex);
                    }
                }
                Collections.sort(cL);
                commandList.put(rankNeeded.getLevel(), cL);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                FileoutputUtil.outputFileError(FileoutputUtil.CommandEx_Log, ex);
            }
        }
    }

    private static void sendDisplayMessage(MapleClient c, String msg, CommandType type) {
        if (c.getPlayer() == null) {
            return;
        }
        switch (type) {
            case NORMAL:
                c.getPlayer().dropMessage(-11, msg);
                break;
            case TRADE:
                c.getPlayer().dropMessage(-2, "Error : " + msg);
                break;
        }
    }

    public static void dropHelp(MapleClient c) {
        StringBuilder sb = new StringBuilder();
        sb.append("\r\n??????");
        sb.append("\r\n@GM ???????????????????????????????????????????????????");
        sb.append("\r\n@?????????????????????????????????????????????");
        sb.append("\r\n@???????????????????????????????????????NPC???????????????????????????");
        sb.append("\r\n@???????????????????????????????????????");
        sb.append("\r\n@???????????????????????????????????????????????????");
        sb.append("\r\n@?????????????????????????????????????????????????????????????????????");
        sb.append("\r\n@?????????????????????????????????????????????????????????");
        sb.append("\r\n@fm??????????????????????????????");
        sb.append("\r\n* ??????????????????????????????????????????Tab????????????????????????????????????????????????");
        sb.append("\r\n???????????????1???2???3??????????????????????????????");
        for (String command : sb.toString().split("\r\n")) {
            c.getPlayer().dropMessage(-11, command);
        }
        if (!c.getPlayer().isIntern()) {
            return;
        }
        sb = null;
        for (int i = 0; i <= c.getPlayer().getGMLevel(); i++) {
            if (commandList.containsKey(i)) {
                final PlayerGMRank pGMRank = PlayerGMRank.getByLevel(i);
                sb = new StringBuilder("");
                final StringBuilder comment = new StringBuilder("");
                char[] gmRank = pGMRank.getCommandPrefix();
                for (int j = 0; j < gmRank.length; j++) {
                    if (gmRank[j] == '/' && !c.isGM()) {
                        continue;
                    }
                    comment.append('"').append(gmRank[j]).append('"');
                    if (j != gmRank.length - 1 && gmRank.length != 1) {
                        comment.append("???");
                    }
                }
                c.getPlayer().dropMessage(-11, "-----------------------------------------------------------------------------------------");
                if (pGMRank == PlayerGMRank.NORMAL) {
                    c.getPlayer().dropMessage(-11, "????????????(??????:" + comment + ")???");
                } else if (pGMRank == PlayerGMRank.INTERN) {
                    c.getPlayer().dropMessage(-11, "?????????????????????(??????:" + comment + ")???");
                } else if (pGMRank == PlayerGMRank.GM) {
                    c.getPlayer().dropMessage(-11, "?????????????????????(??????:" + comment + ")???");
                } else if (pGMRank == PlayerGMRank.SUPERGM) {
                    c.getPlayer().dropMessage(-11, "?????????????????????(??????:" + comment + ")???");
                } else if (pGMRank == PlayerGMRank.ADMIN) {
                    c.getPlayer().dropMessage(-11, "?????????????????????(??????:" + comment + ")???");
                }
                for (String s : commandList.get(i)) {
                    if ((gmRank.length > 1 && s.substring(0, 1).equals(String.valueOf(gmRank[0]))) || gmRank.length == 1) {
                        sb.append(s.substring(1));
                        sb.append("???");
                    }
                }
                c.getPlayer().dropMessage(-11, sb.toString());
            }
        }
    }

    public static boolean processCommand(MapleClient c, String line, CommandType type) {
        if (line.startsWith("/") && c.getPlayer().cheakSkipOnceChat()) {
            return true;
        }
        for (PlayerGMRank prefix : PlayerGMRank.values()) {
            for (char chr : prefix.getCommandPrefix()) {
                if (line.startsWith(String.valueOf(chr) + String.valueOf(chr))) {
                    return false;
                }
            }
        }

        boolean dropUnFound = false;
        String mapleHelp = "";
        String searchHelp = "";
        for (char chr : PlayerGMRank.NORMAL.getCommandPrefix()) {
            if (String.valueOf(line.charAt(0)).equals(String.valueOf(chr))) {
                String[] splitted = line.split(" ");
                splitted[0] = splitted[0].toLowerCase();

                MapleCommand co = commands.get(splitted[0]);
                if (co == null || co.getType() != type) {
                    if (splitted[0].equals(line.charAt(0) + "??????")) {
                        dropHelp(c);
                        return true;
                    }
                    dropUnFound = true;
                    mapleHelp = "[???????????????]????????????????????? '" + chr + "??????' ????????????????????? '" + chr + "' ???????????????.";
                    continue;
                }
                try {
                    int ret = co.execute(c, splitted); //Don't really care about the return value. ;D
                } catch (Exception e) {
                    sendDisplayMessage(c, "???????????????????????????", type);
                    if (c.getPlayer().isGM()) {
                        sendDisplayMessage(c, "??????: " + e, type);
                        FileoutputUtil.outputFileError(FileoutputUtil.CommandEx_Log, e);
                    }
                }
                return true;
            }
        }

        if (c.getPlayer().getGMLevel() > PlayerGMRank.NORMAL.getLevel()) {
            if (line.charAt(0) == '`' && c.getPlayer().getGMLevel() > 2) {
                for (final ChannelServer cserv : ChannelServer.getAllInstances()) {
                    cserv.broadcastGMMessage(tools.packet.CField.multiChat("[????????????] " + c.getPlayer().getName(), line.substring(1), 4));
                }
                return true;
            }
            for (PlayerGMRank pGMRank : PlayerGMRank.values()) {
                if (PlayerGMRank.NORMAL == pGMRank) {
                    continue;
                }
                for (char chr : pGMRank.getCommandPrefix()) {
                    if (String.valueOf(line.charAt(0)).equals(String.valueOf(chr))) {
                        String[] splitted = line.split(" ");
                        splitted[0] = splitted[0].toLowerCase();

                        MapleCommand co = commands.get(splitted[0]);
                        if (co == null) {
                            String commandStr = "??????";
                            if (splitted[0].equals(line.charAt(0) + commandStr)) {
                                dropHelp(c);
                                return true;
                            }
                            dropUnFound = true;
                            chr = c.isGM() ? pGMRank.getCommandPrefix()[0] : chr;
                            mapleHelp = "[???????????????]????????????????????? '" + chr + "??????' ????????????????????? '" + chr + "' ???????????????.";
                            searchHelp = "?????? '" + chr + "???????????? ????????????' ????????????????????? '" + chr + "' ??????.";
                            continue;
                        }
                        if (c.getPlayer().getGMLevel() >= co.getReqGMLevel()) {
                            int ret = 0;
                            try {
                                ret = co.execute(c, splitted);
                            } catch (ArrayIndexOutOfBoundsException x) {
                                sendDisplayMessage(c, "?????????????????????????????????????????????????????????: " + x, type);
                            } catch (Exception e) {
                                FileoutputUtil.outputFileError(FileoutputUtil.CommandEx_Log, e);
                            }
                            if (ret > 0 && c.getPlayer() != null) { //incase d/c after command or something
                                if (c.getPlayer().isGM()) {
                                    logCommandToDB(c.getPlayer(), line, "gmlog");
                                } else {
                                    logCommandToDB(c.getPlayer(), line, "internlog");
                                }
                            }
                        } else {
                            sendDisplayMessage(c, "?????????????????????????????????", type);
                        }
                        return true;
                    }
                }
            }
        }

        if (dropUnFound) {
            sendDisplayMessage(c, mapleHelp, type);
            if (!searchHelp.isEmpty()) {
                sendDisplayMessage(c, searchHelp, type);
            }
            return true;
        }
        return false;
    }

    public static void logCommandToDB(MapleCharacter player, String command, String table) {
        PreparedStatement ps = null;
        try {
            ps = DatabaseConnection.getConnection().prepareStatement("INSERT INTO " + table + " (cid, command, mapid) VALUES (?, ?, ?)");
            ps.setInt(1, player.getId());
            ps.setString(2, command);
            ps.setInt(3, player.getMap().getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            FileoutputUtil.outputFileError(FileoutputUtil.CommandEx_Log, ex);
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
            }
        }
    }

    public static String getCommandsForLevel(int level) {
        String commandlist = "";
        for (int i = 0; i < commandList.get(level).size(); i++) {
            commandlist += commandList.get(level).get(i);
            if (i + 1 < commandList.get(level).size()) {
                commandlist += ", ";
            }
        }
        return commandlist;
    }

    public static HashMap<Integer, ArrayList<String>> getCommandList() {
        return commandList;
    }
}
