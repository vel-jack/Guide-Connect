package com.hsv.connect.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.hsv.connect.Login;
import com.hsv.connect.R;
import com.hsv.connect.UserDB;
import com.hsv.connect.httpservice.ResValues;
import com.hsv.connect.httpservice.WebConnector;

import org.json.JSONObject;

public class SignUp extends AppCompatActivity {
    UserDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_signup);
    }

    public void registerUser(View view) {
        EditText e1 = (EditText)findViewById(R.id.signup_name);
        EditText e2 = (EditText)findViewById(R.id.login_email);
        EditText e3 = (EditText)findViewById(R.id.login_pass);
        String s_email = e2.getText().toString();
        String s_pass  = e3.getText().toString();
        String s_name  = e1.getText().toString();
        String query = "signup.php?mail="+s_email+"&name="+s_name+"&pass="+s_pass;
        query = query.replaceAll(" ", "%20");
        String result="";
        ResValues resValues;
        WebConnector connector = new WebConnector();
        if(s_email.isEmpty() || s_pass.isEmpty() || s_name.isEmpty()){
            Toast.makeText(getApplicationContext(),"fill the fields",Toast.LENGTH_SHORT).show();
        }
        else {
            resValues = connector.connectService(query);
            connector.connectService("location.php?mail="+s_email);
            Log.w("Signup", "Signup class...");
            try {
                for (int i = 0; i < resValues.jArray.length(); i++) {
                    JSONObject jO = resValues.jArray.getJSONObject(i);
                    result = jO.getString("signup");
                }
                Log.w("json at login parse..",result);
                if(result.equals("failed")){
                    Log.w("status - ",result);
                }else{
                    Log.w("status",result);
                    db = new UserDB(this);
                    db.insertId(s_email);
                    Intent i = new Intent(SignUp.this,RegisterDomain.class);
                    i.putExtra("mail",s_email);
                    startActivity(i);
                    finish();
                }
            } catch (Exception e) {
                Log.w("json at sign up...", e.toString());
            }
            Toast.makeText(getApplicationContext(), "Signup.java", Toast.LENGTH_SHORT).show();
        }

    }

    public void signUpToLogin(View view) {
        Intent i = new Intent(SignUp.this, Login.class);
        startActivity(i);
        finish();
    }
}