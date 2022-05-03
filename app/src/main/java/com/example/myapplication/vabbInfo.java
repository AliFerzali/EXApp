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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class vabbInfo extends AppCompatActivity {
    final Calendar myCalendar= Calendar.getInstance();
    EditText editText,meddelande;
    String username, quantity, start_date, end_date,URL,str;
    Button ok_button;
    TextView datum;
    DateHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vabb_info);
        Bundle extras = getIntent().getExtras();
        username= extras.getString("username");
        URL = "http://192.168.0.9/API/API/vabb.php";
        editText=(EditText)findViewById(R.id.vabbDatum);
        meddelande=(EditText)findViewById(R.id.vabb_edit_text);
        ok_button = (Button)findViewById(R.id.vabb_ok_Button);
        datum = (TextView)findViewById(R.id.summary);
        handler = new DateHandler();

        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
                datum.setText(end_date);


            }
        };
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(vabbInfo.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                Toast.makeText(getApplicationContext(),String.valueOf(myCalendar.get(Calendar.DAY_OF_MONTH)),Toast.LENGTH_SHORT).show();
            }
        });
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putdata();
                moveToOptionsPage(username);
            }
        });
    }

    private void updateLabel(){
        if(!(handler.isValid(myCalendar.getTime(),new Date(System.currentTimeMillis())))) {
            Toast.makeText(vabbInfo.this,"Ange ett giltigt datum",Toast.LENGTH_SHORT).show();
        }
        else
        {
            String myFormat="20yy-MM-dd";
            SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
            editText.setText(dateFormat.format(myCalendar.getTime()));

            //date.compareTo(date);
            start_date =  editText.getText().toString().trim();
            Log.d("Todays's date", "updateLabel2: "+ start_date );
            handler.setStartDate(start_date);
            end_date = handler.getNextDayDate();
            Log.d("Next day's date", "updateLabel2: "+ end_date );
        }
    }

    public void moveToOptionsPage(String str){
        Intent intent = new Intent(this, OptionsPage.class);
        startActivity(intent.putExtra("username",str));
    }

    void putdata()
    {
        quantity = "1";
        str = meddelande.getText().toString();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[5];
                inputfields[0]= "username";
                inputfields[1]= "quantity";
                inputfields[2]= "start_date";
                inputfields[3]= "end_date";
                inputfields[4]= "meddelande";
                String [] data = new String[5];
                data[0]=username;
                data[1]=quantity;
                data[2]=start_date;
                data[3]=end_date;
                data[4]=str;
                PutData putData = new PutData(URL,"POST",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                {   String res = putData.getResult();
                    Log.d("RESUlT",res);
                    if(res.equals("Inserted successfully"))
                    {
                        moveToOptionsPage(username);
                    }else{
                        Toast.makeText(getApplicationContext(),"Failed to insert to semestertabell",Toast.LENGTH_SHORT).show();
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