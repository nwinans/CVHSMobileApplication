package com.benrcarvergmail.cvhsmobileapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Benjamin on 2/28/2016.
 */
public class BluetoothActivity extends AppCompatActivity {

    private ArrayAdapter<String> listAdapater;
    private ArrayList<String> pairedDevices;
    private Set<BluetoothDevice> devicesArray;

    private IntentFilter filter;
    private BluetoothAdapter btAdapater;
    private BroadcastReceiver receiver;
    private Button connectNew;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        init();

        if (btAdapater == null) {
            Toast.makeText(getApplicationContext(), "No bluetooth capabilities detected.", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            // If the adapter isn't enabled...
            if (!btAdapater.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, 1); // Check if the intent worked (returned true)
            }

            // Get the paired devices
            getPairedDevices();
        }
    }

    private void getPairedDevices() {
        devicesArray = btAdapater.getBondedDevices();

        if (devicesArray.size()> 0) {
            for (BluetoothDevice device: devicesArray) {
                pairedDevices.add(device.getName());
            }
        } else {

        }
    }

    private void init() {
        connectNew = (Button) findViewById(R.id.toggleBluetooth);
        listView = (ListView) findViewById(R.id.listView);
        listAdapater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, 0);
        listView.setAdapter(listAdapater);
        btAdapater = BluetoothAdapter.getDefaultAdapter();
        pairedDevices = new ArrayList<String>();

        // Set up the IntentFilter
        filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    listAdapater.add(device.getName() + "\n" + device.getAddress());
                }

                else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                    // Run some code...
                }

                else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                    // Run some code...
                }

                else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                    // Run some code...
                }
            }
        };

        registerReceiver(receiver, filter);

        /* An IntentFilter is a structured description of Intent values to be matched.
        An IntentFilter can match against actions, categories, and data (either via
        its type, scheme, and/or path) in an Intent. It also includes a "priority" value
        which is used to order multiple matching filters. */

        IntentFilter filterDiscoveryStarted = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        registerReceiver(receiver, filterDiscoveryStarted);

        IntentFilter filterDiscoveryFinished = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filterDiscoveryFinished);

        IntentFilter filterStateChanged = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(receiver, filterStateChanged);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Bluetooth must be enabled to continue.",
                Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}
