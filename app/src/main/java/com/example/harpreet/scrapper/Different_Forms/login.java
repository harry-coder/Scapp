package com.example.harpreet.scrapper.Different_Forms;


import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.app.Fragment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.harpreet.scrapper.Gps_service;
import com.example.harpreet.scrapper.MainActivity;
import com.example.harpreet.scrapper.R;
import com.example.harpreet.scrapper.volly.MyApplication;
import com.example.harpreet.scrapper.volly.vollySingleton;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended.SLIDING;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class login extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AVLoadingIndicatorView avi1;

    private EditText mobile, password;
    private Button login;
    private TextView signin,forgot;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private vollySingleton volly;
    private RequestQueue queue;
    private String mobileNumber, userpassword;

    private SharedPreferences preferences;
    private String mypref = "usercontact";
    Toolbar tool;
    public login() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static login newInstance(String param1, String param2) {
        login fragment = new login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();


        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(false);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
         tool= (Toolbar) getActivity().findViewById(R.id.toolbar);

        volly = vollySingleton.getInstance();
        queue = vollySingleton.getInstance().getRequestQueue();


        preferences = getActivity().getSharedPreferences(mypref, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

      //  final View vv = getActivity().findViewById(R.id.frame_include);
        View view = inflater.inflate(R.layout.login, container, false);
        mobile = (EditText) view.findViewById(R.id.mobile);
        password = (EditText) view.findViewById(R.id.password);
        login = (Button) view.findViewById(R.id.login);
        avi1 = (AVLoadingIndicatorView) view.findViewById(R.id.avi1);

        forgot= (TextView) view.findViewById(R.id.forgot);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mobileNumber = mobile.getText().toString();
                userpassword = password.getText().toString();
                if (!preferences.contains("contact")) {



                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("contact", mobileNumber);

                        editor.apply();


                }
                else
                {
                    String mobilecontact=preferences.getString("contact",null);
                    if (mobilecontact != null && !mobilecontact.equals(mobileNumber)) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("contact", mobileNumber);

                        editor.apply();


                    }
                }


                publishComment(mobileNumber, userpassword);


            }
        });


        signin = (TextView) view.findViewById(R.id.signin);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


           //     getFragmentManager().beginTransaction().replace(vv.getId(), new signup()).commit();





            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         //    getFragmentManager().beginTransaction().replace(vv.getId(),new ForgotPassword()).commit();


            }
        });



        return view;
    }

    public void ServiceStart() {

     boolean serviceRunning=   isMyServiceRunning(Gps_service.class);
       if(!serviceRunning) {
           Intent intent = new Intent(getActivity(), Gps_service.class);
           getActivity().startService(intent);
       }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



    public boolean runTimePermission() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
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
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();

            } else {
                runTimePermission();
            }

        }

    }


    public void publishComment(final String mobile, final String password) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                avi1.setVisibility(View.VISIBLE);
                avi1.smoothToShow();
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

                                       if(checkLocationService()) {

                                           avi1.smoothToHide();
                                           ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), null);

                                           startActivity(new Intent(getActivity(), MainActivity.class), compat.toBundle());


                                           ServiceStart();

                                       }

                                    } else {

                                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                                .setTitleText("Credentials check")
                                                .setContentText(message)

                                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                    @Override
                                                    public void onClick(SweetAlertDialog sDialog) {
                                                        sDialog.dismissWithAnimation();

                                                        avi1.smoothToHide();
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
                        Toast.makeText(getActivity(), "Volly error" + error, Toast.LENGTH_SHORT).show();
                        System.out.println(error);
                    }
                });


                queue.add(request_json);
            }
        });


    }

    public boolean checkLocationService()
    {
        LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            avi1.smoothToHide();
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setMessage("Please Enable location service first");
            dialog.setPositiveButton("Enable it", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getActivity().startActivity(myIntent);

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
        }
        else {
            return true;
        }

    return false;
    }




}
