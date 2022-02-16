package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.os.Handler;
import android.content.Intent;
import android.os.Bundle;
import java.util.Timer;
import java.lang.Runnable;
import java.util.TimerTask;


public class VerificationPage extends AppCompatActivity {
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_page);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(VerificationPage.this,OptionsPage.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}