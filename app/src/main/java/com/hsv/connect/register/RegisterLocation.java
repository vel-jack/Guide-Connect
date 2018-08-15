package com.hsv.connect.register;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.*;
import android.location.Location;
import android.support.annotation.StringDef;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hsv.connect.R;
import com.hsv.connect.httpservice.ResValues;
import com.hsv.connect.httpservice.WebConnector;
import com.hsv.connect.profile.UserProfile;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.util.Locale.*;

public class RegisterLocation extends AppCompatActivity {
    LocationManager lm;
    double latitude;
    double longitude;
    Geocoder g ;
    String ad;
    String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_location);
        g = new Geocoder(this, Locale.UK);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        mail=(String )b.get("mail");
        ad = "no_address";
    }

    public void pickMe(View view) {
        final TextView locationText = (TextView)findViewById(R.id.locationText);
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            Toast.makeText(getApplicationContext(),"Check your Permissions for the app",Toast.LENGTH_LONG).show();
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        try {
            if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                Toast.makeText(getApplicationContext(),"Network Provider",Toast.LENGTH_SHORT).show();
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude=location.getLatitude();
                        longitude=location.getLongitude();
                        List<Address> locations = null;
                        try{
                            locations=g.getFromLocation(latitude,longitude,1);
                            if(locations.size()!=0){
                                ad = "Address : "+locations.get(0).getAddressLine(0);
                                locationText.setText(ad);
                            }
                        }catch (IOException ex){
                            ex.printStackTrace();
                            Toast.makeText(getApplicationContext(),ex.toString(),Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            } else if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(getApplicationContext(),"GPS Provider",Toast.LENGTH_SHORT).show();
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        locationText.setText(String.valueOf(location.getLatitude())+"\n"+String.valueOf(location.getLongitude()));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Please turn on your Location", Toast.LENGTH_LONG).show();

            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }
    }


    public void registerLocation(View view) {
        String address = "address.php?mail="+mail+"&address="+ad;
        address = address.replaceAll(" ", "%20");
        String myLocation = "updatelocation.php?mail="+mail+"&latitude="+latitude+"&longitude="+longitude;
        String result="";
        ResValues resAdd;
        WebConnector connector = new WebConnector();
        if(ad != "no_address"){
            connector.connectService(myLocation);
            resAdd = connector.connectService(address);
            Log.w("RegLocation", "Domain_class");
            try {
                for (int i = 0; i < resAdd.jArray.length(); i++) {
                    JSONObject jO = resAdd.jArray.getJSONObject(i);
                    result = jO.getString("signup");
                }
                Log.w("JSN LOC parse..",result);
                if(result.equals("failed")){
                    Log.w("status - ",result);
                }else{
                    Log.w("status",result);
                    Intent i = new Intent(RegisterLocation.this,UserProfile.class);
                    startActivity(i);
                    finish();
                }
            } catch (Exception e) {
                Log.w("jsonAtLoc...", e.toString());
            }
            Toast.makeText(getApplicationContext(), "RegisterLocation.java", Toast.LENGTH_SHORT).show();
        }
    }
}
