package com.francois.patrick.projet.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Patrick Francois on 13/08/2018.
 */
public class DataManager implements Serializable {
    private static SharedPreferences mSaveMood;
    private static final String PREF_KEY_MOODS = "PREF_KEY_MOODS", PREF_KEY_COMMENTS = "PREF_KEY_COMMENTS", PREF_KEY_DAY = "PREF_KEY_DAY";
    private static List <Moods> mMoodListData;

    /*** These function is for saved the temporary data(used in MainActivity in onPause() ***/
    public static void SaveDataTemporary(Context context, int mDayOfYear, int mNumColor, String mComment) {
        mSaveMood = context.getSharedPreferences("Moods", Context.MODE_PRIVATE);
        mSaveMood.edit().putInt(PREF_KEY_DAY, mDayOfYear).apply();
        mSaveMood.edit().putInt(PREF_KEY_MOODS, mNumColor).apply();
        mSaveMood.edit().putString(PREF_KEY_COMMENTS, mComment).apply();
    }

        /*** This function is for loaded the temporary data (used in MainActivity in OnResume()) ***/
        public static int LoadMoodTemporary (Context context){
            int mNumColor;
            mSaveMood = context.getSharedPreferences("Moods", Context.MODE_PRIVATE);
            mNumColor = mSaveMood.getInt(PREF_KEY_MOODS, 4);
            return mNumColor;
        }

        /*** This function is for saved data finally (used in MainActivity in onResume()) ***/
        public static int savedMood(Context context) {

            try( FileOutputStream fos = context.openFileOutput("Moods.ser", Context.MODE_PRIVATE );
                 ObjectOutputStream oos = new ObjectOutputStream(fos)){

                oos.writeObject(mMoodListData);
                System.out.println("Saved File");

            }catch (IOException e){ System.out.println("Don't found");}

            return 4;
        }


    public static void loadMoods(Context context) {

            try (FileInputStream fis = context.openFileInput("Moods.ser");
            ObjectInputStream ois = new ObjectInputStream(fis)) {

                mMoodListData = (List<Moods>) ois.readObject();
                mMoodListData.add ((Moods) ois.readObject());

            } catch (FileNotFoundException e1) {
                System.out.print("No moods !");
            } catch (IOException e1) {
                e1.printStackTrace(); System.out.print("IO");
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();System.out.print("class");
            }
    }
}
