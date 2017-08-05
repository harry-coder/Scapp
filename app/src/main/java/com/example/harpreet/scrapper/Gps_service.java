package com.example.harpreet.scrapper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.harpreet.scrapper.Different_Forms.location;
import com.example.harpreet.scrapper.Different_Forms.login;
import com.example.harpreet.scrapper.volly.MyApplication;
import com.example.harpreet.scrapper.volly.vollySingleton;
import com.google.android.gms.maps.LocationSource;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.example.harpreet.scrapper.volly.MyApplication.getGeoCodingApi;

/**
 * Created by Harpreet on 17/06/2017.
 */

public class Gps_service extends Service {

    LocationManager locationManager;
    LocationListener locationListener;
    private vollySingleton volly;
    private RequestQueue queue;

    private SharedPreferences mobileNumberpref;
    private String mypref = "usercontact";
    DatabaseReference mdatabase;
    String timeToCompare = "06:00";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();

        volly = vollySingleton.getInstance();
        queue = vollySingleton.getInstance().getRequestQueue();
        mobileNumberpref = getSharedPreferences(mypref, MODE_PRIVATE);
        String usercontact = mobileNumberpref.getString("contact", null);

        /*Criteria criteria = new Criteria();
       String provider = locationManager.getBestProvider(criteria, false);
        //noinspection MissingPermission
        Location location1 = locationManager.getLastKnownLocation(provider);
        locationListener.onLocationChanged(location1);
*/

        if (usercontact != null) {
            mdatabase = FirebaseDatabase.getInstance().getReference(usercontact);

        } else {

            mdatabase = FirebaseDatabase.getInstance().getReference("Unknown user");

        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {

               /* Criteria criteria = new Criteria();
              String  provider = locationManager.getBestProvider(criteria, false);
                //noinspection MissingPermission
                Location location1 = locationManager.getLastKnownLocation(provider);
*/


            /*    String currentTime = new SimpleDateFormat("HH:mm").format(new Date());

                 = currentTime.equals(timeToCompare);
*/
                if(new SimpleDateFormat("HH:mm").format(new Date()).equals(timeToCompare))
                {
                    stopSelf();
                    Toast.makeText(Gps_service.this, "Your service has been stopped!!", Toast.LENGTH_SHORT).show();
                }

                //mdatabase.child("Location " + location.getTime()).setValue(location.getLongitude() + "," + location.getLatitude());

                getPlace(location.getLatitude(),location.getLongitude(),location.getTime());

                // System.out.println("Service has started....");

               // Toast.makeText(Gps_service.this, "your Location " + location.getLatitude(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        };

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, locationListener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    public void getPlace(final double lat, final double lon, final long time) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {

                JsonObjectRequest jsoon = new JsonObjectRequest(getGeoCodingApi(lat, lon), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            String res=response.getJSONArray("results").getJSONObject(0).getString("formatted_address");

                            mdatabase.child("Location " + time).setValue(res);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }


                );

                queue.add(jsoon);


                return null;


            }


        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }



    }


