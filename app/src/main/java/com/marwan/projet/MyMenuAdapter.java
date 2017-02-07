package com.marwan.projet;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marwan.projet.model.MyMenuItem;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by marwan on 1/10/2016.
 */
public class MyMenuAdapter extends ArrayAdapter<MyMenuItem> {
    private List<MyMenuItem> items;
    private MyListener listener;

    public MyMenuAdapter(Context context, List<MyMenuItem> items, MyListener listener) {
        super(context, R.layout.list_inner_view, items);
        this.items = items;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Log.d("adapter","hhhh");
        if (v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.list_inner_view, null);
        }
        final MyMenuItem item = items.get(position);
        if (item != null) {
            TextView nameTxt = (TextView) v.findViewById(R.id.nameTxt);
            TextView descTxt = (TextView) v.findViewById(R.id.descText);
            TextView priceTxt = (TextView) v.findViewById(R.id.priceTxt);
            ImageView iv = (ImageView) v.findViewById(R.id.BtnAdd);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEvent(item);
                }
            });
            if (nameTxt != null) nameTxt.setText(item.getName());
            if (descTxt != null) descTxt.setText(item.getDesc().substring(0,25));
            if (priceTxt != null) {
                NumberFormat nf = NumberFormat.getNumberInstance();
                priceTxt.setText(nf.format(item.getPrice()) + "Dhs");
            }

        }
        return v;
    }
}