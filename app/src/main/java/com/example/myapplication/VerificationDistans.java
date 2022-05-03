package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class VerificationDistans extends AppCompatActivity {
    Button OK, Back;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_distans);
        Bundle extras = getIntent().getExtras();
        username= extras.getString("username");
        OK = (Button)findViewById(R.id.distans_ok_Button);
        Back = (Button)findViewById(R.id.back_button);
        OK.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                moveToEndDistans();
            } });
        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                moveToOptionsPage(username);
            } });
    }
    public void moveToOptionsPage(String str){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent.putExtra("username",str));
    }
    public void moveToEndDistans(){
        Intent intent = new Intent(this, EndDistans.class);
        startActivity(intent);
    }
}