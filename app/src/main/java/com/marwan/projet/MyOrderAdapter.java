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
 * Created by marwan on 2/7/2016.
 */
public class MyOrderAdapter  extends ArrayAdapter<MyMenuItem> {
    private List<MyMenuItem> items;
    private MyListener listener;

    public MyOrderAdapter (Context context, List<MyMenuItem> items, MyListener listener) {
        super(context, R.layout.order_list_item, items);
        this.items = items;
        this.listener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Log.d("adapter","hhhh");
        if (v == null) {
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.order_list_item, null);
        }
        final MyMenuItem item = items.get(position);
        if (item != null) {
            TextView nameTxt = (TextView) v.findViewById(R.id.onameTxt);
            TextView priceTxt = (TextView) v.findViewById(R.id.opriceTxt);
            ImageView iv = (ImageView) v.findViewById(R.id.BtnAdd);
            /*iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onEvent(item);
                }
            });*/
            if (nameTxt != null) nameTxt.setText(item.getName());
            if (priceTxt != null) {
                NumberFormat nf = NumberFormat.getNumberInstance();
                priceTxt.setText(nf.format(item.getPrice()) + "Dhs");
            }

        }
        return v;
    }
}
