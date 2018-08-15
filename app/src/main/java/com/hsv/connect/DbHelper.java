package com.hsv.connect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vel Jack on 1/25/2018.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "clite";
    private static final String TBLNAME = "clite";

    private static final String k_name    = "c_name";
    private static final String k_email   = "c_email";
    private static final String k_domain  = "c_domain";
    private static final String k_field   = "c_field";
    private static final String k_address = "c_address";
    private static final String k_pass    = "c_pass";

    public DbHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String user_detail = "create table "+TBLNAME+"("+k_email+" text,"+k_name+" text,"+k_domain+" text,"+k_field+" text,"+k_address+" text,"+k_pass+" text)";
        db.execSQL(user_detail);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TBLNAME);
        onCreate(db);
    }
    
    public void userDetail(CUser c_u){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(k_email,c_u.getEmail());
        v.put(k_name,c_u.getName());
        v.put(k_domain,c_u.getDomain());
        v.put(k_field,c_u.getField());
        v.put(k_address,c_u.getAddress());
        v.put(k_pass,c_u.getPass());
        db.insert(TBLNAME,null,v);
        db.close();
    }

    public void removeDetail(CUser c_u){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBLNAME, k_email + " = ?",
                new String[] { String.valueOf(c_u.getEmail())});
        db.close();
    }
    public void update(CUser c_u){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(k_email,c_u.getEmail());
        v.put(k_name,c_u.getName());
        v.put(k_domain,c_u.getDomain());
        v.put(k_field,c_u.getField());
        v.put(k_address,c_u.getAddress());
        v.put(k_pass,c_u.getPass());
        db.update(TBLNAME,v,k_email+"=?",new String[] { String.valueOf(c_u.getEmail())});
        db.close();
    }

    //Get_USER_from_SQLITE
    public CUser getUser(){
        String selectQuery = "SELECT  * FROM " + TBLNAME;
        CUser r_user = new CUser();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            r_user.email   = cursor.getString(0);
            r_user.name    = cursor.getString(1);
            r_user.domain  = cursor.getString(2);
            r_user.field   = cursor.getString(3);
            r_user.address = cursor.getString(4);
            r_user.pass    = cursor.getString(5);
            return r_user;
        }
        else {
            return r_user;
        }
    }
}