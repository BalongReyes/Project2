package FrameSystem.LoginGroup.Managers;

import java.sql.SQLException;

import ConsoleSystem.Console;
import DatabaseSystem.AccountsData.AccountsDataTable;
import DatabaseSystem.SettingsData.SettingsDataHandler;
import DatabaseSystem.SettingsData.SettingsDataTable;
import MainSystem.Manager;

public class ManagerLogin extends Manager {

    public static void initDefault() {
        resetUI();
    }

    private static void resetUI() {
    }

// Methods ===================================================================================================
    
    private static AccountsDataTable accountLoggedIn = null;

    public static AccountsDataTable getAccountLoggedIn() {
        return accountLoggedIn;
    }

    public static void updateAccountLoggedIn(AccountsDataTable accountLoggedIn) {
        if (!ManagerLogin.accountLoggedIn.idEquals(accountLoggedIn.getId())) {
            return;
        }
        ManagerLogin.accountLoggedIn = accountLoggedIn;
    }

    public static boolean isLoggedIn() {
        return accountLoggedIn != null;
    }

    public static SettingsDataTable getSettingsData() {
        return getSettingsData(false);
    }

    public static SettingsDataTable getSettingsData(boolean refresh) {
        try {
            return SettingsDataHandler.findDataByAccountId(accountLoggedIn.getId(), refresh);
        } catch (SQLException e) {
            Console.errorOut("Finding settings data error", e);
        }
        return null;
    }

}
