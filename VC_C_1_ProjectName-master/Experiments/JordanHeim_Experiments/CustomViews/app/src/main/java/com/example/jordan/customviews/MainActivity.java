package com.example.jordan.customviews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeButtons();
    }

    protected void initializeButtons() {
        final Button Button1 = (Button) findViewById(R.id.button1);
        final Button Button2 = (Button) findViewById(R.id.button2);
        final Button Button3 = (Button) findViewById(R.id.button3);
        final Button Button4 = (Button) findViewById(R.id.button4);
        final Button Button5 = (Button) findViewById(R.id.button5);
        final Button Button6 = (Button) findViewById(R.id.button6);

        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pop = new Intent(MainActivity.this, pop_view.class);
                startActivity(pop);
            }
        });

        Button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Intent view = new Intent();
                startActivity(view);
            }
        });

        Button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //add here
            }
        });
        Button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // add here
            }
        });
        Button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // add here
            }
        });
        Button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // add here
            }
        });
        //add more buttons here
    }
    }




