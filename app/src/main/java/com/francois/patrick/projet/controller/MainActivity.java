package com.francois.patrick.projet.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.francois.patrick.projet.R;

import java.util.ArrayList;
import java.util.Calendar;

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

        /*ViewPager*/

        initViewPager();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i("position", String.valueOf(position));
                SharedPreferences sharedPref = getSharedPreferences("today",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit ();
                editor.putInt ("Mood",position);
                editor.commit ();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViewPager() {
        VerticalViewPager viewPager = (VerticalViewPager) findViewById(R.id.mViewPager);
        String title = "ContentFragment";
        viewPager.setAdapter(new MoodPagerAdapter.Holder(getSupportFragmentManager())
                .add(MoodFragment.newInstance(screen.get(0), smiley.get(0)))
                .add(MoodFragment.newInstance(screen.get(1), smiley.get(1)))
                .add(MoodFragment.newInstance(screen.get(2), smiley.get(2)))
                .add(MoodFragment.newInstance(screen.get(3), smiley.get(3)))
                .add(MoodFragment.newInstance(screen.get(4), smiley.get(4)))
                .set());
    }
}