package com.example.harpreet.scrapper.volly;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Harpreet on 21/02/2017.
 */

public class vollySingleton {

    static vollySingleton instance = null;
    RequestQueue requestQueue = null;


    private vollySingleton() {
        requestQueue = Volley.newRequestQueue(MyApplication.getContext());


    }

    public static vollySingleton getInstance() {
        if (instance == null) {
            return instance = new vollySingleton();
        }

        return instance;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
