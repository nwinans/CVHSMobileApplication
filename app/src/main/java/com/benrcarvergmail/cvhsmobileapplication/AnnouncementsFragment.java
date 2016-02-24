package com.benrcarvergmail.cvhsmobileapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;;

import java.util.ArrayList;

public class AnnouncementsFragment extends Fragment {

    private ArrayList<String> data;

    public AnnouncementsFragment() {
        // Instantiate the data ArrayList so we may populate it during onCreateView()
        data = new ArrayList<String>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_announcements, container, false);
        // Create object reference to the RecyclerView created in fragment_announcements.xml
        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.rv_recycler_view);
        // Ensure that its size is fixed (unchanging)
        rv.setHasFixedSize(true);
        // Populate the data ArrayList. We currently do not utilize the boolean return type
        populateData();
        // Create an adapter for the RecyclerView, passing the ArrayList of text we want displayed
        MyRecyclerViewAdapater adapter = new MyRecyclerViewAdapater(data);
        // Set the RecyclerView's adapater to the one we just created
        rv.setAdapter(adapter);

        // A LinearLayoutManager is a A RecyclerView.LayoutManager
        // implementation which provides similar functionality to ListView.
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }

    // This will populate the data ArrayList with the data we want to display. This may
    // eventually get more complicated (if we require lots of different data other than
    // text to be shown. Additionally, this will eventually grab the information from a server.
    private boolean populateData() {
        // This text was generated with an Android Studio plugin known as Insert Dummy Text. That
        // fact is completely useless but nevertheless, it's a good plugin and I recommend it.
        data.add("Est pius nixus, cesaris. Lotus, audax fraticinidas sensim consumere de " +
                "bi-color, " + "superbus demissio. Ubi est albus stella?"); // Fake Latin option.
        data.add("Be embittered. When the body of beauty visualizes the mans of the follower, " +
                "the grace will know explosion of the politics. " +
                "Do and you will be viewed theosophically. The definition of your mans will sit " +
                "cosmically when you emerge that extend is the yogi."); // Esoteric Wisdom
        data.add("After warming the chickpeas, enamel avocado, rhubarb and maple syrup " +
                "with it in a plastic bag. Toast two chocolates, rice, and marmalade in a large " +
                "frying pan over medium heat, cook for a dozen minutes and soak with some " +
                "zucchini."); // Cullinary Inspiration
        data.add("Pants grow with grace at the undead pantano river! Sing swiftly like an " +
                "old sailor. Amnesty, grace, and courage. Yo-ho-ho, yer not firing me " +
                "without a faith!" ); // Pirate Lingo
        data.add("Peace at the wormhole was the ionic cannon of starlight travel, deserved to a " +
                "small creature. Courage at the saucer section that is when intelligent " +
                "ferengis die."); // Science Fiction

        return true; // May eventually return false if unable to pull data from server
    }

}
