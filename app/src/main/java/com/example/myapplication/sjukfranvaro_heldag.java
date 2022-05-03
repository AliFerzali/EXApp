package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class sjukfranvaro_heldag extends AppCompatActivity {
    Button idag,imorgon, Back;
    String username, URL;
    int dayvariabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjukfranvaro_heldag);
        String newString;
        Bundle extras = getIntent().getExtras();
        username= extras.getString("username");
        URL = "http://193.10.223.254/API/API/sjukanmala.php";
        Back = (Button)findViewById(R.id.back_button);
        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                moveToOptionsPage();
            } });
        idag = (Button)findViewById(R.id.idag_button);
        idag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayvariabel = 0;
                moveToSjukfranvaroHeldagInfo(username, dayvariabel);
            }
        });
        imorgon = (Button) findViewById(R.id.imorgon_button);
        imorgon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dayvariabel = 1;
                moveToSjukfranvaroHeldagInfo(username, dayvariabel);
            }
        });
    }
    public void moveToOptionsPage(){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent);
    }
    public void moveToSjukfranvaroHeldagInfo(String username, int dayvariabel){

        Intent intent = new Intent(this, sjukfranvaro_heldag_info.class);
        startActivity(intent.putExtra("username", username).putExtra("dayVariabel", dayvariabel));
    }

}