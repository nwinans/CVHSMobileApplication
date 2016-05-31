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

        TextView link1 = (TextView) rootView.findViewById(R.id.educator_toolkit);
        TextView link2 = (TextView) rootView.findViewById(R.id.general_statistics);
        TextView link3 = (TextView) rootView.findViewById(R.id.what_should_i_say);
        TextView link4 = (TextView) rootView.findViewById(R.id.what_body_image);

        link1.setMovementMethod(LinkMovementMethod.getInstance());
        link2.setMovementMethod(LinkMovementMethod.getInstance());
        link3.setMovementMethod(LinkMovementMethod.getInstance());
        link4.setMovementMethod(LinkMovementMethod.getInstance());

        link1.setText(Html.fromHtml(text1));
        link2.setText(Html.fromHtml(text2));
        link3.setText(Html.fromHtml(text3));
        link4.setText(Html.fromHtml(text4));

        return rootView;
    }
}
