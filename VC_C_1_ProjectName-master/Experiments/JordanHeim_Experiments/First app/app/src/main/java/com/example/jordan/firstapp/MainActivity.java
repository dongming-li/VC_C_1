package com.example.jordan.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int prime1= 0;
        int prime2 = 0;




        FunTestsSTuffs dynamicDaveEXE = new FunTestsSTuffs("stuffs");





        Button btn = (Button) findViewById(R.id.btnDoMagic);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BigInteger n = new BigInteger("25491999487");
                BigInteger i = new BigInteger("2");
                BigInteger zero = new BigInteger("0");
                BigInteger one = new BigInteger("1");
                System.out.println(n.mod(i).equals(zero));
                while(!(n.mod(i).equals(zero))) {

                    //System.out.println("number tried is: " + i);
                    i = i.add(one);


                }


                Toast.makeText(getApplicationContext(), "the prime factors are: " + i +" and : " + n.divideAndRemainder(i)[0] , Toast.LENGTH_LONG)
                    .show();
            }
        });
    }
}
