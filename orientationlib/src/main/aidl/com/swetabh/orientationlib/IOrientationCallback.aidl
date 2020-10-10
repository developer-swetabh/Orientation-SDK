// IOrientationCallback.aidl
package com.swetabh.orientationlib;

// Declare any non-default types here with import statements

oneway interface IOrientationCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void OnResponse(int res);
}
