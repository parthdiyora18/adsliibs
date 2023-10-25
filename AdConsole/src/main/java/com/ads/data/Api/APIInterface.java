package com.ads.data.Api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIInterface {
    // FileZilla
    @POST()
    @FormUrlEncoded
    Call<File_Recover> get_file_ads_detail(@Field("packagename") String packagename, @Url String url);

    // IpData
    @GET()
    Call<IPModel> getipdata(@Url String url);
}