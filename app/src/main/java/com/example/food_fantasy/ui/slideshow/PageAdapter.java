package com.example.food_fantasy.ui.slideshow;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.food_fantasy.ui.dishes.DishesFragment;
import com.example.food_fantasy.ui.tools.ToolsFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int num_of_tabs;
    public PageAdapter(FragmentManager fm , int num_of_tabs) {
        super(fm);
        this.num_of_tabs=num_of_tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new SlideshowFragment();
            case 1:
                return new SlideshowFragment();
             default:
                 return null;
        }
    }

    @Override
    public int getCount() {
        return num_of_tabs;
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}
