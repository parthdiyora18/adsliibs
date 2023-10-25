package com.ads.data.Local_ads;

import static com.ads.data.AdsControl.app_data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.ads.data.Conts;
import com.ads.data.OnClickListener;
import com.ads.data.R;
import com.bumptech.glide.Glide;

import java.util.Objects;

public class MyAdsInter {
    static OnClickListener callback;
    static Animation animZoomIn;

    @SuppressLint("UseCompatLoadingForDrawables")
    public MyAdsInter(final Activity act, final Appdetail appdetail, OnClickListener myCallback2) {
        callback = myCallback2;
        if (app_data != null && app_data.size() > 0) {
            Dialog dialog = new Dialog(act, R.style.FullWidth_Dialog);
            @SuppressLint("InflateParams") View view = LayoutInflater.from(act).inflate(R.layout.local_inter_ad, null);
            dialog.setContentView(view);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            Objects.requireNonNull(window).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            animZoomIn = AnimationUtils.loadAnimation(act, R.anim.slide_in_bottom);
            CardView cvTopAd = dialog.findViewById(R.id.cvTopAd);
            RelativeLayout lat1 = dialog.findViewById(R.id.lat1);
            TextView install = dialog.findViewById(R.id.install);
            ImageView ad_close = dialog.findViewById(R.id.ad_close);
            TextView App_name = dialog.findViewById(R.id.appname);
            ImageView appicon = dialog.findViewById(R.id.app_icon);
            ImageView ad_banner = dialog.findViewById(R.id.ad_banner);
            TextView app_ad_body = dialog.findViewById(R.id.ad_body);
            cvTopAd.startAnimation(animZoomIn);
            try {
                Glide.with(act).load(appdetail.getApp_icon()).into(appicon);
                Glide.with(act).load(appdetail.getApp_banner()).into(ad_banner);
                App_name.setText(appdetail.getApp_name());
                App_name.setSelected(true);
                app_ad_body.setText(appdetail.getApp_body());
                app_ad_body.setSelected(true);
                if (app_data != null && app_data.size() > 0) {
                    if (appdetail.getPakag().equals(app_data.get(0).getQureka_url())) {
                        install.setText("Play Now");
                    } else {
                        install.setText("Install");
                    }
                }

            } catch (Exception ignored) {
            }
            install.setOnClickListener(v -> {
                if (app_data != null && app_data.size() > 0) {
                    if (appdetail.getPakag().equals(app_data.get(0).getQureka_url())) {
                        try {
                            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                            customIntent.setToolbarColor(ContextCompat.getColor(act, R.color.first_color));
                            Conts.openCustomTab(act, customIntent.build(), Uri.parse(appdetail.getPakag()));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + appdetail.getPakag()));
                            act.startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                dialog.dismiss();
            });

            lat1.setOnClickListener(v -> {
                if (app_data != null && app_data.size() > 0) {
                    if (appdetail.getPakag().equals(app_data.get(0).getQureka_url())) {
                        try {
                            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                            customIntent.setToolbarColor(ContextCompat.getColor(act, R.color.first_color));
                            Conts.openCustomTab(act, customIntent.build(), Uri.parse(appdetail.getPakag()));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                            i.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + appdetail.getPakag()));
                            act.startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                dialog.dismiss();
            });
            new Handler().postDelayed(() -> {
                ad_close.setVisibility(View.VISIBLE);
                ad_close.setOnClickListener(v -> dialog.dismiss());
            }, 2500);
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