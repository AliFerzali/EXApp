package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DistansPage extends AppCompatActivity {
    Button Send,Back;
    EditText Text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distans_page);
        Send = (Button)findViewById(R.id.distans_skicka_button);
        Back = (Button)findViewById(R.id.back_button);
        Text = (EditText) findViewById(R.id.distans_edit_text);
        Send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!Text.getText().toString().isEmpty() ) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Wrong Credentials",Toast.LENGTH_SHORT).show();}
                moveToVerificationDistans();
            } });
        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                moveToOptionsPage();
            } });

    }
    public void moveToVerificationDistans(){
        Intent intent = new Intent(this, VerificationDistans.class);
        startActivity(intent);
    }
    public void moveToOptionsPage(){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent);
    }
}