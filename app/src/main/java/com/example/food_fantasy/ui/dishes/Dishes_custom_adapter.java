package com.example.food_fantasy.ui.dishes;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.food_fantasy.ApiHit;
import com.example.food_fantasy.ui.dishes.Edit_dish_baker;
import com.example.food_fantasy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.food_fantasy.splash.pref;

public class Dishes_custom_adapter extends BaseAdapter {
    Context c;
    ArrayList<Dishes_template> arr;
    AlertDialog.Builder alert_dialog;
    ApiHit process_for_deldish = new ApiHit();

    public Dishes_custom_adapter(Context c, ArrayList<Dishes_template> arr) {
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
    public View getView(final int i,View view, ViewGroup viewGroup) {
        if (view == null)
            view = LayoutInflater.from(c).inflate(R.layout.dish_baker_template, viewGroup, false);
//            view = LayoutInflater.from(c).inflate(R.layout.dish_baker_template, viewGroup, false);

        final Dishes_template d = (Dishes_template) this.getItem(i);

        ImageView img = (ImageView) view.findViewById(R.id.dishes_img);
        TextView title = (TextView) view.findViewById(R.id.dishes_title);
        TextView des = (TextView) view.findViewById(R.id.dishes_des);
        EditText dish_id = (EditText) view.findViewById(R.id.dish_id);
        ImageView del_icon = (ImageView) view.findViewById(R.id.dishes_del_icon);
        ImageView edit_icon = (ImageView) view.findViewById(R.id.dishes_edit_icon);
        RelativeLayout dish_layout=(RelativeLayout) view.findViewById(R.id.dish_one_view);

        title.setText(d.getDish_title());
//        img.setImageResource(d.getDish_img());
        des.setText(d.getDish_des());

        dish_id.setText(d.getDish_id());

        del_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View del_view) {
                alert_dialog = new AlertDialog.Builder(del_view.getContext());
                alert_dialog.setTitle("Confirmation");
                alert_dialog.setMessage("Do you want to delete this dish?");

                alert_dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        delete_dish(d.getDish_id());
                        arr.remove(i);
                        notifyDataSetChanged();

//                        ((ViewManager)view.getParent()).removeView(view);
//                        view.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                });

                alert_dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alert = alert_dialog.create();
                alert.show();
            }
        });

        edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(c, "Clicked on Edit Button", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(c, Edit_dish_baker.class);
                c.startActivity(i);
            }
        });
        dish_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(c, "Clicked on dish view", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    public  void delete_dish(String dish_id){
        try{

            JSONObject new_dish = new JSONObject();
            try {
                new_dish.put("dish_id", dish_id);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            process_for_deldish.execute("http://testbysindhu.mobifinplus.com/delete_dish_baker.php","delete_dish",new_dish.toString());
        }
        catch(Exception ex)
        {
//            Toast.makeText(getApplicationContext(),"Something went wrong. please restart app",Toast.LENGTH_SHORT).show();
        }
    }
}