package Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vc_c_1.statify.R;

/**
 * Friend Fragment that is currently unimplemented
 */
public class FriendsMainFragment extends Fragment {

    /**
     * friends fragment currently not implemented. due to time contraints we couldn't do this part of our project
     */
    public FriendsMainFragment() {
        // Required empty public constructor
    }

    /**
     * OnCreate Method that inflates friends main fragment
     * @param inflater Default inflater
     * @param container Default container
     * @param savedInstanceState Default Bundle
     * @return View that is used later on down the road
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_friends_main, container, false);
    }

}
