package com.francois.patrick.projet.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.francois.patrick.projet.R;

import java.util.ArrayList;

/**
 * Created by Patrick Francois on 29/10/2018.
 */
public class MoodPagerAdapter extends FragmentPagerAdapter {


    private static int NUM_ITEMS = 5;
    public static ArrayList <Integer> smiley;
    public static ArrayList <Integer> screen;

    public MoodPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        smiley = new ArrayList <>();
        screen = new ArrayList <>();

        /* add image to the ArrayList */
        smiley.add(0, R.drawable.smiley_happy);
        smiley.add(1, R.drawable.smiley_super_happy);
        smiley.add(2, R.drawable.smiley_normal);
        smiley.add(3, R.drawable.smiley_disappointed);
        smiley.add(4, R.drawable.smiley_sad);

        /* add colors to the ArrayList */
        screen.add(0, R.color.light_sage);
        screen.add(1, R.color.banana_yellow);
        screen.add(2, R.color.cornflower_blue_65);
        screen.add(3, R.color.warm_grey);
        screen.add(4, R.color.faded_red);


    }
    @Override
    public Fragment getItem(int position) {
        return MoodFragment.newInstance(screen.get(position), smiley.get(position));
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

}