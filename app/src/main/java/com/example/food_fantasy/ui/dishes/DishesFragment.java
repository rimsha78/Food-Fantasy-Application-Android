package com.example.food_fantasy.ui.dishes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.food_fantasy.ApiHit;
import com.example.food_fantasy.R;
import com.example.food_fantasy.home_baker;
import com.example.food_fantasy.ui.dashboard_baker.Dashboard_custom_adapter;
import com.example.food_fantasy.ui.dashboard_baker.Dashboard_template;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import static com.example.food_fantasy.splash.pref;

public class DishesFragment extends Fragment {
    ListView listViewv;
    Dishes_custom_adapter adapter;
    ProgressBar loader;
    private Handler mHandler = new Handler();

    private ApiHit process = new ApiHit();

    private com.example.food_fantasy.ui.dishes.DishesViewModel DishesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        DishesViewModel =
//                ViewModelProviders.of(this).get(DishesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        setHasOptionsMenu(true);
//        final TextView textView = root.findViewById(R.id.text_gallery);
//        DishesViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        loader = root.findViewById(R.id.loader_all_dishes);

        adapter = new Dishes_custom_adapter(getContext(), getData());
        listViewv = (ListView)  root.findViewById(R.id.dis);
        listViewv.setAdapter(adapter);

        return root;
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_dish, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.add_dish){
            Intent i = new Intent(getActivity(), Add_dishes.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList<Dishes_template> getData(){
//
//        try{
//
//            JSONObject new_dish = new JSONObject();
//            try {
//                new_dish.put("baker_id", pref.getString("baker_id", null));
//
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            process.execute("http://testbysindhu.mobifinplus.com/get_dishes_baker.php","all_dishes",new_dish.toString());
//            Log.e("server response","process executed");
//        }
//        catch(Exception ex)
//        {
//            Toast.makeText(getActivity(),"Something went wrong. please restart app",Toast.LENGTH_SHORT).show();
//        }
//        Log.e("server response","timer start");

        ArrayList<Dishes_template> ad = new ArrayList<Dishes_template>();
        String response;
        do{
            response = pref.getString("all_dishes", null);
            if(response == null)
                Log.e("server response","still null");
        }while (response == null);

        Log.e("server response",response);

        try {
            JSONArray jsonArr = new JSONArray(response);

            for (int i = 0; i < jsonArr.length(); i++) {
                JSONObject jsonObj = jsonArr.getJSONObject(i);
                ad.add(new Dishes_template(jsonObj.getString("dish_id") ,jsonObj.getString("title") , jsonObj.getString("description") ));
                Log.e("server response", jsonObj.toString());
            }
        }
        catch (JSONException e){
            Toast.makeText(getActivity(),"Something went wrong. please restart app",Toast.LENGTH_SHORT).show();
        }



//
//        ad.add(new Dishes_template(R.drawable.month_profit, "Month Profit", "quality food" ));
//        ad.add(new Dishes_template(R.drawable.total_profit, "Total Profit", "quality food" ));
//        ad.add(new Dishes_template(R.drawable.current_order, "Current Order", "quality food" ));
//        ad.add(new Dishes_template(R.drawable.current_month, "Current Month", "quality food" ));


        return ad;
    }

    private void removeLoader(){
        Log.e("server response","loader removed function");
        this.loader.setVisibility(View.GONE);
        Log.e("server response","loader removed");
    }
}