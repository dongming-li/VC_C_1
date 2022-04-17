package Fragments;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vc_c_1.statify.MainActivity;
import vc_c_1.statify.R;

/**
 * Settings Fragment that is currently unimplemented
 */
public class SettingsMainFragment extends Fragment {

    /**
     * Default constructor
     */
    public SettingsMainFragment() {
        // Required empty public constructor
    }

    /**
     * on creation, the logout button will be created
     * this method will also set the onclicklistener to clear the local table and open up to the main activity when pressed
     * @param inflater Default inflater
     * @param container Default container
     * @param savedInstanceState Default bundle
     * @return View used later on down the line in Fragment creation
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings_main, container, false);
        // Inflate the layout for this fragment
        Button btn = (Button)v.findViewById(R.id.logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClientSideDBManager lm = new ClientSideDBManager(getActivity());
                lm.onUpgrade(null, 0,1);
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return v;
    }

}
