package Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vc_c_1.statify.R;

/**
 * NewsFeed Fragment that is currently unimplemented
 */
public class NewsfeedMainFragment extends Fragment {

    /**
     * unimplemented fragment that we couldn't complete due to time constraints
     */
    public NewsfeedMainFragment() {
        // Required empty public constructor
    }

    /**
     * Basic Fragment inflater
     * @param inflater Default inflater
     * @param container Default container
     * @param savedInstanceState Default bundle
     * @return View for fragment construction later down the road
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_newsfeed_main, container, false);
    }

}
