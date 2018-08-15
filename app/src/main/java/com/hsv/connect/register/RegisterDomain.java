package com.hsv.connect.register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.hsv.connect.CUser;
import com.hsv.connect.DbHelper;
import com.hsv.connect.R;
import com.hsv.connect.httpservice.ResValues;
import com.hsv.connect.httpservice.WebConnector;
import com.hsv.connect.profile.UserProfile;

import org.json.JSONObject;

public class RegisterDomain extends AppCompatActivity {
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_domain);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        mail=(String )b.get("mail");
    }


    public void registerDomain(View view) {
        Spinner s_dom   = (Spinner)findViewById(R.id.domain_spinner);
        Spinner s_field = (Spinner)findViewById(R.id.field_spinner);
        EditText e1 = (EditText) findViewById(R.id.register_skill);
        EditText e2 = (EditText) findViewById(R.id.register_desc);
        String domain  = s_dom.getSelectedItem().toString();
        String field   = s_field.getSelectedItem().toString();
        String skill   = e1.getText().toString();
        String desc    = e2.getText().toString();
//        String address = "";

        String query = "updatedetails.php?mail="+mail+"&domain="+domain+"&field="+field+"&skill="+skill+"&desc="+desc;
        query = query.replaceAll(" ", "%20");
        String result="";
        ResValues resValues;
        WebConnector connector = new WebConnector();
        if(skill.isEmpty() || desc.isEmpty()){
            Toast.makeText(getApplicationContext(),"fill the fields",Toast.LENGTH_SHORT).show();
        }
        else {
            resValues = connector.connectService(query);
            Log.w("RegDomain", "Domain_class");
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
                    Intent i = new Intent(RegisterDomain.this,RegisterLocation.class);
                    i.putExtra("mail",mail);
                    startActivity(i);
                    finish();
                }
            } catch (Exception e) {
                Log.w("jsonAtDomain...", e.toString());
            }
            Toast.makeText(getApplicationContext(), "RegisterDomain.java", Toast.LENGTH_SHORT).show();
        }
    }

}




//    public void domainRegistration(View view) {
////        Intent i = new Intent(RegisterDomain.this,GetLocation.class);
//        Spinner s_dom   = (Spinner)findViewById(R.id.domain_spinner);
//        Spinner s_field = (Spinner)findViewById(R.id.field_spinner);
////        i.putExtra("param_email",email_from_reg);
////        i.putExtra("param_name",name_from_reg);
////        i.putExtra("param_pass",pass_from_reg);
////        i.putExtra("param_domain",s_dom.getSelectedItem().toString());
////        i.putExtra("param_field",s_field.getSelectedItem().toString());
//        String sp_dom = s_dom.getSelectedItem().toString();
//        String sp_field = s_field.getSelectedItem().toString();
//
//        DbHelper db = new DbHelper(this);
//        CUser  user = new CUser();
//        user.setEmail(email_from_reg);
//        user.setName(name_from_reg);
//        user.setDomain(sp_dom);
//        user.setField(sp_field);
//        db.userDetail(user);
////        Toast.makeText(getApplicationContext(),email_from_reg+"\n"+name_from_reg+"\n"+pass_from_reg,Toast.LENGTH_SHORT).show();
////        startActivity(i);
//        finish();
//
//    }