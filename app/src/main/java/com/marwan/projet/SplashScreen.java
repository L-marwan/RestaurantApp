package com.marwan.projet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.marwan.projet.data.DbHelper;
import com.marwan.projet.data.RequestSingleton;
import com.marwan.projet.model.MyMenu;

import org.json.JSONObject;

/**
 * Created by marwan on 2/6/2016.
 */
public class SplashScreen extends Activity{


    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        StartAnimations();
    }
    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l=(RelativeLayout) findViewById(R.id.rellayout);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        TextView iv = (TextView) findViewById(R.id.textFood);
        ImageView im = (ImageView) findViewById(R.id.imgLogo);
        iv.clearAnimation();
        iv.startAnimation(anim);
        im.clearAnimation();
        im.startAnimation(anim);

        ProgressBar spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.startAnimation(anim);

        //do db sync here
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                loadData();

            }},4000);



    }

    private void loadData(){
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, cons.SERVICE_URL + "menu", (String) null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //for debugging
                            Log.d("JSON", response.toString().substring(1, 30));
                            //use Gson to convert json result to MyMenu POJO
                           MyMenu mMenu = new Gson().fromJson(response.toString(), MyMenu.class);
                            //put the data in the database
                            new DbHelper(getApplicationContext()).addItems(mMenu);
                            Log.d("RESPONSE", "got it!!");
                            //done setting up the data, start app
                            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception ex) {

                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //catching errors is for scrubs
                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        //queue the request
        RequestSingleton.getmInstance(this).addToRequestQueue(jsObjRequest);
    }


}
