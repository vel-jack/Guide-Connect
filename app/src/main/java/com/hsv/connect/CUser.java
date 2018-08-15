package com.hsv.connect;

/**
 * Created by Vel Jack on 1/25/2018.
 */

public class CUser {
    String name=null;
    String email=null;
    String domain=null;
    String field=null;
    String address=null;
    String pass=null;
    public CUser(){}
    public CUser(String name,String email,String domain,String field,String address,String pass){
        this.name = name;
        this.email = email;
        this.domain = domain;
        this.address = address;
        this.field = field;
        this.pass = pass;
    }
    //Set_method
    public void setName(String name){
        this.name = name;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setDomain(String domain){
        this.domain = domain;
    }
    public void setField(String field){
        this.field = field;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setPass(String pass){this.pass = pass;}

    //Get_Methods
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    public String getDomain(){
        return domain;
    }
    public String getField(){
        return field;
    }
    public String getAddress(){
        return address;
    }
    public String getPass(){return pass;}
}