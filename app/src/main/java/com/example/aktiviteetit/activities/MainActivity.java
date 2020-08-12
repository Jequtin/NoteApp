package com.example.aktiviteetit.activities;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aktiviteetit.R;
import com.example.aktiviteetit.activities.Koulu;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView textView1, textView2;
    Button button1, button2;
    //@RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        TextView textView1 = (TextView) findViewById(R.id.textView1);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        Button button1 = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);




        String Sun = "Sunnuntai";
        String Lau = "Lauantai";
        String Per = "Perjantai";
        String Tor = "Torstai";
        String Kes = "Keskiviikko";
        String Tii = "Tiistai";
        String Maa = "Maanantai";
        textView2.setText(String.valueOf(dayOfWeek));

            if (String.valueOf(dayOfWeek).equals("5")) {

                textView2.setText(Tor);
            } else if (String.valueOf(dayOfWeek).equals("2")) {

                textView2.setText(Maa);
            } else if (String.valueOf(dayOfWeek).equals("3")) {

                textView2.setText(Tii);
            } else if (String.valueOf(dayOfWeek).equals("4")) {

                textView2.setText(Kes);
            } else if (String.valueOf(dayOfWeek).equals("6")) {

                textView2.setText(Per);
            } else if (String.valueOf(dayOfWeek).equals("7")) {

                textView2.setText(Lau);
            } else if (String.valueOf(dayOfWeek).equals("1")) {

                textView2.setText(Sun);
            }

            button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avaaKoulu();
            }
        });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    avaaJumppa();
                }
            });
    }
    public void avaaKoulu() {
        Intent intent = new Intent(this, Koulu.class);
        startActivity(intent);

    }
    public void avaaJumppa() {
        Intent intent = new Intent(this, Jumppa.class);
        startActivity(intent);

    }

    }




