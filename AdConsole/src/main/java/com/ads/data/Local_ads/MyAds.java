package com.ads.data.Local_ads;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ads.data.Conts;
import com.ads.data.OnClickListener;

public class MyAds {
    private String theNumber = "theNumber";
    private Appdetail[] appdetails;
    private int aNumber = 0;

    /**
     * @noinspection deprecation
     */
    public MyAds(final Context context, String url) {
        spInit(context);
        new JsonObjectGetter(context, url, new JsonObjectGetListener() {
            @Override
            public void onSuccess(Appdetail[] appdetails) {
                MyAds.this.appdetails = appdetails;
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }
    public void local_Banner(ViewGroup banner_ad) {
        Conts.log_debug("Parth", "local banner ad show");
        MyAdsView myAdsView = new MyAdsView(banner_ad.getContext());
        myAdsView.set_small_native_banner_Values(appdetails[aNumber]);
        banner_ad.removeAllViews();
        banner_ad.addView(myAdsView);
        setaNumber();
    }
    public void small_local_Native_Banner(ViewGroup small_native_banner_ad) {
        Conts.log_debug("Parth", "local small native banner ad show");
        MyAdsView myAdsView = new MyAdsView(small_native_banner_ad.getContext());
        myAdsView.set_small_native_banner_Values(appdetails[aNumber]);
        small_native_banner_ad.removeAllViews();
        small_native_banner_ad.addView(myAdsView);
        setaNumber();
    }

    public void small_local_Native(ViewGroup small_native_ad) {
        Conts.log_debug("Parth", "local small native ad show");
        MyAdsView myAdsView = new MyAdsView(small_native_ad.getContext());
        myAdsView.set_small_native_Values(appdetails[aNumber]);
        small_native_ad.removeAllViews();
        small_native_ad.addView(myAdsView);
        setaNumber();
    }

    public void local_Native(ViewGroup native_ad) {
        Conts.log_debug("Parth", "local native ad show");
        MyAdsView myAdsView = new MyAdsView(native_ad.getContext());
        myAdsView.set_big_native_Values(appdetails[aNumber]);
        native_ad.removeAllViews();
        native_ad.addView(myAdsView);
        setaNumber();
    }

    public void show_local_InterAd(Activity activity, OnClickListener callback) {
        if (appdetails != null) {
            Conts.log_debug("Parth", "local inter ad show");
            new MyAdsInter(activity, appdetails[aNumber], callback);
            setaNumber();
        } else {
            callback.onClick();
        }
    }

    public void local_Appopen_show(Activity activity, OnClickListener callback) {
        if (appdetails != null) {
            Conts.log_debug("Parth", "local appopen ad show");
            new MyAppopen(activity, appdetails[aNumber], callback);
            setaNumber();
        } else {
            callback.onClick();
        }
    }

    private void spInit(Context context) {
        FayazSP.init(context);
        aNumber = FayazSP.getInt(theNumber, 0);
    }

    private void setaNumber() {
        aNumber++;
        if (aNumber == appdetails.length) {
            aNumber = 0;
        }
        FayazSP.put(theNumber, aNumber);
    }
}