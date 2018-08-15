package com.hsv.connect.profile;


import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hsv.connect.R;
import com.hsv.connect.UserDB;
import com.hsv.connect.httpservice.ResValues;
import com.hsv.connect.httpservice.WebConnector;

import org.json.JSONObject;

import java.beans.IndexedPropertyChangeEvent;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static android.R.attr.finishOnTaskLaunch;
import static android.R.attr.flipInterval;
import static android.R.attr.name;

public class PeopleProfile extends AppCompatActivity {
    WebConnector connector;
    String me="";
    String mail;
    String rate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.people_profile);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        mail=(String )b.get("mail");
        connector = new WebConnector();
        try {
            UserDB db = new UserDB(this);
            me= db.getId();
        }catch (Exception e){
            Log.w("Ex",e.toString());
        }
        ResValues resFollow = connector.connectService("follow.php?me="+me+"&he="+mail);
        String follow="",followers="",following="";
        Button followButton=(Button) findViewById(R.id.follow);
        TextView ff = (TextView) findViewById(R.id.people_follow);
        try {
            for (int i = 0; i < resFollow.jArray.length(); i++) {
                JSONObject jO = resFollow.jArray.getJSONObject(i);
                follow    = jO.getString("isfollow");
                followers  = jO.getString("follower");
                following   = jO.getString("following");
                Log.w("result",follow+","+followers+","+following);
            }

            if(follow.equals("1")){
                followButton.setText("unfollow");
            }
            else {
                followButton.setText("follow");
            }
            ff.setText(followers+" follower / "+following+" following");
        } catch (Exception e) {
            Log.w("ppl_prof_folw", e.toString());
            e.printStackTrace();
        }

        TextView user_name = (TextView) findViewById(R.id.people_name_p);
        TextView user_email= (TextView) findViewById(R.id.people_email_p);
        TextView user_dom  = (TextView) findViewById(R.id.people_dom_p);
        TextView user_field= (TextView) findViewById(R.id.people_field_p);
        TextView user_skill= (TextView) findViewById(R.id.people_skills_p);
        TextView user_desc= (TextView) findViewById(R.id.people_desc_p);
//        TextView user_rating = (TextView) findViewById(R.id.people_rating);
        TextView user_address   = (TextView) findViewById(R.id.people_addr_p);

        ResValues resValues;

        resValues = connector.connectService("select.php?mail=" + mail);
        String name="",domain="",field="",address="",skill="",desc="";
        try {
            for (int i = 0; i < resValues.jArray.length(); i++) {
                JSONObject jO = resValues.jArray.getJSONObject(i);
                name    = jO.getString("name");
                domain  = jO.getString("domain");
                field   = jO.getString("field");
                address = jO.getString("address");
                desc  = jO.getString("description");
                skill = jO.getString("skill");
//                rate = jO.getString("rating");
            }
            user_name.setText(name);
            user_dom.setText(domain);
            user_address.setText(address);
            user_field.setText(field);
            user_email.setText(mail);
            user_skill.setText(skill);
            user_desc.setText(desc);
//            user_rating.setText(rate+"/5");
        } catch (Exception e) {
            Log.w("people_profile", e.toString());
        }
    }

    public void toggleFollow(View view) {
        Button followButton=(Button) findViewById(R.id.follow);
        String toggleText = followButton.getText().toString();
        connector.connectService("togglefollow.php?action="+toggleText+"&me="+me+"&he="+mail);
        finish();
        startActivity(getIntent());
    }

    public void rateUser(View view) {

        final Dialog d = new Dialog(this);
        d.setContentView(R.layout.rating_layout);
        d.setTitle("User Rating");
//        final float calc_rate = {Float.parseFloat(rate)};
        final RatingBar ratingBar = (RatingBar) d.findViewById(R.id.ratingBar);
        final TextView r = (TextView) findViewById(R.id.people_rating);
        d.show();

            final Button rateButton = (Button) d.findViewById(R.id.okbutton);
            rateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    r.setText(String.valueOf(ratingBar.getRating()));
//                    calc_rate = (calc_rate + ratingBar.getRating()) / 2.0f;
                    d.dismiss();
                }
            });
    }

}
