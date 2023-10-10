package com.ads.data.Local_ads;

public interface JsonObjectGetListener {
    void onSuccess(MyAd[] myAds);

    void onError(String error);
}
