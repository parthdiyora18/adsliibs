package com.ads.data.Local_ads;

import static com.ads.data.AdsControl.app_data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.ads.data.Conts;
import com.ads.data.OnClickListener;
import com.ads.data.R;
import com.bumptech.glide.Glide;

import java.util.Objects;

public class MyAppopen {
    static OnClickListener callback;

    public MyAppopen(final Activity activity, final MyAd myAd, OnClickListener callback2) {
        callback = callback2;
        if (app_data != null && app_data.size() > 0) {
            Dialog dialog = new Dialog(activity, android.R.style.Theme_Translucent_NoTitleBar);
            @SuppressLint("InflateParams") View view = LayoutInflater.from(activity).inflate(R.layout.local_appopen, null);
            dialog.setContentView(view);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            Objects.requireNonNull(window).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RelativeLayout lay1 = dialog.findViewById(R.id.llPersonalAd);
            TextView install = dialog.findViewById(R.id.install);
            LinearLayout linearLayout = dialog.findViewById(R.id.ll_continue_app);
            TextView App_name = dialog.findViewById(R.id.txt_appname);
            ImageView appicon = dialog.findViewById(R.id.app_icon);
            ImageView ad_banner = dialog.findViewById(R.id.ad_banner);
            TextView app_ad_body = dialog.findViewById(R.id.ad_body);
            try {
                Glide.with(activity).load(myAd.getApp_icon()).into(appicon);
                Glide.with(activity).load(myAd.getApp_banner()).into(ad_banner);
                App_name.setText(myAd.getApp_name());
                App_name.setSelected(true);
                app_ad_body.setText(myAd.getApp_body());
                app_ad_body.setSelected(true);
                install.setText("Install");
            } catch (Exception ignored) {
            }
            install.setOnClickListener(v -> {
                if (myAd.getPakag().equals(app_data.get(0).getQureka_url())) {
                    try {
                        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                        customIntent.setToolbarColor(ContextCompat.getColor(activity, R.color.first_color));
                        Conts.openCustomTab(activity, customIntent.build(), Uri.parse(myAd.getPakag()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + myAd.getPakag()));
                        activity.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            lay1.setOnClickListener(v -> {
                if (myAd.getPakag().equals(app_data.get(0).getQureka_url())) {
                    try {
                        CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                        customIntent.setToolbarColor(ContextCompat.getColor(activity, R.color.first_color));
                        Conts.openCustomTab(activity, customIntent.build(), Uri.parse(myAd.getPakag()));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + myAd.getPakag()));
                        activity.startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            linearLayout.setOnClickListener(v -> dialog.dismiss());

            dialog.setOnDismissListener(dialog1 -> {
                if (callback != null) {
                    callback.onClick();
                    callback = null;
                }
            });
            dialog.show();
        } else {
            if (callback != null) {
                callback.onClick();
                callback = null;
            }
        }
    }
}
