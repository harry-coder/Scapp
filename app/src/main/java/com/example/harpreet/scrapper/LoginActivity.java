package com.example.harpreet.scrapper;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.View;

import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harpreet.scrapper.CustomElements.CustomFontTextView;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;


/**
 * A login screen that offers login via email/password.
 */


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;


import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.example.harpreet.scrapper.volly.MyApplication;
import com.example.harpreet.scrapper.volly.vollySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class LoginActivity extends AppCompatActivity {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView logo;
    private EditText mobile, password;
    private TextView login;
    private TextView signin, forgot;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private vollySingleton volly;
    private RequestQueue queue;
    private String mobileNumber, userpassword;

    private SharedPreferences preferences;
    private String mypref = "usercontact";

    private ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        /*Toolbar tool1 = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tool1);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);
*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        volly = vollySingleton.getInstance();
        queue = vollySingleton.getInstance().getRequestQueue();

        progressDialog = new ProgressDialog(this);

        preferences = getSharedPreferences(mypref, Context.MODE_PRIVATE);

        mobile = (EditText) findViewById(R.id.mobile);
        password = (EditText) findViewById(R.id.password);
        login = (TextView) findViewById(R.id.login);


        logo= (TextView) findViewById(R.id.shimmer_tv);
       /* Shimmer shimmer = new Shimmer();
        shimmer.start(textView);
        shimmer.setDuration(5000);*/
        forgot = (TextView) findViewById(R.id.tv_forgotpassword);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotificationBroadcast();
                mobileNumber = mobile.getText().toString();
                userpassword = password.getText().toString();
                if (!preferences.contains("contact")) {


                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("contact", mobileNumber);

                    editor.apply();


                } else {
                    String mobilecontact = preferences.getString("contact", null);
                    if (mobilecontact != null && !mobilecontact.equals(mobileNumber)) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("contact", mobileNumber);

                        editor.apply();


                    }
                }


                publishComment(mobileNumber, userpassword);


            }
        });


        signin = (TextView) findViewById(R.id.tv_signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //   getFragmentManager().beginTransaction().replace(vv.getId(), new signup()).commit();

                startActivity(new Intent(LoginActivity.this, Signup_Activity.class));
                overridePendingTransition(R.anim.activity_in, R.anim.avtivity_out);


            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, ForgotPassword_Activity.class));
                overridePendingTransition(R.anim.activity_in, R.anim.avtivity_out);


            }
        });


    }

    public void createNotificationBroadcast() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        notificationIntent.addCategory("android.intent.category.DEFAULT");

        int codeRequest = 100;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, codeRequest, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 9);

        cal.set(Calendar.MINUTE,0);


     //   alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.back_in, R.anim.back_out);

    }

    public void ServiceStart() {

        boolean serviceRunning = isMyServiceRunning(Gps_service.class);
        if (!serviceRunning) {
            Intent intent = new Intent(this, Gps_service.class);
            this.startService(intent);
        }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public boolean runTimePermission() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 100);

            return true;
        }
        return false;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                runTimePermission();
            }

        }

    }


    public void publishComment(final String mobile, final String password) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                progressDialog.setMessage("Loading..");

                progressDialog.show();
                final HashMap<String, String> params = new HashMap<String, String>();

                params.put("password", password);
                params.put("mobNum", mobile);


                JsonObjectRequest request_json = new JsonObjectRequest(Request.Method.POST, MyApplication.loginUrl, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    boolean success = response.getBoolean("success");

                                    String message = response.getString("msg");

                                    if (success && !runTimePermission()) {

                                        if (checkLocationService()) {

                                            //avi1.smoothToHide();

                                            progressDialog.dismiss();

                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                            overridePendingTransition(R.anim.activity_in, R.anim.avtivity_out);


                                            ServiceStart();

                                        }

                                    } else {

                                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Credentials check")
                                                .setContentText(message)

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {
                                                        sDialog.dismissWithAnimation();

                                                        progressDialog.dismiss();

                                                    }
                                                })
                                                .show();


                                        //btnSignIn.setProgress(100);
                                        //btnSignIn.setText("Error");
                                        // btnSignIn.setBackgroundColor(Color.RED);
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this, "Volly error" + error, Toast.LENGTH_SHORT).show();
                        System.out.println(error);
                    }
                });


                queue.add(request_json);
            }
        });


    }

    public boolean checkLocationService() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            progressDialog.dismiss();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Please Enable location service first");
            dialog.setPositiveButton("Enable it", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);

                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    checkLocationService();

                    paramDialogInterface.dismiss();

                }
            });
            dialog.show();
        } else {
            return true;
        }

        return false;
    }


}

