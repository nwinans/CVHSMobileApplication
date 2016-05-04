package com.benrcarvergmail.cvhsmobileapplication;

import android.content.DialogInterface;
import android.content.Intent;
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

    // References to the buttons
    private Button mButtonClasses;
    private Button mButtonSchedule;
    private Button mButtonTeachers;

    private static final String TAG = "AcademicsFragment";

    // Constructor
    public AcademicsFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_academics, container, false);

        // Translation animation
        final Animation animTranslate = AnimationUtils.loadAnimation(getActivity(), R.anim.button_translate);

        mButtonClasses = (Button) rootView.findViewById(R.id.button_classes);
        mButtonSchedule = (Button) rootView.findViewById(R.id.button_schedule);
        mButtonTeachers = (Button) rootView.findViewById(R.id.button_teachers);

        /*

        Commented out because I dislike how it looks for now.

        mButtonClasses.startAnimation(animTranslate);
        mButtonSchedule.startAnimation(animTranslate);
        mButtonTeachers.startAnimation(animTranslate);
        */

        mButtonClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                AlertDialog.Builder alertAdd = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                final View view = inflater.inflate(R.layout.easteregg_alerts_dialog, null);
                alertAdd.setView(view);
                alertAdd.setNeutralButton("Escape the Memes!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int integer) {
                        Log.i(TAG, "Easter Egg exited.");
                    }
                });

                final ImageView mGlowingSmall = (ImageView) view.findViewById(R.id.image_view_oliver_small);
                final ImageView mRyleyTrahan = (ImageView) view.findViewById(R.id.image_view_ryley_trahan);
                final ImageView mNealJarvis = (ImageView) view.findViewById(R.id.image_view_neal_jarvis);
                final ImageView mBarakObama = (ImageView) view.findViewById(R.id.image_view_barack_obama);

                mGlowingSmall.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Random RNG = new Random();
                        int picture = RNG.nextInt(4);
                        if (picture == 3) {
                            mBarakObama.setVisibility(View.VISIBLE);
                            mNealJarvis.setVisibility(View.GONE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mRyleyTrahan.setVisibility(View.GONE);
                        } else if (picture == 2) {
                            mNealJarvis.setVisibility(View.VISIBLE);
                            mBarakObama.setVisibility(View.GONE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mRyleyTrahan.setVisibility(View.GONE);
                        } else if (picture == 1) {
                            mRyleyTrahan.setVisibility(View.VISIBLE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mBarakObama.setVisibility(View.GONE);
                            mNealJarvis.setVisibility(View.GONE);
                        } else if (picture == 0) {
                            mGlowingSmall.setVisibility(View.VISIBLE);
                            mRyleyTrahan.setVisibility(View.GONE);
                            mBarakObama.setVisibility(View.GONE);
                            mNealJarvis.setVisibility(View.GONE);
                        }
                    }
                });

                mRyleyTrahan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Random RNG = new Random();
                        int picture = RNG.nextInt(4);
                        if (picture == 3) {
                            mBarakObama.setVisibility(View.VISIBLE);
                            mNealJarvis.setVisibility(View.GONE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mRyleyTrahan.setVisibility(View.GONE);
                        } else if (picture == 2) {
                            mNealJarvis.setVisibility(View.VISIBLE);
                            mBarakObama.setVisibility(View.GONE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mRyleyTrahan.setVisibility(View.GONE);
                        } else if (picture == 1) {
                            mRyleyTrahan.setVisibility(View.VISIBLE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mBarakObama.setVisibility(View.GONE);
                            mNealJarvis.setVisibility(View.GONE);
                        } else if (picture == 0) {
                            mGlowingSmall.setVisibility(View.VISIBLE);
                            mRyleyTrahan.setVisibility(View.GONE);
                            mBarakObama.setVisibility(View.GONE);
                            mNealJarvis.setVisibility(View.GONE);
                        }
                    }
                });

                mNealJarvis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Random RNG = new Random();
                        int picture = RNG.nextInt(4);
                        if (picture == 3) {
                            mBarakObama.setVisibility(View.VISIBLE);
                            mNealJarvis.setVisibility(View.GONE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mRyleyTrahan.setVisibility(View.GONE);
                        } else if (picture == 2) {
                            mNealJarvis.setVisibility(View.VISIBLE);
                            mBarakObama.setVisibility(View.GONE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mRyleyTrahan.setVisibility(View.GONE);
                        } else if (picture == 1) {
                            mRyleyTrahan.setVisibility(View.VISIBLE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mBarakObama.setVisibility(View.GONE);
                            mNealJarvis.setVisibility(View.GONE);
                        } else if (picture == 0) {
                            mGlowingSmall.setVisibility(View.VISIBLE);
                            mRyleyTrahan.setVisibility(View.GONE);
                            mBarakObama.setVisibility(View.GONE);
                            mNealJarvis.setVisibility(View.GONE);
                        }
                    }
                });

                mBarakObama.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Random RNG = new Random();
                        int picture = RNG.nextInt(4);
                        if (picture == 3) {
                            mBarakObama.setVisibility(View.VISIBLE);
                            mNealJarvis.setVisibility(View.GONE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mRyleyTrahan.setVisibility(View.GONE);
                        } else if (picture == 2) {
                            mNealJarvis.setVisibility(View.VISIBLE);
                            mBarakObama.setVisibility(View.GONE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mRyleyTrahan.setVisibility(View.GONE);
                        } else if (picture == 1) {
                            mRyleyTrahan.setVisibility(View.VISIBLE);
                            mGlowingSmall.setVisibility(View.GONE);
                            mBarakObama.setVisibility(View.GONE);
                            mNealJarvis.setVisibility(View.GONE);
                        } else if (picture == 0) {
                            mGlowingSmall.setVisibility(View.VISIBLE);
                            mRyleyTrahan.setVisibility(View.GONE);
                            mBarakObama.setVisibility(View.GONE);
                            mNealJarvis.setVisibility(View.GONE);
                        }
                    }
                });

                alertAdd.show();
            }
        });

        return rootView;
    }
}
