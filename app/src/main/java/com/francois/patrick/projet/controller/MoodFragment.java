package com.francois.patrick.projet.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.francois.patrick.projet.R;

/**
 * Created by Patrick Francois on 29/10/2018.
 */
public class MoodFragment extends Fragment {

    private String mComm;

    public static MoodFragment newInstance (int color,int smiley){

        Bundle args = new Bundle();
        args.putInt("color", color);
        args.putInt("smiley", smiley);
        MoodFragment fragment = new MoodFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.mood, container, false);
        ImageView smiley = v.findViewById(R.id.smiley);
        smiley.setImageDrawable(getResources().getDrawable(getArguments().getInt("smiley")));
        RelativeLayout screen = v.findViewById(R.id.screen);
        screen.setBackgroundColor(getResources().getColor(getArguments().getInt("color")));

        ImageButton mAddComm = v.findViewById(R.id.add_comm);
        ImageButton mHistoric = v.findViewById(R.id.history);

        /* Add listener on ImageButton mAddComm for open pop up with EditBox for input commentary */
        mAddComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText mInputComm = new EditText(getActivity());
                final AlertDialog.Builder mDialogAddComm = new AlertDialog.Builder(getActivity()).setMessage("Comment").setView(mInputComm).setPositiveButton("Valid", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mComm = mInputComm.getText().toString();
                        SharedPreferences sharedPref = getActivity ().getSharedPreferences("today",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit ();
                        editor.putString ("Comment",mComm);
                        editor.commit ();
                    }
                });
                mDialogAddComm.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                mDialogAddComm.show();
            }
        });

        /* Add listener on ImageButton Historic for open List Historic activity */
        mHistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), ListHistoricActivity.class);
                startActivity(mIntent);
            }
        });
        return v;

    }

}