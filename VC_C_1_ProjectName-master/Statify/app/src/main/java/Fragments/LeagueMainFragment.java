package Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vc_c_1.statify.R;

/**
 * Leagues Fragment that is currently unimplemented
 */
public class LeagueMainFragment extends Fragment {

    /**
     * Required empty constructor
     */
    public LeagueMainFragment() { }

    /**
     * Inflates the league main fragment on create
     * @param inflater Default inflater
     * @param container Default container
     * @param savedInstanceState Default Bundle
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_league_main, container, false);
    }

}
