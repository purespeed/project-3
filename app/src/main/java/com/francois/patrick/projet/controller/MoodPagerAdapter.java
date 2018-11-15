package com.francois.patrick.projet.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Patrick Francois on 29/10/2018.
 */
public class MoodPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 5;

    private List<Fragment> fragments = new ArrayList<>();

    public MoodPagerAdapter(FragmentManager fragmentManager,  List<Fragment> fragments) {
        super(fragmentManager);

        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public static class Holder {

        private final List<Fragment> fragments = new ArrayList<>();
        private FragmentManager manager;

        public Holder(FragmentManager manager) {
            this.manager = manager;
        }

        public Holder add(Fragment f) {
            fragments.add(f);
            return this;
        }

        public MoodPagerAdapter set() {
            return new MoodPagerAdapter(manager, fragments);
        }
    }
}