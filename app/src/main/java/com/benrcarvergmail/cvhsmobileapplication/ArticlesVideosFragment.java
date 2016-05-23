package com.benrcarvergmail.cvhsmobileapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * Created by Benjamin on 5/20/2016.
 */
public class ArticlesVideosFragment extends Fragment {

    private final static String TAG = "ArticlesVideosFragment";

    private LinearLayout mLinearLayoutArticles;
    private LinearLayout mLinearLayoutVideos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView() for AnnouncementsFragment called.");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_articlesvideos, container, false);

        final Button buttonArticles = (Button) rootView.findViewById(R.id.button_articles);
        final Button buttonVideos = (Button) rootView.findViewById(R.id.button_videos);

        mLinearLayoutArticles = (LinearLayout) rootView.findViewById(R.id.linear_layout_articles);
        mLinearLayoutVideos = (LinearLayout) rootView.findViewById(R.id.linear_layout_videos);

        // Since 'Articles' is selected by default, set the
        // articles button's text color to be the lighter green
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            buttonArticles.setTextColor(getResources().getColor(R.color.colorButtonLightGreen, null));
            buttonVideos.setTextColor(getResources().getColor(R.color.colorButtonDarkGreen, null));
        } else {
            buttonArticles.setTextColor(getResources().getColor(R.color.colorButtonLightGreen));
            buttonVideos.setTextColor(getResources().getColor(R.color.colorButtonDarkGreen));
        }

        buttonArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLinearLayoutArticles.setVisibility(View.VISIBLE);
                mLinearLayoutVideos.setVisibility(View.GONE);

                // Ensure we use non-deprecated code to change the text color
                // to indicate the selected type of content (articles vs videos)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    buttonArticles.setTextColor(getResources().getColor(R.color.colorButtonLightGreen, null));
                    buttonVideos.setTextColor(getResources().getColor(R.color.colorButtonDarkGreen, null));
                } else {
                    buttonArticles.setTextColor(getResources().getColor(R.color.colorButtonLightGreen));
                    buttonVideos.setTextColor(getResources().getColor(R.color.colorButtonDarkGreen));
                }
            }
        });

        buttonVideos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mLinearLayoutArticles.setVisibility(View.GONE);
                mLinearLayoutVideos.setVisibility(View.VISIBLE);

                // Ensure we use non-deprecated code to change the text color
                // to indicate the selected type of content (articles vs videos)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    buttonArticles.setTextColor(getResources().getColor(R.color.colorButtonDarkGreen, null));
                    buttonVideos.setTextColor(getResources().getColor(R.color.colorButtonLightGreen, null));
                } else {
                    buttonArticles.setTextColor(getResources().getColor(R.color.colorButtonDarkGreen));
                    buttonVideos.setTextColor(getResources().getColor(R.color.colorButtonLightGreen));
                }
            }
        });

        return rootView;
    }
}
