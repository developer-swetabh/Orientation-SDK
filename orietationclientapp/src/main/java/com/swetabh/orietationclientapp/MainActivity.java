package com.swetabh.orietationclientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.TextView;

import com.swetabh.orientationlib.IOrientation;
import com.swetabh.orientationlib.IOrientationCallback;

public class MainActivity extends AppCompatActivity implements ServiceConnection {

    private static final String TAG = MainActivity.class.getSimpleName();

    private IOrientation mOrientationService;
    private boolean mServiceConnected;
    private TextView mTxtOrientation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTxtOrientation = findViewById(R.id.txt_orientation);
        Log.d(TAG, "Swetabh->here : onCreate()");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Swetabh->here : onStart() : Binding Service");
        bindService(new Intent("com.swetabh.orientation.OrientationService").setPackage("com.swetabh.phoneorientationservice"), this, BIND_AUTO_CREATE);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        Log.d(TAG, "Swetabh->here : onServiceConnected : Service is connected");
        mServiceConnected = true;
        mOrientationService = IOrientation.Stub.asInterface(service);
        try {
            Log.d(TAG, "Swetabh->here : onServiceConnected() : callingGetOrientation() ");
            mOrientationService.getOrientation(new IOrientationCallback.Stub() {
                @Override
                public void OnResponse(int res) throws RemoteException {
                    Log.d(TAG, "Swetabh->here : OnResponse() : res = " + res);
                    if (res == 1) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTxtOrientation.setText("Current Orientation is Landscape");
                            }
                        });

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTxtOrientation.setText("Current Orientation is Portrait");
                            }
                        });
                    }
                }
            });
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d(TAG, "Swetabh->here : onServiceDisConnected : Service is disconnected");
        mServiceConnected = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "Swetabh->here : onStop() : calling unbindService");
        unbindService(this);
    }
}
