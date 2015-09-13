package com.example.ali.topcoderandroid.Helpers;

import android.util.Log;

/**
 * Created by ali on 13.09.2015.
 */
public class LogHelper {
    private static String LOG_TAG = "topcoderandroid";

    public static  void  Log(Exception e)
    {
        Log.e(LOG_TAG, "exception", e);
    }
}
