package com.ads.data;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.Nullable;

import com.applovin.sdk.AppLovinSdk;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.MobileAds;
import com.inmobi.sdk.InMobiSdk;
import com.inmobi.sdk.SdkInitializationListener;

import org.json.JSONException;
import org.json.JSONObject;

public class App extends Application {
    private static App _instance;
    AppOpen appOpenManager;

    public void onCreate() {
        super.onCreate();
        _instance = this;
        // Admob , adx
        MobileAds.initialize(this, initializationStatus -> {
        });
        // Fb
        AudienceNetworkAds.initialize(_instance);
        // Applovin
        AppLovinSdk.getInstance(_instance).isInitialized();
        AppLovinSdk.getInstance(_instance).setMediationProvider("max");
        AppLovinSdk.initializeSdk(_instance, configuration -> {
        });
        // Inmobi
        JSONObject consentObject = new JSONObject();
        try {
            consentObject.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
            consentObject.put("gdpr", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        InMobiSdk.init(this, "b4994419ed5a4683b08df417a0062a0a", consentObject, new SdkInitializationListener() {
            @Override
            public void onInitializationComplete(@Nullable Error error) {
                if (null != error) {
                    Conts.log_debug("Parth", "InMobi Init failed -" + error.getMessage());
                } else {
                    Conts.log_debug("Parth", "InMobi Init Successful");
                }
            }
        });
        appOpenManager = new AppOpen(this);
        if (!AdsControl.isOnline(_instance)) {
            brodCarst(_instance);
        }
    }

    public static synchronized App getInstance() {
        App app;
        synchronized (App.class) {
            app = _instance;
        }
        return app;
    }

    NetworkChangeReceiver brd;

    public void brodCarst(final Context ctx) {
        try {
            brd = new NetworkChangeReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            ctx.registerReceiver(brd, filter);
        } catch (Exception ignored) {
        }
    }

    public boolean check;
    private boolean wifinet = false;
    private boolean datanet = false;

    public class NetworkChangeReceiver extends BroadcastReceiver {
        boolean c = true;

        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (c) {
                c = false;
                check = true;
            }
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo activeNetwork1 = cm.getActiveNetworkInfo();
            if (activeNetwork1 != null) {
                if (activeNetwork1.getType() == ConnectivityManager.TYPE_WIFI) {
                    wifinet = true;
                } else if (activeNetwork1.getType() == ConnectivityManager.TYPE_MOBILE) {
                    datanet = true;
                }
            }
            if (wifinet || datanet) {
                try {
                    if (AdsControl.isOnline(context)) {
                        check = false;
                        Conts conts = new Conts(context);
                        conts.App_Data_panal();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    if (AdsControl.isOnline(context)) {
                        check = false;
                        Conts conts = new Conts(context);
                        conts.App_Data_File_Zilla();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}