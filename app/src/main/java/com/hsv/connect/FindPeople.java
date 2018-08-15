package com.hsv.connect;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hsv.connect.httpservice.ResValues;
import com.hsv.connect.httpservice.WebConnector;
import com.hsv.connect.profile.PeopleProfile;

import org.json.JSONObject;


public class FindPeople extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ResValues resValues,myLoc;
    WebConnector connector;
    String me;
    double latt,longt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_people);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        connector = new WebConnector();

//        try {
//            UserDB db = new UserDB(this);
//            me= db.getId();
//        }catch (Exception e){
//            Log.w("Ex",e.toString());
//        }

        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        String domain=(String )b.get("domain");
        String field =(String)b.get("field");
        resValues = connector.connectService("findpeople.php?domain="+domain+"&field="+field);
        //myLoc = connector.connectService("mylocation.php?mail="+me);

//        try {
//            for (int i = 0; i < myLoc.jArray.length(); i++) {
//                JSONObject jO = myLoc.jArray.getJSONObject(i);
//                // jO.getString("name");
//                latt = Double.parseDouble(jO.getString("latitude"));
//                longt = Double.parseDouble(jO.getString("longitude"));
//                Marker m = mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(latt,longt))
//                        .title(jO.getString("emailid"))
//                        .snippet(jO.getString("name")+" ("+jO.getString("skill")+")")
//
//                );
//                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(latt,longt)));
//                mMap.animateCamera(CameraUpdateFactory.zoomIn());
//                mMap.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
//            }
//        } catch (Exception e) {
//            Log.w("Markers Error", e.toString());
//        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (googleMap != null) {

            // More info: https://developers.google.com/maps/documentation/android/infowindows
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    // Determine what marker is clicked by using the argument passed in
                    // for example, marker.getTitle() or marker.getSnippet().
                    // Code here for navigating to fragment activity.
                    Intent i =new Intent(FindPeople.this, PeopleProfile.class);
                    i.putExtra("mail",marker.getTitle());
                    startActivity(i);
                }
            });
        }
//        Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//////////////////////////////////////////////////////////////////////////////
//        ArrayList<LatLng> latLngs = new ArrayList<>();
//        MarkerOptions options = new MarkerOptions();
        double x,y;
        try {
            for (int i = 0; i < resValues.jArray.length(); i++) {
                JSONObject jO = resValues.jArray.getJSONObject(i);
                    // jO.getString("name");
                x = Double.parseDouble(jO.getString("latitude"));
                y = Double.parseDouble(jO.getString("longitude"));
                Marker m = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(x,y))
                .title(jO.getString("emailid"))
                .snippet(jO.getString("name")+" ("+jO.getString("skill")+")")
                );
                m.getId();
            }
        } catch (Exception e) {
            Log.w("Markers Error", e.toString());
        }

/////////////////////////////////////////////////////////////////////////////


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
    }




}
