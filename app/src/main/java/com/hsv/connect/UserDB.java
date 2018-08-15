package com.hsv.connect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vel Jack on 2/23/2018.
 */

public class UserDB  extends SQLiteOpenHelper {
    private static final String DBNAME = "tempdb";
    private static final String TBLNAME = "user";
    private static final String email   = "email";

    public UserDB(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String q = "create table "+TBLNAME+"("+email+" text)";
        db.execSQL(q);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TBLNAME);
        onCreate(db);
    }
    public void insertId(String s){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put(email,s);
        db.insert(TBLNAME,null,v);
        db.close();
    }
    public void removeId(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TBLNAME,null,null);
        Log.w("reomve_id","all");
        db.close();
    }
    public String getId(){
        String selectQuery = "SELECT  * FROM " + TBLNAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String id=null;
        if(cursor.getCount() == 0){
            return "null";
        }
        else if(cursor.moveToFirst()){
            id =  cursor.getString(0);
            Log.w("get_id",id);
            return id;
        }
        else {
            Log.w("get_id_err",id);
            return "null";
        }
    }
}
