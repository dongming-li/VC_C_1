package Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.NoSuchElementException;
import java.util.Scanner;

import communication.ClientCommHttp;
import vc_c_1.statify.MainActivity;
import vc_c_1.statify.R;

import static vc_c_1.statify.MainActivity.getLm;

/**
 * Main dashboard for modules for a given activity.
 */
public class DashboardMain extends Fragment {
    /**
     * Temporary button for passing back and forth
     */
    Button tempButton;
    /**
     * Temporary context for passing back and forth
     */
    Context tempContext;
    /**
     * View that is saved from startup and used elsewhere
     */
    View v;
    /**
     * Button Text for setting and getting methods
     */
    static String btnText0;
    /**
     * Button Text for setting and getting methods
     */
    static String btnText1;
    /**
     * Button Text for setting and getting methods
     */
    static String btnText2;
    /**
     * Button Text for setting and getting methods
     */
    static String btnText3;
    /**
     * Button Text for setting and getting methods
     */
    static String btnText4;
    /**
     * Button Text for setting and getting methods
     */
    static String btnText5;
    /**
     * Local manager that is initialized and stored as instance variable easily
     */
    private static ClientSideDBManager lm;


    /**
     * Required default constructor
     */
    public DashboardMain() {}

    /**
     * When created, gets the locally saved button texts and sets the text and initiallizes the onclicklisteners
     * @param inflater Default fragment layout inflater
     * @param container Default ViewGroup container
     * @param savedInstanceState Unused save-state of fragment. Required by override.
     * @return The initialized inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_dashboard_main, container, false);
        tempContext = getActivity();

        TextView edit = (TextView)v.findViewById(R.id.DashText);
        edit.setText(MainActivity.getcurrentActivity());

        lm = new ClientSideDBManager(getActivity());

        String toParse= lm.pullLayout(MainActivity.getcurrentActivity());
        try {
            Scanner scan = new Scanner(toParse);
            scan.useDelimiter(",");



        if (scan.hasNext()){btnText0 = scan.next();} if(btnText0 == null){btnText0="+";}
        if (scan.hasNext()){btnText1 = scan.next();} if(btnText1 == null){btnText1="+";}
        if (scan.hasNext()){btnText2 = scan.next();} if(btnText2 == null){btnText2="+";}
        if (scan.hasNext()){btnText3 = scan.next();} if(btnText3 == null){btnText3="+";}
        if (scan.hasNext()){btnText4 = scan.next();} if(btnText4 == null){btnText4="+";}
        if (scan.hasNext()){btnText5 = scan.next();} if(btnText5 == null){btnText5="+";}
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }




        //declare the buttons and feed them into the initializer
        Button btn0 = (Button)v.findViewById(R.id.button10);   initializeButtons(btn0);    btn0.setText(btnText0);
        Button btn1 = (Button)v.findViewById(R.id.button11);   initializeButtons(btn1);    btn1.setText(btnText1);
        Button btn2 = (Button)v.findViewById(R.id.button12);   initializeButtons(btn2);    btn2.setText(btnText2);
        Button btn3 = (Button)v.findViewById(R.id.button13);   initializeButtons(btn3);    btn3.setText(btnText3);
        Button btn4 = (Button)v.findViewById(R.id.button14);   initializeButtons(btn4);    btn4.setText(btnText4);
        Button btn5 = (Button)v.findViewById(R.id.button15);   initializeButtons(btn5);    btn5.setText(btnText5);



        // Inflate the layout for this fragment
        return v;

    }

    /**
     * Initialize the default properties of the dashboard buttons
     * @param btn Button object to be initialized
     */
    private void initializeButtons(Button btn){
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openModule(view);
            }
        });
        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClick(view);
                return true;
            }
        });

    }

    /**
     * On longClick of a button, the method will determine if the button has anything there or not.
     * if it does contain text, then it will go into a dialog for changing it or removing it
     * if it does not contain text, it will go into a dialog for adding a new module.
     * @param view View object of the button
     */
    private void longClick(View view){
        String stringCheck = getResources().getString(R.string.plus_sign);
        tempButton = (Button) view;
        if(tempButton.getText().toString().equals(stringCheck)){
            addOrChangeModule();

            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(tempContext);
        builder.setTitle("Module Options")
                .setItems(R.array.dashboard_module_settings_active, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which){
                            case 0:     addOrChangeModule();         break;
                            case 1:     tempButton.setText(R.string.plus_sign);     break;
                            default:
                                break;
                        }


                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Method called when the user wants to add or change a module.
     * builds a dialog that gives options for making changes to the module. then calls updateLayout() to update the local storage
     */
    private void addOrChangeModule() {
        AlertDialog.Builder builder = new AlertDialog.Builder(tempContext);
        builder.setTitle("Select Module")
                .setItems(R.array.dashboard_module_selection, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String[] selectionOptions = getResources().getStringArray(R.array.dashboard_module_selection);
                        switch (which) {
                            case 0:     tempButton.setText(selectionOptions[0]);  break;
                            case 1:     tempButton.setText(selectionOptions[1]);  break;
                            case 2:     tempButton.setText(selectionOptions[2]);  break;
                            default:
                                break;
                        }

                        updateLayout();


                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * When button is selected, this method will look at the text of it and go through a switch statement to go to the correct fragment
     * that matches its text. Then the method will jump over to the selected fragment.
     * @param view View object of the button
     */
    public void openModule(View view){
        tempButton = (Button) view;
        String btnState = tempButton.getText().toString();

        // Changes onClick response based on button string value (aka its state)
        switch(btnState){
            case "Raw Data":
                getFragmentManager().beginTransaction().replace(R.id.fragment, new RawDataModuleFrag()).addToBackStack(null).commit();
                break;
            case "Calculated Data":
                CalculatedDataFragment calc = new CalculatedDataFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, calc).addToBackStack(null).commit();
                break;
            case "Graph" :
                GraphFragment graph = new GraphFragment();
                getFragmentManager().beginTransaction().replace(R.id.fragment, graph).addToBackStack(null).commit();
                break;
            default:
                addOrChangeModule();
                break;
        }
    }

    /**
     * This method will update the local storage with the new layouts.
     * It will scan all button texts and assemble a string version of it
     * then the method will update the local storage and server with the new layout
     */
    private void updateLayout(){
        Button btn0 = (Button)v.findViewById(R.id.button10);
        Button btn1 = (Button)v.findViewById(R.id.button11);
        Button btn2 = (Button)v.findViewById(R.id.button12);
        Button btn3 = (Button)v.findViewById(R.id.button13);
        Button btn4 = (Button)v.findViewById(R.id.button14);
        Button btn5 = (Button)v.findViewById(R.id.button15);

        String newLayout = "";
        newLayout += btn0.getText().toString() + ",";
        newLayout += btn1.getText().toString() + ",";
        newLayout += btn2.getText().toString() + ",";
        newLayout += btn3.getText().toString() + ",";
        newLayout += btn4.getText().toString() + ",";
        newLayout += btn5.getText().toString();


        ClientSideDBManager lm = getLm();
        lm.updateLayout(MainActivity.getcurrentActivity(), newLayout);

        ClientCommHttp c = new ClientCommHttp();
        String data = MainActivity.getCurrentUserID() + "%20" + MainActivity.getcurrentActivity() + "%20" + newLayout;
        try {
            c.sendData("updateLayout", data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}



















