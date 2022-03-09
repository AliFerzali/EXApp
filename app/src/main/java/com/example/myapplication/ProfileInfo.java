package com.example.myapplication;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileInfo extends AppCompatActivity {
   private String URL,URL2;

   TextView F_name,S_name,email,password,id,Semester_days, flex_tim,new_password,Edit_text,SecQues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);
        init();

    }


    private void init()  {
        String newString;
        Bundle extras = getIntent().getExtras();
        newString= extras.getString("username");
        Log.d("UserName", "init: "+ newString);
        Edit_text = findViewById(R.id.EditProfileText);
        URL="http://192.168.56.1/API/profileInfo.php";
        URL2="http://192.168.56.1/API/CheckSecQues.php";
        F_name = (TextView) findViewById(R.id.firstname_view);
        S_name = (TextView) findViewById(R.id.SecondName_View);
        email = (TextView) findViewById(R.id.Email_view);
        password =(TextView) findViewById(R.id.password_view);
        id = (TextView) findViewById(R.id.id_view);
        Semester_days = (TextView) findViewById(R.id.days_view);
        flex_tim = (TextView) findViewById(R.id.Hours_view);
        new_password = findViewById(R.id.NewPasswordText);
        SecQues = findViewById(R.id.securityQues);
        SecQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckingForSecQues(newString);
            }
        });

        putdata(newString);
        //getJsonObject(newString);
        Edit_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( getApplicationContext(),EditProfileActivity.class));
            }
        });
        new_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),NewPasswordActivity.class));
            }
        });

    }
    void CheckingForSecQues(String username)
    {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[1];
                inputfields[0]= "username";

                String [] data = new String[1];
                data[0]=username;

                PutData putData = new PutData(URL2,"POST",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                {   String res = putData.getResult();
                    Log.d("RESUlT 2",res);
                   if(res.equals("Exists"))
                   {
                       Toast.makeText(getApplicationContext(),"Du har redan valt dina säkerhetsfrågor",Toast.LENGTH_SHORT).show();
                   }else
                       startActivity(new Intent(getApplicationContext(),SetSecQuestions.class).putExtra("username",username));

                }
                }


            }
        });
    }
    void putdata(String username)
    {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String [] inputfields = new String[1];
                inputfields[0]= "username";

                String [] data = new String[1];
                data[0]=username;

                PutData putData = new PutData(URL,"GET",inputfields,data);
                if(putData.startPut())
                {   if(putData.onComplete())
                    {   String res = putData.getResult();
                        Log.d("RESUlT",res);
                        try {
                            JSONObject jsonObject = new JSONObject(res);
                            Log.d("JSON", "run: "+jsonObject.getString("firstname"));
                            F_name.setText(jsonObject.getString("firstname"));
                            S_name.setText(jsonObject.getString("secondname"));
                            id.setText(String.valueOf(jsonObject.getInt("id")));
                            email.setText(jsonObject.getString("email"));
                            password.setText(jsonObject.getString("password"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }


            }
        });
    }
    void getJsonObject(String str)
    {
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, URL,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                   JSONObject js= response.getJSONObject("username");
                   String str = js.toString();
                    Log.d("JSONOBJECT", "onResponse: "+String.valueOf(response.getJSONObject("username"))+ "STR" + str) ;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                data.put("username",str);

                return data;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
