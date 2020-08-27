package com.example.food_fantasy.ui.slideshow;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.food_fantasy.R;
import com.example.food_fantasy.order_details;
import com.example.food_fantasy.ui.dishes.Dishes_template;
import com.example.food_fantasy.ui.dishes.Edit_dish_baker;

import java.util.ArrayList;

public class Slideshow_CustomAdapter extends BaseAdapter {


    Context c;
    ArrayList<SlideshowTemplate> arr;

    public Slideshow_CustomAdapter(Context c, ArrayList<SlideshowTemplate> arr) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(c).inflate(R.layout.myorder_baker_template, viewGroup, false);
//            view = LayoutInflater.from(c).inflate(R.layout.dish_baker_template, viewGroup, false);

        final SlideshowTemplate d = (SlideshowTemplate) this.getItem(i);

       // ImageView img = (ImageView) view.findViewById(R.id.dishes_img);
        TextView order_idI = (TextView) view.findViewById(R.id.order_id);
        TextView dateI = (TextView) view.findViewById(R.id.date);
        TextView amountI = (TextView) view.findViewById(R.id.Total_amount);
        Button details= (Button) view.findViewById(R.id.details);
        RelativeLayout order_layout=(RelativeLayout) view.findViewById(R.id.myorderlayout);

        order_idI.setText(d.getOrder_id());
        dateI.setText(d.getDate_id());
        amountI.setText(d.getTotal_amount());


      details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(c, "Clicked on Edit Button", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(c, order_details.class);
                c.startActivity(i);
            }
        });
        /*dish_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "Clicked on dish view", Toast.LENGTH_SHORT).show();
            }
        });
*/

        return view;
    }
}
