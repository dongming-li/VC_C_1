package Fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Scanner;

import communication.ClientCommHttp;
import vc_c_1.statify.MainActivity;
import vc_c_1.statify.R;

import static vc_c_1.statify.MainActivity.getLm;

/**
 * Manages the solo mode homepage. Displays the list of all the activities the user is tracking statistics for.
 */
public class SoloFragment extends Fragment {
    /**
     * temporary button that is used for passing in some methods
     */
    private Button tempButton;

    /**
     * Required empty constructor
     */
    public SoloFragment() { }

    /**
     * This method pulls the titles from local storage and scans through them and creates a string
     * all buttons on the page are initialized and their text is set as it goes down. Then the add layout button is initialized separately
     * @param inflater Default fragment layout inflater
     * @param container Default ViewGroup container
     * @param savedInstanceState Unused save-state of fragment. Required by override.
     * @return The initialized inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_solo, container, false);

        ClientSideDBManager lm = getLm();

        String titles = lm.pullTitles();
        System.out.println("Current Titles: " + titles);
        Scanner scan = new Scanner(titles);
        scan.useDelimiter(",");

        Button btn0 = (Button) v.findViewById(R.id.Solo0);
        if (scan.hasNext()) {
            btn0.setText(scan.next());
        }
        initializeButtons(btn0);
        Button btn1 = (Button) v.findViewById(R.id.Solo1);
        if (scan.hasNext()) {
            btn1.setText(scan.next());
        }
        initializeButtons(btn1);
        Button btn2 = (Button) v.findViewById(R.id.Solo2);
        if (scan.hasNext()) {
            btn2.setText(scan.next());
        }
        initializeButtons(btn2);
        Button btn3 = (Button) v.findViewById(R.id.Solo3);
        if (scan.hasNext()) {
            btn3.setText(scan.next());
        }
        initializeButtons(btn3);
        Button btn4 = (Button) v.findViewById(R.id.Solo4);
        if (scan.hasNext()) {
            btn4.setText(scan.next());
        }
        initializeButtons(btn4);
        Button btn5 = (Button) v.findViewById(R.id.Solo5);
        if (scan.hasNext()) {
            btn5.setText(scan.next());
        }
        initializeButtons(btn5);


        Button addOr = (Button) v.findViewById(R.id.Add_Or);
        addOr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addOrChangeLayout();
            }
        });

        return v;
    }

    /**
     * This method initiliazs the button passed by setting its onclick and onlongclick listeners
     * @param button Button to be initialized
     */
    private void initializeButtons(Button button) {
        if (button.getText().toString().equals("+")) {
            button.setVisibility(View.VISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDashboard(view);
            }
        });
        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                longClick(view);
                return false;
            }
        });
        //if empty set text to +
        //set transparency of button
    }

    /**
     * This method gives the user a dialog to edit the current button with choices they can choose from
     * @param view View object of the button that was long-clicked
     */
    private void longClick(View view) {
        String stringCheck = getResources().getString(R.string.plus_sign);
        tempButton = (Button) view;
        if (tempButton.getText().toString().equals(stringCheck)) {
            addOrChangeLayout();

            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Layout Options")
                .setItems(R.array.dashboard_module_settings_active, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                addOrChangeLayout();
                                break;
                            case 1:
                                tempButton.setText(R.string.plus_sign);
                                break;
                            case 2:
                                uploadLayout(tempButton);
                            default:
                                break;
                        }


                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Uploads the selected layout to the server so others can download it.
     * @param btn Button of layout to be uploaded. Used to get text for data collect for upload
     */
    public void uploadLayout(Button btn){
        ClientCommHttp cc = new ClientCommHttp();
        ClientSideDBManager lm = new ClientSideDBManager(getActivity());
        String btnText = btn.getText().toString();

        String data = MainActivity.getCurrentUserID()+";";
                data += btnText+";" ;
                data += lm.pullLayout(btnText)+";";

        try {
            cc.sendData("uploadLayout", data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Opens the dashboard fragment of the selected fragment
     * @param view Button of selected activity
     */
    public void openDashboard(View view) {
        Button button = (Button) view;
        if (button.getText().toString().equals("+")) {
            return;
        }

        Button stringOfView = (Button) view;
        MainActivity.setcurrentActivity(stringOfView.getText().toString());

        getFragmentManager().beginTransaction().replace(R.id.fragment, new DashboardMain()).addToBackStack(null).commit();
    }

    /**
     * This method gives the user a dialog to select if they want to add or change the current module
     */
    private void addOrChangeLayout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Module")
                .setItems(R.array.addOr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                getNewLayoutName();
                                break;
                            case 1:
                               switchToBrowse();


                                break;
                            default:
                                break;
                        }


                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * This method simply switches the user over into the browse layouts fragment when the user decides that they want to get a
     * layout from the server
     */
    private void switchToBrowse(){
        getFragmentManager().beginTransaction().replace(R.id.fragment,new BrowseLayouts()).addToBackStack(null).commit();
    }

    /**
     * This method gets the new layout name that the user wants in the form of a string
     */
    private void getNewLayoutName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.text_with_button, null));
        builder.setPositiveButton("Add Layout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Dialog d = Dialog.class.cast(dialog);

                EditText text = (EditText) d.findViewById(R.id.TEXT_DIALOG);
                String stringText = text.getText().toString();
                fillButtonWithText(stringText);
                dialog.dismiss();
            }
        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * This method fills the buttons with text from the local storage then sends its data off to the server
     * @param text Text to populate the button with
     */
    private void fillButtonWithText(String text){
        text = text.replace(" ","");
        Button solo0 = getActivity().findViewById(R.id.Solo0);
        Button solo1 = getActivity().findViewById(R.id.Solo1);
        Button solo2 = getActivity().findViewById(R.id.Solo2);
        Button solo3 = getActivity().findViewById(R.id.Solo3);
        Button solo4 = getActivity().findViewById(R.id.Solo4);
        Button solo5 = getActivity().findViewById(R.id.Solo5);

        ClientSideDBManager lm = getLm();
        lm.updateLayout(text, "+,+,+,+,+,+");
        if (solo0.getText().toString().equals("+")){solo0.setText(text);}
        else if (solo1.getText().toString().equals("+")){
            solo1.setText(text);}
        else if (solo2.getText().toString().equals("+")){
            solo2.setText(text);}
        else if (solo3.getText().toString().equals("+")){
            solo3.setText(text);}
        else if (solo4.getText().toString().equals("+")){
            solo4.setText(text);}
        else if (solo5.getText().toString().equals("+")){
            solo5.setText(text);}
        else {
            return;
        }


        ClientCommHttp c = new ClientCommHttp();
        String data = MainActivity.getCurrentUserID() + "%20" + text + "%20" +  "+,+,+,+,+,+";
        try {
            c.sendData("add%20layout", data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}


