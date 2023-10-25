package com.ads.data.Local_ads;

public interface JsonObjectGetListener {
    void onSuccess(Appdetail[] appdetails);

    void onError(String error);
}

