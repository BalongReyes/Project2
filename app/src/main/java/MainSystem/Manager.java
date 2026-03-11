package MainSystem;

import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Managers.ManagerModuleLogin;

public class Manager {

    protected static SFrame frame;

    public static void setDefault(SFrame frame) {
        Manager.frame = frame;
        
        ManagerModuleLogin.initDefault(frame.moduleLogin);
    }

}
