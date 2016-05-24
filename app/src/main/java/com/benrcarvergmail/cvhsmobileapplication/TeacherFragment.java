package com.benrcarvergmail.cvhsmobileapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 3Robotics on 5/20/2016.
 */
public class TeacherFragment extends Fragment {

    private ArrayList<Teacher> myData;
    private List<String> mGroupList;
    private List<String> mChildList;
    private Map<String, List<String>> mClubCollection;

    private static final String TAG = "ClubsFragment";

    /**
     * Instantiates a new Club fragment.
     */
    public TeacherFragment() {
        // Instantiate the mData ArrayList so we may populate it during onCreateView()
        myData = new ArrayList<>();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_teachers, container, false);

        // Create object reference to the RecyclerView created in fragment_clubs.xml
        // Populate the mData ArrayList. We currently do not utilize the boolean return type
        populateData();

        createGroupList();

        createCollection();

        ExpandableListView mExpandableListView = (ExpandableListView) rootView.findViewById(R.id.listview);

        final ClubsExpandableListAdapter expandableListAdapater = new ClubsExpandableListAdapter(getActivity(), mGroupList, mClubCollection);

        mExpandableListView.setAdapter(expandableListAdapater);

        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final String selected = (String) expandableListAdapater.getChild(groupPosition, childPosition);
                Toast.makeText(getContext(), selected, Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        return rootView;
    }

    private void createGroupList() {
        mGroupList = new ArrayList<>();
        for (int i = 0; i < myData.size(); i++) {
            // Log.i(TAG,(i + ": " + mData.get(i).getTitle()));
            mGroupList.add(myData.get(i).getName());
        }
    }

    private void createCollection() {
        // Preparing clubs collection (the children, I guess)
        String[] randomData = { "Dank Memes", "Click for more memes", "Club memes" };
        String[] defaultData = { "Mr. Small", "The robotics club is a club about robotics", "Memes are Great" };
        mClubCollection = new LinkedHashMap<>();

        for (String club: mGroupList) {
            Log.i(TAG, "Club: " + club);
            if (club.equals("Robotics")) {
                loadChild(defaultData);
            } else {
                loadChild(randomData);
            }

            mClubCollection.put(club, mChildList);
        }
    }

    private void loadChild(String[] TeachersData) {
        mChildList = new ArrayList<>();
        mChildList.addAll(Arrays.asList(TeachersData));
    }

    /* This will populate the mData ArrayList with the mData we want to display. This may
     eventually get more complicated (if we require lots of different mData other than
     text to be shown. Additionally, this will eventually grab the information from a server.
     */
    private void populateData() {
        /* This text was generated with an Android Studio plugin known as Insert Dummy Text. That
         fact is completely useless but nevertheless, it's a good plugin and I recommend it. I
         add a new line ( + "\n" to each String to ensure it doesn't get cut off. This may mess
         things up of the String is only one line though, so we'll see what happens.
         */

        /* populateData() is called every time onCreateView() is called by an clubFragment.
         This happens fairly often. Effectively, with the way RecyclerView works and all, it happens
         a lot. That means that every single time populateData is called, all of this the mData below
         is re-added to the mData ArrayList. If I neglect to clear the ArrayList (I ensure that it isn't
         null to avoid a NullPointerException), there will be duplicated mData in the ArrayList. Android
         obviously doesn't know any better than to create extra Cards out of this duplicated mData, resulting
         in lots and lots of cards with the exact same mData. Clearing the ArrayList each time the populateData()
         method is called ensures that there aren't any duplicates. Whether or not there's a better way to do this
         is beyond me at the moment, but this works currently and I'm fine with that.
         */
        if (myData != null) {
            myData.clear();
        }

}
