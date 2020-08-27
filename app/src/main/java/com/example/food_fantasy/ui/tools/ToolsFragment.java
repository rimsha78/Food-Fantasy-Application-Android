package com.example.food_fantasy.ui.tools;


import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.food_fantasy.R;
import com.example.food_fantasy.ui.tools.editProfile;
import com.example.food_fantasy.ui.dashboard_baker.HomeFragment;
import com.example.food_fantasy.ui.login.LoginActivity;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_tools, container, false);

        final Button edit = view.findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(c, "Clicked on Edit Button", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), editProfile.class);
                startActivity(i);
            }
        });


        return view;
    }
}