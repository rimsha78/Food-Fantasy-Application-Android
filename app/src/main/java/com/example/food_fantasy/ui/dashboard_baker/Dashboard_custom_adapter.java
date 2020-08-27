package com.example.food_fantasy.ui.dashboard_baker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import java.lang.Object;

import com.example.food_fantasy.R;
import com.example.food_fantasy.ui.dashboard_baker.Dashboard_template;


import java.util.ArrayList;

public class Dashboard_custom_adapter extends BaseAdapter {

    Context c;
    ArrayList<Dashboard_template> arr;

    public Dashboard_custom_adapter(Context c, ArrayList<Dashboard_template> arr){
        this.c = c;
        this.arr = arr;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int i) {
        return (Object) arr.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
            view = LayoutInflater.from(c).inflate(R.layout.dashboard_template, viewGroup, false);

        final Dashboard_template d = (Dashboard_template)this.getItem(i);

        ImageView img = (ImageView) view.findViewById(R.id.logo);
        TextView text = (TextView) view.findViewById(R.id.caption);
        CardView card = (CardView) view.findViewById(R.id.dashboard_cardview);

        text.setText(d.getCaption());
        img.setImageResource(d.getLogo());

        card.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(c,d.getCaption(),Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
