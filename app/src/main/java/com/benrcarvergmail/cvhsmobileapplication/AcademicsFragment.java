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
        Commented out until the views are added to the layout xml file
        
        Button mButtonAthletics = (Button) rootView.findViewById(R.id.button_athletics);
        Button mButtonBlackboard = (Button) rootView.findViewById(R.id.button_blackboard);
        Button mButtonSIS = (Button) rootView.findViewById(R.id.button_sis);
        Button mButtonBellSchedule = (Button) rootView.findViewById(R.id.button_bellSchedule);
        */
        
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
                Uri uri = Uri.parse("http://libcat.fcps.edu/uhtbin/cgisirsi/x/0/0/57/49?user_id=410WEB");
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
                
                Intent myIntent = new Intent(getActivity(), TeacherActivity.class);
                startActivity(myIntent);
            }
        });
        
        /*
        mButtonAthletics.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                //ToDo: implement ChromeCustomTabs so the user doesn't have to leave the application to open a link
                Uri uri = Uri.parse("http://www.wearecville.com");
                Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(myIntent);
            }
        }
        mButtonBlackboard.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                //ToDo: implement ChromeCustomTabs so the user doesn't have to leave the application to open a link
                Uri uri = Uri.parse("http://fcps.blackboard.com");
                Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(myIntent);
            }
        }
        mButtonSIS.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                //ToDo: implement ChromeCustomTabs so the user doesn't have to leave the application to open a link
                Uri uri = Uri.parse("http://www.wearecville.com");
                Intent myIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(myIntent);
            }
        }
        mButtonBellSchedule.setOnClickListener(new View.OnClickListener() {
            @Override 
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), BellSchedule.class);
                startActivity(myIntent);
            }
        }
        */

        return rootView;
    }
}
