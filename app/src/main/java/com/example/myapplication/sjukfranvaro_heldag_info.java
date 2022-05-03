package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class sjukfranvaro_heldag_info extends AppCompatActivity {
    Button Send,Back;
    int dayVariabel;
    EditText internMeddelande,externMeddelande;
    String username, start_date, start_time, end_time,end_date,quantity,intern_meddelande, extern_meddelande, URL;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjukfranvaro_heldag_info);
        Bundle extras = getIntent().getExtras();
        username = extras.getString("username");
        dayVariabel = extras.getInt("dayVariabel");
        URL = "http://193.10.223.254/API/API/sjukfranvaroHeldag.php";
        Send = (Button)findViewById(R.id.sjukfranvaro_send_button);
        Back = (Button)findViewById(R.id.back_button);
        internMeddelande = (EditText) findViewById(R.id.internMeddelande_edit_text);
        externMeddelande = (EditText) findViewById(R.id.externMeddelande_edit_text);
        //dayVariabel = 0 --> idag, dayVariabel = 1 --> imorgon
        if (dayVariabel == 0){
            //Today's date
            Calendar calendar= Calendar.getInstance();
            java.util.Date dateObj = calendar.getTime();
            start_date = sdf.format(dateObj);
            Log.d( "onCreate: Todays date",start_date);
            //Tomorrow's date
            Calendar calendar1= Calendar.getInstance();
            calendar1.add(Calendar.DAY_OF_YEAR, 1);
            java.util.Date dateObj1 = calendar1.getTime();
            end_date = sdf.format(dateObj1);
            Log.d( "onCreate: ", end_date);
        }
        else {
            //Tomorrow's date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar= Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            java.util.Date dateObj = calendar.getTime();
            start_date = sdf.format(dateObj);
            Log.d( "Tomorrow's date",start_date);
            //After tomorrow's date
            Calendar calendar1= Calendar.getInstance();
            calendar1.add(Calendar.DAY_OF_YEAR, 2);
            java.util.Date dateObj1 = calendar1.getTime();
            end_date = sdf.format(dateObj1);
            Log.d( "After tomorrow's date", end_date);
        }


        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!internMeddelande.getText().toString().isEmpty() || !externMeddelande.getText().toString().isEmpty()){
                    intern_meddelande = internMeddelande.getText().toString();
                    extern_meddelande = externMeddelande.getText().toString();
                    putdata();
                    moveToOptionsPage(username);
                }
                else {
                    Toast.makeText(getApplicationContext(),
                            "Wrong Credentials",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void moveToOptionsPage(String str){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent.putExtra("username",str));
    }

    void putdata()
    {
        quantity = "1";
        start_time = "00:00:00";
        end_time = "24:00:00";
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[8];
                inputfields[0]= "username";
                inputfields[1]= "quantity";
                inputfields[2]= "start_date";
                inputfields[3]= "start_time";
                inputfields[4]= "end_date";
                inputfields[5]= "end_time";
                inputfields[6]= "message";
                inputfields[7]= "extern_message";
                String [] data = new String[8];
                data[0]=username;
                data[1]=quantity;
                data[2]=start_date;
                data[3]=start_time;
                data[4]=end_date;
                data[5]=end_time;
                data[6]=intern_meddelande;
                data[7]=extern_meddelande;
                PutData putData = new PutData(URL,"POST",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                {   String res = putData.getResult();
                    Log.d("RESUlT",res);
                    if(res.equals("Inserted successfully"))
                    {
                        moveToOptionsPage(username);
                    }else{
                        //Toast.makeText(getApplicationContext(),"Failed to insert to sjuktabell",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"On complete is not completed",Toast.LENGTH_SHORT).show();
                }
                }else{
                    Toast.makeText(getApplicationContext(),"failed putting data",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}