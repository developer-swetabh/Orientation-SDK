// IOrientation.aidl
package com.swetabh.orientationlib;

import com.swetabh.orientationlib.IOrientationCallback;
// Declare any non-default types here with import statements

oneway interface IOrientation {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void getOrientation(in IOrientationCallback callback);
}
