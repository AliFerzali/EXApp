package com.example.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button Sign_in, Reset_password;
    EditText User_name, Password;
    String username,password,URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sign_in = (Button)findViewById(R.id.sign_in_button);
        Reset_password = (Button)findViewById(R.id.reset_pass_button);
        User_name = (EditText) findViewById(R.id.username_field);
        Password = (EditText) findViewById(R.id.password_field);
        URL = "http://192.168.56.1/API/newlogin.php";

        Sign_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                username = String.valueOf(User_name.getText());
                password = String.valueOf(Password.getText());
                Log.d("username",username);
                Log.d("pass",password);
                if(username.isEmpty())
                {
                    User_name.setError("Mata in ditt användarnamn");
                    User_name.requestFocus();
                }
                else if(password.isEmpty())
                {
                    Password.setError("Mata in ditt lösenord");
                    Password.requestFocus();
                }
                else
                    putdata();
            } });

        Reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
            }});
    }
    public void moveToVerification(String uname){
        Intent intent = new Intent(this, VerificationPage.class);
        startActivity(intent.putExtra("username",uname));
    }


    void putdata()
    {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[2];
                inputfields[0]= "username";
                inputfields[1]= "password";
                String [] data = new String[2];
                data[0]=username;
                data[1]=password;
                PutData putData = new PutData(URL,"POST",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                {   String res = putData.getResult();
                    Log.d("RESUlT",res);
                    if(res.equals("Login succeed!"))
                    {
                        moveToVerification(username);
                    }else{
                        Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_SHORT).show();
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
    void login() //using the volley library
    {
                        /*StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Login succeed!")) {
                            moveToVerification();
                        } else
                            Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.toString().trim(),Toast.LENGTH_SHORT).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> data=new HashMap<>();
                        data.put("username",username);
                        data.put("password",password);
                        return data;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);*/
    }
}
