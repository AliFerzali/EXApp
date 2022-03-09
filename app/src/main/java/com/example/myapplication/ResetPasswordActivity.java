package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ResetPasswordActivity extends AppCompatActivity {
    Spinner FirstQuestion, SecondQuestion, ThirdQuestion;
    EditText FirstAnswer,SecondAnswer,ThirdAnswer,Uname;
    String username;
    Button SubmitButton;
    private final String URL = "http://192.168.56.1/API/GetSecAnswers.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        init();
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                putdata();
            }
        });


    }

    private void init()
    {
        FirstQuestion = findViewById(R.id.questionsList1);
        SecondQuestion = findViewById(R.id.questionsList2);
        ThirdQuestion = findViewById(R.id.questionsList3);
        FirstAnswer = findViewById(R.id.answer1);
        SecondAnswer = findViewById(R.id.answer2);
        ThirdAnswer = findViewById( R.id.answer3);
        SubmitButton = findViewById(R.id.submitButton);
        Uname = findViewById(R.id.username_input);

        ArrayAdapter<CharSequence> firstAdapter= ArrayAdapter.createFromResource(this,R.array.SecurityQuestions, android.R.layout.simple_spinner_item);
        firstAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        FirstQuestion.setAdapter(firstAdapter);

        ArrayAdapter<CharSequence> secondAdapter= ArrayAdapter.createFromResource(this,R.array.SecurityQuestions, android.R.layout.simple_spinner_item);
        secondAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SecondQuestion.setAdapter(firstAdapter);

        ArrayAdapter<CharSequence> ThirdAdapter= ArrayAdapter.createFromResource(this,R.array.SecurityQuestions, android.R.layout.simple_spinner_item);
        ThirdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ThirdQuestion.setAdapter(firstAdapter);

    }

    void putdata() {
        String uname = Uname.getText().toString().trim();
        String firstAnswer = (FirstAnswer.getText().toString().trim()).toLowerCase();
        String secondAnswer = (SecondAnswer.getText().toString().trim()).toLowerCase();
        String thirdAnswer = (ThirdAnswer.getText().toString().trim()).toLowerCase();

        if ((uname.isEmpty())){
            Uname.setError("Användarnamn är obligatorisk");
            Uname.requestFocus();
        }else if(firstAnswer.isEmpty()) {
            FirstAnswer.setError("obligatoriskt fält");
            FirstAnswer.requestFocus();
        }else if(secondAnswer.isEmpty()) {
            SecondAnswer.setError("Obligatoriskt fält");
            SecondAnswer.requestFocus();
        }else if(thirdAnswer.isEmpty()){
            ThirdAnswer.setError("Obligatoriskt fält");
            ThirdAnswer.requestFocus();
        }else{
            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String [] inputfields = new String[4];
                    inputfields[0]= "username";
                    inputfields[1]= "first_question";
                    inputfields[2]= "second_question";
                    inputfields[3]= "third_question";
                    String [] data = new String[4];
                    data[0]=uname.toLowerCase();
                    data[1]=firstAnswer;
                    data[2]=secondAnswer;
                    data[3]=thirdAnswer;

                    PutData putData = new PutData(URL,"POST",inputfields,data);
                    if(putData.startPut())
                    {   if(putData.onComplete())
                    {   String res = putData.getResult();
                        Log.d("RESUlT",res);
                        if(res.equals("Verified"))
                        {
                            Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),SetNewPassword.class).putExtra("username",data[0]));
                        }else{
                            Toast.makeText(getApplicationContext(),"reset failed",Toast.LENGTH_SHORT).show();
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
}