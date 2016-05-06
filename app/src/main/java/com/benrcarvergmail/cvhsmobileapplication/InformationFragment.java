package com.benrcarvergmail.cvhsmobileapplication;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;



import com.benrcarvergmail.cvhsmobileapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InformationFragment extends ListFragment {
    private String[] data;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public InformationFragment(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_information,container , false);

        // get the listview
        expListView = (ExpandableListView) expListView.findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Math Teachers");
        listDataHeader.add("Science Teachers");
        listDataHeader.add("English Teachers");
        listDataHeader.add("History Teachers");

        // Adding child data
        List<String> MathTeachers = new ArrayList<String>();
        MathTeachers.add("Beatty, Kathy");
        MathTeachers.add("Bonnell, Brittany");
        MathTeachers.add("Hwang, Renae Eunae");
        MathTeachers.add("Joyce, Masha");
        MathTeachers.add("Kent, Elizabeth");
        MathTeachers.add("McCarthy, Megan");
        MathTeachers.add("Mishin, Julia");
        MathTeachers.add("Mossholder, Steven");
        MathTeachers.add("Nicholson, Marisa");
        MathTeachers.add("Noga, Matthew");
        MathTeachers.add("Person, Gordon");
        MathTeachers.add("Post, Eric");
        MathTeachers.add("Rigby, Susan");
        MathTeachers.add("Rubin, Mary");
        MathTeachers.add("Shanahan, Mary");
        MathTeachers.add("Small, Oliver");
        MathTeachers.add("Terninko, Miriam");
        MathTeachers.add("Wallace, Jessica");
        MathTeachers.add("Williams, Jeb");

        List<String> EnglishTeachers = new ArrayList<String>();
        EnglishTeachers.add("Berg, Jessica");
        EnglishTeachers.add("Bergel, Jennifer");
        EnglishTeachers.add("Borah, Brian");
        EnglishTeachers.add("Brown, Margot");
        EnglishTeachers.add("D'Orazio, Marissa");
        EnglishTeachers.add("DuncanHudspeth, Sharon");
        EnglishTeachers.add("Filsinger, Jennifer");
        EnglishTeachers.add("Harar, Bethany");
        EnglishTeachers.add("Hughes, Alison");


        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }



}
