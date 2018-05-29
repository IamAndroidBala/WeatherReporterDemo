package com.weather.weatherreporter.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * used to display the logs
 * if isTestBuild == true then log will print
 * else log will not print
 * isTestBuild must be false when app is going to release for production
 */

public class AppLog {
    public static void d(String msg){
        if(Constants.isTestBuild) {
            if (!TextUtils.isEmpty(msg)) {
                Log.d("MyApp", "====>>>> " + msg);
            }
        }
    }
}
