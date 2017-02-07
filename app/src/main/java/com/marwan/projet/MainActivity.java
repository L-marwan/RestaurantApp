package com.marwan.projet;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.marwan.projet.data.DbHelper;
import com.marwan.projet.data.RequestSingleton;
import com.marwan.projet.model.MyMenuItem;
import com.marwan.projet.model.Order;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MyListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    private ArrayList<String> categories = new ArrayList<>();
    private ArrayList<MyMenuItem> order = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter. after loading data
        loadData();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                createAndShowOrderDialog();
            }
        });

    }

    private void loadData() {

        DbHelper db = new DbHelper(this);
        categories = db.getCategories();
        //setup the pager and tab layout
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            return MenuFragment.newInstance(categories.get(position), getApplicationContext(),
                    MainActivity.this);
        }

        @Override
        public int getCount() {
            //number of fragments is the number of categories
            return categories.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position < categories.size() && position >= 0) {
                return categories.get(position);
            }
            return null;
        }
    }


    @Override
    public void onEvent(MyMenuItem item) {
        order.add(item);
        Log.e("Listener", "got item " + item.getCategorie());
    }

    private void createAndShowOrderDialog() {
        //costum dialog

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.order_activity);
        dialog.setTitle("Order Items");

        TextView tv = (TextView) dialog.findViewById(R.id.somme);
        tv.setText(124 + "Dh");
        Button btn = (Button) dialog.findViewById(R.id.confirm);
        ListView li = (ListView) dialog.findViewById(R.id.orderList);
        li.setAdapter(new MyOrderAdapter(this, order, this));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order o = new Order();
                o.setState("new");
                o.setTable(4);
                o.setTotal(BigDecimal.valueOf(15.4));
                o.setItems(order);
                postOrder(o);
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void postOrder(Order o) {
        // ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        JSONObject jsn = null;

        jsonString = new Gson().toJson(o);
        try {
            jsn = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("mapper", "done mapping");
        if (jsn != null) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    cons.SERVICE_URL + "post/new", jsn, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("POST", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("POST", error.getMessage());

                }
            });
            RequestSingleton.getmInstance(this).addToRequestQueue(jsonObjectRequest);

        }
    }
}
