package com.example.jordan.fragments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FragmentsMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_main);

        Frag1 frag = new Frag1();
        Frag1 frag2 = new Frag1();
        Frag1 frag3 = new Frag1();
        Frag1 frag4 = new Frag1();
        Frag1 frag5 = new Frag1();
        Frag1 frag6 = new Frag1();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment, frag).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment2, frag2).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment3, frag3).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment4, frag4).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment5, frag5).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment6, frag6).commit();




    }



}
