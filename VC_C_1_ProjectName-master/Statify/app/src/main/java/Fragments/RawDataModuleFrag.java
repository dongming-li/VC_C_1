package Fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.StrictMode;

import java.io.IOException;
import java.util.Scanner;

//import Communication.ClientCommunicator;
import communication.ClientCommHttp;
import vc_c_1.statify.R;

/**
 * raw data module where the user can input raw data to be sent and stored in the server for later use
 */
public class RawDataModuleFrag extends Fragment {
    View v;
    Context tempContext;
    ViewGroup rawDataScoreOutput;
/**
    public RawDataModuleFrag() {
        // Required empty public constructor
    }


    /**
     * initializes the button to add data from the user. and inflates the fragment from the xml file
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_raw_data_module, container, false);
        tempContext = getActivity();
/**
        rawDataScoreOutput = (ViewGroup) v.findViewById(R.id.rawDataScoreOutput);
        ClientCommHttp cc = new ClientCommHttp();
        enableStrictMode();
        // If the rawDataScoreOutput is empty, load data from the database
        // Should only run the first time this fragment is opened
        // Future module openings should be handled by the savedInstanceState
        if(rawDataScoreOutput.getChildCount() == 0){
            // First send a request to the server with cc.sendData(request);
            // Server will first respond with the number of scored is about to send
            // The is used to maintain a for loop that will parse through the scores to construct a new score with addNewScore(score)
            String response = null;
            try {
                response = cc.sendData("pull%20", "");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scanner in = new Scanner(response);
            String success = in.next();
            if(success.equals("success")){
                int size = in.nextInt();
                for(int i = 0; i < size; i++){
                    addNewScore(in.next());
                }
            }

        }
*/



        Button send = (Button)v.findViewById(R.id.SendData);
        send.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            EditText viewToSend = (EditText) getView().findViewById(R.id.editText);
            String out = viewToSend.getText().toString();
            TextView toSend = (TextView) getView().findViewById(R.id.toSend);


            if(!out.isEmpty()){
                addNewScore(out);
                ClientCommHttp cc = new ClientCommHttp();
                try {
                    cc.sendData("add", out);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            viewToSend.setText(R.string.empty_string);

            }
        });
        return v;
    }

    /**
     * method for adding new raw data to the server passed a score from user input and it is stored ito the server
     * @param score
     */
    private void addNewScore(String score){
        // Creates new textView for score
        TextView toAdd = new TextView(tempContext);
        String scoreDisplay = "Game " + Integer.toString(rawDataScoreOutput.getChildCount() + 1) + ": " + score;
        toAdd.setText(scoreDisplay);
        rawDataScoreOutput.addView(toAdd);
    }





}
