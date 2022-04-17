package com.example.cow.tutorialtesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String MESSAGE = "com.example.cow.tutorialtesting.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void somethingButton(View view) {
        // Whatever button does here
        Intent intent = new Intent(this, SomethingActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(MESSAGE, message);
        startActivity(intent);

    }
}
