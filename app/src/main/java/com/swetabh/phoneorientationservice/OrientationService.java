package com.swetabh.phoneorientationservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class OrientationService extends Service {
    public OrientationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new OrientationImpl(OrientationService.this);
    }
}
