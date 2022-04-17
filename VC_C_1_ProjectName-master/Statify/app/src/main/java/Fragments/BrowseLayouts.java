package Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.Scanner;

import DataProcessing.ExpressionCalculator;
import communication.ClientCommHttp;
import vc_c_1.statify.MainActivity;
import vc_c_1.statify.R;

/**
 * Fragment used for finding a layout from the server to import into the users activities
 */
public class BrowseLayouts extends Fragment {
    private View view;

    /**
     * Required empty public constructor
     */
    public BrowseLayouts() { }

    /**
     * On creation of this fragment, it will inflate it and then pull layouts from the server to populate the screen
     * @param inflater Default fragment layout inflater
     * @param container Default viewgroup container
     * @param savedInstanceState Unused save-state of fragment. Required by override.
     * @return The initialized inflated view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_browse_layouts, container, false);
        view=v;
        pullLayouts();
        return v;
    }

    /**
     * Creates a Layout that the user can select. needs a user id, title, layout, and download count to construct.
     * Called every time there is a layout that is pulled and needs to be created
     * @param userId user ID
     * @param title Title of layout
     * @param layout Layout Data
     * @param downloads Number of downloads
     * @param view View object of button pressed. Used to help find selected layout
     */
    private void createDataPoint(final String userId, final String title, final String layout, final String downloads, View view){

        ViewGroup vg = (ViewGroup) view.findViewById(R.id.BrowseLayouts);
        final int locationID = vg.getChildCount() - 1;
        LinearLayout ll = new LinearLayout(getActivity());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.weight = 1.0f;

        //set the variables
        TextView tv1 = new TextView(getActivity());
        tv1.setText(title);
        tv1.setLayoutParams(layoutParams);
        ll.addView(tv1);

        TextView tv2 = new TextView(getActivity());
        tv2.setText(layout);
        tv2.setLayoutParams(layoutParams);
        ll.addView(tv2);

        TextView tv3 = new TextView(getActivity());
        tv3.setText(userId);
        tv3.setLayoutParams(layoutParams);
        ll.addView(tv3);

        TextView tv4 = new TextView(getActivity());
        tv4.setText(downloads);
        tv4.setLayoutParams(layoutParams);
        ll.addView(tv4);

        Button btn = new Button(getActivity());
        btn.setText("select");
        btn.setLayoutParams(layoutParams);
        btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               onSelectLayout(title, layout, userId, downloads);

            }
        });
        ll.addView(btn);

        vg.addView(ll,locationID);


    }

    /**
     * Gets layouts from the server. passes over the string retrieved to interpretLayouts() to be interpreted into data points
     */
    private void pullLayouts(){
        ClientCommHttp cc = new ClientCommHttp();
        String layouts = "";
        try {
            layouts = cc.sendData("pullLayouts","###");
            interpretLayouts(layouts);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes in layouts from pullLayouts() and parses through it to get data to populate the screen using createDataPoint() method
     * @param layouts Layout to be interpreted
     */
    private void interpretLayouts(String layouts){

        Scanner uploadScanner = new Scanner(layouts);
        uploadScanner.useDelimiter("#");
        while(uploadScanner.hasNext()) {
            String upload = uploadScanner.next();
            Scanner scan = new Scanner(upload);
            scan.useDelimiter(";");
            String uid = scan.next();
            String title = scan.next();
            String numDownloads = scan.next();
            String layout = scan.next();
            
            createDataPoint(uid, title, layout, numDownloads, view);
            scan.close();
        }
        uploadScanner.close();
    }

    /**
     * When a a layout is selected by a user, this method will be called.
     * it updates the local storage with the new layout and data inside.
     * It also will send a command to the server to add the layout to the users saved layouts
     * then the user will be redirected into the solo fragment
     * @param title Title of layout
     * @param data Layout data
     * @param uid User ID
     * @param numDownloads Number of Downloads
     */
    private void onSelectLayout(String title, String data, String uid, String numDownloads){
        ClientSideDBManager lm = new ClientSideDBManager(getActivity());
        lm.updateLayout(title, data);
        ClientCommHttp cc = new ClientCommHttp();
        try {
            String toSend = MainActivity.getCurrentUserID() + "%20" + title + "%20" +  "+,+,+,+,+,+";
            cc.sendData("add%20layout", toSend);

        } catch (Exception e) {
            e.printStackTrace();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment, new SoloFragment()).addToBackStack(null).commit();
    }


}
