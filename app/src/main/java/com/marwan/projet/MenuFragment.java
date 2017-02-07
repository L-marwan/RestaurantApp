package com.marwan.projet;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;
import com.marwan.projet.data.DbHelper;
import com.marwan.projet.model.MyMenu;
import com.marwan.projet.model.MyMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marwan on 2/6/2016.
 */
public class MenuFragment extends Fragment {
    private ListView listView;
    private static  MyListener listener;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_CAT = "cat";
    private static String category;
    private static DbHelper db;


    public MenuFragment() {

    }



    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MenuFragment newInstance(String cat,Context ctx, MyListener listen) {
        MenuFragment fragment = new MenuFragment();
        category = cat;
        listener = listen;
        db = new DbHelper(ctx.getApplicationContext());
        Bundle args = new Bundle();
        MyMenu myMenu = new MyMenu();
        ArrayList<MyMenuItem> items = db.getItemsByCategory(category);
        myMenu.setItems(items);
        Log.e("NEWINSTANCE",items.get(0).getName());
        //transform the filterd menu to json string to be able to transform it in the bundle
        String json = new Gson().toJson(myMenu);

        args.putString("menu", json);
        fragment.setArguments(args);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        //convert the json string we got from the bundle to get the items for the listview
        String json = getArguments().getString("menu");
        MyMenu m = new Gson().fromJson(json, MyMenu.class);
        initListView(rootView, m.getItems());


        return rootView;
    }

    /**
     * takes care of setting up the listview
     *
     * @param v
     * @param items
     */
    private void initListView(final View v, List<MyMenuItem> items) {
        final Context ctx = v.getContext();
        listView = (ListView) v.findViewById(R.id.list);
        MyMenuAdapter adapter = new MyMenuAdapter(ctx, items,listener );
        listView.setAdapter(adapter);

    }


}
