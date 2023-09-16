package com.ads.data.Api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface APIInterface {
    // FileZilla
    @POST()
    @FormUrlEncoded
    Call<File_Recover> get_file_ads_detail(@Field("packagename") String packagename, @Url String url);

    // Panal
    @POST("ads")
    @FormUrlEncoded
    Call<Panal_Recover> get_panal_ads_detail(@Field("package_name") String packagename);
}