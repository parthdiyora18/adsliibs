package com.ads.data.Local_ads;

import android.content.Context;
import android.content.SharedPreferences;

public class FayazSP {
    public static SharedPreferences sharedPreferences;
     static Context context;

    public static SharedPreferences init(Context context) {
        FayazSP.context = context;
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("MyAds", Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public static void put(String title, int value) {
        sharedPreferences.edit().putInt(title, value).apply();
    }

    public static int getInt(String title, int defaultValue) {
        return sharedPreferences.getInt(title, defaultValue);
    }

    public static void clearAll() {
        sharedPreferences.edit().clear().apply();
    }
}
