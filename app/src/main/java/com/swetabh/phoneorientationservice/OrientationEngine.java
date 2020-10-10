package com.swetabh.phoneorientationservice;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.swetabh.orientationlib.IOrientationCallback;

/* A class used to compute the orientation from sensor data and give the result as a callback
 * */
public class OrientationEngine implements SensorEventListener {

    private static final String TAG = OrientationEngine.class.getSimpleName();

    private static OrientationEngine sInstance = null;
    private Sensor mRotationSensor;
    private static final int SENSOR_DELAY = 800 * 1000; // 8ms
    private static final int FROM_RADS_TO_DEGS = -57;
    private IOrientationCallback mCallback;

    static OrientationEngine getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new OrientationEngine(context);
        }
        return sInstance;
    }

    private OrientationEngine(Context context) {
        try {
            SensorManager mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            mRotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
            mSensorManager.registerListener(this, mRotationSensor, SENSOR_DELAY);
        } catch (Exception e) {
            Toast.makeText(context, "Hardware compatibility issue", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.d(TAG, "Swetabh->here : onSensorChanged() . . .");
        if (event.sensor == mRotationSensor) {
            if (event.values.length > 4) {
                float[] truncatedRotationVector = new float[4];
                System.arraycopy(event.values, 0, truncatedRotationVector, 0, 4);
                update(truncatedRotationVector);
            } else {
                update(event.values);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    void registerCallback(IOrientationCallback callback) {
        mCallback = callback;
    }

    private void update(float[] vectors) {
        Log.d(TAG, "Swetabh->here : update() entry . . . ");
        float[] rotationMatrix = new float[9];
        SensorManager.getRotationMatrixFromVector(rotationMatrix, vectors);
        int worldAxisX = SensorManager.AXIS_X;
        int worldAxisZ = SensorManager.AXIS_Z;
        float[] adjustedRotationMatrix = new float[9];
        SensorManager.remapCoordinateSystem(rotationMatrix, worldAxisX, worldAxisZ, adjustedRotationMatrix);
        float[] orientation = new float[3];
        SensorManager.getOrientation(adjustedRotationMatrix, orientation);
        float pitch = orientation[1] * FROM_RADS_TO_DEGS;
        float roll = orientation[2] * FROM_RADS_TO_DEGS;
        Log.d(TAG, "Swetabh->here : Orientation roll = " + roll);
        if (Math.abs(roll) > 80 && Math.abs(roll) < 100) {
            try {
                Log.d(TAG, "Swetabh->here : sending orientation callback :  Landscape");
                mCallback.OnResponse(1);
            } catch (RemoteException e) {
                Log.d(TAG, "Landscape Exception e = " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            try {
                Log.d(TAG, "Swetabh->here : sending orientation callback :  portrait");
                mCallback.OnResponse(0);
            } catch (RemoteException e) {
                Log.d(TAG, "Portrait Exception e = " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
