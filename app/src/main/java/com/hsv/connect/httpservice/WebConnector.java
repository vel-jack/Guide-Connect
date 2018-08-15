package com.hsv.connect.httpservice;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Vel Jack on 2/21/2018.
 */

public class WebConnector {

    public ResValues connectService(String urlextra){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Log.w("Service  :","Start...");
        InputStream is     = null;
        String      line   = null;
        String      result = null;
        String      url    = "http://hsv.000webhostapp.com/"+urlextra;
        //logincheck.php?mail=v @mail.com&pass=v@mail.com";
        StringBuilder sb;
        Log.w("url  :",url);
        ResValues rv = new ResValues();
        try{
            HttpClient   hc     = new DefaultHttpClient();
            HttpPost     req    = new HttpPost(url);
            HttpResponse res    = hc.execute(req);
            HttpEntity   entity = res.getEntity();
            is = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
            sb = new StringBuilder();
            while((line = reader.readLine()) != null){
                sb.append(line+"\n");
            }
            is.close();
            result = sb.toString();
            JSONObject jsonObject = new JSONObject(result);
            Log.w("json_obj :",jsonObject.toString());
            JSONArray  jsonArray  = jsonObject.getJSONArray("result");
            rv.jArray = jsonArray;
            return rv;
        }catch (Exception e){
            Log.w("Exception  :",e.toString());
        }
        return null;
    }
}
