package com.francois.patrick.projet.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.francois.patrick.projet.R;

import java.util.Calendar;

import me.kaelaela.verticalviewpager.VerticalViewPager;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);

        if (alarmMgr != null) {
            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

        }
        /* Declare all components */
        VerticalViewPager mViewPager = findViewById(R.id.mViewPager);





        /*ViewPager*/

        initViewPager();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("position", String.valueOf(position));
                SharedPreferences sharedPref = getSharedPreferences("today", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("Mood", position);
                editor.apply();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViewPager() {
        VerticalViewPager viewPager = findViewById(R.id.mViewPager);
        viewPager.setAdapter(new MoodPagerAdapter(getSupportFragmentManager()));

    }
}