package com.example.btscanning;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class AdvertiseActivity extends AppCompatActivity {


    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    private AdvertiseCallback mAdvertiseCallback;
    private Handler mHandler;
    private Runnable timeoutRunnable;
    private final static int REQUEST_COARSE_LOCATION = 1;
    private long TIMEOUT = 10000;

    public static final String ADVERTISING_FAILED =
            "com.example.android.bluetoothadvertisements.advertising_failed";
    public static final String ADVERTISING_FAILED_EXTRA_CODE = "failureCode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertise);
    }

    public void initialize(View view)
    {
        if(mBluetoothLeAdvertiser == null)
        {
            BluetoothManager mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if(mBluetoothManager != null)
            {
                mBluetoothAdapter = mBluetoothManager.getAdapter();
                if(mBluetoothAdapter != null)
                {
                    if(!mBluetoothAdapter.isEnabled())
                    {
                        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent, 2);
                    }
                    else
                    {
                        if(mBluetoothAdapter.isMultipleAdvertisementSupported())
                        {
                            mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
                            startAdvertising();
                            //setTimeout();
                        }
                    }
                }
                else
                {
                    Toast.makeText(this, "BT adapter null", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "BT manager null", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setTimeout()
    {
        mHandler = new Handler();
        timeoutRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                sendFailureIntent(-1);
                //stopAdvertising();
            }
        };
        mHandler.postDelayed(timeoutRunnable, TIMEOUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        Log.d("advertise", "Entered on activity result");
        if(requestCode == 2)
        {
            if(resultCode == RESULT_OK)
            {
                if(mBluetoothAdapter.isMultipleAdvertisementSupported())
                {
                    mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
                    startAdvertising();
                    //setTimeout();
                }
                else
                {
                    Toast.makeText(this, "Multiple ads not supported", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void startAdvertising()
    {
        mAdvertiseCallback = null;

        if(mAdvertiseCallback == null)
        {
            AdvertiseSettings settings = buildAdvertiseSettings();
            AdvertiseData data = buildAdvertiseData();
            mAdvertiseCallback = new CustomAdvertiseCallback();

            if(mBluetoothLeAdvertiser != null)
            {
                mBluetoothLeAdvertiser.startAdvertising(settings, data, mAdvertiseCallback);
                Log.d("advertise", "Started advertising");
            }
        }
    }

    public void stopAdvertising(View view)
    {
        Log.d("advertise", "Service: Stopping Advertising");
        Toast.makeText(this, "Stopped advertising", Toast.LENGTH_LONG).show();
        if (mBluetoothLeAdvertiser != null)
        {
            mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
            mAdvertiseCallback = null;
        }
    }

    private AdvertiseData buildAdvertiseData()
    {
        AdvertiseData.Builder dataBuilder = new AdvertiseData.Builder();
        dataBuilder.addServiceUuid(Constants.UUID);
        Log.d("advertise", Constants.UUID.toString());
        //dataBuilder.setIncludeDeviceName(true);

        return dataBuilder.build();
    }

    private AdvertiseSettings buildAdvertiseSettings()
    {
        AdvertiseSettings.Builder settingsBuilder = new AdvertiseSettings.Builder();
        settingsBuilder.setAdvertiseMode(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH);
        settingsBuilder.setTimeout(0);

        return settingsBuilder.build();
    }

    private class CustomAdvertiseCallback extends AdvertiseCallback
    {
        @Override
        public void onStartFailure(int errorCode)
        {
            super.onStartFailure(errorCode);

            String description;
            if (errorCode == AdvertiseCallback.ADVERTISE_FAILED_FEATURE_UNSUPPORTED)
                description = "ADVERTISE_FAILED_FEATURE_UNSUPPORTED";
            else if (errorCode == AdvertiseCallback.ADVERTISE_FAILED_TOO_MANY_ADVERTISERS)
                description = "ADVERTISE_FAILED_TOO_MANY_ADVERTISERS";
            else if (errorCode == AdvertiseCallback.ADVERTISE_FAILED_ALREADY_STARTED)
                description = "ADVERTISE_FAILED_ALREADY_STARTED";
            else if (errorCode == AdvertiseCallback.ADVERTISE_FAILED_DATA_TOO_LARGE)
                description = "ADVERTISE_FAILED_DATA_TOO_LARGE";
            else if (errorCode == AdvertiseCallback.ADVERTISE_FAILED_INTERNAL_ERROR)
                description = "ADVERTISE_FAILED_INTERNAL_ERROR";
            else description = "unknown";

            Log.d("advertise", "Advertise fail" + description);
            sendFailureIntent(errorCode);
            //finish();
        }

        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect)
        {
            super.onStartSuccess(settingsInEffect);
            Log.d("advertise", "Advertise success");
        }
    }

    private void sendFailureIntent(int errorCode)
    {
        Intent failureIntent = new Intent();
        failureIntent.setAction(ADVERTISING_FAILED);
        failureIntent.putExtra(ADVERTISING_FAILED_EXTRA_CODE, errorCode);
        sendBroadcast(failureIntent);
        Log.d("advertise", failureIntent.toString());
    }
}
