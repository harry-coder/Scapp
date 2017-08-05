package com.example.harpreet.scrapper.volly;

import android.content.Context;

/**
 * Created by Harpreet on 21/02/2017.
 */

public class MyApplication extends android.app.Application {

    static MyApplication application = null;


    public static String sheet1="https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1NNSNxBX6mvjlVxf3Hv04f74QXPXFTNRuwPYpr8_wW7w&sheet=15";
    public static String sheet2="https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1NNSNxBX6mvjlVxf3Hv04f74QXPXFTNRuwPYpr8_wW7w&sheet=30";
    public static String sheet3="https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1NNSNxBX6mvjlVxf3Hv04f74QXPXFTNRuwPYpr8_wW7w&sheet=45";
    public static String sheet4="https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1NNSNxBX6mvjlVxf3Hv04f74QXPXFTNRuwPYpr8_wW7w&sheet=60";
    public static String sheet5="https://script.google.com/macros/s/AKfycbygukdW3tt8sCPcFDlkMnMuNu9bH5fpt7bKV50p2bM/exec?id=1NNSNxBX6mvjlVxf3Hv04f74QXPXFTNRuwPYpr8_wW7w&sheet=75";
    public static String sheet6="";

    public static String signupUrl="http://capp-api.okhlee.com/api/scrapper/user/register";
    public static String loginUrl="http://capp-api.okhlee.com/api/scrapper/user/login";

    public static String verifyUrl="http://capp-api.okhlee.com/api/scrapper/user/verify-register-otp";


    public static String forgotpassword="http://capp-api.okhlee.com/api/scrapper/user/forgot";

    public static String verifyforgototp=" http://capp-api.okhlee.com/api/scrapper/user/verify-forgot-otp";


   // public static String geocodingapi="https://maps.googleapis.com/maps/api/geocode/json?latlng=40.714224,-73.961452&key=

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
    public static String  getGeoCodingApi(double lat,double lon)
    {

        return "https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+""+lon+"&key="+"AIzaSyCDVqkvSQpLf9NQTfUAHvs9FS-0vp0J7aI";

    }




    public static MyApplication getInstance() {
        return application;
    }

    public static Context getContext() {
        return application.getApplicationContext();
    }
}
