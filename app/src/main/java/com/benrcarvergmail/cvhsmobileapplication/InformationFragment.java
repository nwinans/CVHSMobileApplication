package com.benrcarvergmail.cvhsmobileapplication;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.ListView;



import com.benrcarvergmail.cvhsmobileapplication.R;
public class InformationFragment extends ListFragment {
    private ListView listView ;
    private String[] data;

    public InformationFragment(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_information, container,
                false);

        String[] values = new String[] { "Math", "English", "Science","History" };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.listview_layout, values);
        setListAdapter(adapter);
        return rootView;
    }

}
