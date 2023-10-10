package com.ads.data.Local_ads;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ads.data.OnClickListener;

public class MyLocalAds {
    private String theNumber = "theNumber";
    private MyAd[] myAds;
    private int aNumber = 0;

    public MyLocalAds(final Context context, String url) {
        spInit(context);
        new JsonObjectGetter(context, url, new JsonObjectGetListener() {
            @Override
            public void onSuccess(MyAd[] myAds) {
                MyLocalAds.this.myAds = myAds;
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, "" + error, Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }

    public void small_local_Native_Banner(ViewGroup small_native_banner_ad) {
        MyAdsView myAdsView = new MyAdsView(small_native_banner_ad.getContext());
        myAdsView.set_small_native_banner_Values(myAds[aNumber]);
        small_native_banner_ad.removeAllViews();
        small_native_banner_ad.addView(myAdsView);
        setaNumber();
    }

    public void small_local_Native(ViewGroup small_native_ad) {
        MyAdsView myAdsView = new MyAdsView(small_native_ad.getContext());
        myAdsView.set_small_native_Values(myAds[aNumber]);
        small_native_ad.removeAllViews();
        small_native_ad.addView(myAdsView);
        setaNumber();
    }

    public void local_Native(ViewGroup native_ad) {
        MyAdsView myAdsView = new MyAdsView(native_ad.getContext());
        myAdsView.set_big_native_Values(myAds[aNumber]);
        native_ad.removeAllViews();
        native_ad.addView(myAdsView);
        setaNumber();
    }

    public void show_local_InterAd(Activity activity, OnClickListener callback) {
        if (myAds != null) {
            new MyAdsInter(activity, myAds[aNumber], callback);
            setaNumber();
        } else {
            callback.onClick();
        }
    }

    public void show_local_Appopen(Activity activity, OnClickListener callback) {
        if (myAds != null) {
            new MyAppopen(activity, myAds[aNumber], callback);
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
        if (aNumber == myAds.length) {
            aNumber = 0;
        }
        FayazSP.put(theNumber, aNumber);
    }
}