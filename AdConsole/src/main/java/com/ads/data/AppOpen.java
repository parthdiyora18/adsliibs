package com.ads.data;

import static com.ads.data.AdsControl.app_data;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import java.util.Objects;

public class AppOpen implements LifecycleObserver, Application.ActivityLifecycleCallbacks {
    private Activity currentActivity;
    static App blueApp;

    public void onActivityCreated(@NonNull Activity activity, Bundle bundle) {
    }

    public void onActivityPaused(@NonNull Activity activity) {
    }

    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
    }

    public void onActivityStopped(@NonNull Activity activity) {
    }

    public void onActivityStarted(@NonNull Activity activity) {
        this.currentActivity = activity;
    }

    public void onActivityResumed(@NonNull Activity activity) {
        this.currentActivity = activity;
    }

    public void onActivityDestroyed(@NonNull Activity activity) {
        this.currentActivity = null;
    }

    public AppOpen(App mapp) {
        blueApp = mapp;
        mapp.registerActivityLifecycleCallbacks(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        appopen_Ads(currentActivity);
    }

    int ad_openad_network = 0;

    public void appopen_Ads(Activity activity) {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAdAppopen().split(",");
                    if (ad_openad_network < adnetwork.length) {
                        switch (adnetwork[ad_openad_network]) {
                            case "native":
                                Native_Ads(activity);
                                ad_openad_network++;
                                break;
                            case "inter":
                                AdsControl.getInstance(currentActivity).show_splash_inter(activity, () -> currentActivity = null);
                                ad_openad_network++;
                                break;
                            case "admob":
                                AdsControl.getInstance(currentActivity).show_Admob_Appopen(activity, () -> currentActivity = null);
                                ad_openad_network++;
                                break;
                            case "adx":
                                AdsControl.getInstance(currentActivity).show_Adx_Appopen(activity, () -> currentActivity = null);
                                ad_openad_network++;
                                break;
                            case "wortise":
                                AdsControl.getInstance(currentActivity).show_Wortise_Appopen(activity, () -> currentActivity = null);
                                ad_openad_network++;
                                break;
                            case "applovin":
                                AdsControl.getInstance(currentActivity).show_Applovin_Appopen(activity, () -> currentActivity = null);
                                ad_openad_network++;
                                break;
                            case "local":
                                AdsControl.getInstance(currentActivity).show_local_Appopen(activity, () -> currentActivity = null);
                                ad_openad_network++;
                                break;
                            default:
                        }
                        if (ad_openad_network == adnetwork.length) {
                            ad_openad_network = 0;
                        }
                    }
                } else {
                    currentActivity = null;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    Dialog ad_dialog;

    void Native_Ads(Activity act) {
        Dialog dialog = new Dialog(act);
        ad_dialog = dialog;
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        this.ad_dialog.setContentView(R.layout.open_native);
        AdsControl.getInstance(act).show_native_ad(dialog.findViewById(R.id.ad_native));
        ImageView continuee = ad_dialog.findViewById(R.id.continuee);
        new Handler().postDelayed(() -> {
            continuee.setVisibility(View.VISIBLE);
            continuee.setOnClickListener(v -> ad_dialog.dismiss());
        }, 2500);
        this.ad_dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(this.ad_dialog.getWindow()).setSoftInputMode(3);
        this.ad_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.ad_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.ad_dialog.show();
    }
}