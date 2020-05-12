import java.io.File;
import java.io.IOException;
import java.util.List;

public class Youtube {
    public static String url = "";
    public static String name = "";
    public static String type = "";
    public static String path = getCurrentDirectory();
    public static Boolean IsPlayList = false;
    static String downLoadMusic = "";

    public static void main(String[] args) {
        createFile("/download_music");
        Gui gui = new Gui("�۪� Youtube �U����");
        gui.setVisible(true);
    }

    public static void DownLoadOne() {
        // �U���榱
        String cmd = getCmd(Youtube.type);
        ExcuteCmd(cmd, "���}���`");
        moveFile(0);
    }

    public static void DownLoadPlayList() {
        // �U������M��
        if(Gui.fileName == null)
        {
            Gui.fileName = "playlist";
        }
        createFile("/download_music/"+Gui.fileName);
        String cmd = getCmd(Youtube.type);
        ExcuteCmd(cmd, "���}���`");
        moveFile(1);
    }

    private static void moveFile(int i) {
        // �����ɮ�
        List<String> music = FileReader.getFileList(path);
        for (int index = 0; index < music.size(); index++) {
            switch (i) {
                case 0:
                    String cmd_0 = "cmd /c  move  " + '"' + path + music.get(index) + '"' + " ./download_music";
                    downLoadMusic = "�w�U��   ------   " + music.get(index);
                    ExcuteCmd(cmd_0, "���ʥ��ѡA�Ф�ʲ����ɮ�");
                    break;
                case 1:
                    String cmd_1 = "cmd /c move " + '"' + path + music.get(index) + '"' + " ./download_music/"+Gui.fileName;
                    downLoadMusic += ("�w�U��   ------   " + music.get(index) + "\n");
                    ExcuteCmd(cmd_1, "���ʥ��ѡA�Ф�ʲ����ɮ�");
                    break;
            }
            if (music.get(index) == "") {
                return;
            }
            Gui.result.setText(downLoadMusic);
        }
        downLoadMusic = "";
    }

    public static void ExcuteCmd(String cmd, String errorMsg) {
        // ���������
        Process process;
        try {
            process = Runtime.getRuntime().exec(cmd);
            int done = process.waitFor();
            if (done != 0) {
                // �p�G�����`�h�X�N����
                process.destroy();
                Gui.result.setText(errorMsg);
                return;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String getCmd(String select) {
        // �гy cmd
        if (select.equals("mp3")) {
            return "cmd /c  youtube-dl --extract-audio --audio-format mp3 " + url;
        } else {
            return "cmd /c  youtube-dl -f mp4 " + url;
        }
    }

    public static void createFile(String file) {
        // �Ыظ�Ƨ�
        File f = null;
        f = new File(path + file);
        f.mkdir();
    }

    public static String getCurrentDirectory() {
        // ���o��e�ؿ�
        File now_directory = new File(".");
        String current_path = now_directory.getAbsolutePath().replaceAll("\\.", "");
        return current_path;
    }
}
