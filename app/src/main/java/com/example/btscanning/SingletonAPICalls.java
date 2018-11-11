package com.example.btscanning;

import android.content.Context;
import com.android.volley.*;
import com.android.volley.toolbox.Volley;


public class SingletonAPICalls {

    private static SingletonAPICalls mInstance;
    private RequestQueue mRequestQueue;

    private SingletonAPICalls(Context context) {
        mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized SingletonAPICalls getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SingletonAPICalls(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }



}
