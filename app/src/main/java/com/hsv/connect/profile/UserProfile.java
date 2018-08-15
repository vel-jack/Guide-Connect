package com.hsv.connect.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.hsv.connect.Login;
import com.hsv.connect.R;
import com.hsv.connect.UserDB;
import com.hsv.connect.httpservice.ResValues;
import com.hsv.connect.httpservice.WebConnector;
import com.hsv.connect.search.SearchPeople;

import org.json.JSONObject;

public class UserProfile extends AppCompatActivity {
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        try {
             UserDB db = new UserDB(this);
             mail= db.getId();
        }catch (Exception e){
            Log.w("Ex",e.toString());
        }

        TextView user_name = (TextView) findViewById(R.id.user_name_p);
        TextView user_email= (TextView) findViewById(R.id.user_email_p);
        TextView user_dom  = (TextView) findViewById(R.id.user_dom_p);
        TextView user_field= (TextView) findViewById(R.id.user_field_p);
        TextView user_address   = (TextView) findViewById(R.id.user_addr_p);
        TextView user_rating = (TextView) findViewById(R.id.user_rating);
        user_email.setText(mail);
        String name="",domain="",field="",address="",rating="";

        ResValues resValues;
        WebConnector connector = new WebConnector();
        resValues = connector.connectService("select.php?mail=" + mail);

        try {
            for (int i = 0; i < resValues.jArray.length(); i++) {
                JSONObject jO = resValues.jArray.getJSONObject(i);
                name    = jO.getString("name");
                domain  = jO.getString("domain");
                field   = jO.getString("field");
                address = jO.getString("address");
                rating  = jO.getString("rating");
            }
            user_name.setText(name);
            user_dom.setText(domain);
            user_address.setText(address);
            user_field.setText(field);
            user_rating.setText(rating+"/5");
        } catch (Exception e) {
            Log.w("json at login...", e.toString());
        }





//        DbHelper db = new DbHelper(this);
//        CUser user = db.getUser();
//        user_name.setText(user.getName());
//        user_email.setText(user.getEmail());
//        user_dom.setText(user.getDomain());
//        user_field.setText(user.getField());
    }

    public void logoutUser(View view) {
//        DbHelper db = new DbHelper(this);
//        CUser user= db.getUser();
//        db.removeDetail(user);
//        Intent intent = new Intent(UserProfile.this, Login.class);
//        startActivity(intent);
//        finish();
        UserDB ud = new UserDB(this);
        ud.removeId();
        Intent intent = new Intent(UserProfile.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void findPeople(View view) {
        Intent i = new Intent(UserProfile.this, SearchPeople.class);
        startActivity(i);
    }
}
