package handling;

import tools.EncodingDetect;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public enum RecvPacketOpcode implements WritableIntValueHolder {

	 // 未知 [01 00 00 00 00 00 00 00 00]
    STRANGE_DATA(0x66),
    // 客戶端驗證[完成]
    CLIENT_HELLO(0x67),
    
    // 0x68
    // 密碼驗證[完成]
    LOGIN_PASSWORD(0x69),
    // 角色選單[完成]
    CHARLIST_REQUEST(0x6A),
//    FUSION_ANVIL(0x94),
    
    // 0x6B
    // 0x6C
    
    // 建立角色驗證[完成](189)
    CREATE_CHAR_AUTH_REQUEST(0x6D),
    // 玩家登入[完成]
    PLAYER_LOGGEDIN(0x6E),
    // 選擇角色[完成]
    CHAR_SELECT(0x6F),
    
    // 0x70
    // 0x71
    
    // 伺服器選單回覆
    SERVERLIST_REQUEST(0x72),
    // 自動登入轉向
    LOGIN_REDIRECTOR(0x73),
    // 檢查角色名稱[完成]189
    CHECK_CHAR_NAME(0x74),
   
    //建立角色 189
    //CREATE_VERIFY(0x7D),
    // 建立角色[完成]189
    CREATE_CHAR(0x7D),
    // 50等角色卡角色建立
    CREATE_LV50_CHAR(0x75),
    // 建立終極冒險家 189
    CREATE_ULTIMATE(0x7F),//(0x7F),
    // 刪除角色189
    DELETE_CHAR(0x80),
    
    // 客戶端錯誤[完成]
    CLIENT_FEEDBACK(0x86),//(0x86),
    
    // 打工系统
    PART_TIME_JOB(0x90),//(0x90),
    // 角色卡
    CHARACTER_CARD(0x91),//(0x91),
    // 未知
    ENABLE_LV50_CHAR(0x92),
    
    // 0x93
    // 0x94
    
    // Pong[完成]-189
    PONG(0x95),
    
    // 0x96
    
    // 客戶端錯誤【[ Name: %s, Job: %d, Field: %d, World: %d, Channel: %d ]\r\n】[完成]
    CLIENT_ERROR(0x97),
    
    // 0x98
    // 0x99
    GET_UNK(0x99),//對不起來 
    // 0x9A
    // 0x9B
    // 0x9C
    // 0x9D
    
    // 選擇性別 189
    SET_GENDER(0x9E),
    // 伺服器狀態 189
    SERVERSTATUS_REQUEST(0xA0),
    
    // 0xA0
    
    // 背景驗證[完成]//0xA1 189
    GET_SERVER(0xA1),
    
    // 0xA2
    // 0xA3
    // 0xA4
    // 0xA5

    // 客戶端開始(顯示視窗)[完成] 189
    CLIENT_START(0x00A6),
   // 申請變更角色名稱[完成]
    APPLY_CHANGE_CHAR_NAME(0xA7),//(0xA7),
    
    // 0xA8
    // 0xA9
    
    // 變更地圖[完成]
    CHANGE_MAP(0xAA),//(0xAA),189
    // 變更頻道[完成]
    CHANGE_CHANNEL(0xAB),//(0xAB),189
    
    // 0xAC
    // 0xAD
    // 0xAE
    
    // 購物商城[完成]
    ENTER_CASH_SHOP(0xAF),//(0xAF),
    // PvP開始
    ENTER_PVP(0xB0),//(0xB0),
    
    // 0xB1
    
    // 阿斯旺開始
    ENTER_AZWAN(0xB2),//(0xB2),
    // 阿斯旺活動
    ENTER_AZWAN_EVENT(0xB3),//(0xB3),
    // 離開阿斯旺
    LEAVE_AZWAN(0xB4),//(0xB4),
    // PvP隊伍
    ENTER_PVP_PARTY(0xB5),//(0xB5),
    // 離開PvP
    LEAVE_PVP(0xB6),//(0xB6),
    
    // 0xB7
    // 0xB8
    
    // 玩家移動[完成]
    MOVE_PLAYER(0xB9),//(0xB9),
    // 取消椅子[完成]
    CANCEL_CHAIR(0xBA),//(0xBA),
    // 使用椅子[完成]
    USE_CHAIR(0xBB),//(0xBB),
    
    // 0xBC
    
    // 近距離攻擊[完成]
    CLOSE_RANGE_ATTACK(0xBD),//(0xBD),
    // 遠距離攻擊[完成]
    RANGED_ATTACK(0xBE),//(0xBE),
    // 魔法攻擊[完成]
    MAGIC_ATTACK(0xBF),//(0xBF),
    // 被動攻擊(抗壓...)
    PASSIVE_ATTACK(0xC0),//(0xC0),
    //到這裡
    // 0xC1
    // 0xC2
    
    // 角色受傷[完成]
    TAKE_DAMAGE(0xCF),//(0xC3),
    // PvP攻擊
    PVP_ATTACK(0xD0),//(0xC4),
    // 普通聊天[完成]
    GENERAL_CHAT(0xD1),//(0xC5),
    // 關閉黑板
    CLOSE_CHALKBOARD(0xD2),//(0xC6),
    // 臉部情緒
    FACE_EXPRESSION(0xD3),//(0xC7),
    // 機器人臉部情緒
    FACE_ANDROID(0xD4),//(0xC8),
    // 使用物品效果
    USE_ITEMEFFECT(0xD5),//(0xC9),
    // 使用原地復活
    WHEEL_OF_FORTUNE(0xD6),//(0xCA),
    // 使用稱號效果
    USE_TITLE(0xD7),//(0xCB),
    
    // 0xCC
    
    // 變更天使破壞者外觀
    ANGELIC_CHANGE(0xDA),//(0xCD),
    
    // 0xCE
    // 0xCF
    // 0xD0
    // 0xD1
    // 0xD2
    // 0xD3
    // 0xD4
    // 0xD5
    
    // Npc交談[完成]
    NPC_TALK(0xE3),//(0xD6),
    
    // 0xD7
    
    // Npc詳細交談[完成]
    NPC_TALK_MORE(0xE5),//(0xD8),
    // Npc商店[完成]
    NPC_SHOP(0xE6),//(0xD9),
    // 倉庫
    STORAGE(0xE7),//(0xDA),
    // 精靈商人
    USE_HIRED_MERCHANT(0xE8),//(0xDB),
    // 精靈商人物品
    MERCH_ITEM_STORE(0xE9),//(0xDC),
    // 宅配操作
    PACKAGE_OPERATION(0xEA),//(0xDD),
    // 取消開放通道
    MECH_CANCEL(0xEB),//(0xDE),
    
    // 0xDF
    // 0xE0
    
    // 寒冰迅移[完成]
    SPAWN_SPECIAL(0xEE),//(0xE1),
    
    // 0xE2
    // 0xE3
    
    
    // 智慧貓頭鷹(5230000)
    OWL(0xF1),//(0xE4),
    // 智慧貓頭鷹購買
    OWL_WARP(0xF2),//(0xE5),
    
    // 0xE6
    
    // 管理員商店
    ADMIN_SHOP(0xF4),//(0xE7),
    // 向上整理[完成]
    ITEM_SORT(0xF5),//(0xE8),
    // 種類排序[完成]
    ITEM_GATHER(0xF6),//(0xE9),
    // 物品移動[完成]
    ITEM_MOVE(0xF7),//(0xEA),
    
    // 0xEB【輸入觀戰板內容】

    // 移動道具至背包欄位
    MOVE_BAG(0xF8),//(0xEC),
    // 背包道具至道具欄位
    SWITCH_BAG(0xF9),//(0x7FFF),
    
    // 0xEE
    
    // 使用物品[完成]
    USE_ITEM(0xFC),//(0xEF),
    // 取消物品效果
    CANCEL_ITEM_EFFECT(0xFD),//(0xF0),
    
    // 0xF1
    
    // 使用召喚包(2100017)
    USE_SUMMON_BAG(0xFF),//(0xF2),
    // 使用寵物食品
    PET_FOOD(0x0100),//(0xF3),
    // 提神飲料
    USE_MOUNT_FOOD(0x0101),//(0xF4),
    // 使用腳本物品
    USE_SCRIPTED_NPC_ITEM(0x0102),//(0xF5),
    // 使用製作書
    USE_RECIPE(0x0103),//(0xF6),
    
    // 0xF7
    // 0xF8
    // 0xF9
    
    // 使用商城道具
    USE_CASH_ITEM(0x0104),//(0xFA),
    // 使用附加潛能印章
    USE_ADDITIONAL_ITEM(0x0105),//(0xFB),
    // 是否允許寵物拾取道具
    ALLOW_PET_LOOT(0x0106),//(0xFC),
    // 是否允許寵物自動餵食
    ALLOW_PET_AOTO_EAT(0x0107),//(0xFD),
    // 使用捕捉道具
    USE_CATCH_ITEM(0x010A),//(0xFE),
    
    // 0xFF
    // 0x100
    
    // 使用技能書
    USE_SKILL_BOOK(0x010B),//(0x101),
    
    // 0x102
    // 0x103
    // 0x104
    
    // 經驗瓶(2230000)
    USE_EXP_POTION(0x0110),//(0x105),
    
    // 0x106
    // 0x107
    // 0x108
    // 0x109
    // 0x10A
    
    // 智慧貓頭鷹開始搜索
    USE_OWL_MINERVA(0x0114),//(0x110),
    // 使用瞬移之石
    USE_TELE_ROCK(0x0115),//(0x111),
    // 使用回家卷軸[完成]
    USE_RETURN_SCROLL(0x0116),//(0x10D),
    // 移動至梅斯特鎮
    MOVE_ARDENTMILL(0x0117),//(0x10E),
    // 使用卷軸
    USE_UPGRADE_SCROLL(0x0118),//(0x10F),
    // 使用卷軸保護卡(5064300)
    USE_FLAG_SCROLL(0x0119),//(0x110),
    // 使用裝備強化卷軸
    USE_EQUIP_SCROLL(0x011A),//(0x111),
    // 使用潛能賦予卷軸
    USE_POTENTIAL_SCROLL(0x011E),//(0x112),
    // 使用附加潛在能力賦予卷軸
    USE_BONUS_POTENTIAL_SCROLL(0x011F),//(0x113),
    // 使用烙的印章(2049500)
    USE_CARVED_SEAL(0x0120),//(0x114),
    // 使用奇怪的方塊(2710000)
    USE_CRAFTED_CUBE(0x0121),//(0x115),
    
    // 0x11B
    
    // 靈魂卷軸
    USE_SOUL_ENCHANTER(0x0122),//(0x11A),
    // 靈魂寶珠
    USE_SOUL_SCROLL(0x0123),//(0x11B),
    // 咒語的痕跡[完成]
    EQUIPMENT_ENCHANT(0x0124),//(0x11C),
    
    // +0x2
    
    // 使用背包[完成]
    USE_BAG(0x0127),//(0x11E),
    // 使用放大鏡[完成]
    USE_MAGNIFY_GLASS(0x0128),//(0x11F),
    
    // +0x7
    
    // 使用能力點數[完成]
    DISTRIBUTE_AP(0x012F),//(0x126),
    // 自動分配能力點數[完成]
    AUTO_ASSIGN_AP(0x0130),//(0x127),
    
    // +0x2
    
    // 自動恢復HP/MP[完成]
    HEAL_OVER_TIME(0x0132),//(0x129),
    
    // 0x12A [Int][Long][Short][Short]
    // 0x12B

    // 使用技能點數[完成]
    DISTRIBUTE_SP(0x0135),//(0x12C),
    // 角色使用技能[完成]
    SPECIAL_SKILL(0x0136),//(0x12D),
    // 取消輔助效果[完成]
    CANCEL_BUFF(0x0137),//(0x12E),
    // 技能效果[完成]
    SKILL_EFFECT(0x0138),//(0x12F),
    // 楓幣掉落[完成]
    MESO_DROP(0x0139),//(0x130),
    // 添加名聲
    GIVE_FAME(0x013A),//(0x131),
    
    // 0x132
    
    // 角色信息[完成]
    CHAR_INFO_REQUEST(0x013C),//(0x133),
    // 召喚寵物[完成]
    SPAWN_PET(0x013D),//(0x134),
    
    // 0x135
    
    // 取消異常效果
    CANCEL_DEBUFF(0x013F),//(0x136),
    // 腳本地圖
    CHANGE_MAP_SPECIAL(0x0140),//(0x137),
    // 使用時空門
    USE_INNER_PORTAL(0x0141),//(0x138),
    
    // 0x139
    
    // 使用瞬移之石
    TROCK_ADD_MAP(0x0142),//(0x13A),
    // 使用測謊機
    LIE_DETECTOR(0x0144),//(0x13B),
    // 測謊機技能
    LIE_DETECTOR_SKILL(0x0145),//(0x13C),
    // 確認測謊機驗證碼 
    LIE_DETECTOR_RESPONSE(0x0146),//(0x13D),
    // 重新整理測謊機驗證碼
    LIE_DETECTOR_REFRESH(0x0147),//(0x13E),
    // 舉報玩家
    REPORT(0x0148),//(0x13F),
    // 任務操作
    QUEST_ACTION(0x0149),//(0x140),
    // 重新領取勳章
    REISSUE_MEDAL(0x014A),//(0x141),
    // 輔助效果回應
    BUFF_RESPONSE(0x014B),//(0x142),
    
    // 0x143
    // 0x144
    // 0x145
    // 0x146
    // 0x147
    
    // 技能組合[完成]
    SKILL_MACRO(0x0151),//(0x148),
    
    // 0x149
    
    // 獎勵道具
    REWARD_ITEM(0x0153),//(0x14A),
    // 鍛造技能
    ITEM_MAKER(0x0156),//(0x14B),
    // 全部修理(勇士之村(辛德))
    REPAIR_ALL(0x0157),//(0x14C),
    // 裝備修理
    REPAIR(0x0158),//(0x14D),
    
    // 0x14E
    // 0x14F
    
    // 請求跟隨()
    FOLLOW_REQUEST(0x015C),//(0x150),
    
    // 0x151
    // 0x152
    // 0x153
    // 0x154
    
    // 組隊任務獎勵
    PQ_REWARD(0x015E),//(0x155),
    // 請求跟隨回覆
    FOLLOW_REPLY(0x015F),//(0x156),
    // 自動跟隨回覆()
    AUTO_FOLLOW_REPLY(0x0160),//(0x157),
    // 能力值信息[完成]
    PROFESSION_INFO(0x0161),//(0x158),
    // 使用培養皿[完成]
    USE_POT(0x0162),//(0x159),
    // 清理培養皿[完成]
    CLEAR_POT(0x0163),//(0x15A),
    // 餵食培養皿[完成]
    FEED_POT(0x0164),//(0x15B),
    // 治癒培養皿[完成]
    CURE_POT(0x0165),//(0x15C),
    // 培養皿獎勵[完成]
    REWARD_POT(0x0166),//(0x15D),
    // 阿斯旺復活
    AZWAN_REVIVE(0x0167),//(0x15E),
    
    // 0x15F
    
    // 使用髮型卷[2540000]
    USE_COSMETIC(0x0168),//(0x160),
    // DF連擊[意志之劍取消]
    DF_COMBO(0x011E),//(0x161),
    // 神之子狀態轉換
    ZERO_STAT_CHANGE(0x016A),
    // 神之子
    ZERO_CLOTHES(0x016B),
    // 使用能力傳播者
    INNER_CIRCULATOR(0x016E),
    // PvP重生
    PVP_RESPAWN(0x011B),//(2),
    // 管理員聊天
    ADMIN_CHAT(0x0121),//(0x176),
    // 隊伍聊天
    PARTYCHAT(0x0183),//(0x177),
    // 悄悄話[完成]
    COMMAND(0x0185),//(0x178),
    // 聊天招待[完成]
    MESSENGER(0x0186),//(0x179),
    // 玩家互動
    PLAYER_INTERACTION(0x0187),//(0x17A),
    // 隊伍操作[完成]
    PARTY_OPERATION(0x0188),//(0x17B),
    // 接受/拒絕組隊邀請
    DENY_PARTY_REQUEST(0x0189),//(0x17C),
    // 允許組隊邀請
    ALLOW_PARTY_INVITE(0x018A),//(0x17D),
    // 建立遠征隊
    EXPEDITION_OPERATION(0x018B),//(0x17E),
    // 遠征隊搜尋
    EXPEDITION_LISTING(0x018C),//(0x17F),
    // 公會操作[完成]
    GUILD_OPERATION(0x018F),//(0x181),
    // 拒絕公會邀請
    DENY_GUILD_REQUEST(0x0190),
    // 申請加入公會
    JOIN_GUILD_REQUEST(0x0191),
    // 取消加入公會
    JOIN_GUILD_CANCEL(0x0192),
    // 允許加入公會邀請
    ALLOW_GUILD_JOIN(0x0193),
    // 拒絕加入公會邀請
    DENY_GUILD_JOIN(0x0194),//(2),
    // 管理員指令
    ADMIN_COMMAND(0x0195),//(0x183),
    // 管理員指令
    ADMIN_COMMAND2(0x0196),//(0x184),
    // 管理員日誌
    ADMIN_LOG(0x0197),//(0x185),
    // 好友操作[完成]
    BUDDYLIST_MODIFY(0x0198),//(0x18C),
    
    // 0x18D
    // 0x18E
    // 0x18F
    // 0x190
    
    // 訊息操作
    NOTE_ACTION(0x019E),//(0x191),
    
    // 0x192
    
    // 使用時空門
    USE_DOOR(0x019F),//(0x193),
    // 使用開放通道
    USE_MECH_DOOR(0x01A0),//(0x194),
    
    // 0x195
    
    // 變更鍵盤設置[完成]
    CHANGE_KEYMAP(0x01A2),//(0x196),
    // 猜拳遊戲
    RPS_GAME(0x01A3),//(0x197),
    // 戒指操作
    RING_ACTION(0x01A4),//(0x198),
    // 結婚操作
    WEDDING_ACTION(0x01A5),//(0x199),
    // 公會聯盟操作
    ALLIANCE_OPERATION(0x01A6),//(0x19A),
    // 拒絕公會聯盟邀請
    DENY_ALLIANCE_REQUEST(0x01A7),//(0x19B),
    // 與 狂狼/骑士团 嚮導時召喚的NPC對話
    CYGNUS_SUMMON(0x01A8),//(0x19C),
    
    // 0x19D
    
    // 狂郎勇士連擊
    ARAN_COMBO(0x01AA),//(0x19E),
    // 怪物CRC Key改變回傳
    MONSTER_CRC_KEY(0x01AC),//(0x19F),
    // 製作道具完成[完成]
    CRAFT_DONE(0x01AD),//(0x1B2),
    // 製作道具效果[完成]
    CRAFT_EFFECT(0x01AE),//(0x1B3),
    // 製作道具開始[完成]
    CRAFT_MAKE(0x01AF),//(0x1B4),
    
    // 0x1A3
    // 0x1A4
    // 0x1A5
    // 0x1A6
    // 0x1A7
    // 0x1A8
    // 0x1A9
    
    // 變更房間[完成]
    CHANGE_ROOM_CHANNEL(0x01C7),//(0x1B9),
    
    // 0x1BA
    
    // 選擇技能
    CHOOSE_SKILL(0x01C8),//(0x1BB),
    // 技能竊取
    SKILL_SWIPE(0x01C9),//(0x1BC),
    // 檢視技能
    VIEW_SKILLS(0x01CA),//(0x1BD),
    // 撤銷偷竊技能
    CANCEL_OUT_SWIPE(0x01CB),//(0x1BE),
    
    // 0x1BF
    // 0x1C0
    // 0x1C1
    // 0x1C2
    // 0x1C3
    // 0x1C4
    
    // 釋放意志之劍[完成]
    RELEASE_TEMPEST_BLADES(0x01C5),//(0x1C5),
    
    // 0x1C6
    // 0x1B7
    // 0x1B8
    // 0x1B9
    // 0x1BA
    // 0x1BB
    
    // 更新超級技能[完成]
    UPDATE_HYPER(0x01DA),//(0x1CC),
    // 重置超級技能[完成]
    RESET_HYPER(0x01DB),//(0x1CD),
    // 被怪物抓到
    MONSTER_BAN(0x01DE),//(0x1C8),
    // 返回選角界面[完成]
    BACK_TO_CHARLIST(0x01EC),//(0x1E0),
    // 創建角色跟刪除角色輸入的驗證碼
    SECURITY_CODE(0x01EE),//(0x1EF),
    // 更新烈焰溜溜球個數
    PINKBEAN_YOYO_REQUEST,
    // 快速移動(非打開NPC)
    QUICK_MOVE_SPECIAL(0x01EF),//(0x1EA),
    // 幸運怪物
    LUCKY_LUCKY_MONSTORY(0x01F0),//(0x1F4),
    // 活動卡片
    EVENT_CARD(0x01F1),//(0x1F9),
    // 神之子鏡子世界地圖傳送
    ZERO_QUICK_MOVE(0x01F2),//(0x1FB),
    
    // 開啟活動列表[完成]
    OPEN_EVENT_LIST(0x01F3),//(0x201),
    
    // 凱撒快速鍵[完成]
    KAISER_QUICK_KEY(0x01F5),//(0x20E),
    
    // 黑名單[完成]
    BLACK_LIST(0x021B),//(0x21C),
    
    // 0x207
    // 0x208
    
    // 賓果189
    BINGO(0x0212),//(0x21e),
    // 燃燒計畫189
    COMBUSTION_PROJECT(0x0220),//(0x21f),
    // 變更角色順序[完成]189
    CHANGE_CHAR_POSITION(0x0221),//(0x220),
    // 創角進入遊戲[完成]189
    CREATE_CHAR_SELECT(0x0222),//(0x221),
    
    // 寵物移動[完成]189
    MOVE_PET(0x0225),//(0x225),
    // 寵物說話[完成]
    PET_CHAT(0x0226),//(0x226),
    // 寵物指令[完成]
    PET_COMMAND(0x0226),//(0x227),
    // 寵物拾取[完成]
    PET_LOOT(0x0227),//(0x228),
    // 寵物自動吃藥[完成]
    PET_AUTO_POT(0x0228),//(0x229),
    // 寵物_除外道具[完成]
    PET_IGNORE(0x0229),//(0x22A),
    // 寵物自動吃食品[完成]
    PET_AUTO_FOOD(0x022A),//(0x22B),
    // 花狐移動
    MOVE_HAKU(0x022C),//(0x22C),
    // 花狐動作(包括變身)
    HAKU_ACTION(0x022D),//(0x22D),
    // 影朋花狐使用輔助技能
    HAKU_USE_BUFF(0x022E),//(0x22E),
    // 召喚獸移動[完成]
    MOVE_SUMMON(0x023A),//(0x236),
    // 召喚獸攻擊[完成]
    SUMMON_ATTACK(0x023B),//(0x237),
    // 召喚獸傷害[完成]
    DAMAGE_SUMMON(0x023C),//(0x238),
    // 召喚獸技能[完成]
    SUB_SUMMON(0x023D),//(0x239),
    // 召喚獸移除[完成]
    REMOVE_SUMMON(0x022F),//(0x23A),
    // 龍神移動
    MOVE_DRAGON(0x0244),//(0x23B),
    // 使用物品任務
    USE_ITEM_QUEST(0x0247),//(0x23C),
    // 機器人移動
    MOVE_ANDROID(0x0248),//(0x23D),
    // 機器人情感回覆(176.Done)
    ANDROID_EMOTION_RESULT(0x0249),//(0x23E),
    // 更新任務
    UPDATE_QUEST(0x0250),//(0x23F),
    // 任務物品
    QUEST_ITEM(0xFFFF),//(0x240),
    
    // 0x23E
    // 0x23F
    // 0x240
    
    // 快速欄按鍵[完成]
    QUICK_SLOT(0x0250),//(0x24B),
    // 按下按鈕
    BUTTON_PRESSED(0x0251),
    
    // 0x24D
    
    // 操控角色完成回覆
    DIRECTION_COMPLETE(0x026D),//(0x24E),
    
    // 0x24F
    // 0x250
    
    // 程序清單
    SYSTEM_PROCESS_LIST(0x0244),//(0x251),
    
    // 0x252
    
    // 神之子-開始強化
    ZERO_SCROLL_START(0x026E),//(0x253),
    // 神之子-武器潛在能力
    ZERO_WEAPON_ABILITY(0x0273),//(0x254),
    // 神之子-武器介面
    ZERO_WEAPON_UI(0x026F),//(0x255),
    // 神之子-與精靈對話
    ZERO_NPC_TALK(0x0270),//(0x256),
    // 神之子-使用卷軸
    ZERO_WEAPON_SCROLL(0x0271),//(0x257),
    // 神之子-武器成長
    ZERO_WEAPON_UPGRADE(0x0272),//(0x258),
    // 神之子-武器成長
    ZERO_WEAPON_UPGRADE_START(0x0273),//(0x259),
    // 讀取角色成功
    LOAD_PLAYER_SCCUCESS(0x0276),//(0x25A),
    
    // 0x25B
    // 0x25C
    // 0x25D
    // 0x25E
    // 0x25F
    
    // 箭座控制[完成]
    ARROW_BLASTER_ACTION(0x0279),//(0x260),
    
    // 0x261
    // 0x262
    // 0x263
    // 0x264
    // 0x265
    // 0x266
    // 0x267
    // 0x268
    // 0x269
    // 0x26A
    // 0x26B
    // 0x26C
    // 0x26D
    // 0x26E
    // 0x26F
    // 0x270
    // 0x271
    
    // 遊戲嚮導[完成]
    GUIDE_TRANSFER(0x027A),//(0x276),
    
    // 0x273
    // 0x274
    // 0x275
    // 0x276
    // 0x277
    // 0x278
    // 0x279
    // 0x27A
    // 0x27B
    // 0x27C
    // 0x27D
    
    // 新星世界[完成]
    SHINING_STAR_WORLD(0x030D),//(0x27C),
    // Boss清單[完成]
    BOSS_LIST(0x030E),//(0x27D),
    
    // +22
    
    // 公會佈告欄操作
    BBS_OPERATION(0x030F),//(0x2A1),
    
    // 0x2A2
    // 0x2A3
    // 0x2A4
    // 0x2A5
    
    // 離開遊戲 
    EXIT_GAME(0x02A6),//(0x2A6),
    
    // 0x29B
    
    // 潘姆音樂
    PAM_SONG(0x0311),//(0x2C0),
    // 新年賀卡(2160101)[完成]
    NEW_YEAR_CARD(0x3135),//(0x2C0),
    // 聖誕團隊藥水[2212000][完成]
    TRANSFORM_PLAYER(0x0312),//(0x2C1),
    
    // 0x2C2 [Long]

    // 進擊的巨人視窗選項反饋
    ATTACK_ON_TITAN_SELECT(0x0313),//(0x2C3),
    
    // 0x2C4
    
    // 拍賣系統[完成]
    ENTER_MTS(0x0314),//(0x2C5),
    // 使用兵法書(2370000)[完成]
    SOLOMON(0x0315),//(0x2C6),
    // 獲得兵法書經驗值[完成]
    GACH_EXP(0x0316),//(0x2C7),
    
    // +0xE
    
    // 使用強化任意門[完成]
    CHRONOSPHERE(0x0324),//(0x2D5),
    
    // +0x4
    
    // 使用閃耀方塊(5062017)[完成]
    USE_FLASH_CUBE(0x0328),//(0x2D9),
    
    // +0x31
    
    // 怪物移動[完成]
    MOVE_LIFE(0x033E),//(0x30A),
    // 怪物復仇[完成]
    AUTO_AGGRO(0x033F),//(0x30B),
    // 怪物自爆[完成]
    MONSTER_BOMB(0x0340),//(0x30C),
    
    // +0x18
    
    // Npc動作(包括說話)[完成]
    NPC_ACTION(0x0358),//(0x324),
    
    // +0x7
    
    // 拾取物品[完成]
    ITEM_PICKUP(0x035F),//(0x32B),
    
    // +0x3
    
    // 攻擊箱子[完成]
    DAMAGE_REACTOR(0x0362),//(0x32E),
    // 雙擊箱子[完成]
    TOUCH_REACTOR(0x0363),//(0x32F),
    
    // 0x330
    // 0x331
    // 0x332
    
    // 召喚分解機
    MAKE_EXTRACTOR(0x036A),//(0x333),
    // 玩家資料更新
    UPDATE_ENV(0x0367),//(0x334),
    
    // 0x335
    // 0x336
    
    // 滾雪球
    SNOWBALL(0x0380),//(0x337),
    // 向左擊飛
    LEFT_KNOCK_BACK(0x0381),//(0x338),
    // 玩家更新
    PLAYER_UPDATE(0x0382),//(0x34B),
    // 推薦隊員[完成]
    MEMBER_SEARCH(0x0383),//(0x34C),
    // 尋找隊伍[完成]
    PARTY_SEARCH(0x0385),//(0x34E),
    // 開始採集[完成]
    START_HARVEST(0x038A),//(0x353),
    // 停止採集[完成]
    STOP_HARVEST(0x038B),//(0x354),
    
    // 0x355
    // 0x356
    
    // 快速移動(開啟Npc)[完成]
    QUICK_MOVE(0x038E),//(0x357),
    // 採集符文輪
    TOUCH_RUNE(0x038F),//(0x358),
    // 取得符文
    USE_RUNE(0x0390),//(0x359),
    
    // 購物商城更新[完成]
    CS_UPDATE(0x041E),//(0x3E0),
    // 購買點數道具[完成]
    BUY_CS_ITEM(0x041F),//(0x3E1),
    // 使用兌換券
    COUPON_CODE(0x0420),//(0x3E2),
    // 購物商城送禮
    CS_GIFT(0x0423),//(0x3E3),
    // 儲存造型設計
    CASH_CATEGORY(0x0425),//(0x3E4),
    // 創建角色二次密碼認證[完成]
    CREATE_CHAR_AUTH(0x03F9),//(0x3EF),
    // 使用黃金鐵鎚
    GOLDEN_HAMMER(0x0408),//(0x3D6),
    // 黃金鐵鎚使用完成
    VICIOUS_HAMMER(0x0409),//(0x3D7),
    
    // 0x3D8
    
    // 使用白金鎚子
    PLATINUM_HAMMER(0x040B),//(0x3D9),
    
    // 戰鬥分析開始[完成]
    BATTLE_STATISTICS(0x040C),//(0x404),
    
    // 獲得獎勵
    REWARD(0x0452),//(0x41E),
    // 裝備特效開關
    EFFECT_SWITCH(0x045A),//(0x424),
    // 未知OPS，不繼續增加
    UNKNOWN,
    // 使用世界樹的祝福(2048500)
    USE_ABYSS_SCROLL,
    MONSTER_BOOK_DROPS,
    // General
    RSA_KEY,
    MAPLETV,
    CRASH_INFO,
    // Login
    GUEST_LOGIN,
    TOS,
    VIEW_SERVERLIST,
    REDISPLAY_SERVERLIST,
    CHAR_SELECT_NO_PIC,
    AUTH_REQUEST,
    VIEW_REGISTER_PIC,
    VIEW_SELECT_PIC,
    CLIENT_FAILED,
    ENABLE_SPECIAL_CREATION,
    CREATE_SPECIAL_CHAR,
    AUTH_SECOND_PASSWORD,
    WRONG_PASSWORD,
    ENTER_FARM,
    CHANGE_CODEX_SET,
    CODEX_UNK,
    USE_NEBULITE,
    USE_ALIEN_SOCKET,
    USE_ALIEN_SOCKET_RESPONSE,
    USE_NEBULITE_FUSION,
    TOT_GUIDE,
    GET_BOOK_INFO,
    USE_FAMILIAR,
    SPAWN_FAMILIAR,
    RENAME_FAMILIAR,
    PET_BUFF(0x013E),
    USE_TREASURE_CHEST,
    SOLOMON_EXP,    
    XMAS_SURPRISE,
    TWIN_DRAGON_EGG,
    YOUR_INFORMATION,
    FIND_FRIEND,
    PINKBEAN_CHOCO_OPEN,
    PINKBEAN_CHOCO_SUMMON,
    BUY_SILENT_CRUSADE,
    CASSANDRAS_COLLECTION,
    BUDDY_ADD,
    //HAKU_1D8,
    //HAKU_1D9,
    PVP_SUMMON,
    MOVE_FAMILIAR,
    TOUCH_FAMILIAR,
    ATTACK_FAMILIAR,
    REVEAL_FAMILIAR,
    FRIENDLY_DAMAGE,
    HYPNOTIZE_DMG,
    MOB_BOMB,
    MOB_NODE,
    DISPLAY_NODE,
    MONSTER_CARNIVAL,
    CLICK_REACTOR,
    CANDY_RANKING,
    COCONUT,
    SHIP_OBJECT(0x035E),
    PLACE_FARM_OBJECT,
    FARM_SHOP_BUY,
    FARM_COMPLETE_QUEST,
    FARM_NAME,
    HARVEST_FARM_BUILDING,
    USE_FARM_ITEM,
    RENAME_MONSTER,
    NURTURE_MONSTER,
    EXIT_FARM,
    FARM_QUEST_CHECK,
    FARM_FIRST_ENTRY,
    PYRAMID_BUY_ITEM,
    CLASS_COMPETITION,
    MAGIC_WHEEL,
    BLACK_FRIDAY,
    RECEIVE_GIFT_EFFECT,
    UPDATE_RED_LEAF,
    //Not Placed:
    SPECIAL_STAT,
    DRESSUP_TIME,
    OS_INFORMATION,
    LUCKY_LOGOUT,
    PONG2(0x02C2),
    PONG3(0x02C3),
    LICENSE_REQUEST(0x9D),
    OPEN_WORLD_MAP(0x032E),
    ICE_WALK_ATTACK(0xCE),
    USE_HURT_EFFECT(0xD8),
    UNKNOW_33(0xD9),
    RETURN_TO_BASE(0xEC),
    USE_SP_RESET(0x010C),
    USE_AP_RESET(0x010D),
    HEAL_OVER_POTENTIAL(0x0133),
    USE_REDUCER(0x016C),
    RESET_INNER(0x016D),
    GAIN_FORCE(0x011D),
    GUILD_搜索(0x018E),
    ARAN_COMBO1(0x01AB),
    UPDATE_INC_HYPER(0x01DC),
    RESET_INC_HYPER(0x01DD),
    CHAR_INFO(0x01ED),
    SKILL_UNKNOW(0x01F4),
    斗燃(0x021F),
    OPEN_KEYMAP(0x024F),
    UNKNOW_22(0x0267),
    爆破手填装弹药(0x02E3),
    MONSTER_OBJECT_ID(0x0331),
    KILL_MONSTER_升级杀(0x034E),
    XENON_POWER(0x042C),
    客户端进程列表(0x0255),
    MESSENGER_RANKING;

    private short code = -2;

    public void setValue(int code) {
        this.code = (short) code;
    }
    
    @Override
    public void setValue(short newval) {
        this.code = newval;
    }

    @Override
    public final short getValue() {
        return code;
    }

    private RecvPacketOpcode() {
        this.code = 0x7FFE;
    }

    private RecvPacketOpcode(final int code) {
        this.code = (short) code;
    }

    public static String nameOf(short value) {
        for (RecvPacketOpcode header : RecvPacketOpcode.values()) {
            if (header.getValue() == value) {
                return header.name();
            }
        }
        return "UNKNOWN";
    }

    public static boolean isSpamHeader(RecvPacketOpcode header) {
        switch (header.name()) {
            case "PONG":
            case "NPC_ACTION":
//            case "ENTER"":
//            case "CRASH_INFO":
//            case "AUTH_REQUEST":
//            case "SPECIAL_MOVE":
            case "MOVE_LIFE":
            case "MOVE_PLAYER":
            case "MOVE_ANDROID":
//            case "MOVE_DRAGON":
            case "MOVE_SUMMON":
//            case "MOVE_FAMILIAR":
//            case "MOVE_PET":
            case "AUTO_AGGRO":
//            case "CLOSE_RANGE_ATTACK":
//            case "QUEST_ACTION":
            case "HEAL_OVER_TIME":
//            case "CHANGE_KEYMAP":
//            case "USE_INNER_PORTAL":
//            case "MOVE_HAKU":
//            case "FRIENDLY_DAMAGE":
//            case "CLOSE_RANGE_ATTACK":
//            case "RANGED_ATTACK":
//            case "ARAN_COMBO":
//            case "SPECIAL_STAT":
//            case "UPDATE_HYPER":
//            case "RESET_HYPER":
//            case "ANGELIC_CHANGE":
//            case "DRESSUP_TIME":
            case "BUTTON_PRESSED":
            case "STRANGE_DATA":
            case "SYSTEM_PROCESS_LIST":
            case "PINKBEAN_YOYO_REQUEST":
                return true;
            default:
                return false;
        }
    }

    public static void loadValues() {
        Properties props = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("recvops.properties")) {
            props.load(new BufferedReader(new InputStreamReader(fileInputStream, EncodingDetect.getJavaEncode("recvops.properties"))));
        } catch (IOException ex) {
            InputStream in = RecvPacketOpcode.class.getClassLoader().getResourceAsStream("recvops.properties");
            if (in == null) {
                System.out.println("未讀取 recvops.properties 檔案, 使用內建 RecvPacketOpcode 列舉");
                return;
            }
            try {
                props.load(in);
            } catch (IOException e) {
                throw new RuntimeException("讀取 recvops.properties 檔案異常", e);
            }
        }
        ExternalCodeTableGetter.populateValues(props, values());
    }

    static {
        loadValues();
    }
}
