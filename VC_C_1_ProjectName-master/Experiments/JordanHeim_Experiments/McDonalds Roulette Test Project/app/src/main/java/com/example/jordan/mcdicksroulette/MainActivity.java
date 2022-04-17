package com.example.jordan.mcdicksroulette;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button btn = (Button) findViewById(R.id.ButtonStuff);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random Rand = new Random();

                String stringOut = "You're Fate is the: ";
                boolean isSatified =false;
                while(isSatified == false){
                    int n = Rand.nextInt()%13;
                switch (n) {
                    case 1:
                        n = 1;
                        stringOut += "Big Mac";
                        isSatified = true;
                        break;
                    case 2:
                        n = 2;
                        stringOut += "Quarter Pounder";
                        isSatified = true;
                        break;
                    case 3:
                        n = 3;
                        stringOut += "Double Cheeseburger";
                        isSatified = true;
                        break;
                    case 4:
                        n = 4;
                        stringOut += "2 Cheeseburgers";
                        isSatified = true;
                        break;
                    case 5:
                        n = 5;
                        stringOut += "Ranch BlT";
                        isSatified = true;
                        break;
                    case 6:
                        n = 6;
                        stringOut += "Club";
                        isSatified = true;
                        break;
                    case 7:
                        n = 7;
                        stringOut += "Big MacClassic";
                        isSatified = true;
                        break;
                    case 8:
                        n = 8;
                        stringOut += "Southern Style Chicken";
                        isSatified = true;
                        break;
                    case 9:
                        n = 9;
                        stringOut += "Chicken Selects";
                        isSatified = true;
                        break;
                    case 10:
                        n = 10;
                        stringOut += "10 Pc Chicken McNuggets";
                        isSatified = true;
                        break;
                    case 11:
                        n = 11;
                        stringOut += "File-O-Fish";
                        isSatified = true;
                        break;
                    case 12:
                        n = 12;
                        stringOut += "Angus Deluxe";
                        isSatified = true;
                        break;
                    case 13:
                        n = 13;
                        stringOut += "Angus Bacon & Cheese";
                        isSatified = true;
                        break;
                }
                }
                Toast.makeText(getApplicationContext(), stringOut, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }



    }

