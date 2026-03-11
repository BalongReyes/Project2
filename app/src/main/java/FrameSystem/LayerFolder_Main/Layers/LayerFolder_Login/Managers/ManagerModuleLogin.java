
package FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Managers;

import ConsoleSystem.Console;
import DatabaseSystem.AccountsData.AccountsDataHandler;
import DatabaseSystem.AccountsData.AccountsDataTable;
import EventSystem.Listeners.MouseClickedAdaptor;
import FrameSystem.LayerFolder_Main.Components.LayerMain;
import FrameSystem.LayerFolder_Main.Layers.LayerFolder_Login.Module.ModuleLogin;
import MainSystem.Manager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class ManagerModuleLogin extends Manager{
    
    public static ModuleLogin moduleLogin;

    public static void initDefault(ModuleLogin initModuleLogin){
        moduleLogin = initModuleLogin;
        
        resetUI();
        frame.layerMain_Login.addLayeredPanelShowListener(evt -> {
            resetUI();
            moduleLogin.loginUsernameField.requestFocus();
        });
        
        moduleLogin.loginButton.addMouseListener((MouseClickedAdaptor) evt -> {
            loginAccount();
        });
        
        MouseListener m = (MouseClickedAdaptor) evt -> {
            logoutAccount();
        };
        
        moduleLogin.loginUsernameField.addActionListener(evt -> {
            moduleLogin.loginPasswordField.requestFocus();
        });
        moduleLogin.loginPasswordField.addActionListener(evt -> {
            loginAccount();
        });
        
        moduleLogin.loginUsernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt){
                if(!moduleLogin.loginUsernameField.getText().isEmpty()){
                    moduleLogin.loginError1.setVisible(false);
                }
            }
        });
        moduleLogin.loginPasswordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent evt){
                if(moduleLogin.loginPasswordField.getPassword().length != 0){
                    moduleLogin.loginError2.setVisible(false);
                }
            }
        });
    }
    
    private static void resetUI(){
        moduleLogin.loginError1.setVisible(false);
        moduleLogin.loginError2.setVisible(false);
        moduleLogin.loginError3.setVisible(false);
        moduleLogin.loginUsernameField.setText("");
        moduleLogin.loginPasswordField.setText("");
        moduleLogin.loginPasswordField.showPassword(false);
    }
    
// Methods ===================================================================================================

    private static AccountsDataTable accountLoggedIn = null;

    public static AccountsDataTable getAccountLoggedIn(){
        return accountLoggedIn;
    }

    public static void updateAccountLoggedIn(AccountsDataTable accountLoggedIn){
        if(!ManagerModuleLogin.accountLoggedIn.idEquals(accountLoggedIn.getId())) return;
        ManagerModuleLogin.accountLoggedIn = accountLoggedIn;
    }
    
    public static boolean isLoggedIn(){
        return accountLoggedIn != null;
    }
    
// -----------------------------------------------------------------------------------------------------------
    
    public static void logoutAccount(){
        accountLoggedIn = null;
        LayerMain.showLayer(frame.layerMain_Login);
        moduleLogin.loginUsernameField.requestFocus();
    }
    
    public static void loginAccount(){
        boolean insufficientInput = false;
        
        if(moduleLogin.loginUsernameField.getText().isEmpty()) insufficientInput = true;
        moduleLogin.loginError1.setVisible(moduleLogin.loginUsernameField.getText().isEmpty());
        
        if(moduleLogin.loginPasswordField.getPassword().length == 0) insufficientInput = true;
        moduleLogin.loginError2.setVisible(moduleLogin.loginPasswordField.getPassword().length == 0);
        
        if(insufficientInput){
            moduleLogin.loginError3.setVisible(false);
            return;
        }
        
        AccountsDataTable accountSuccessLogin = null;
        try{
            accountSuccessLogin = AccountsDataHandler.loginAccount(moduleLogin.loginUsernameField.getText(), moduleLogin.loginPasswordField.getPassword());
        }catch(SQLException e){
            Console.errorOut("Login error", e);
            moduleLogin.offlineMode();
        }
        if(accountSuccessLogin != null){
            accountLoggedIn = accountSuccessLogin;
            LayerMain.showLayer(frame.layerMain_Home);
            resetUI();
        }else{
            moduleLogin.loginError3.setVisible(true);
        }
    }
    
}
