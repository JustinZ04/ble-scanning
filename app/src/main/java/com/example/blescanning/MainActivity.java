package com.example.blescanning;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

// This might be working
public class MainActivity extends AppCompatActivity
{
    private static long SCAN_PERIOD = 10000;
    private BluetoothAdapter mBluetoothAdapter;
    private ScanCallback mScanCallback;
    private Handler mHandler;
    private BluetoothLeScanner mBluetoothLeScanner;
    private int ACCESS_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button attendButton = findViewById(R.id.button2);
        attendButton.setEnabled(false);

        /*
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_COARSE_LOCATION);
        }

        if(mBluetoothAdapter != null)
        {
            mHandler = new Handler();

            if(!mBluetoothAdapter.isEnabled())
            {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 2);
            }
        }
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(requestCode == 2)
        {
            if(resultCode == RESULT_OK) {
                mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

                if (mBluetoothLeScanner != null)
                {
                    startScanning();
                }
            }
        }
    }

    public void sendMessage(View view)
    {
        BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_COARSE_LOCATION);
        }

        if (mBluetoothAdapter != null)
        {
            mHandler = new Handler();

            if (!mBluetoothAdapter.isEnabled())
            {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 2);
            }
            else
            {
                mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();

                if (mBluetoothLeScanner != null)
                {
                    startScanning();
                }
            }
        }


    }

    private void startScanning()
    {
        if(mScanCallback == null)
        {
            mHandler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    stopScanning();
                }
            }, SCAN_PERIOD);

            mScanCallback = new SampleScanCallback();
            mBluetoothLeScanner.startScan(buildScanFilters(), buildScanSettings(), mScanCallback);
            Log.d("scan", "Started scanning");
        }
    }

    private List<ScanFilter> buildScanFilters()
    {
        List<ScanFilter> scanFilters = new ArrayList<>();

        ScanFilter.Builder builder = new ScanFilter.Builder();
        builder.setServiceUuid(Constants.UUID);
        scanFilters.add(builder.build());

        return scanFilters;
    }

    private ScanSettings buildScanSettings()
    {
        ScanSettings.Builder builder = new ScanSettings.Builder();
        builder.setScanMode(ScanSettings.SCAN_MODE_LOW_POWER);

        return builder.build();
    }

    private void stopScanning()
    {
        mBluetoothLeScanner.stopScan(mScanCallback);
        mScanCallback = null;
        Log.d("scan", "Stopped scanning");
    }

    private class SampleScanCallback extends ScanCallback
    {
        // I think this has to be here
        private ArrayList<BluetoothDevice> deviceList = new ArrayList<>();
        @Override
        public void onScanResult(int callbackType, ScanResult result)
        {
            super.onScanResult(callbackType, result);
            BluetoothDevice b = result.getDevice();
            String record = result.getScanRecord().toString();

            if(result.getScanRecord().getServiceUuids().contains(Constants.UUID))
            {
                Button attendButton = findViewById(R.id.button2);
                attendButton.setEnabled(true);
            }

            Log.d("device", "a result: " + record);
            Log.i("callbackType", String.valueOf(callbackType));
            Toast.makeText(getApplicationContext(), record, Toast.LENGTH_SHORT).show();
            deviceList.add(b);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results)
        {
            Log.d("device", "In batch results");
            super.onBatchScanResults(results);

            for(ScanResult result : results)
            {
                BluetoothDevice b = result.getDevice();
                deviceList.add(b);
            }
        }

        @Override
        public void onScanFailed(int errorCode)
        {
            super.onScanFailed(errorCode);
            Toast.makeText(getBaseContext(),"Scan failed " + errorCode, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
