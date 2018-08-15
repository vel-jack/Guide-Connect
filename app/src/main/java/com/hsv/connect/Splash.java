package com.hsv.connect;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import com.hsv.connect.profile.UserProfile;

public class Splash extends AppCompatActivity {


    private static int SPLASH_T=2300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        UserDB db = new UserDB(this);
        String user = db.getId();

        final Intent intent ;
        if(user == "null") {
            intent = new Intent(Splash.this, Login.class);
        }
        else{
            intent = new Intent(Splash.this, UserProfile.class);
        }
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                startActivity(intent);
                finish();
            }
        },SPLASH_T);

    }
}
