package com.ads.data.Local_ads;

import static com.ads.data.AdsControl.app_data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.ads.data.Conts;
import com.ads.data.R;
import com.squareup.picasso.Picasso;

public class MyAdsView extends RelativeLayout {
    Context context;
    ImageView AppIcon;
    ImageView ad_banner;
    TextView textViewAppName;
    TextView ad_body;
    TextView ad_call_to_action;
    private String url;

    public MyAdsView(Context context) {
        super(context);
        this.context = context;
    }

    public void set_small_native_banner_Values(final Appdetail appdetail) {
        inflate(context, R.layout.local_small_native_banner, this);
        AppIcon = findViewById(R.id.ad_app_icon);
        textViewAppName = findViewById(R.id.ad_headline);
        ad_body = findViewById(R.id.ad_body);
        ad_call_to_action = findViewById(R.id.ad_call_to_action);
        Picasso.get().load(appdetail.getApp_icon()).into(AppIcon);
        textViewAppName.setText(appdetail.getApp_name());
        ad_body.setText(appdetail.getApp_body());
        this.url = appdetail.getPakag();
        if (app_data != null && app_data.size() > 0) {
            if (appdetail.getPakag().equals(app_data.get(0).getQureka_url())) {
                ad_call_to_action.setText("Play Now");
            } else {
                ad_call_to_action.setText("Install");
            }
        }
        setOnClick();
    }

    public void set_small_native_Values(final Appdetail appdetail) {
        inflate(context, R.layout.local_small_native_ad, this);
        AppIcon = findViewById(R.id.small_native_ad_app_icon);
        textViewAppName = findViewById(R.id.ad_Tital);
        ad_body = findViewById(R.id.small_native_ad_body);
        ad_call_to_action = findViewById(R.id.small_native_ad_call_to_action);
        Picasso.get().load(appdetail.getApp_icon()).into(AppIcon);
        textViewAppName.setText(appdetail.getApp_name());
        ad_body.setText(appdetail.getApp_body());
        this.url = appdetail.getPakag();
        if (app_data != null && app_data.size() > 0) {
            if (appdetail.getPakag().equals(app_data.get(0).getQureka_url())) {
                ad_call_to_action.setText("Play Now");
            } else {
                ad_call_to_action.setText("Install");
            }
        }
        setOnClick();
    }

    public void set_big_native_Values(final Appdetail appdetail) {
        inflate(context, R.layout.local_native_ad, this);
        AppIcon = findViewById(R.id.ad_app_icon);
        ad_banner = findViewById(R.id.ad_banner);
        textViewAppName = findViewById(R.id.ad_headline);
        ad_body = findViewById(R.id.ad_body);
        ad_call_to_action = findViewById(R.id.ad_call_to_action);
        Picasso.get().load(appdetail.getApp_icon()).into(AppIcon);
        Picasso.get().load(appdetail.getApp_banner()).into(ad_banner);
        textViewAppName.setText(appdetail.getApp_name());
        ad_body.setText(appdetail.getApp_body());
        this.url = appdetail.getPakag();
        if (app_data != null && app_data.size() > 0) {
            if (appdetail.getPakag().equals(app_data.get(0).getQureka_url())) {
                ad_call_to_action.setText("Play Now");
            } else {
                ad_call_to_action.setText("Install");
            }
        }
        setOnClick();
    }

    private void setOnClick() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (app_data != null && app_data.size() > 0) {
                    if (url.equals(app_data.get(0).getQureka_url())) {
                        try {
                            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                            customIntent.setToolbarColor(ContextCompat.getColor(context, R.color.first_color));
                            Conts.openCustomTab((Activity) context, customIntent.build(), Uri.parse(url));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + url));
                            context.startActivity(browse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}