package com.benrcarvergmail.cvhsmobileapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static android.support.v4.app.ActivityCompat.startActivityForResult;

/**
 * Created by Benjamin on 2/28/2016.
 */
public class BluetoothFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        Intent intent = new Intent(rootView.getContext(), BluetoothActivity.class);
        startActivity(intent);
        return rootView;
    }

}
