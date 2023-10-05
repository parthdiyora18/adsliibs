package com.ads.data.Api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    static Retrofit retrofit = null;
    private static final String ip_url = "http://ip-api.com/";

    public static Retrofit get_panal_Client(String key) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder().baseUrl(key).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        return retrofit;
    }

    public static Retrofit get_file_Client(String key) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder().baseUrl(key).addConverterFactory(GsonConverterFactory.create()).client(client).build();
        return retrofit;
    }

    public static Retrofit get_ip_clint() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ip_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}