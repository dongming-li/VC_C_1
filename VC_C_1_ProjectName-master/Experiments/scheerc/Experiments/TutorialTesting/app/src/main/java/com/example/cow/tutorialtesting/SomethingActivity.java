package com.example.cow.tutorialtesting;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.TextView;

public class SomethingActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);


        //
        Intent intent = getIntent();
        String message = "I've opened a new activity!";
        TextView textView = (TextView) findViewById(R.id.textView);
    }
}
