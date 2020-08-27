package com.example.food_fantasy.ui.slideshow;

import android.os.Bundle;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.food_fantasy.R;
import com.example.food_fantasy.ui.dishes.Dishes_custom_adapter;
import com.example.food_fantasy.ui.dishes.Dishes_template;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {
    ListView listViewv;
    Slideshow_CustomAdapter adapter;
    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);*/
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        //final TextView textView = root.findViewById(R.id.text_slideshow);
        //slideshowViewModel.getText().observe(this, new Observer<String>() {
            /*@Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }*/
       // });
        adapter = new Slideshow_CustomAdapter(getContext(), getData());
        listViewv = (ListView)  root.findViewById(R.id.dis);
        listViewv.setAdapter(adapter);

        return root;
    }

    public ArrayList<SlideshowTemplate> getData(){
        ArrayList<SlideshowTemplate> ad = new ArrayList<SlideshowTemplate>();

        ad.add(new SlideshowTemplate("Order Id 27","Date 27-feb-2018","Amount 10" ));
        ad.add(new SlideshowTemplate("Order Id 28","Date 27-feb-2018","Amount 10" ));
        ad.add(new SlideshowTemplate("Order Id 29","Date 27-feb-2018","Amount 10" ));
        ad.add(new SlideshowTemplate("Order Id 30","Date 27-feb-2018","Amount 10" ));
        return ad;
    }
}