package MainSystem;

import DatabaseSystem.Database;

public class Main {

    public static boolean debugDataHandlerRefresh = true;
    public static boolean coloredOutput = true;

    public static SFrame frame;

    public static void main(String[] args) {
        Database.openConnection();

        frame = new SFrame();
        frame.setVisible(true);

        Manager.setDefault(frame);
        frame.initShowDefaultLayer();
        frame.setVisible(true);
    }

}
