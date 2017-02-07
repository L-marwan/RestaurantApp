package com.marwan.projet.data;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by marwan on 1/10/2016.
 */

/**
 *
 * a singleton class to handle networking
 * according to the google and volley framework docs
 */
public class RequestSingleton {
    private static   RequestSingleton mInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    private RequestSingleton(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public static synchronized RequestSingleton getmInstance (Context context){
        if(mInstance == null){
            mInstance = new RequestSingleton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            //getApplicationContext() keeps from leaking the Activity if someone passes one in
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }

}
