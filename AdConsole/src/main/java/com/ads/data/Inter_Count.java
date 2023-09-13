package com.ads.data;

import android.content.Context;
import android.content.SharedPreferences;

public class Inter_Count {
    SharedPreferences sharedPrefs;
    private static final String APP_PREFS = "adcount";
    private static Inter_Count instance;

    private Inter_Count(Context context) {
        sharedPrefs = context.getApplicationContext().getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    public static synchronized Inter_Count getInstance(Context context) {
        if (instance == null)
            instance = new Inter_Count(context);
        return instance;
    }

    public void storeClicks(int count) {
        SharedPreferences.Editor meditor = sharedPrefs.edit();
        meditor.putInt(APP_PREFS, count);
        meditor.apply();
    }

    public int getNumberOfClicks() {
        int clicksNumber = sharedPrefs.getInt(APP_PREFS, 1);
        return clicksNumber;
    }
}