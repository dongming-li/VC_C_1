package com.example.jordan.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Frag1 extends Fragment {
public View v;
    public Frag1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_frag1, container, false);
        Button button = v.findViewById(R.id.fragment);
        Button button2 = v.findViewById(R.id.fragment2);
        Button button3 = v.findViewById(R.id.fragment3);
        Button button4 = v.findViewById(R.id.fragment4);
        Button button5 = v.findViewById(R.id.fragment5);
        Button button6 = v.findViewById(R.id.fragment6);
/**
        helper(button);
        helper(button2);
        helper(button3);
        helper(button4);
        helper(button5);
        helper(button6);
**/


        return v;
    }

    public void helper(Button button){
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              StopWatch st = new StopWatch();

               FragmentManager fragmentManager = getFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.replace(R.id.fragment, st);

               fragmentTransaction.commit();



           }
       });
    }

}



