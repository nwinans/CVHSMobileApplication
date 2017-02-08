package com.benrcarvergmail.cvhsmobileapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Yoohan on 5/31/2016.
 */
public class ArticlesVideosFragment extends Fragment {

    private final static String TAG = "ArticlesVideosFragment";

    private LinearLayout mLinearLayoutArticles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView() for AnnouncementsFragment called.");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_articlesvideos, container, false);

        mLinearLayoutArticles = (LinearLayout) rootView.findViewById(R.id.linear_layout_articles);

        String text1 = "<a href='http://www.nationaleatingdisorders.org/sites/default/files/Toolkits/EducatorToolkit.pdf'> Educator Toolkit </a>";
        String text2 = "<a href='http://www.nationaleatingdisorders.org/general-statistics'> General Statistics </a>";
        String text3 = "<a href='http://www.nationaleatingdisorders.org/what-should-i-say'> What should I say? </a>";
        String text4 = "<a href='http://www.nationaleatingdisorders.org/what-body-image'> Body Image </a>";
        String text5 = "<a href='http://www.SAMHSA.gov'> Project AWARE </a>";
        String text6 = "<a href='http://www.stopbullying.gov'> Stop Bullying </a>";
        String text7 = "<a href='https://www.fcps.edu/resources/student-safety-and-wellness/mental-health-resources-and-emergency-services-information'> FCPS Crisis Link </a>";
        String text8 = "<a href='https://www.fcps.edu/resources/student-safety-and-wellness/mental-health-and-resiliency'> Mental Health and Resiliency </a>";

        TextView link1 = (TextView) rootView.findViewById(R.id.educator_toolkit);
        TextView link2 = (TextView) rootView.findViewById(R.id.general_statistics);
        TextView link3 = (TextView) rootView.findViewById(R.id.what_should_i_say);
        TextView link4 = (TextView) rootView.findViewById(R.id.what_body_image);
        TextView link5 = (TextView) rootView.findViewById(R.id.project_aware);
        TextView link6 = (TextView) rootView.findViewById(R.id.stop_bullying);
        TextView link7 = (TextView) rootView.findViewById(R.id.crisis_link);
        TextView link8 = (TextView) rootView.findViewById(R.id.mental_health);

        link1.setMovementMethod(LinkMovementMethod.getInstance());
        link2.setMovementMethod(LinkMovementMethod.getInstance());
        link3.setMovementMethod(LinkMovementMethod.getInstance());
        link4.setMovementMethod(LinkMovementMethod.getInstance());
        link5.setMovementMethod(LinkMovementMethod.getInstance());
        link6.setMovementMethod(LinkMovementMethod.getInstance());
        link7.setMovementMethod(LinkMovementMethod.getInstance());
        link8.setMovementMethod(LinkMovementMethod.getInstance());

        link1.setText(Html.fromHtml(text1));
        link2.setText(Html.fromHtml(text2));
        link3.setText(Html.fromHtml(text3));
        link4.setText(Html.fromHtml(text4));
        link5.setText(Html.fromHtml(text5));
        link6.setText(Html.fromHtml(text6));
        link7.setText(Html.fromHtml(text7));
        link8.setText(Html.fromHtml(text8));

        link1.setLinkTextColor(getResources().getColor(android.R.color.white));
        link2.setLinkTextColor(getResources().getColor(android.R.color.white));
        link3.setLinkTextColor(getResources().getColor(android.R.color.white));
        link4.setLinkTextColor(getResources().getColor(android.R.color.white));
        link5.setLinkTextColor(getResources().getColor(android.R.color.white));
        link6.setLinkTextColor(getResources().getColor(android.R.color.white));
        link7.setLinkTextColor(getResources().getColor(android.R.color.white));
        link8.setLinkTextColor(getResources().getColor(android.R.color.white));


        return rootView;
    }
}
