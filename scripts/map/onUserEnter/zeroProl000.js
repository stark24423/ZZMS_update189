var status = -1;

function action(mode, type, selection) {
    if (mode == 0) {
        status--;
    } else {
        status++;
    }

    switch (ms.getMapId()) {        
        case 321000000:
            if (status == 0) {
                ms.changeMusic("Bgm33.img/NastyLiar");
                ms.getDirectionStatus(true);
                ms.EnableUI(1);
                ms.DisableUI(true);
                ms.gainItem(1003840, 1);
		ms.teachSkill(101120204, 0, 10); // �i���ɭ�����
		ms.teachSkill(101120104, 0, 10); // �i���H�a�r��
                ms.teachSkill(101110203, 0, 10); // �i���ۭ�������
                ms.teachSkill(101110200, 0, 10); // �i���ۭ������s
                ms.teachSkill(101110102, 0, 10); // �i���ۭ�
                ms.teachSkill(101100201, 0, 10); // �i���j�ۤ��b
                ms.teachSkill(101100101, 0, 10); // �i���Z�����Y
                ms.teachSkill(101000101, 0, 10); // �i���¤O�_��
            } else {
                ms.EnableUI(0);
                ms.dispose();
                ms.warp(321000000, 0);
            }
            break;
        default:
            ms.dispose();
    }
}