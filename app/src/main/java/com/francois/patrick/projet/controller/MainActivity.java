package com.francois.patrick.projet.controller;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.francois.patrick.projet.R;
import com.francois.patrick.projet.model.DataManager;
import com.francois.patrick.projet.model.MoodList;
import com.francois.patrick.projet.model.Moods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import me.kaelaela.verticalviewpager.VerticalViewPager;


public class MainActivity extends AppCompatActivity {

    private VerticalViewPager mViewPager;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private ImageView mSmiley;
    private View mScreen;
    static final int MIN_DISTANCE = 150;
    private int mNumColor = 4, mDayOfYear;
    private SharedPreferences mSaveMood;
    private static final String PREF_KEY_MOODS = "PREF_KEY_MOODS", PREF_KEY_COMMENTS = "PREF_KEY_COMMENTS", PREF_KEY_DAY = "PREF_KEY_DAY", PREF_KEY_FIRSTLAUNCH = "PREF_KEY_FIRSTLAUNCH";
    private float y1;

    private MediaPlayer mPlayer = null;
    private ArrayList <Integer> sound;
    public static ArrayList <Integer> smiley;
    public static ArrayList <Integer> screen;


    private void playSound(int resId) {
        if (mPlayer != null) {
            mPlayer.stop();

            mPlayer.release();
        }
        mPlayer = MediaPlayer.create(this, resId);
        mPlayer.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);


        /* Declare all components */
        mViewPager = findViewById(R.id.mViewPager);
        mScreen = findViewById(R.id.screen);

        mSmiley = findViewById(R.id.smiley);


        mSaveMood = getSharedPreferences("Moods", MODE_PRIVATE);


        sound = new ArrayList <>();
        smiley = new ArrayList <>();
        screen = new ArrayList <>();

        /*add sound to the ArrayList */
        sound.add(0, R.raw.oneup);
        sound.add(1, R.raw.niveau_termine);
        sound.add(2, R.raw.hurry_up);
        sound.add(3, R.raw.tuyau);
        sound.add(4, R.raw.game_over);

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


        mViewPager.setAdapter(new MoodPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("position", String.valueOf(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float y2 = event.getY();
                float deltaY = y2 - y1;
                /* Use Math.abs if deltaY < 0 */
                if (Math.abs(deltaY) > MIN_DISTANCE) {
                    if (y2 > y1) {
                        if (mNumColor < 4) {
                            mNumColor++;

                        }
                    } else {
                        if (mNumColor > 0) {
                            mNumColor--;
                        }
                    }
                    mScreen.setBackgroundColor(ContextCompat.getColor(this, screen.get(mNumColor)));
                    mSmiley.setImageResource(smiley.get(mNumColor));
                    playSound(sound.get(mNumColor));
                }

                break;
        }

        return super.onTouchEvent(event);

    }


    /* This method is call when the user start the application or just come back on it.
    @Override
    protected void onResume() {
        super.onResume();
        Calendar mCalendar;

        mCalendar = Calendar.getInstance(Locale.getDefault());
        mDayOfYear = mCalendar.get(Calendar.DAY_OF_YEAR);

        int mDiff = mDayOfYear - mSaveMood.getInt(PREF_KEY_DAY, 999);

        int mFirstLaunch = mSaveMood.getInt(PREF_KEY_FIRSTLAUNCH, 1);

        /* Here we call CheckedData for with two arguments :
         *      - mDiff : the difference between the actual day and the last day where the user was connected
         *      - mFirstLaunch : it's equal 1 if the user launch the application for the first time, else it's
         *                       equal 0
        CheckedData(mDiff, mFirstLaunch);

    }*/

    /*  Here we checked the day of the year for know how long the user have not open the application
     *   , if it upper at the last time, we saved finally the moods with DataManager,
     *   else we loaded the temporary mood.
     *   If the user launch the application for the first time, we just saved the mood temporary
    public void CheckedData(int mDiff, int mFirstLaunch){
        if (mFirstLaunch != 1) {

            mNumColor = mSaveMood.getInt(PREF_KEY_MOODS, 999);
            mComm = mSaveMood.getString(PREF_KEY_COMMENTS, null);

            if (mDiff == 0) {
                mNumColor = DataManager.LoadMoodTemporary(this);
            } else if (mDiff > 1) {
                Moods mMood = new Moods(mSaveMood.getInt(PREF_KEY_DAY, 999), mNumColor, mComm);
                DataManager.LoadMoodTemporary(this);

                MoodList.addMood(mMood);
                mNumColor = DataManager.savedMood(this);

                for (int i = (mSaveMood.getInt(PREF_KEY_DAY, 999) + 1); i <= (mDayOfYear - 1); i++) {
                    Moods mMoodBase = new Moods(i, 4, null);
                    DataManager.loadMoods(this);
                    MoodList.addMood(mMoodBase);
                    DataManager.savedMood(this);
                }
                mSaveMood.edit().putInt(PREF_KEY_MOODS, mNumColor).apply();
                mSaveMood.edit().putString(PREF_KEY_COMMENTS, null).apply();
                mSaveMood.edit().putInt(PREF_KEY_DAY, mDayOfYear).apply();
                mComm = null;
            } else {

                Moods mMood = new Moods(mDayOfYear - 1, mNumColor, mComm);
                DataManager.loadMoods(this);

                MoodList.addMood(mMood);

                mNumColor = DataManager.savedMood(this);
                mSaveMood.edit().putString(PREF_KEY_COMMENTS, null).apply();
                mComm = null;
            }

            mScreen.setBackgroundColor(ContextCompat.getColor(this, screen.get(mNumColor)));
            mSmiley.setImageResource(smiley.get(mNumColor));
        }
        else {
            mSaveMood.edit().putInt(PREF_KEY_FIRSTLAUNCH, 0).apply();
        }
    }*/

    }
