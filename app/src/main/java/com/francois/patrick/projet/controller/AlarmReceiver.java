package com.francois.patrick.projet.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Patrick Francois on 29/10/2018.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("whatever","Alexandre");

        SharedPreferences sharedPref = context.getSharedPreferences("Moods",Context.MODE_PRIVATE);
        /*
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Moods", jsonString);
        editor.commit();*/

        String listMoods = sharedPref.getString("Moods", "");

    }

}
