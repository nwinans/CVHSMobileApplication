package com.benrcarvergmail.cvhsmobileapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

// ToDo: Thoroughly comment all of this code.

/**
 * Created by Benjamin on 5/3/2016.
 */
public class AcademicsFragment extends Fragment {

    private static final String TAG = "AcademicsFragment";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_academics, container, false);

        // Translation animation
        final Animation animTranslate = AnimationUtils.loadAnimation(getActivity(), R.anim.button_translate);

        // References to the buttons
        Button mButtonClasses = (Button) rootView.findViewById(R.id.button_classes);
        Button mButtonSchedule = (Button) rootView.findViewById(R.id.button_schedule);
        Button mButtonTeachers = (Button) rootView.findViewById(R.id.button_teachers);
        Button mButtonLibrary = (Button) rootView.findViewById(R.id.button_library);

        /*

        Commented out because I dislike how it looks for now.

        mButtonClasses.startAnimation(animTranslate);
        mButtonSchedule.startAnimation(animTranslate);
        mButtonTeachers.startAnimation(animTranslate);
        */

        mButtonClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), ClassesActivity.class);
                startActivity(myIntent);
            }
        });

        mButtonLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Link to FCPS Library Catalogs
                Uri uri = Uri.parse("http://libcat.fcps.edu/uhtbin/cgisirsi/?ps=niWwvVsnMv/305/113560215/60/1182/X");
                Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(myIntent);
            }
        });

        mButtonSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), ScheduleActivity.class);
                startActivity(myIntent);
            }
        });

        mButtonTeachers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "You have unleashed the power of Mountain Dew!", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "Easter Egg found!");

                Intent myIntent = new Intent(getActivity(), TeacherActivity.class);
                startActivity(myIntent);
            }
        });

        return rootView;
    }
}
