package com.hsv.connect;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hsv.connect.httpservice.ResValues;
import com.hsv.connect.httpservice.WebConnector;
import com.hsv.connect.profile.PeopleProfile;
import com.hsv.connect.profile.UserProfile;
import com.hsv.connect.register.SignUp;

import org.json.JSONObject;

import java.security.spec.ECField;

public class Login extends AppCompatActivity {
    UserDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginToSignUp(View view) {
        Intent i = new Intent(Login.this, SignUp.class);
        startActivity(i);
    }

    public void doLogin(View view) {
        ResValues resValues;
        WebConnector connector = new WebConnector();
        EditText e1 = (EditText)findViewById(R.id.login_email);
        EditText e2 = (EditText)findViewById(R.id.login_pass);
        String l_email = e1.getText().toString();
        String l_pass  = e2.getText().toString();
        String  result = "";
        if(l_email.isEmpty() || l_pass.isEmpty()){
            Toast.makeText(getApplicationContext(),"Fill the fields",Toast.LENGTH_SHORT).show();
        }
        else {
            resValues = connector.connectService("logincheck.php?mail=" + l_email + "&pass=" + l_pass);
            Log.w("Login... :", "login class...");
            try {
                for (int i = 0; i < resValues.jArray.length(); i++) {
                    JSONObject jO = resValues.jArray.getJSONObject(i);
                    result = jO.getString("emailid");
                }
                Log.w("json at login parse..",result);
                if(result.equals("failed")){
                    Log.w("status","failed");
                    Toast.makeText(getApplicationContext(),"Email or Password wrong",Toast.LENGTH_SHORT).show();
                }else{
                    Log.w("status","success");
                    db = new UserDB(this);
                    db.insertId(l_email);
                    Intent i = new Intent(Login.this, UserProfile.class);
                    startActivity(i);
                    finish();
                }
            } catch (Exception e) {
                Log.w("json at login...", e.toString());
            }
        }
    }

}
