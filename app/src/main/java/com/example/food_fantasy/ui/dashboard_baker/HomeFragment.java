package com.example.food_fantasy.ui.dashboard_baker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.food_fantasy.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    GridView gv;
    Dashboard_custom_adapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        View rootForDash = inflater.inflate(R.layout.dashboard_template, container, false);

        adapter = new Dashboard_custom_adapter(getContext(), getData());
        gv = (GridView)  root.findViewById(R.id.gv);
        gv.setAdapter(adapter);

//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel.class);
//
//        final TextView textView = root.findViewById(R.id.caption);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    public ArrayList<Dashboard_template> getData(){
        ArrayList<Dashboard_template> ad = new ArrayList<Dashboard_template>();

        ad.add(new Dashboard_template(R.drawable.total_sales, "Total Sale"));
        ad.add(new Dashboard_template(R.drawable.month_profit, "Month Profit"));
        ad.add(new Dashboard_template(R.drawable.total_profit, "Total Profit"));
        ad.add(new Dashboard_template(R.drawable.current_order, "Current Order"));
        ad.add(new Dashboard_template(R.drawable.current_month, "Current Month"));
        ad.add(new Dashboard_template(R.drawable.total_orders, "Total Order"));

        return ad;
    }
}