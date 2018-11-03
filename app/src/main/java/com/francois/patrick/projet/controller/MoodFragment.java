package com.francois.patrick.projet.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
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

    private static int mSmiley;
    private static int mColor;
    private ImageButton mAddComm;
    private ImageButton mHistoric;
    private String mComm;

    public static MoodFragment newInstance (int color,int smiley){
        MoodFragment moodFragment = new MoodFragment();
        mSmiley = smiley;
        mColor = color;
        return moodFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.mood, container, false);
        ImageView smiley = v.findViewById(R.id.smiley);
        smiley.setImageDrawable(getResources().getDrawable(mSmiley));
        RelativeLayout screen = v.findViewById(R.id.screen);
        screen.setBackgroundColor(getResources().getColor(mColor));

        mAddComm = v.findViewById(R.id.add_comm);
        mHistoric = v.findViewById(R.id.history);

        /* Add listener on ImageButton mAddComm for open pop up with EditBox for input commentary */
        mAddComm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText mInputComm = new EditText(getActivity());
                final AlertDialog.Builder mDialogAddComm = new AlertDialog.Builder(getActivity()).setMessage("Commentaries").setView(mInputComm).setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mComm = mInputComm.getText().toString();
                    }
                });
                mDialogAddComm.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
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
                Intent mIntent = new Intent(getActivity(), List_Historic.class);
                startActivity(mIntent);
            }
        });
        return v;

    }

}
