package vc_c_1.statify;

import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Scanner;

import authentication.*;
import communication.*;
import security.*;

import Fragments.FriendsMainFragment;
import Fragments.LeagueMainFragment;
import Fragments.ClientSideDBManager;
import Fragments.NewsfeedMainFragment;
import Fragments.SettingsMainFragment;
import Fragments.SoloFragment;

/**
 * Main activity for the application.
 * A fragment manager is used to switch out and replace the current active view on the activity.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The current sport the user is currently using
     */
    private static String currentActivity = "";

    /**
     * The userID of the current active user
     */
    private static String currentUserID = "";

    /**
     * The username of the current active user
     */
    private static String currentUsername = "";

    /**
     * Manages SQLite database
     */
    private static ClientSideDBManager lm;

    /**
     * Creates the interface for the registration functionality
     */
    private RegistrationInterface registration = new Registration(new User(), new BCryptHash());//dependency injection

    /**
     * Creates the interface for the login functionality
     */
    private LoginInterface login = new Login(new User(), new BCryptHash());//dependency injection


    /**
     * Controls the functionality of the bottom navigation bar
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment f = null;
            switch (item.getItemId()) {
                case R.id.navigation_solo:      f = new SoloFragment();             break;
                case R.id.navigation_leagues:   f = new LeagueMainFragment();       break;
                case R.id.navigation_newsfeed:  f = new NewsfeedMainFragment();     break;
                case R.id.navigation_friends:   f = new FriendsMainFragment();      break;
                case R.id.navigation_settings:  f = new SettingsMainFragment();     break;
                default:
                    return false;
            }
            getFragmentManager().beginTransaction().replace(R.id.fragment, f ).addToBackStack(null).commit();
            return true;
        }
    };

    /**
     * Creates the main activity view where all the fragment view are hosted
     * @param savedInstanceState Unused save state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        lm = new ClientSideDBManager(this);
        ArrayList<String> userData = new ArrayList<String>();
        userData = lm.getUserData();

        if(userData.size() == 0){
            this.openLoginDialog();
        } else {
            currentUserID = userData.get(0);
            getFragmentManager().beginTransaction().add(R.id.fragment, new SoloFragment()).addToBackStack(null).commit();
        }
    }

    public static String getcurrentActivity(){
        return currentActivity;
    }
    public static void setcurrentActivity(String newSportName){
        newSportName = newSportName.replace(" ", "");

        currentActivity = newSportName;
    }
    public static String getCurrentUserID() { return currentUserID; }
    public static String getCurrentUsername(){return currentUsername; }
    public static ClientSideDBManager getLm(){
        return lm;
    }


    /**
     * Manages the hardware back button press action
     */
    @Override
    public void onBackPressed(){
        int count = getFragmentManager().getBackStackEntryCount();

        if(count == 0){
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
    }

    /**
     * Opens the login dialog
     */
    public void openLoginDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(this.getLayoutInflater().inflate(R.layout.dialog_log_in, null));
        builder.setTitle("Log In")
                .setPositiveButton("Log In",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = Dialog.class.cast(dialog);

                        EditText usernameBox = (EditText) d.findViewById(R.id.username);
                        EditText passwordBox = (EditText) d.findViewById(R.id.password);
                        String username = usernameBox.getText().toString();
                        String password = passwordBox.getText().toString();
                        try {
                            if(!isValidSignOn(username, password)){
                                openLoginDialog();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        setTitle(username);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Register",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        openRegisterDialog();
                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });



        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Opens the account registration dialog
     */
    private void openRegisterDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(this.getLayoutInflater().inflate(R.layout.dialog_register_account, null));
        builder.setTitle("Register Account")
                .setPositiveButton("Register",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = Dialog.class.cast(dialog);

                        EditText usernameBox = (EditText) d.findViewById(R.id.username);
                        EditText passwordBox = (EditText) d.findViewById(R.id.password);
                        EditText emailBox = (EditText) d.findViewById(R.id.email);
                        EditText emailVerifyBox = (EditText) d.findViewById(R.id.emailVerify);
                        String username = usernameBox.getText().toString();
                        String password = passwordBox.getText().toString();
                        String email = emailBox.getText().toString();
                        String emailVerify = emailVerifyBox.getText().toString();

                        try {
                            if(!isValidSignOn(username, email, emailVerify, password)){
                                openRegisterDialog();
                            }
                            else
                                openLoginDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // Open up new dialog for account registration
                        openLoginDialog();

                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });



        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Login - Authenticates an existing user
     *
     * @param username User input username
     * @param password User input password
     * @return Return true if valid
     */
    private boolean isValidSignOn(String username, String password) throws Exception {


        lm.onUpgrade(null, 0,1);

        //**/currentUser = username;
        System.out.println("BEFORE LOGIN SERVER CALL BEFORE LOGIN SERVER CALL");//it's not even getting here
        int userID = login.userID(username, password);
        currentUserID = "" + userID;//current user becomes the UserID of the current user
        currentUsername = username;
        lm.updateUserData(Integer.toString(userID), password, 1);
        //**/boolean success = username.equals(password);
        boolean success = (userID != 0);

        if(success) {
            ClientCommHttp c = new ClientCommHttp();
            try {
                int rank = login.getUserInfo(userID);
                String refresh = c.sendData("refresh", currentUserID);
                Scanner scan = new Scanner(refresh);
                scan.useDelimiter(";;;");
                String layouts = scan.next();
                lm.updateLayouts(layouts);

                String calcData = scan.next();
                lm.updateCalculatedData(calcData);
                lm.updateUserData("" + userID, username, rank);


                scan.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            getFragmentManager().beginTransaction().add(R.id.fragment, new SoloFragment()).addToBackStack(null).commit();
        }

        return success;

    }


    /**
     * Registration - Creates a new user account
     *
     * @param username User input username
     * @param email User input email
     * @param emailVerify User input email (again for verification)
     * @param password User input password
     * @return Return true if new account is valid
     * @throws Exception
     */
    private boolean isValidSignOn(String username, String email, String emailVerify, String password) throws Exception {

        boolean success = registration.register(username, email, emailVerify, password);

        return success;
    }
}
