package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class sjukfranvaro_anpassaTid extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText1,editText2,internMeddelande,externMeddelande, startTime, endTime, kalender;
    String username, quantity, start_date, start_time, end_time, end_date, intern_meddelande, extern_meddelande, URL;
    Button ok_button;
    DateHandler handler;
    TimePicker timePicker, EndtimePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjukfranvaro_anpassa_tid);
        Bundle extras = getIntent().getExtras();
        username= extras.getString("username");
        URL = "http://193.10.223.254/API/API/sjukfranvaroHeldag.php";
        editText1 = (EditText)findViewById(R.id.startDatum_sjuk);
        editText2 = (EditText)findViewById(R.id.slutDatum_sjuk);
        internMeddelande = (EditText)findViewById(R.id.sjukfranvaro_intern_Meddelande);
        externMeddelande = (EditText)findViewById(R.id.sjukfranvaro_extern_Meddelande);
        startTime = (EditText)findViewById(R.id.StartTime_sjuk);
        endTime = (EditText)findViewById(R.id.endTime_sjuk);
        kalender = (EditText)findViewById(R.id.sjukfranvaro_kalender);
        ok_button = (Button)findViewById(R.id.sjuk_ok_Button);
        handler = new DateHandler();
        timePicker = (TimePicker)findViewById(R.id.StarttimePicker_sjuk);
        timePicker.setIs24HourView(true);
        EndtimePicker = (TimePicker)findViewById(R.id.endTimePicker_sjuk);
        EndtimePicker.setIs24HourView(true);

        DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel1();
            }
        };
        editText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(sjukfranvaro_anpassaTid.this,date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                Toast.makeText(getApplicationContext(),String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH)),Toast.LENGTH_SHORT).show();

            }
        });

        DatePickerDialog.OnDateSetListener date2 =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel2();

            }
        };
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker.setVisibility(View.VISIBLE);
            }
        });
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(sjukfranvaro_anpassaTid.this,date2,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                Toast.makeText(getApplicationContext(),String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH)),Toast.LENGTH_SHORT).show();
                timePicker.setVisibility(View.GONE);
                String time = getTime(timePicker);
                startTime.setText(time);
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndtimePicker.setVisibility(View.VISIBLE);
            }
        });
        kalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndtimePicker.setVisibility(View.GONE);
                String time = getTime(EndtimePicker);
                endTime.setText(time);
            }
        });
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time = getTime(EndtimePicker);
                endTime.setText(time);
                EndtimePicker.setVisibility(View.GONE);
                putdata();
                moveToOptionsPage(username);
            }
        });
    }

    private void updateLabel1(){
        if(!(handler.isValid(myCalendar.getTime(),new Date(System.currentTimeMillis())))) {
            Toast.makeText(sjukfranvaro_anpassaTid.this,"Ange ett giltig datum",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String myFormat="20yy-MM-dd";
            SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
            editText1.setText(dateFormat.format(myCalendar.getTime()));
            start_date =  editText1.getText().toString().trim();
            handler.setStartDate(start_date);

        }
    }

    private void updateLabel2(){
        if(!(handler.isValid(myCalendar.getTime(),new Date(System.currentTimeMillis())))) {
            Toast.makeText(sjukfranvaro_anpassaTid.this,"Ange ett giltig datum",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String myFormat="20yy-MM-dd";
            SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
            editText2.setText(dateFormat.format(myCalendar.getTime()));
            end_date =  editText2.getText().toString().trim();
            handler.setStartDate(end_date);

        }
    }
    public void moveToOptionsPage(String str){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent.putExtra("username",str));
    }

    public String getTime(TimePicker timePicker)
    {
        String minute = "";
        int hours= timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();
        if (min >= 0 && min <= 9) {
            minute = "0" + String.valueOf(min);
            return String.valueOf(hours) + ":" + minute;
        } else
            return String.valueOf(hours) + ":" + String.valueOf(min);

    }


    void putdata()
    {
        //beräkna antalet arbetsdagar mellan start_date och end_date
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            SimpleDateFormat sdf = new SimpleDateFormat("20yy-MM-dd");
            int workingDays = 0;
            try {
                Calendar start = Calendar.getInstance();
                start.setTime(sdf.parse(start_date));

                Calendar end = Calendar.getInstance();
                end.setTime(sdf.parse(end_date));

                workingDays = 0;
                while (!start.after(end))
                {
                    int day = start.get(Calendar.DAY_OF_WEEK);
                    if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY))
                        workingDays++;
                    start.add(Calendar.DATE, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            quantity = String.valueOf(workingDays);
        }

        intern_meddelande = internMeddelande.getText().toString();
        extern_meddelande = externMeddelande.getText().toString();
        start_time = startTime.getText().toString();
        end_time = endTime.getText().toString();

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
                        Toast.makeText(getApplicationContext(),"Failed to insert to sjuktabell",Toast.LENGTH_SHORT).show();
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