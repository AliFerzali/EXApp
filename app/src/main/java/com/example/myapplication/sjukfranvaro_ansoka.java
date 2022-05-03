package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class sjukfranvaro_ansoka extends AppCompatActivity {
    Button anpassaTid,heldag, Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjukfranvaro_ansoka);
        String newString;
        Bundle extras = getIntent().getExtras();
        newString = extras.getString("username");
        Back = (Button)findViewById(R.id.back_button);
        Back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                moveToOptionsPage(newString);
            } });
        anpassaTid = (Button)findViewById(R.id.anpassaTid_button);
        anpassaTid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSjukfranvaro_anpassaTid(newString);
            }
        });
        heldag = (Button) findViewById(R.id.heldag_button);
        heldag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToSjukfranvaroHeldag(newString);
            }
        });
    }
    public void moveToOptionsPage(String username){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent.putExtra("username", username));
    }
    public void moveToSjukfranvaro_anpassaTid(String username){
        Intent intent = new Intent(this, sjukfranvaro_anpassaTid.class);
        startActivity(intent.putExtra("username", username));
    }
    public void moveToSjukfranvaroHeldag(String username){
        Intent intent = new Intent(this, sjukfranvaro_heldag.class);
        startActivity(intent.putExtra("username", username));
    }
    public void moveToSemesterInfofler(String username){
        Intent intent = new Intent(this, SemesterinfoflerActivity.class);
        startActivity(intent.putExtra("username", username));
    }
}