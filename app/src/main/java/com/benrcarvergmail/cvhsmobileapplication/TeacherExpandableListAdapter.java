package com.benrcarvergmail.cvhsmobileapplication;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by 3Robotics on 5/20/2016.
 */
public class TeacherExpandableListAdapter extends BaseExpandableListAdapter {
    // Having context objects is dangerous but the tutorial I am following uses one
    private final Activity mContext;
    private Map<String, List<Teacher>> myTeacherCollection;
    private List<String> myTeachers;

    // Constructor
    public TeacherExpandableListAdapter(Activity context, List<String> teachers, Map<String, List<Teacher>> teacherCollections) {
        this.mContext = context;
        this.myTeacherCollection = teacherCollections;
        this.myTeachers = teachers;
    }
    public void updateData(List<String> teachers, Map<String, List<Teacher>> teachersCollections){
        myTeacherCollection = teachersCollections;
        myTeachers = teachers;
    }
    @Override
    public int getGroupCount() {
        return myTeachers.size();
    }

    /**
     * Returns the number of children of the ExpandableListView
     * @param groupPosition the group position for the list view
     * @return the number of children
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return myTeacherCollection.get(myTeachers.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return myTeachers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return myTeacherCollection.get(myTeachers.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String clubName = (String) getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item_clubs, null);
        }

        TextView listHeader = (TextView) convertView.findViewById(R.id.textview_clubTitle);
        listHeader.setTypeface(null, Typeface.BOLD);
        listHeader.setText(clubName);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String club = (String) getChild(groupPosition, childPosition).toString();
        LayoutInflater inflater = mContext.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.child_item_clubs, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.textview_clubTitle);
        // ImageView clubIcon = (ImageView) convertView.findViewById(R.id.imageview_clubIcon);

        item.setText(club);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}


