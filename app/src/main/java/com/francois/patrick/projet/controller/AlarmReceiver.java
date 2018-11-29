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

        SharedPreferences sharedPref = context.getSharedPreferences("Today",Context.MODE_PRIVATE);
        String comment = sharedPref.getString ("comment","");
        int mood = sharedPref.getInt ("mood",0);
        Log.i("comment",comment);
        Log.i("mood",String.valueOf(mood));

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("Moods", String.valueOf(mood));
        editor.apply();


    }

}
