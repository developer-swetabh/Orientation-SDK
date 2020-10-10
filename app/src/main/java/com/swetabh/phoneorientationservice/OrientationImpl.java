package com.swetabh.phoneorientationservice;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;

import com.swetabh.orientationlib.IOrientation;
import com.swetabh.orientationlib.IOrientationCallback;

public class OrientationImpl extends IOrientation.Stub {
    private static final String TAG = OrientationImpl.class.getSimpleName();
    private OrientationEngine mEngine;

    OrientationImpl(Context context) {
        Log.d(TAG, "Swetabh->here : Constructor");
        mEngine = OrientationEngine.getInstance(context);
    }

    @Override
    public void getOrientation(IOrientationCallback callback) throws RemoteException {
        Log.d(TAG, "Swetabh->here : getOrientation() entry . . . ");
        mEngine.registerCallback(callback);
    }

}
