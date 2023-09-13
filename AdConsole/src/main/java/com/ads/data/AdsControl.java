package com.ads.data;

import static com.ads.data.Api.APIClient.Base_url;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.ads.data.Api.APIClient;
import com.ads.data.Api.APIInterface;
import com.ads.data.Api.Data;
import com.ads.data.Api.Recover;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdFormat;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdkUtils;
import com.bumptech.glide.Glide;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAdBase;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.inmobi.ads.AdMetaInfo;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.InMobiInterstitial;
import com.inmobi.ads.listeners.BannerAdEventListener;
import com.inmobi.ads.listeners.InterstitialAdEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wortise.ads.WortiseSdk;
import com.wortise.ads.banner.BannerAd;
import com.wortise.ads.natives.GoogleNativeAd;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import cz.msebera.android.httpclient.entity.mime.Header;
import retrofit2.Call;

public class AdsControl {
    @SuppressLint("StaticFieldLeak")
    private static AdsControl mInstance;
    @SuppressLint("StaticFieldLeak")
    static Context activity;
    String TAG = "Parth";
    // Banner
    boolean isGoogleBannerLoaded;
    boolean isAdxBannerLoaded;
    boolean isFBBannerLoaded;
    boolean isApplovinBannerLoaded;
    boolean isWortiseBannerLoaded;
    boolean isInmobiBannerLoaded;
    AdView googleBannerAd;
    AdManagerAdView adxBannerAd;
    com.facebook.ads.AdView fbadView;
    @SuppressLint("StaticFieldLeak")
    MaxAdView applovin_banner_ad;
    BannerAd wBannerAd;
    InMobiBanner InmobiBannerAd;

    // Mediam Ragtangal
    boolean isAdmob_Mediam_Ragtangal_Loaded;
    boolean isAdx_Mediam_Ragtangal_Loaded;
    boolean isFB_Mediam_Ragtangal_Loaded;
    boolean isApplovin_Mediam_Ragtangal_Loaded;
    boolean isWortise_medium_ragtangal_Loaded;
    AdView admobMediam_Ragtangal;
    AdManagerAdView adxMediam_Ragtangal;
    com.facebook.ads.AdView fb_Ragtangal_adView;
    @SuppressLint("StaticFieldLeak")
    MaxAdView applovin_Medium_Ragtangal_adview;
    BannerAd w_medium_ragtangal;

    // Native Ad
    boolean isadmob_native_Loaded;
    boolean isadx_native_Loaded;
    boolean isFB_Native_Loaded;
    boolean isApplovin_Native_Loaded;
    boolean isWortise_Native_Loaded;
    boolean isLocal_Native_Loaded;
    NativeAd Admob_native_Ad;
    NativeAd Adx_native_Ad;
    com.facebook.ads.NativeAd fb_native_Ad;
    MaxAd Applovin_native_ad;
    @SuppressLint("StaticFieldLeak")
    MaxNativeAdView applovin_maxnativeadview;
    NativeAd wortise_native_Ad;
    @SuppressLint("StaticFieldLeak")
    GoogleNativeAd wortise_google_native_Ad;

    // Small Native Ad
    boolean isAdmob_small_native_Loaded;
    boolean isadx_small_native_Loaded;
    boolean isFb_small_native_Loaded;
    boolean isapplovin_small_native_Loaded;
    boolean isWortise_small_Native_Loaded;
    boolean isLocal_small_Native_Loaded;
    NativeAd Admob_small_native_Ad;
    NativeAd Adx_small_native_Ad;
    NativeBannerAd fb_small_native_Ad;
    MaxAd Applovin_small_native_Ad;
    @SuppressLint("StaticFieldLeak")
    MaxNativeAdView applovin_small_native_Ad;
    NativeAd wortise_small_native_Ad;
    @SuppressLint("StaticFieldLeak")
    GoogleNativeAd wortise_google_small_native_Ad;

    // Small Naitive Banner Ad
    boolean isadmob_small_native_banner_Loaded;
    boolean isadx_small_native_banner_Loaded;
    boolean isFB_small_native_banner_Loaded;
    boolean isApplovin_small_native_banner_Loaded;
    boolean isWortise_small_Native_banner_Loaded;
    boolean isLocal_small_Native_banner_Loaded;
    NativeAd Admob_small_native_banner_Ad;
    NativeAd Adx_small_native_banner_Ad;
    NativeBannerAd fb_small_native_banner_Ad;
    MaxAd Applovin_small_native_banner_Ad;
    @SuppressLint("StaticFieldLeak")
    MaxNativeAdView applovin_small_native_banner_Ad;
    NativeAd wortise_small_native_banner_Ad;
    @SuppressLint("StaticFieldLeak")
    GoogleNativeAd wortise_small_google_native_banner_Ad;

    // Inter
    boolean isGoogleInterLoaded;
    boolean isAdxInterLoaded;
    boolean isFBInterLoaded;
    boolean isApplovinInterLoaded;
    boolean isWortiseInterLoaded;
    boolean isInmobiInterLoaded;
    boolean isLocalInterLoaded;
    InterstitialAd ADMOBInterstitialAd;
    AdManagerInterstitialAd ADXInterstitialAd;
    com.facebook.ads.InterstitialAd FB_interstitialAd;
    MaxInterstitialAd Applovin_maxInterstitialAd;
    com.wortise.ads.interstitial.InterstitialAd Wortise_inter;
    InMobiInterstitial Inmobi_inter;

    // Appopen
    boolean isadmob_appopen_Loaded;
    boolean isadx_appopen_Loaded;
    boolean iswortise_appopen_Loaded;
    boolean isapplovin_appopen_Loaded;
    boolean islocal_appopen_Loaded;
    com.wortise.ads.appopen.AppOpenAd wortise_open;
    MaxAppOpenAd applovin_appopen;
    AppOpenAd admob_appOpenAd_inter;
    AppOpenAd adx_appOpenAd_inter;
    AppOpenAd admob_appOpenAd;
    AppOpenAd adx_appOpenAd;

    int adCounter;
    SharedPreferences prefs;

    public AdsControl(Context context) {
        activity = context;
        prefs = activity.getSharedPreferences(activity.getPackageName(), 0);
    }

    public static AdsControl getInstance(Context context) {
        activity = context;
        if (mInstance == null) {
            mInstance = new AdsControl(context);
        }
        return mInstance;
    }

    public static boolean isOnline(Context ctx) {
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    // --------------------------------------------------- Service -----------------------------------------------------------

    AsyncHttpClient downloadcount = new AsyncHttpClient();
    int success = 0;

    public void installcounter(String packagename) {
        RequestParams params = new RequestParams();
        params.put("package_name", packagename);
        downloadcount.post(Base_url + "appInstall", params, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("Response : ", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(String.valueOf(response));
                    success = jsonObject.getInt("success");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
            }
        });
    }

    // TODO: 7/17/2023 Main Service Call
    public static ArrayList<Data> app_data = new ArrayList<>();

    @SuppressLint("ObsoleteSdkInt")
    public void init(final Activity activity, String packagename, getDataListner Callback) {
        boolean isBeingDebugged = Settings.Secure.getInt(activity.getContentResolver(), Settings.Global.ADB_ENABLED, 0) == 1;
        if (isNetworkAvailable()) {
            try {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
                    Call<Recover> call1 = apiInterface.getadsdetail(packagename);
                    call1.enqueue(new retrofit2.Callback<>() {
                        @Override
                        public void onResponse(@NotNull Call<Recover> call, @NotNull retrofit2.Response<Recover> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                if (response.body().getData() != null) {
                                    app_data.clear();
                                    Conts.log_debug(TAG, "Parth_Diyora " + response.body().getData());
                                    app_data.add(response.body().getData());
                                    WortiseSdk.initialize(activity, app_data.get(0).getWortiseAppId());
                                    String ridirect_app = app_data.get(0).getRedirectApp();
                                    if (!ridirect_app.equalsIgnoreCase("")) {
                                        activity.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse("https://play.google.com/store/apps/details?id=" + ridirect_app)));
                                    } else {
                                        if (app_data.get(0).isVpn_option()) {
                                            Conts conts = new Conts(activity);
                                            conts.check_VPN_App(activity, () -> {
                                                if (isBeingDebugged && app_data.get(0).isDev_option()) {
                                                    Conts conts1 = new Conts(activity);
                                                    conts1.Debugging(() -> preload_ads_call(Callback));
                                                } else {
                                                    preload_ads_call(Callback);
                                                }
                                            });
                                        } else {
                                            if (isBeingDebugged && app_data.get(0).isDev_option()) {
                                                Conts conts1 = new Conts(activity);
                                                conts1.Debugging(() -> preload_ads_call(Callback));
                                            } else {
                                                preload_ads_call(Callback);
                                            }
                                        }
                                    }
                                } else {
                                    preload_ads_call(Callback);
                                    Toast.makeText(activity, "Server not Response", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Recover> call, @NonNull Throwable t) {
                            call.cancel();
                            preload_ads_call(Callback);
                        }
                    });
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Conts conts = new Conts(activity);
            conts.App_Data(activity, packagename);
        } else {
            Conts conts = new Conts(activity);
            conts.networkinfo();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    // TODO: 8/29/2023  Preload ads
    private void preload_ads_call(getDataListner myCallback) {
        if (app_data != null && app_data.size() > 0) {
            if (app_data.get(0).isAds_show()) {
                if (app_data.get(0).isPreload_native_ads()) {
                    if (app_data.get(0).getAd_native_type().equalsIgnoreCase("mrec")) {
                        medium_rect_Ads();
                    } else {
                        native_Ads();
                    }
                    small_native_banner_Ads();
                    small_native_Ads();
                }
                if (app_data.get(0).isPreload_inter_ads()) {
                    if (app_data.get(0).getAd_inter_type().equalsIgnoreCase("appopen")) {
                        appopen_Ads();
                    } else {
                        inter_Ads();
                    }
                }
                if (app_data.get(0).isPreload_banner_ads()) {
                    banner_Ads();
                }
                call(myCallback);
            } else {
                call(myCallback);
            }
        } else {
            call(myCallback);
        }
    }

    // TODO: 7/17/2023 Call Intent
    private void call(getDataListner myCallback) {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String adnetwork = app_data.get(0).getAdSplash();
                    switch (adnetwork) {
                        case "native":
                            new Handler().postDelayed(() -> open_native(myCallback), 3000);
                            break;
                        case "inter":
                            AdsControl.getInstance(activity).show_splash_inter(() -> Next_Call(myCallback));
                            break;
                        case "admob":
                            AdsControl.getInstance(activity).show_Admob_Appopen(() -> Next_Call(myCallback));
                            break;
                        case "adx":
                            AdsControl.getInstance(activity).show_Adx_Appopen(() -> Next_Call(myCallback));
                            break;
                        case "applovin":
                            AdsControl.getInstance(activity).show_Applovin_Appopen(() -> Next_Call(myCallback));
                            break;
                        case "wortise":
                            AdsControl.getInstance(activity).show_Wortise_Appopen(() -> Next_Call(myCallback));
                            break;
                        case "local":
                            AdsControl.getInstance(activity).show_local_Appopen(() -> Next_Call(myCallback));
                            break;
                        case "off":
                            Next_Call(myCallback);
                            break;
                        default:
                    }
                } else {
                    Next_Call(myCallback);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: 7/17/2023  Open Native Dialog Ads
    Dialog ad_dialog;

    private void open_native(getDataListner callback) {
        Dialog dialog = new Dialog(activity);
        ad_dialog = dialog;
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        this.ad_dialog.setContentView(R.layout.open_native);
        show_native_ad(dialog.findViewById(R.id.ad_native));
        ImageView continuee = ad_dialog.findViewById(R.id.continuee);
        new Handler().postDelayed(() -> {
            continuee.setVisibility(View.VISIBLE);
            continuee.setOnClickListener(v -> {
                ad_dialog.dismiss();
                secound_splash_Ads(callback);
            });
        }, 2500);
        this.ad_dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(this.ad_dialog.getWindow()).setSoftInputMode(3);
        this.ad_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.ad_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.ad_dialog.show();
    }

    private void secound_splash_Ads(getDataListner callback2) {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String adnetwork = app_data.get(0).getAd_secound_splash();
                    switch (adnetwork) {
                        case "inter":
                            AdsControl.getInstance(activity).show_splash_inter(() -> Next_Call(callback2));
                            break;
                        case "admob":
                            AdsControl.getInstance(activity).show_Admob_Appopen(() -> Next_Call(callback2));
                            break;
                        case "adx":
                            AdsControl.getInstance(activity).show_Adx_Appopen(() -> Next_Call(callback2));
                            break;
                        case "applovin":
                            AdsControl.getInstance(activity).show_Applovin_Appopen(() -> Next_Call(callback2));
                            break;
                        case "wortise":
                            AdsControl.getInstance(activity).show_Wortise_Appopen(() -> Next_Call(callback2));
                            break;
                        case "local":
                            AdsControl.getInstance(activity).show_local_Appopen(() -> Next_Call(callback2));
                            break;
                        case "off":
                            Next_Call(callback2);
                            break;
                        default:
                    }
                } else {
                    Next_Call(callback2);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void Next_Call(getDataListner myCallback) {
        myCallback.onSuccess();
    }

    //-------------------------------------------------------- Banner Ads -------------------------------------------------------

    // TODO: 7/31/2023  Preload Banner Ads
    int ad_banner_network = 0;
    int current_admob_BannerId = 0;
    int current_adx_BannerId = 0;
    int current_fb_BannerId = 0;
    int current_applovin_BannerId = 0;
    int current_wortise_BannerId = 0;
    int current_inmobi_BannerId = 0;

    private void banner_Ads() {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAdBanner().split(",");
                    if (ad_banner_network < adnetwork.length) {
                        switch (adnetwork[ad_banner_network]) {
                            case "admob":
                                String[] admob_BannerId = app_data.get(0).getAdmobBannerid().split(",");
                                if (current_admob_BannerId < admob_BannerId.length) {
                                    preloadBannerAd_Admob(admob_BannerId[current_admob_BannerId]);
                                    current_admob_BannerId++;
                                    if (current_admob_BannerId == admob_BannerId.length) {
                                        current_admob_BannerId = 0;
                                    }
                                }
                                ad_banner_network++;
                                break;
                            case "adx":
                                String[] adx_BannerId = app_data.get(0).getAdxBannerId().split(",");
                                if (current_adx_BannerId < adx_BannerId.length) {
                                    preloadBannerAd_Adx(adx_BannerId[current_adx_BannerId]);
                                    current_adx_BannerId++;
                                    if (current_adx_BannerId == adx_BannerId.length) {
                                        current_adx_BannerId = 0;
                                    }
                                }
                                ad_banner_network++;
                                break;
                            case "fb":
                                String[] fb_BannerId = app_data.get(0).getFbBannerId().split(",");
                                if (current_fb_BannerId < fb_BannerId.length) {
                                    preloadBannerAd_FB(fb_BannerId[current_fb_BannerId]);
                                    current_fb_BannerId++;
                                    if (current_fb_BannerId == fb_BannerId.length) {
                                        current_fb_BannerId = 0;
                                    }
                                }
                                ad_banner_network++;
                                break;
                            case "applovin":
                                String[] applovin_BannerId = app_data.get(0).getApplovin_banner_id().split(",");
                                if (current_applovin_BannerId < applovin_BannerId.length) {
                                    preloadBannerAd_Applovin(applovin_BannerId[current_applovin_BannerId]);
                                    current_applovin_BannerId++;
                                    if (current_applovin_BannerId == applovin_BannerId.length) {
                                        current_applovin_BannerId = 0;
                                    }
                                }
                                ad_banner_network++;
                                break;
                            case "wortise":
                                String[] wortise_BannerId = app_data.get(0).getWortiseBannerId().split(",");
                                if (current_wortise_BannerId < wortise_BannerId.length) {
                                    preloadBannerAd_Wortise(wortise_BannerId[current_wortise_BannerId]);
                                    current_wortise_BannerId++;
                                    if (current_wortise_BannerId == wortise_BannerId.length) {
                                        current_wortise_BannerId = 0;
                                    }
                                }
                                ad_banner_network++;
                                break;
                            case "inmobi":
                                String[] inmobi_BannerId = app_data.get(0).getInmobi_banner_id().split(",");
                                if (current_inmobi_BannerId < inmobi_BannerId.length) {
                                    preloadBannerAd_Inmobi(Long.valueOf(inmobi_BannerId[current_inmobi_BannerId]));
                                    current_inmobi_BannerId++;
                                    if (current_inmobi_BannerId == inmobi_BannerId.length) {
                                        current_inmobi_BannerId = 0;
                                    }
                                }
                                ad_banner_network++;
                                break;
                            default:
                        }
                        if (ad_banner_network == adnetwork.length) {
                            ad_banner_network = 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Admob Mode
    @SuppressLint("MissingPermission")
    private void preloadBannerAd_Admob(String placementId) {
        if (isGoogleBannerLoaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final AdView admob_Banner = new AdView(activity);
            admob_Banner.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, AdSize.FULL_WIDTH));
            admob_Banner.setAdUnitId(placementId);
            AdRequest adRequest = new AdRequest.Builder().build();
            admob_Banner.loadAd(adRequest);
            admob_Banner.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    Conts.log_debug(TAG, "Admob Banner Loadedd ");
                    googleBannerAd = admob_Banner;
                    isGoogleBannerLoaded = true;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    super.onAdFailedToLoad(adError);
                    Conts.log_debug(TAG, "Admob Banner Failed" + adError.getMessage());
                    banner_Ads();
                }
            });
        }
    }

    // Adx Mode
    @SuppressLint("MissingPermission")
    private void preloadBannerAd_Adx(String placementId) {
        if (isAdxBannerLoaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final AdManagerAdView adx_Banner = new AdManagerAdView(activity);
            adx_Banner.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, AdSize.FULL_WIDTH));
            adx_Banner.setAdUnitId(placementId);
            @SuppressLint("VisibleForTests") AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            adx_Banner.loadAd(adRequest);
            adx_Banner.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Conts.log_debug(TAG, "Adx Banner Loadedd ");
                    adxBannerAd = adx_Banner;
                    isAdxBannerLoaded = true;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Adx Banner Failed" + loadAdError.getMessage());
                    banner_Ads();
                }
            });
        }
    }

    // FB Mode
    private void preloadBannerAd_FB(String placementId) {
        if (isFBBannerLoaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final com.facebook.ads.AdView fb_banner = new com.facebook.ads.AdView(activity, placementId, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {

                @Override
                public void onAdLoaded(Ad ad) {
                    Conts.log_debug(TAG, "Fb Banner Loadedd ");
                    fbadView = fb_banner;
                    isFBBannerLoaded = true;
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB Banner Failed" + adError.getErrorMessage());
                    banner_Ads();
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };
            fb_banner.loadAd(fb_banner.buildLoadAdConfig().withAdListener(adListener).build());
        }
    }

    // Applovin Mode
    private void preloadBannerAd_Applovin(String placementId) {
        if (isApplovinBannerLoaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final MaxAdView applo_banner_ad = new MaxAdView(placementId, activity);
            applo_banner_ad.setLayoutParams(new ViewGroup.LayoutParams(320, 50));
            applo_banner_ad.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(MaxAd maxAd) {
                }

                @Override
                public void onAdCollapsed(MaxAd maxAd) {
                }

                @Override
                public void onAdLoaded(MaxAd maxAd) {
                    Conts.log_debug(TAG, "Applovin Banner Loadedd ");
                    applovin_banner_ad = applo_banner_ad;
                    isApplovinBannerLoaded = true;
                }

                @Override
                public void onAdDisplayed(MaxAd maxAd) {
                }

                @Override
                public void onAdHidden(MaxAd maxAd) {
                }

                @Override
                public void onAdClicked(MaxAd maxAd) {
                }

                @Override
                public void onAdLoadFailed(String s, MaxError maxError) {
                    Conts.log_debug(TAG, "Applovin Banner Failed" + maxError.getMessage());
                    banner_Ads();
                }

                @Override
                public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                }
            });
            applo_banner_ad.loadAd();
        }
    }

    // Wortise Mode
    private void preloadBannerAd_Wortise(String placementId) {
        if (isWortiseBannerLoaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final BannerAd wortise_BannerAd = new BannerAd(activity);
            wortise_BannerAd.setAdSize(com.wortise.ads.AdSize.HEIGHT_50);
            wortise_BannerAd.setAdUnitId(placementId);
            wortise_BannerAd.loadAd();
            wortise_BannerAd.setListener(new BannerAd.Listener() {
                @Override
                public void onBannerLoaded(@NonNull BannerAd bannerAd) {
                    Conts.log_debug(TAG, "Wortise Banner Loadedd ");
                    wBannerAd = wortise_BannerAd;
                    isWortiseBannerLoaded = true;
                }

                @Override
                public void onBannerClicked(@NonNull BannerAd bannerAd) {
                }

                @Override
                public void onBannerFailed(@NonNull BannerAd bannerAd, @NonNull com.wortise.ads.AdError adError) {
                    Conts.log_debug(TAG, "Wortise banner Failed " + adError);
                    banner_Ads();
                }
            });
        }
    }

    // Inmobi Mode
    private void preloadBannerAd_Inmobi(Long placementId) {
        if (isInmobiBannerLoaded) {
            return;
        }
        if (!(placementId == 0)) {
            final InMobiBanner inMobiBanner = new InMobiBanner(activity, placementId);
            inMobiBanner.setBannerSize(320, 50);
            inMobiBanner.load();
            inMobiBanner.setListener(new BannerAdEventListener() {
                @Override
                public void onAdFetchFailed(@NonNull InMobiBanner inMobiBanner, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                    super.onAdFetchFailed(inMobiBanner, inMobiAdRequestStatus);

                }

                public void onAdLoadFailed(@NonNull InMobiBanner inMobiBanner, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                    super.onAdLoadFailed(inMobiBanner, inMobiAdRequestStatus);
                    Conts.log_debug(TAG, "Inmobi banner Failed: " + inMobiAdRequestStatus.getMessage());
                    banner_Ads();
                }

                @Override
                public void onAdFetchSuccessful(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo adMetaInfo) {
                    super.onAdFetchSuccessful(inMobiBanner, adMetaInfo);
                }

                @Override
                public void onAdLoadSucceeded(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo adMetaInfo) {
                    super.onAdLoadSucceeded(inMobiBanner, adMetaInfo);
                    Conts.log_debug(TAG, "Inmobi banner loaded");
                    InmobiBannerAd = inMobiBanner;
                    isInmobiBannerLoaded = true;
                }
            });
        }
    }

    // TODO: 7/17/2023  Show Banner Ads
    @SuppressLint("MissingPermission")
    public void show_banner_ad(final ViewGroup banner_container) {
        if (app_data != null && app_data.size() > 0) {
            if (app_data.get(0).isAds_show()) {
                if (app_data.get(0).isPreload_banner_ads()) {
                    if (isGoogleBannerLoaded) {
                        try {
                            if (googleBannerAd.getParent() != null) {
                                ((ViewGroup) googleBannerAd.getParent()).removeView(googleBannerAd);
                            }
                            banner_container.addView(googleBannerAd);
                            isGoogleBannerLoaded = false;
                            banner_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isAdxBannerLoaded) {
                        try {
                            try {
                                if (adxBannerAd.getParent() != null) {
                                    ((ViewGroup) adxBannerAd.getParent()).removeView(adxBannerAd);
                                }
                                banner_container.addView(adxBannerAd);
                                isAdxBannerLoaded = false;
                                banner_Ads();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isFBBannerLoaded) {
                        try {
                            if (fbadView.getParent() != null) {
                                ((ViewGroup) fbadView.getParent()).removeView(fbadView);
                            }
                            banner_container.addView(fbadView);
                            isFBBannerLoaded = false;
                            banner_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isApplovinBannerLoaded) {
                        try {
                            if (applovin_banner_ad.getParent() != null) {
                                ((ViewGroup) applovin_banner_ad.getParent()).removeView(applovin_banner_ad);
                            }
                            banner_container.addView(applovin_banner_ad);
                            isApplovinBannerLoaded = false;
                            banner_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isWortiseBannerLoaded) {
                        try {
                            if (wBannerAd.getParent() != null) {
                                ((ViewGroup) wBannerAd.getParent()).removeView(wBannerAd);
                            }
                            banner_container.addView(wBannerAd);
                            isWortiseBannerLoaded = false;
                            banner_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isInmobiBannerLoaded) {
                        try {
                            if (InmobiBannerAd.getParent() != null) {
                                ((ViewGroup) InmobiBannerAd.getParent()).removeView(InmobiBannerAd);
                            }
                            banner_container.addView(InmobiBannerAd);
                            isInmobiBannerLoaded = false;
                            banner_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    try {
                        String[] adnetwork = app_data.get(0).getAdBanner().split(",");
                        if (ad_banner_network < adnetwork.length) {
                            switch (adnetwork[ad_banner_network]) {
                                case "admob":
                                    String[] admob_BannerId = app_data.get(0).getAdmobBannerid().split(",");
                                    if (current_admob_BannerId < admob_BannerId.length) {
                                        String placementId = admob_BannerId[current_admob_BannerId];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final AdView admob_Banner = new AdView(activity);
                                            admob_Banner.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, AdSize.FULL_WIDTH));
                                            admob_Banner.setAdUnitId(placementId);
                                            AdRequest adRequest = new AdRequest.Builder().build();
                                            admob_Banner.loadAd(adRequest);
                                            admob_Banner.setAdListener(new AdListener() {
                                                @Override
                                                public void onAdLoaded() {
                                                    Conts.log_debug(TAG, "Admob Banner Loadedd ");
                                                    try {
                                                        if (admob_Banner.getParent() != null) {
                                                            ((ViewGroup) admob_Banner.getParent()).removeView(admob_Banner);
                                                        }
                                                        banner_container.addView(admob_Banner);
                                                    } catch (Exception e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }

                                                @Override
                                                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                                    super.onAdFailedToLoad(adError);
                                                    Conts.log_debug(TAG, "Admob Banner Failed" + adError.getMessage());
                                                }
                                            });
                                            current_admob_BannerId++;
                                            if (current_admob_BannerId == admob_BannerId.length) {
                                                current_admob_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_banner_network++;
                                    break;
                                case "adx":
                                    String[] adx_BannerId = app_data.get(0).getAdxBannerId().split(",");
                                    if (current_adx_BannerId < adx_BannerId.length) {
                                        String placementId = adx_BannerId[current_adx_BannerId];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final AdManagerAdView adx_Banner = new AdManagerAdView(activity);
                                            adx_Banner.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, AdSize.FULL_WIDTH));
                                            adx_Banner.setAdUnitId(placementId);
                                            @SuppressLint("VisibleForTests") AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
                                            adx_Banner.loadAd(adRequest);
                                            adx_Banner.setAdListener(new AdListener() {
                                                @Override
                                                public void onAdLoaded() {
                                                    super.onAdLoaded();
                                                    Conts.log_debug(TAG, "Adx Banner Loadedd ");
                                                    try {
                                                        if (adx_Banner.getParent() != null) {
                                                            ((ViewGroup) adx_Banner.getParent()).removeView(adx_Banner);
                                                        }
                                                        banner_container.addView(adx_Banner);
                                                    } catch (Exception e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }

                                                @Override
                                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                                    super.onAdFailedToLoad(loadAdError);
                                                    Conts.log_debug(TAG, "Adx Banner Failed" + loadAdError.getMessage());
                                                }
                                            });
                                            current_adx_BannerId++;
                                            if (current_adx_BannerId == adx_BannerId.length) {
                                                current_adx_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_banner_network++;
                                    break;
                                case "fb":
                                    String[] fb_BannerId = app_data.get(0).getFbBannerId().split(",");
                                    if (current_fb_BannerId < fb_BannerId.length) {
                                        String placementId = fb_BannerId[current_fb_BannerId];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final com.facebook.ads.AdView fb_banner = new com.facebook.ads.AdView(activity, placementId, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
                                            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {

                                                @Override
                                                public void onAdLoaded(Ad ad) {
                                                    Conts.log_debug(TAG, "Fb Banner Loadedd ");
                                                    try {
                                                        if (fb_banner.getParent() != null) {
                                                            ((ViewGroup) fb_banner.getParent()).removeView(fb_banner);
                                                        }
                                                        banner_container.addView(fb_banner);
                                                    } catch (Exception e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }

                                                @Override
                                                public void onError(Ad ad, AdError adError) {
                                                    Conts.log_debug(TAG, "FB Banner Failed" + adError.getErrorMessage());
                                                }

                                                @Override
                                                public void onAdClicked(Ad ad) {
                                                }

                                                @Override
                                                public void onLoggingImpression(Ad ad) {
                                                }
                                            };
                                            fb_banner.loadAd(fb_banner.buildLoadAdConfig().withAdListener(adListener).build());
                                            current_fb_BannerId++;
                                            if (current_fb_BannerId == fb_BannerId.length) {
                                                current_fb_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_banner_network++;
                                    break;
                                case "applovin":
                                    String[] applovin_BannerId = app_data.get(0).getApplovin_banner_id().split(",");
                                    if (current_applovin_BannerId < applovin_BannerId.length) {
                                        String placementId = applovin_BannerId[current_applovin_BannerId];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final MaxAdView applo_banner_ad = new MaxAdView(placementId, activity);
                                            applo_banner_ad.setLayoutParams(new ViewGroup.LayoutParams(320, 50));
                                            applo_banner_ad.setListener(new MaxAdViewAdListener() {
                                                @Override
                                                public void onAdExpanded(MaxAd maxAd) {
                                                }

                                                @Override
                                                public void onAdCollapsed(MaxAd maxAd) {
                                                }

                                                @Override
                                                public void onAdLoaded(MaxAd maxAd) {
                                                    Conts.log_debug(TAG, "Applovin Banner Loadedd ");
                                                    try {
                                                        if (applo_banner_ad.getParent() != null) {
                                                            ((ViewGroup) applo_banner_ad.getParent()).removeView(applo_banner_ad);
                                                        }
                                                        banner_container.addView(applo_banner_ad);
                                                    } catch (Exception e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }

                                                @Override
                                                public void onAdDisplayed(MaxAd maxAd) {
                                                }

                                                @Override
                                                public void onAdHidden(MaxAd maxAd) {
                                                }

                                                @Override
                                                public void onAdClicked(MaxAd maxAd) {
                                                }

                                                @Override
                                                public void onAdLoadFailed(String s, MaxError maxError) {
                                                    Conts.log_debug(TAG, "Applovin Banner Failed" + maxError.getMessage());
                                                }

                                                @Override
                                                public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                                                }
                                            });
                                            applo_banner_ad.loadAd();
                                            current_applovin_BannerId++;
                                            if (current_applovin_BannerId == applovin_BannerId.length) {
                                                current_applovin_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_banner_network++;
                                    break;
                                case "wortise":
                                    String[] wortise_BannerId = app_data.get(0).getWortiseBannerId().split(",");
                                    if (current_wortise_BannerId < wortise_BannerId.length) {
                                        String placementId = wortise_BannerId[current_wortise_BannerId];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final BannerAd wortise_BannerAd = new BannerAd(activity);
                                            wortise_BannerAd.setAdSize(com.wortise.ads.AdSize.HEIGHT_50);
                                            wortise_BannerAd.setAdUnitId(placementId);
                                            wortise_BannerAd.loadAd();
                                            wortise_BannerAd.setListener(new BannerAd.Listener() {
                                                @Override
                                                public void onBannerLoaded(@NonNull BannerAd bannerAd) {
                                                    Conts.log_debug(TAG, "Wortise Banner Loadedd ");
                                                    try {
                                                        if (wortise_BannerAd.getParent() != null) {
                                                            ((ViewGroup) wortise_BannerAd.getParent()).removeView(wortise_BannerAd);
                                                        }
                                                        banner_container.addView(wortise_BannerAd);
                                                    } catch (Exception e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }

                                                @Override
                                                public void onBannerClicked(@NonNull BannerAd bannerAd) {
                                                }

                                                @Override
                                                public void onBannerFailed(@NonNull BannerAd bannerAd, @NonNull com.wortise.ads.AdError adError) {
                                                    Conts.log_debug(TAG, "Wortise banner Failed " + adError);
                                                }
                                            });
                                            current_wortise_BannerId++;
                                            if (current_wortise_BannerId == wortise_BannerId.length) {
                                                current_wortise_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_banner_network++;
                                    break;
                                case "inmobi":
                                    String[] inmobi_BannerId = app_data.get(0).getInmobi_banner_id().split(",");
                                    if (current_inmobi_BannerId < inmobi_BannerId.length) {
                                        Long placementId = Long.valueOf(inmobi_BannerId[current_inmobi_BannerId]);
                                        if (!(placementId == 0)) {
                                            final InMobiBanner inMobiBanner = new InMobiBanner(activity, placementId);
                                            inMobiBanner.setBannerSize(320, 50);
                                            inMobiBanner.load();
                                            inMobiBanner.setListener(new BannerAdEventListener() {
                                                @Override
                                                public void onAdFetchFailed(@NonNull InMobiBanner inMobiBanner, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                                                    super.onAdFetchFailed(inMobiBanner, inMobiAdRequestStatus);

                                                }

                                                public void onAdLoadFailed(@NonNull InMobiBanner inMobiBanner, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                                                    super.onAdLoadFailed(inMobiBanner, inMobiAdRequestStatus);
                                                    Conts.log_debug(TAG, "Inmobi banner Failed: " + inMobiAdRequestStatus.getMessage());
                                                }

                                                @Override
                                                public void onAdFetchSuccessful(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo adMetaInfo) {
                                                    super.onAdFetchSuccessful(inMobiBanner, adMetaInfo);
                                                }

                                                @Override
                                                public void onAdLoadSucceeded(@NonNull InMobiBanner inMobiBanner, @NonNull AdMetaInfo adMetaInfo) {
                                                    super.onAdLoadSucceeded(inMobiBanner, adMetaInfo);
                                                    Conts.log_debug(TAG, "Inmobi banner loaded");
                                                    try {
                                                        if (inMobiBanner.getParent() != null) {
                                                            ((ViewGroup) inMobiBanner.getParent()).removeView(inMobiBanner);
                                                        }
                                                        banner_container.addView(inMobiBanner);
                                                    } catch (Exception e) {
                                                        throw new RuntimeException(e);
                                                    }
                                                }
                                            });
                                            current_inmobi_BannerId++;
                                            if (current_inmobi_BannerId == inmobi_BannerId.length) {
                                                current_inmobi_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_banner_network++;
                                    break;
                                default:
                            }
                            if (ad_banner_network == adnetwork.length) {
                                ad_banner_network = 0;
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    //---------------------------------------------Small Native banner type Ads ----------------------------------------------

    // TODO: 7/17/2023  Preload Small NAtive Banner Ads
    int ad_small_native_banner_network = 0;

    int current_admob_small_native_BannerId = 0;
    int current_adx_small_native_BannerId = 0;
    int current_fb_small_native_BannerId = 0;
    int current_applovin_small_native_BannerId = 0;
    int current_wortise_small_native_BannerId = 0;

    private void small_native_banner_Ads() {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAdSmallNativeBanner().split(",");
                    if (ad_small_native_banner_network < adnetwork.length) {
                        switch (adnetwork[ad_small_native_banner_network]) {
                            case "admob":
                                String[] admob_small__native_banner_id = app_data.get(0).getAdmobNativeid().split(",");
                                if (current_admob_small_native_BannerId < admob_small__native_banner_id.length) {
                                    preload_Admob_Native_Banner_Ad(admob_small__native_banner_id[current_admob_small_native_BannerId]);
                                    current_admob_small_native_BannerId++;
                                    if (current_admob_small_native_BannerId == admob_small__native_banner_id.length) {
                                        current_admob_small_native_BannerId = 0;
                                    }
                                }
                                ad_small_native_banner_network++;
                                break;
                            case "adx":
                                String[] adx_small_native_banner_id = app_data.get(0).getAdxNativeId().split(",");
                                if (current_adx_small_native_BannerId < adx_small_native_banner_id.length) {
                                    preload_Adx_Native_Banner(adx_small_native_banner_id[current_adx_small_native_BannerId]);
                                    current_adx_small_native_BannerId++;
                                    if (current_adx_small_native_BannerId == adx_small_native_banner_id.length) {
                                        current_adx_small_native_BannerId = 0;
                                    }
                                }
                                ad_small_native_banner_network++;
                                break;
                            case "fb":
                                String[] fb_native_banner_id = app_data.get(0).getFbNativeBannerId().split(",");
                                if (current_fb_small_native_BannerId < fb_native_banner_id.length) {
                                    preload_Fb_Native_BannerAd(fb_native_banner_id[current_fb_small_native_BannerId]);
                                    current_fb_small_native_BannerId++;
                                    if (current_fb_small_native_BannerId == fb_native_banner_id.length) {
                                        current_fb_small_native_BannerId = 0;
                                    }
                                }
                                ad_small_native_banner_network++;
                                break;
                            case "applovin":
                                String[] applovin_small_native_banner_id = app_data.get(0).getApplovin_small_native_bannerid().split(",");
                                if (current_applovin_small_native_BannerId < applovin_small_native_banner_id.length) {
                                    preload_Applovin_Native_BannerAd(applovin_small_native_banner_id[current_applovin_small_native_BannerId]);
                                    current_applovin_small_native_BannerId++;
                                    if (current_applovin_small_native_BannerId == applovin_small_native_banner_id.length) {
                                        current_applovin_small_native_BannerId = 0;
                                    }
                                }
                                ad_small_native_banner_network++;
                                break;
                            case "wortise":
                                String[] wortise_small_native_id = app_data.get(0).getWortiseNativeId().split(",");
                                if (current_wortise_small_native_BannerId < wortise_small_native_id.length) {
                                    preload_Wortise_Native_BannerAd(wortise_small_native_id[current_wortise_small_native_BannerId]);
                                    current_wortise_small_native_BannerId++;
                                    if (current_wortise_small_native_BannerId == wortise_small_native_id.length) {
                                        current_wortise_small_native_BannerId = 0;
                                    }
                                }
                                ad_small_native_banner_network++;
                                break;
                            case "local":
                                preload_Local_Native_BannerAd();
                                ad_small_native_banner_network++;
                                break;
                            default:
                        }
                        if (ad_small_native_banner_network == adnetwork.length) {
                            ad_small_native_banner_network = 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Admob Mode
    private void preload_Admob_Native_Banner_Ad(String placementId) {
        if (isadmob_small_native_banner_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                Admob_small_native_banner_Ad = nativeAd;
                isadmob_small_native_banner_Loaded = true;
                Conts.log_debug(TAG, "Admob Small Native Banner Ad Loaded");
            });
            builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Admob Small Native Banner Ad Failed");
                    small_native_banner_Ads();
                }

                public void onAdLoaded() {
                    super.onAdLoaded();
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
    }

    // Adx Mode
    @SuppressLint("MissingPermission")
    private void preload_Adx_Native_Banner(String placementId) {
        if (isadx_small_native_banner_Loaded) {
            return;
        }

        if (!placementId.equalsIgnoreCase("")) {
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                Conts.log_debug(TAG, "Adx Small Native Banner Ad Loaded");
                Adx_small_native_banner_Ad = nativeAd;
                isadx_small_native_banner_Loaded = true;

            });
            builder.withAdListener(new AdListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Adx Small Native Banner Ad Failed");
                    small_native_banner_Ads();
                }

                public void onAdLoaded() {
                    super.onAdLoaded();
                }
            }).build().loadAd(new AdManagerAdRequest.Builder().build());
        }
    }

    // FB Mode
    private void preload_Fb_Native_BannerAd(String placementId) {
        if (isFB_small_native_banner_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final NativeBannerAd fb_nativeBanner_Ad = new NativeBannerAd(activity, placementId);
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                    Conts.log_debug(TAG, "FB Native ad finished downloading all assets.");
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB Native ad failed to load: " + adError.getErrorMessage());
                    small_native_banner_Ads();
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    Conts.log_debug(TAG, "FB Native Banner ad is loaded");
                    fb_small_native_banner_Ad = fb_nativeBanner_Ad;
                    isFB_small_native_banner_Loaded = true;

                }

                @Override
                public void onAdClicked(Ad ad) {
                    Conts.log_debug(TAG, "FB Native ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    Conts.log_debug(TAG, "FB Native ad impression logged!");
                }
            };
            // Request an ad
            fb_nativeBanner_Ad.loadAd(fb_nativeBanner_Ad.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        }
    }

    // Applovin Mode
    private void preload_Applovin_Native_BannerAd(String placementId) {
        if (isApplovin_small_native_banner_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(placementId, activity);
            nativeAdLoader.setRevenueListener(ad -> {
            });
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                    Conts.log_debug(TAG, "Applovin Small Native Banner ad Loaded");
                    applovin_small_native_banner_Ad = nativeAdView;
                    Applovin_small_native_banner_Ad = ad;
                    isApplovin_small_native_banner_Loaded = true;
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    Conts.log_debug(TAG, "Applovin Small Native Banner ad Failed " + error.getMessage());
                    small_native_banner_Ads();
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {
                }
            });
            nativeAdLoader.loadAd(new NativeAds(activity).create_Small_Native_Banner_AdView());
        }
    }

    // Wortise Mode
    private void preload_Wortise_Native_BannerAd(String placementId) {
        if (isWortise_small_Native_banner_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final GoogleNativeAd wortise_google_native_banner = new GoogleNativeAd(activity, placementId, new GoogleNativeAd.Listener() {
                @Override
                public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {
                }

                @Override
                public void onNativeFailed(@NonNull GoogleNativeAd googleNativeAd, @NonNull com.wortise.ads.AdError adError) {
                    Conts.log_debug(TAG, "Wortise Small Native Banner Ad Failed");
                    small_native_banner_Ads();
                }

                @Override
                public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {
                }

                @Override
                public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull NativeAd nativeAd) {
                    Conts.log_debug(TAG, "Wortise Small Naive Banner Loaded");
                    wortise_small_google_native_banner_Ad = googleNativeAd;
                    wortise_small_native_banner_Ad = nativeAd;
                    isWortise_small_Native_banner_Loaded = true;

                }
            });
            wortise_google_native_banner.load();
        }
    }

    // Local Mode
    private void preload_Local_Native_BannerAd() {
        if (isLocal_small_Native_banner_Loaded) {
            return;
        }
        isLocal_small_Native_banner_Loaded = true;
    }

    @SuppressLint("SetTextI18n")
    private void show_local_native_banner_ad(ViewGroup native_banner_ad) {
        if (app_data != null && app_data.size() > 0) {
            RelativeLayout custm_native = native_banner_ad.findViewById(R.id.custm_native_ad);
            ImageView app_icon_native = native_banner_ad.findViewById(R.id.ad_app_icon);
            TextView app_name_native = native_banner_ad.findViewById(R.id.ad_headline);
            TextView app_ad_body = native_banner_ad.findViewById(R.id.ad_body);
            TextView ad_call_to_action = native_banner_ad.findViewById(R.id.ad_call_to_action);
            try {
                Glide.with(activity).load(app_data.get(0).getNew_app_icon()).into(app_icon_native);
                app_name_native.setText(app_data.get(0).getNew_app_name());
                app_name_native.setSelected(true);
                app_ad_body.setText(app_data.get(0).getNew_app_body());
                app_ad_body.setSelected(true);
                ad_call_to_action.setText("Install");
            } catch (Exception ignored) {
            }
            custm_native.setOnClickListener(view -> {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW").setData(Uri.parse(app_data.get(0).getNew_app_link()));
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // TODO: 7/17/2023 Show Small Native Banner Ads
    @SuppressLint("SetTextI18n")
    public void show_small_native_banner_ad(final ViewGroup native_banner_ad) {
        if (app_data != null && app_data.size() > 0) {
            if (app_data.get(0).isAds_show()) {
                if (app_data.get(0).isPreload_native_ads()) {
                    if (isadmob_small_native_banner_Loaded) {
                        new NativeAds(activity).Admob_Small_Native_Banner_Ad(Admob_small_native_banner_Ad, native_banner_ad);
                        Conts.log_debug(TAG, "Admob Native Banner ad show");
                        isadmob_small_native_banner_Loaded = false;
                        small_native_banner_Ads();
                    } else if (isadx_small_native_banner_Loaded) {
                        new NativeAds(activity).Admob_Small_Native_Banner_Ad(Adx_small_native_banner_Ad, native_banner_ad);
                        Conts.log_debug(TAG, "Adx Native Banner ad show");
                        isadx_small_native_banner_Loaded = false;
                        small_native_banner_Ads();
                    } else if (isFB_small_native_banner_Loaded) {
                        new NativeAds(activity).FB_Smalle_Native_Banner(fb_small_native_banner_Ad, native_banner_ad);
                        Conts.log_debug(TAG, "FB Native Banner ad show");
                        isFB_small_native_banner_Loaded = false;
                        small_native_banner_Ads();
                    } else if (isApplovin_small_native_banner_Loaded) {
                        if (Applovin_small_native_banner_Ad != null) {
                            native_banner_ad.removeAllViews();
                        }
                        native_banner_ad.removeAllViews();
                        native_banner_ad.addView(applovin_small_native_banner_Ad);
                        Conts.log_debug(TAG, "Applovin Native Banner ad show");
                        isApplovin_small_native_banner_Loaded = false;
                        small_native_banner_Ads();
                    } else if (isWortise_small_Native_banner_Loaded) {
                        new NativeAds(activity).Admob_Small_Native_Banner_Ad(wortise_small_native_banner_Ad, native_banner_ad);
                        Conts.log_debug(TAG, "Wortise Native Banner ad show");
                        isWortise_small_Native_banner_Loaded = false;
                        small_native_banner_Ads();
                    } else if (isLocal_small_Native_banner_Loaded) {
                        @SuppressLint("InflateParams") ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.local_small_native_banner, null);
                        native_banner_ad.removeAllViews();
                        native_banner_ad.addView(viewGroup);
                        show_local_native_banner_ad(native_banner_ad);
                        Conts.log_debug(TAG, "Local Native Banner ad show");
                        isLocal_small_Native_banner_Loaded = false;
                        small_native_banner_Ads();
                    }
                } else {
                    try {
                        String[] adnetwork = app_data.get(0).getAdSmallNativeBanner().split(",");
                        if (ad_small_native_banner_network < adnetwork.length) {
                            switch (adnetwork[ad_small_native_banner_network]) {
                                case "admob":
                                    String[] admob_small_native_banner_id = app_data.get(0).getAdmobNativeid().split(",");
                                    if (current_admob_small_native_BannerId < admob_small_native_banner_id.length) {
                                        String placementId = admob_small_native_banner_id[current_admob_small_native_BannerId];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
                                            builder.forNativeAd(nativeAd -> {
                                                Conts.log_debug(TAG, "Admob Native Banner ad show");
                                                new NativeAds(activity).Admob_Small_Native_Banner_Ad(nativeAd, native_banner_ad);
                                            });
                                            builder.withAdListener(new AdListener() {
                                                @Override
                                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                                    super.onAdFailedToLoad(loadAdError);
                                                    Conts.log_debug(TAG, "Admob Small Native Banner Ad Failed" + loadAdError.getMessage());
                                                }

                                                public void onAdLoaded() {
                                                    super.onAdLoaded();
                                                }
                                            }).build().loadAd(new AdRequest.Builder().build());
                                            current_admob_small_native_BannerId++;
                                            if (current_admob_small_native_BannerId == admob_small_native_banner_id.length) {
                                                current_admob_small_native_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_small_native_banner_network++;
                                    break;
                                case "adx":
                                    String[] adx_small_native_banner_id = app_data.get(0).getAdxNativeId().split(",");
                                    if (current_adx_small_native_BannerId < adx_small_native_banner_id.length) {
                                        String placementId = adx_small_native_banner_id[current_adx_small_native_BannerId];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
                                            builder.forNativeAd(nativeAd -> {
                                                Conts.log_debug(TAG, "Adx Native Banner ad show");
                                                new NativeAds(activity).Admob_Small_Native_Banner_Ad(nativeAd, native_banner_ad);
                                            });
                                            builder.withAdListener(new AdListener() {
                                                @SuppressLint("MissingPermission")
                                                @Override
                                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                                    super.onAdFailedToLoad(loadAdError);
                                                    Conts.log_debug(TAG, "Adx Small Native Banner Ad Failed" + loadAdError.getMessage());
                                                }

                                                public void onAdLoaded() {
                                                    super.onAdLoaded();
                                                }
                                            }).build().loadAd(new AdManagerAdRequest.Builder().build());
                                            current_adx_small_native_BannerId++;
                                            if (current_adx_small_native_BannerId == adx_small_native_banner_id.length) {
                                                current_adx_small_native_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_small_native_banner_network++;
                                    break;
                                case "fb":
                                    String[] fb_small_native_banner_id = app_data.get(0).getFbNativeBannerId().split(",");
                                    if (current_fb_small_native_BannerId < fb_small_native_banner_id.length) {
                                        String placementId = fb_small_native_banner_id[current_fb_small_native_BannerId];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final NativeBannerAd fb_nativeBanner_Ad = new NativeBannerAd(activity, placementId);
                                            NativeAdListener nativeAdListener = new NativeAdListener() {
                                                @Override
                                                public void onMediaDownloaded(Ad ad) {
                                                }

                                                @Override
                                                public void onError(Ad ad, AdError adError) {
                                                    Conts.log_debug(TAG, "FB Native Banner ad Failed" + adError.getErrorMessage());
                                                }

                                                @Override
                                                public void onAdLoaded(Ad ad) {
                                                    Conts.log_debug(TAG, "FB Native Banner ad show");
                                                    new NativeAds(activity).FB_Smalle_Native_Banner(fb_nativeBanner_Ad, native_banner_ad);
                                                }

                                                @Override
                                                public void onAdClicked(Ad ad) {
                                                }

                                                @Override
                                                public void onLoggingImpression(Ad ad) {
                                                }
                                            };
                                            // Request an ad
                                            fb_nativeBanner_Ad.loadAd(fb_nativeBanner_Ad.buildLoadAdConfig().withAdListener(nativeAdListener).build());
                                            current_fb_small_native_BannerId++;
                                            if (current_fb_small_native_BannerId == fb_small_native_banner_id.length) {
                                                current_fb_small_native_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_small_native_banner_network++;
                                    break;
                                case "applovin":
                                    String[] applovin_small_native_banner_id = app_data.get(0).getApplovin_small_native_bannerid().split(",");
                                    if (current_applovin_small_native_BannerId < applovin_small_native_banner_id.length) {
                                        String placementId = applovin_small_native_banner_id[current_applovin_small_native_BannerId];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(placementId, activity);
                                            nativeAdLoader.setRevenueListener(ad -> {
                                            });
                                            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                                                @Override
                                                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                                                    Conts.log_debug(TAG, "Applovin Native Banner ad show");
                                                    if (ad != null) {
                                                        native_banner_ad.removeAllViews();
                                                    }
                                                    native_banner_ad.removeAllViews();
                                                    native_banner_ad.addView(nativeAdView);
                                                }

                                                @Override
                                                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                                                    Conts.log_debug(TAG, "Applovin Small Native Banner ad Failed " + error.getMessage());
                                                }

                                                @Override
                                                public void onNativeAdClicked(final MaxAd ad) {
                                                }
                                            });
                                            nativeAdLoader.loadAd(new NativeAds(activity).create_Small_Native_Banner_AdView());
                                            current_applovin_small_native_BannerId++;
                                            if (current_applovin_small_native_BannerId == applovin_small_native_banner_id.length) {
                                                current_applovin_small_native_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_small_native_banner_network++;
                                    break;
                                case "wortise":
                                    String[] wortise_small_native_banner_id = app_data.get(0).getWortiseNativeId().split(",");
                                    if (current_wortise_small_native_BannerId < wortise_small_native_banner_id.length) {
                                        String placementId = wortise_small_native_banner_id[current_wortise_small_native_BannerId];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final GoogleNativeAd wortise_google_native_banner = new GoogleNativeAd(activity, placementId, new GoogleNativeAd.Listener() {
                                                @Override
                                                public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {
                                                }

                                                @Override
                                                public void onNativeFailed(@NonNull GoogleNativeAd googleNativeAd, @NonNull com.wortise.ads.AdError adError) {
                                                    Conts.log_debug(TAG, "Wortise Small Native Banner Ad Failed" + adError);
                                                }

                                                @Override
                                                public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {
                                                }

                                                @Override
                                                public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull NativeAd nativeAd) {
                                                    Conts.log_debug(TAG, "Wortise Native Banner ad show");
                                                    new NativeAds(activity).Admob_Small_Native_Banner_Ad(nativeAd, native_banner_ad);
                                                }
                                            });
                                            wortise_google_native_banner.load();
                                            current_wortise_small_native_BannerId++;
                                            if (current_wortise_small_native_BannerId == wortise_small_native_banner_id.length) {
                                                current_wortise_small_native_BannerId = 0;
                                            }
                                        }
                                    }
                                    ad_small_native_banner_network++;
                                    break;
                                case "local":
                                    @SuppressLint("InflateParams") ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.local_small_native_banner, null);
                                    native_banner_ad.removeAllViews();
                                    native_banner_ad.addView(viewGroup);
                                    RelativeLayout custm_native = native_banner_ad.findViewById(R.id.custm_native_ad);
                                    ImageView app_icon_native = native_banner_ad.findViewById(R.id.ad_app_icon);
                                    TextView app_name_native = native_banner_ad.findViewById(R.id.ad_headline);
                                    TextView app_ad_body = native_banner_ad.findViewById(R.id.ad_body);
                                    TextView ad_call_to_action = native_banner_ad.findViewById(R.id.ad_call_to_action);
                                    try {
                                        Glide.with(activity).load(app_data.get(0).getNew_app_icon()).into(app_icon_native);
                                        app_name_native.setText(app_data.get(0).getNew_app_name());
                                        app_name_native.setSelected(true);
                                        app_ad_body.setText(app_data.get(0).getNew_app_body());
                                        app_ad_body.setSelected(true);
                                        ad_call_to_action.setText("Install");
                                    } catch (Exception ignored) {
                                    }
                                    custm_native.setOnClickListener(view -> {
                                        try {
                                            Intent intent = new Intent("android.intent.action.VIEW").setData(Uri.parse(app_data.get(0).getNew_app_link()));
                                            activity.startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    ad_small_native_banner_network++;
                                    break;
                                default:
                            }
                            if (ad_small_native_banner_network == adnetwork.length) {
                                ad_small_native_banner_network = 0;
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    //--------------------------------------------- Small Native Ads --------------------------------------------------------------------------
// TODO: 7/17/2023  Preload Small Native Ads
    int ad_small_native_network = 0;

    int current_admob_small_native_Id = 0;
    int current_adx_small_native_Id = 0;
    int current_fb_small_native_Id = 0;
    int current_applovin_small_native_Id = 0;
    int current_wortise_small_native_Id = 0;

    private void small_native_Ads() {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAdSmallNative().split(",");
                    if (ad_small_native_network < adnetwork.length) {
                        switch (adnetwork[ad_small_native_network]) {
                            case "admob":
                                String[] admob_small_native_id = app_data.get(0).getAdmobNativeid().split(",");
                                if (current_admob_small_native_Id < admob_small_native_id.length) {
                                    preload_Admob_Small_Native_Ad(admob_small_native_id[current_admob_small_native_Id]);
                                    current_admob_small_native_Id++;
                                    if (current_admob_small_native_Id == admob_small_native_id.length) {
                                        current_admob_small_native_Id = 0;
                                    }
                                }
                                ad_small_native_network++;
                                break;
                            case "adx":
                                String[] adx_small_native_id = app_data.get(0).getAdxNativeId().split(",");
                                if (current_adx_small_native_Id < adx_small_native_id.length) {
                                    preload_Adx_Small_Native_Ad(adx_small_native_id[current_adx_small_native_Id]);
                                    current_adx_small_native_Id++;
                                    if (current_adx_small_native_Id == adx_small_native_id.length) {
                                        current_adx_small_native_Id = 0;
                                    }
                                }
                                ad_small_native_network++;
                                break;
                            case "fb":
                                String[] fb_small_native_id = app_data.get(0).getFbNativeBannerId().split(",");
                                if (current_fb_small_native_Id < fb_small_native_id.length) {
                                    preload_Fb_Small_Native_Ad(fb_small_native_id[current_fb_small_native_Id]);
                                    current_fb_small_native_Id++;
                                    if (current_fb_small_native_Id == fb_small_native_id.length) {
                                        current_fb_small_native_Id = 0;
                                    }
                                }
                                ad_small_native_network++;
                                break;
                            case "applovin":
                                String[] applovin_small_native_id = app_data.get(0).getApplovin_small_nativeid().split(",");
                                if (current_applovin_small_native_Id < applovin_small_native_id.length) {
                                    preload_Applovin_Small_NativeAd(applovin_small_native_id[current_applovin_small_native_Id]);
                                    current_applovin_small_native_Id++;
                                    if (current_applovin_small_native_Id == applovin_small_native_id.length) {
                                        current_applovin_small_native_Id = 0;
                                    }
                                }
                                ad_small_native_network++;
                                break;
                            case "wortise":
                                String[] wortise_small_native_id = app_data.get(0).getWortiseNativeId().split(",");
                                if (current_wortise_small_native_Id < wortise_small_native_id.length) {
                                    preload_Wortise_Small_Native_Ad(wortise_small_native_id[current_wortise_small_native_Id]);
                                    current_wortise_small_native_Id++;
                                    if (current_wortise_small_native_Id == wortise_small_native_id.length) {
                                        current_wortise_small_native_Id = 0;
                                    }
                                }
                                ad_small_native_network++;
                                break;
                            case "local":
                                preload_Local_Small_Native_Ad();
                                ad_small_native_network++;
                                break;
                            default:
                        }
                        if (ad_small_native_network == adnetwork.length) {
                            ad_small_native_network = 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Admob Mode
    private void preload_Admob_Small_Native_Ad(String placementId) {
        if (isAdmob_small_native_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                Conts.log_debug(TAG, "Admob Small Native Ad Loaded");
                Admob_small_native_Ad = nativeAd;
                isAdmob_small_native_Loaded = true;
            });
            builder.withAdListener(new AdListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Admob Small Native Ad Failed" + loadAdError.getMessage());
                    small_native_Ads();
                }

                public void onAdLoaded() {
                    super.onAdLoaded();
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
    }

    // Adx Mode
    private void preload_Adx_Small_Native_Ad(String placementId) {
        if (isadx_small_native_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                Conts.log_debug(TAG, "Adx Small Native Ad Loaded");
                Adx_small_native_Ad = nativeAd;
                isadx_small_native_Loaded = true;
            });
            builder.withAdListener(new AdListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Adx Small Native Ad Failed" + loadAdError.getMessage());
                    small_native_Ads();
                }

                public void onAdLoaded() {
                    super.onAdLoaded();
                }
            }).build().loadAd(new AdManagerAdRequest.Builder().build());
        }
    }

    // FB Mode
    private void preload_Fb_Small_Native_Ad(String placementId) {
        if (isFb_small_native_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final NativeBannerAd fb_small_native = new NativeBannerAd(activity, placementId);
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onAdLoaded(Ad ad) {
                    Conts.log_debug(TAG, "FB Small Native Banner ad is loaded");
                    fb_small_native_Ad = fb_small_native;
                    isFb_small_native_Loaded = true;
                }

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @SuppressLint("MissingPermission")
                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB Small Native ad failed to load: " + adError.getErrorMessage());
                    small_native_Ads();
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };
            // Request an ad
            fb_small_native.loadAd(fb_small_native.buildLoadAdConfig().withAdListener(nativeAdListener).build());
        }
    }

    // Applovin Mode
    private void preload_Applovin_Small_NativeAd(String placementId) {
        if (isapplovin_small_native_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(placementId, activity);
            nativeAdLoader.setRevenueListener(ad -> {
            });
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                    Conts.log_debug(TAG, "Applovin Small Native ad Loaded");
                    applovin_small_native_Ad = nativeAdView;
                    Applovin_small_native_Ad = ad;
                    isapplovin_small_native_Loaded = true;
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    Conts.log_debug(TAG, "Applovin Small Native ad Failed " + error.getMessage());
                    small_native_Ads();
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {
                }
            });
            nativeAdLoader.loadAd(new NativeAds(activity).create_Small_NativeAdView());
        }
    }

    // Wortise Mode
    private void preload_Wortise_Small_Native_Ad(String placementId) {
        if (isWortise_small_Native_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final GoogleNativeAd wortise_google_small_native = new GoogleNativeAd(activity, placementId, new GoogleNativeAd.Listener() {
                @Override
                public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {
                }

                @SuppressLint("MissingPermission")
                @Override
                public void onNativeFailed(@NonNull GoogleNativeAd googleNativeAd, @NonNull com.wortise.ads.AdError adError) {
                    Conts.log_debug(TAG, "Wortise Small Native Ad Failed" + adError);
                    small_native_Ads();
                }

                @Override
                public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {
                }

                @Override
                public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull NativeAd nativeAd) {
                    Conts.log_debug(TAG, "Wortise Small Native Ad Loaded");
                    wortise_google_small_native_Ad = googleNativeAd;
                    wortise_small_native_Ad = nativeAd;
                    isWortise_small_Native_Loaded = true;
                }
            });
            wortise_google_small_native.load();
        }
    }

    // Local Mode
    private void preload_Local_Small_Native_Ad() {
        if (isLocal_small_Native_Loaded) {
            return;
        }
        isLocal_small_Native_Loaded = true;
    }

    @SuppressLint("SetTextI18n")
    private void show_local_small_native(ViewGroup native_banner_ad) {
        if (app_data != null && app_data.size() > 0) {
            RelativeLayout custm_native = native_banner_ad.findViewById(R.id.custm_small_native_ad);
            ImageView app_icon_native = native_banner_ad.findViewById(R.id.ad_app_icon);
            TextView app_name_native = native_banner_ad.findViewById(R.id.ad_Tital);
            TextView app_ad_body = native_banner_ad.findViewById(R.id.ad_body);
            TextView ad_call_to_action = native_banner_ad.findViewById(R.id.ad_call_to_action);
            try {
                Glide.with(activity).load(app_data.get(0).getNew_app_icon()).into(app_icon_native);
                app_name_native.setText(app_data.get(0).getNew_app_name());
                app_name_native.setSelected(true);
                app_ad_body.setText(app_data.get(0).getNew_app_body());
                app_ad_body.setSelected(true);
                ad_call_to_action.setText("Install");
            } catch (Exception ignored) {
            }
            custm_native.setOnClickListener(view -> {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW").setData(Uri.parse(app_data.get(0).getNew_app_link()));
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    // TODO: 7/17/2023  Show Small Native Ads
    @SuppressLint({"MissingPermission", "SetTextI18n"})
    public void show_small_native_ad(final ViewGroup native_banner_ad) {
        if (app_data != null && app_data.size() > 0) {
            if (app_data.get(0).isAds_show()) {
                if (app_data.get(0).isPreload_native_ads()) {
                    if (isAdmob_small_native_Loaded) {
                        new NativeAds(activity).Admob_Small_Native_Ad(Admob_small_native_Ad, native_banner_ad);
                        Conts.log_debug(TAG, "Admob Small Native ad show");
                        isAdmob_small_native_Loaded = false;
                        small_native_Ads();
                    } else if (isadx_small_native_Loaded) {
                        new NativeAds(activity).Admob_Small_Native_Ad(Adx_small_native_Ad, native_banner_ad);
                        Conts.log_debug(TAG, "Adx Small Native ad show");
                        isadx_small_native_Loaded = false;
                        small_native_Ads();
                    } else if (isFb_small_native_Loaded) {
                        new NativeAds(activity).FB_Smalle_Native(fb_small_native_Ad, native_banner_ad);
                        Conts.log_debug(TAG, "FB Small Native ad show");
                        isFb_small_native_Loaded = false;
                        small_native_Ads();
                    } else if (isapplovin_small_native_Loaded) {
                        if (Applovin_small_native_Ad != null) {
                            native_banner_ad.removeAllViews();
                        }
                        native_banner_ad.removeAllViews();
                        native_banner_ad.addView(applovin_small_native_Ad);
                        Conts.log_debug(TAG, "Applovin Small Native ad show");
                        isapplovin_small_native_Loaded = false;
                        small_native_Ads();
                    } else if (isWortise_small_Native_Loaded) {
                        new NativeAds(activity).Admob_Small_Native_Ad(wortise_small_native_Ad, native_banner_ad);
                        Conts.log_debug(TAG, "Wortise Small Native ad show");
                        isWortise_small_Native_Loaded = false;
                        small_native_Ads();
                    } else if (isLocal_small_Native_Loaded) {
                        @SuppressLint("InflateParams") ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.local_small_native_ad, null);
                        show_local_small_native(viewGroup);
                        native_banner_ad.removeAllViews();
                        native_banner_ad.addView(viewGroup);
                        Conts.log_debug(TAG, "Local Small Native ad show");
                        isLocal_small_Native_Loaded = false;
                        small_native_Ads();
                    }
                } else {
                    try {
                        String[] adnetwork = app_data.get(0).getAdSmallNative().split(",");
                        if (ad_small_native_network < adnetwork.length) {
                            switch (adnetwork[ad_small_native_network]) {
                                case "admob":
                                    String[] admob_small_native_id = app_data.get(0).getAdmobNativeid().split(",");
                                    if (current_admob_small_native_Id < admob_small_native_id.length) {
                                        String placementId = admob_small_native_id[current_admob_small_native_Id];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
                                            builder.forNativeAd(nativeAd -> {
                                                Conts.log_debug(TAG, "Admob Small Native ad show");
                                                new NativeAds(activity).Admob_Small_Native_Ad(nativeAd, native_banner_ad);
                                            });
                                            builder.withAdListener(new AdListener() {
                                                @SuppressLint("MissingPermission")
                                                @Override
                                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                                    super.onAdFailedToLoad(loadAdError);
                                                    Conts.log_debug(TAG, "Admob Small Native Ad Failed" + loadAdError.getMessage());
                                                }

                                                public void onAdLoaded() {
                                                    super.onAdLoaded();
                                                }
                                            }).build().loadAd(new AdRequest.Builder().build());
                                            current_admob_small_native_Id++;
                                            if (current_admob_small_native_Id == admob_small_native_id.length) {
                                                current_admob_small_native_Id = 0;
                                            }
                                        }
                                    }
                                    ad_small_native_network++;
                                    break;
                                case "adx":
                                    String[] adx_small_native_id = app_data.get(0).getAdxNativeId().split(",");
                                    if (current_adx_small_native_Id < adx_small_native_id.length) {
                                        String placementId = adx_small_native_id[current_adx_small_native_Id];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
                                            builder.forNativeAd(nativeAd -> {
                                                Conts.log_debug(TAG, "Adx Small Native ad show");
                                                new NativeAds(activity).Admob_Small_Native_Ad(nativeAd, native_banner_ad);
                                            });
                                            builder.withAdListener(new AdListener() {
                                                @SuppressLint("MissingPermission")
                                                @Override
                                                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                                    super.onAdFailedToLoad(loadAdError);
                                                    Conts.log_debug(TAG, "Adx Small Native Ad Failed" + loadAdError.getMessage());
                                                }

                                                public void onAdLoaded() {
                                                    super.onAdLoaded();
                                                }
                                            }).build().loadAd(new AdManagerAdRequest.Builder().build());
                                            current_adx_small_native_Id++;
                                            if (current_adx_small_native_Id == adx_small_native_id.length) {
                                                current_adx_small_native_Id = 0;
                                            }
                                        }
                                    }
                                    ad_small_native_network++;
                                    break;
                                case "fb":
                                    String[] fb_small_native_id = app_data.get(0).getFbNativeBannerId().split(",");
                                    if (current_fb_small_native_Id < fb_small_native_id.length) {
                                        String placementId = fb_small_native_id[current_fb_small_native_Id];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final NativeBannerAd fb_small_native = new NativeBannerAd(activity, placementId);
                                            NativeAdListener nativeAdListener = new NativeAdListener() {

                                                @Override
                                                public void onAdLoaded(Ad ad) {
                                                    Conts.log_debug(TAG, "FB Small Native ad show");
                                                    new NativeAds(activity).FB_Smalle_Native(fb_small_native, native_banner_ad);
                                                }

                                                @Override
                                                public void onMediaDownloaded(Ad ad) {
                                                    // Native ad finished downloading all assets
                                                }

                                                @SuppressLint("MissingPermission")
                                                @Override
                                                public void onError(Ad ad, AdError adError) {
                                                    Conts.log_debug(TAG, "FB Small Native ad failed to load: " + adError.getErrorMessage());
                                                }

                                                @Override
                                                public void onAdClicked(Ad ad) {
                                                }

                                                @Override
                                                public void onLoggingImpression(Ad ad) {
                                                }
                                            };
                                            // Request an ad
                                            fb_small_native.loadAd(fb_small_native.buildLoadAdConfig().withAdListener(nativeAdListener).build());
                                            current_fb_small_native_Id++;
                                            if (current_fb_small_native_Id == fb_small_native_id.length) {
                                                current_fb_small_native_Id = 0;
                                            }
                                        }
                                    }
                                    ad_small_native_network++;
                                    break;
                                case "applovin":
                                    String[] applovin_small_native_id = app_data.get(0).getApplovin_small_nativeid().split(",");
                                    if (current_applovin_small_native_Id < applovin_small_native_id.length) {
                                        String placementId = applovin_small_native_id[current_applovin_small_native_Id];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(placementId, activity);
                                            nativeAdLoader.setRevenueListener(ad -> {
                                            });
                                            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                                                @Override
                                                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                                                    Conts.log_debug(TAG, "Applovin Small Native ad show");
                                                    if (ad != null) {
                                                        native_banner_ad.removeAllViews();
                                                    }
                                                    native_banner_ad.removeAllViews();
                                                    native_banner_ad.addView(nativeAdView);
                                                }

                                                @Override
                                                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                                                    Conts.log_debug(TAG, "Applovin Small Native ad Failed " + error.getMessage());
                                                }

                                                @Override
                                                public void onNativeAdClicked(final MaxAd ad) {
                                                }
                                            });
                                            nativeAdLoader.loadAd(new NativeAds(activity).create_Small_NativeAdView());
                                            current_applovin_small_native_Id++;
                                            if (current_applovin_small_native_Id == applovin_small_native_id.length) {
                                                current_applovin_small_native_Id = 0;
                                            }
                                        }
                                    }
                                    ad_small_native_network++;
                                    break;
                                case "wortise":
                                    String[] wortise_small_native_id = app_data.get(0).getWortiseNativeId().split(",");
                                    if (current_wortise_small_native_Id < wortise_small_native_id.length) {
                                        String placementId = wortise_small_native_id[current_wortise_small_native_Id];
                                        if (!placementId.equalsIgnoreCase("")) {
                                            final GoogleNativeAd wortise_google_small_native = new GoogleNativeAd(activity, placementId, new GoogleNativeAd.Listener() {
                                                @Override
                                                public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {
                                                }

                                                @SuppressLint("MissingPermission")
                                                @Override
                                                public void onNativeFailed(@NonNull GoogleNativeAd googleNativeAd, @NonNull com.wortise.ads.AdError adError) {
                                                    Conts.log_debug(TAG, "Wortise Small Native Ad Failed" + adError);
                                                }

                                                @Override
                                                public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {
                                                }

                                                @Override
                                                public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull NativeAd nativeAd) {
                                                    Conts.log_debug(TAG, "Wortise Small Native ad show");
                                                    new NativeAds(activity).Admob_Small_Native_Ad(nativeAd, native_banner_ad);
                                                }
                                            });
                                            wortise_google_small_native.load();
                                            current_wortise_small_native_Id++;
                                            if (current_wortise_small_native_Id == wortise_small_native_id.length) {
                                                current_wortise_small_native_Id = 0;
                                            }
                                        }
                                    }
                                    ad_small_native_network++;
                                    break;
                                case "local":
                                    @SuppressLint("InflateParams") ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.local_small_native_ad, null);
                                    native_banner_ad.removeAllViews();
                                    native_banner_ad.addView(viewGroup);
                                    Conts.log_debug(TAG, "Local Small Native ad show");
                                    RelativeLayout custm_native = native_banner_ad.findViewById(R.id.custm_small_native_ad);
                                    ImageView app_icon_native = native_banner_ad.findViewById(R.id.ad_app_icon);
                                    TextView app_name_native = native_banner_ad.findViewById(R.id.ad_Tital);
                                    TextView app_ad_body = native_banner_ad.findViewById(R.id.ad_body);
                                    TextView ad_call_to_action = native_banner_ad.findViewById(R.id.ad_call_to_action);
                                    try {
                                        Glide.with(activity).load(app_data.get(0).getNew_app_icon()).into(app_icon_native);
                                        app_name_native.setText(app_data.get(0).getNew_app_name());
                                        app_name_native.setSelected(true);
                                        app_ad_body.setText(app_data.get(0).getNew_app_body());
                                        app_ad_body.setSelected(true);
                                        ad_call_to_action.setText("Install");
                                    } catch (Exception ignored) {
                                    }
                                    custm_native.setOnClickListener(view -> {
                                        try {
                                            Intent intent = new Intent("android.intent.action.VIEW").setData(Uri.parse(app_data.get(0).getNew_app_link()));
                                            activity.startActivity(intent);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    });
                                    ad_small_native_network++;
                                    break;
                                default:
                            }
                            if (ad_small_native_network == adnetwork.length) {
                                ad_small_native_network = 0;
                            }
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    //--------------------------------------------- Native Ads --------------------------------------------------------------------------

    // TODO: 7/17/2023  Preload Native Ads
    int ad_native_network = 0;

    int current_admob_native_Id = 0;
    int current_adx_native_Id = 0;
    int current_fb_native_Id = 0;
    int current_applovin_native_Id = 0;
    int current_wortise_native_Id = 0;

    private void native_Ads() {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAdNative().split(",");
                    if (ad_native_network < adnetwork.length) {
                        switch (adnetwork[ad_native_network]) {
                            case "admob":
                                String[] admob_native_id = app_data.get(0).getAdmobNativeid().split(",");
                                if (current_admob_native_Id < admob_native_id.length) {
                                    preload_Admob_Native_Ad(admob_native_id[current_admob_native_Id]);
                                    current_admob_native_Id++;
                                    if (current_admob_native_Id == admob_native_id.length) {
                                        current_admob_native_Id = 0;
                                    }
                                }
                                ad_native_network++;
                                break;
                            case "adx":
                                String[] adx_native_id = app_data.get(0).getAdxNativeId().split(",");
                                if (current_adx_native_Id < adx_native_id.length) {
                                    preload_Adx_Native_Ad(adx_native_id[current_adx_native_Id]);
                                    current_adx_native_Id++;
                                    if (current_adx_native_Id == adx_native_id.length) {
                                        current_adx_native_Id = 0;
                                    }
                                }
                                ad_native_network++;
                                break;
                            case "fb":
                                String[] fb_native_id = app_data.get(0).getFbNativeId().split(",");
                                if (current_fb_native_Id < fb_native_id.length) {
                                    preloadFbNativeAd(fb_native_id[current_fb_native_Id]);
                                    current_fb_native_Id++;
                                    if (current_fb_native_Id == fb_native_id.length) {
                                        current_fb_native_Id = 0;
                                    }
                                }
                                ad_native_network++;
                                break;
                            case "applovin":
                                String[] applovin_native_id = app_data.get(0).getApplovin_nativeid().split(",");
                                if (current_applovin_native_Id < applovin_native_id.length) {
                                    preload_Applovin_NativeAd(applovin_native_id[current_applovin_native_Id]);
                                    current_applovin_native_Id++;
                                    if (current_applovin_native_Id == applovin_native_id.length) {
                                        current_applovin_native_Id = 0;
                                    }
                                }
                                ad_native_network++;
                                break;
                            case "wortise":
                                String[] wortise_native_id = app_data.get(0).getWortiseNativeId().split(",");
                                if (current_wortise_native_Id < wortise_native_id.length) {
                                    preload_Wortise_Native_Ad(wortise_native_id[current_wortise_native_Id]);
                                    current_wortise_native_Id++;
                                    if (current_wortise_native_Id == wortise_native_id.length) {
                                        current_wortise_native_Id = 0;
                                    }
                                }
                                ad_native_network++;
                                break;
                            case "local":
                                preload_local_Native_ad();
                                ad_native_network++;
                                break;
                            default:
                        }
                        if (ad_native_network == adnetwork.length) {
                            ad_native_network = 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Admob Mode
    @SuppressLint("MissingPermission")
    private void preload_Admob_Native_Ad(String placementId) {
        if (isadmob_native_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                Conts.log_debug(TAG, "Admob Native Ad Loaded");
                Admob_native_Ad = nativeAd;
                isadmob_native_Loaded = true;
            });
            builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Admob Native Ad Failed" + loadAdError.getMessage());
                    native_Ads();
                }

                public void onAdLoaded() {
                    super.onAdLoaded();
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
    }

    // Adx Mode
    @SuppressLint("MissingPermission")
    private void preload_Adx_Native_Ad(String placementId) {
        if (isadx_native_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                Conts.log_debug(TAG, "Adx Native Ad Loaded");
                Adx_native_Ad = nativeAd;
                isadx_native_Loaded = true;
            });
            builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Adx Native Ad Failed" + loadAdError.getMessage());
                    native_Ads();
                }

                public void onAdLoaded() {
                    super.onAdLoaded();
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
    }

    // FB Mode
    private void preloadFbNativeAd(String placementId) {
        if (isFB_Native_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final com.facebook.ads.NativeAd fbnative_Ad = new com.facebook.ads.NativeAd(activity, placementId);
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }

                @SuppressLint("MissingPermission")
                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB Native ad Failed " + adError.getErrorMessage());
                    native_Ads();
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    Conts.log_debug(TAG, " FB Native ad is loaded");
                    fb_native_Ad = fbnative_Ad;
                    isFB_Native_Loaded = true;
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };
            // Request an ad
            fbnative_Ad.loadAd(fbnative_Ad.buildLoadAdConfig().withAdListener(nativeAdListener).withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL).build());
        }
    }

    // Applovin Mode
    private void preload_Applovin_NativeAd(String placementId) {
        if (isApplovin_Native_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(placementId, activity);
            nativeAdLoader.setRevenueListener(ad -> {
            });
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                    Conts.log_debug(TAG, "Applovin Native ad Loaded");
                    applovin_maxnativeadview = nativeAdView;
                    Applovin_native_ad = ad;
                    isApplovin_Native_Loaded = true;
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    Conts.log_debug(TAG, "Applovin Native ad Failed " + error.getMessage());
                    native_Ads();
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {
                }
            });
            nativeAdLoader.loadAd(new NativeAds(activity).createNativeAdView());
        }
    }

    // Wortise Mode
    private void preload_Wortise_Native_Ad(String placementId) {
        if (isWortise_Native_Loaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final GoogleNativeAd wortise_google_native = new GoogleNativeAd(activity, placementId, new GoogleNativeAd.Listener() {
                @Override
                public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {
                }

                @SuppressLint("MissingPermission")
                @Override
                public void onNativeFailed(@NonNull GoogleNativeAd googleNativeAd, @NonNull com.wortise.ads.AdError adError) {
                    Conts.log_debug(TAG, "Wortise Native ad Failed" + adError);
                    native_Ads();
                }

                @Override
                public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {
                }

                @Override
                public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull NativeAd nativeAd) {
                    Conts.log_debug(TAG, "Wortise Naive ad Loaded");
                    wortise_google_native_Ad = googleNativeAd;
                    wortise_native_Ad = nativeAd;
                    isWortise_Native_Loaded = true;
                }
            });
            wortise_google_native.load();
        }
    }

    // Local Mode
    private void preload_local_Native_ad() {
        if (isLocal_Native_Loaded) {
            return;
        }
        isLocal_Native_Loaded = true;
    }

    @SuppressLint("SetTextI18n")
    private void show_local_native(ViewGroup banner_container) {
        if (app_data != null && app_data.size() > 0) {
            if (isLocal_Native_Loaded) {
                RelativeLayout custm_native = banner_container.findViewById(R.id.custm_native_ad);
                ImageView app_icon_native = banner_container.findViewById(R.id.ad_app_icon);
                TextView app_name_native = banner_container.findViewById(R.id.ad_headline);
                ImageView app_banner = banner_container.findViewById(R.id.ad_banner);
                TextView app_ad_body = banner_container.findViewById(R.id.ad_body);
                TextView ad_call_to_action = banner_container.findViewById(R.id.ad_call_to_action);
                try {
                    Glide.with(activity).load(app_data.get(0).getNew_app_icon()).into(app_icon_native);
                    Glide.with(activity).load(app_data.get(0).getNew_app_banner()).into(app_banner);
                    app_name_native.setText(app_data.get(0).getNew_app_name());
                    app_name_native.setSelected(true);
                    app_ad_body.setText(app_data.get(0).getNew_app_body());
                    app_ad_body.setSelected(true);
                    ad_call_to_action.setText("Install");
                } catch (Exception ignored) {
                }
                custm_native.setOnClickListener(view -> {
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW").setData(Uri.parse(app_data.get(0).getNew_app_link()));
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    // TODO: 8/3/2023  Medium Rect Ad
    int ad_medium_network = 0;
    int current_admob_medium_rectId = 0;
    int current_adx_medium_rectId = 0;
    int current_fb_medium_rectId = 0;
    int current_applovin_medium_rectId = 0;
    int current_wortise_medium_rectId = 0;

    private void medium_rect_Ads() {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAd_medium_rect().split(",");
                    if (ad_medium_network < adnetwork.length) {
                        switch (adnetwork[ad_medium_network]) {
                            case "admob":
                                String[] admob_mrec_id = app_data.get(0).getAdmobMediumRectangleid().split(",");
                                if (current_admob_medium_rectId < admob_mrec_id.length) {
                                    preload_medium_rectAd_Admob(admob_mrec_id[current_admob_medium_rectId]);
                                    current_admob_medium_rectId++;
                                    if (current_admob_medium_rectId == admob_mrec_id.length) {
                                        current_admob_medium_rectId = 0;
                                    }
                                }
                                ad_medium_network++;
                                break;
                            case "adx":
                                String[] adx_mrec_id = app_data.get(0).getAdxMediumRectangleid().split(",");
                                if (current_adx_medium_rectId < adx_mrec_id.length) {
                                    preload_medium_rect_Adx(adx_mrec_id[current_adx_medium_rectId]);
                                    current_adx_medium_rectId++;
                                    if (current_adx_medium_rectId == adx_mrec_id.length) {
                                        current_adx_medium_rectId = 0;
                                    }
                                }
                                ad_medium_network++;
                                break;
                            case "fb":
                                String[] fb_mrec_id = app_data.get(0).getFbMediumRectangleId().split(",");
                                if (current_fb_medium_rectId < fb_mrec_id.length) {
                                    preload_medium_rect_FB(fb_mrec_id[current_fb_medium_rectId]);
                                    current_fb_medium_rectId++;
                                    if (current_fb_medium_rectId == fb_mrec_id.length) {
                                        current_fb_medium_rectId = 0;
                                    }
                                }
                                ad_medium_network++;
                                break;
                            case "applovin":
                                String[] applovin_mrec_id = app_data.get(0).getApplovin_medium_rectangle_id().split(",");
                                if (current_applovin_medium_rectId < applovin_mrec_id.length) {
                                    preload_medium_rect_Applovin(applovin_mrec_id[current_applovin_medium_rectId]);
                                    current_applovin_medium_rectId++;
                                    if (current_applovin_medium_rectId == applovin_mrec_id.length) {
                                        current_applovin_medium_rectId = 0;
                                    }

                                }
                                ad_medium_network++;
                                break;
                            case "wortise":
                                String[] wortise_mrec_id = app_data.get(0).getWortiseMediumRectangleId().split(",");
                                if (current_wortise_medium_rectId < wortise_mrec_id.length) {
                                    preloadmedium_rect_Wortise(wortise_mrec_id[current_wortise_medium_rectId]);
                                    current_wortise_medium_rectId++;
                                    if (current_wortise_medium_rectId == wortise_mrec_id.length) {
                                        current_wortise_medium_rectId = 0;
                                    }
                                }
                                ad_medium_network++;
                                break;
                            default:
                        }
                        if (ad_medium_network == adnetwork.length) {
                            ad_medium_network = 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    // Admob Mode
    @SuppressLint("MissingPermission")
    private void preload_medium_rectAd_Admob(String placmentId) {
        if (isAdmob_Mediam_Ragtangal_Loaded) {
            return;
        }
        if (!placmentId.equalsIgnoreCase("")) {
            final AdView admob_Mediam_Ragtangal = new AdView(activity);
            admob_Mediam_Ragtangal.setAdSize(AdSize.MEDIUM_RECTANGLE);
            admob_Mediam_Ragtangal.setAdUnitId(placmentId);
            AdRequest adRequest = new AdRequest.Builder().build();
            admob_Mediam_Ragtangal.loadAd(adRequest);
            admob_Mediam_Ragtangal.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    Conts.log_debug(TAG, "admob mediam ragtangal loaded");
                    admobMediam_Ragtangal = admob_Mediam_Ragtangal;
                    isAdmob_Mediam_Ragtangal_Loaded = true;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    super.onAdFailedToLoad(adError);
                    Conts.log_debug(TAG, "admob mediam ragtangal failed" + adError.getMessage());
                    medium_rect_Ads();
                }
            });
        }
    }

    // Adx Mode
    @SuppressLint("MissingPermission")
    private void preload_medium_rect_Adx(String placmentId) {
        if (isAdx_Mediam_Ragtangal_Loaded) {
            return;
        }
        if (!placmentId.equalsIgnoreCase("")) {
            final AdManagerAdView adx_Mediam_Ragtangal = new AdManagerAdView(activity);
            adx_Mediam_Ragtangal.setAdSize(AdSize.MEDIUM_RECTANGLE);
            adx_Mediam_Ragtangal.setAdUnitId(placmentId);
            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            adx_Mediam_Ragtangal.loadAd(adRequest);
            adx_Mediam_Ragtangal.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    Conts.log_debug(TAG, "adx mediam ragtangal loaded");
                    adxMediam_Ragtangal = adx_Mediam_Ragtangal;
                    isAdx_Mediam_Ragtangal_Loaded = true;
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "adx mediam ragtangal failed" + loadAdError.getMessage());
                    medium_rect_Ads();
                }

            });
        }
    }

    // FB Mode
    private void preload_medium_rect_FB(String placmentId) {
        if (isFB_Mediam_Ragtangal_Loaded) {
            return;
        }
        if (!placmentId.equalsIgnoreCase("")) {
            final com.facebook.ads.AdView fb_Ragtangal = new com.facebook.ads.AdView(activity, placmentId, com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onAdLoaded(Ad ad) {
                    Conts.log_debug(TAG, "FB mediam ragtangal Loadedd ");
                    fb_Ragtangal_adView = fb_Ragtangal;
                    isFB_Mediam_Ragtangal_Loaded = true;
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB mediam ragtangal Failed" + adError.getErrorMessage());
                    medium_rect_Ads();
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };
            fb_Ragtangal.loadAd(fb_Ragtangal.buildLoadAdConfig().withAdListener(adListener).build());
        }
    }

    // Applovin Mode
    private void preload_medium_rect_Applovin(String placmentId) {
        if (isApplovin_Mediam_Ragtangal_Loaded) {
            return;
        }
        if (!placmentId.equalsIgnoreCase("")) {
            final MaxAdView applovin_medium_rect = new MaxAdView(placmentId, MaxAdFormat.MREC, activity);
            int widthPx = AppLovinSdkUtils.dpToPx(activity, 300);
            int heightPx = AppLovinSdkUtils.dpToPx(activity, 250);
            applovin_medium_rect.setLayoutParams(new ViewGroup.LayoutParams(widthPx, heightPx));
            applovin_medium_rect.loadAd();
            applovin_medium_rect.setListener(new MaxAdViewAdListener() {
                @Override
                public void onAdExpanded(MaxAd maxAd) {
                }

                @Override
                public void onAdCollapsed(MaxAd maxAd) {
                }

                @Override
                public void onAdLoaded(MaxAd maxAd) {
                    Conts.log_debug(TAG, "Applovin mediam ragtangal Loaded");
                    applovin_Medium_Ragtangal_adview = applovin_medium_rect;
                    isApplovin_Mediam_Ragtangal_Loaded = true;
                }

                @Override
                public void onAdDisplayed(MaxAd maxAd) {
                }

                @Override
                public void onAdHidden(MaxAd maxAd) {
                }

                @Override
                public void onAdClicked(MaxAd maxAd) {
                }

                @Override
                public void onAdLoadFailed(String s, MaxError maxError) {
                    Conts.log_debug(TAG, "Applovin mediam ragtangal Failed " + maxError.getMessage());
                    medium_rect_Ads();
                }

                @Override
                public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                }
            });
        }
    }

    // Wortise Mode
    private void preloadmedium_rect_Wortise(String placmentId) {
        if (isWortise_medium_ragtangal_Loaded) {
            return;
        }
        if (!placmentId.equalsIgnoreCase("")) {
            final BannerAd wortise_medium_ragtangal = new BannerAd(activity);
            wortise_medium_ragtangal.setAdSize(com.wortise.ads.AdSize.HEIGHT_250);
            wortise_medium_ragtangal.setAdUnitId(placmentId);
            wortise_medium_ragtangal.loadAd();
            wortise_medium_ragtangal.setListener(new BannerAd.Listener() {
                @Override
                public void onBannerLoaded(@NonNull BannerAd bannerAd) {
                    Conts.log_debug(TAG, "Wortise Medium Ragtangal Loadedd ");
                    w_medium_ragtangal = wortise_medium_ragtangal;
                    isWortise_medium_ragtangal_Loaded = true;
                }

                @Override
                public void onBannerClicked(@NonNull BannerAd bannerAd) {
                }

                @Override
                public void onBannerFailed(@NonNull BannerAd bannerAd, @NonNull com.wortise.ads.AdError adError) {
                    Conts.log_debug(TAG, "Wortise Medium Ragtangal failed " + adError);
                    medium_rect_Ads();
                }
            });
        }
    }

    // TODO: 7/17/2023 Show Native Ads
    @SuppressLint({"MissingPermission", "SetTextI18n"})
    public void show_native_ad(final ViewGroup native_ad) {
        if (app_data != null && app_data.size() > 0) {
            if (app_data.get(0).isAds_show()) {
                if (app_data.get(0).isPreload_native_ads()) {
                    if (isadmob_native_Loaded) {
                        new NativeAds(activity).Admob_NativeAd(Admob_native_Ad, native_ad);
                        Conts.log_debug(TAG, "Admob Native ad show");
                        isadmob_native_Loaded = false;
                        native_Ads();
                    } else if (isadx_native_Loaded) {
                        new NativeAds(activity).Admob_NativeAd(Adx_native_Ad, native_ad);
                        Conts.log_debug(TAG, "Adx Native ad show");
                        isadx_native_Loaded = false;
                        native_Ads();
                    } else if (isFB_Native_Loaded) {
                        new NativeAds(activity).FB_Native(fb_native_Ad, native_ad);
                        Conts.log_debug(TAG, "FB Native ad show");
                        isFB_Native_Loaded = false;
                        native_Ads();
                    } else if (isApplovin_Native_Loaded) {
                        if (Applovin_native_ad != null) {
                            native_ad.removeAllViews();
                        }
                        native_ad.removeAllViews();
                        native_ad.addView(applovin_maxnativeadview);
                        Conts.log_debug(TAG, "Applovin Native ad show");
                        isApplovin_Native_Loaded = false;
                        native_Ads();
                    } else if (isWortise_Native_Loaded) {
                        new NativeAds(activity).Admob_NativeAd(wortise_native_Ad, native_ad);
                        Conts.log_debug(TAG, "Wortise Native ad show");
                        isWortise_Native_Loaded = false;
                        native_Ads();
                    } else if (isLocal_Native_Loaded) {
                        @SuppressLint("InflateParams") ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.local_native_ad, null);
                        show_local_native(viewGroup);
                        native_ad.removeAllViews();
                        native_ad.addView(viewGroup);
                        Conts.log_debug(TAG, "Local Native ad show");
                        isLocal_Native_Loaded = false;
                        native_Ads();
                    } else if (isAdmob_Mediam_Ragtangal_Loaded) {
                        try {
                            if (admobMediam_Ragtangal.getParent() != null) {
                                ((ViewGroup) admobMediam_Ragtangal.getParent()).removeView(admobMediam_Ragtangal);
                            }
                            native_ad.addView(admobMediam_Ragtangal);
                            isAdmob_Mediam_Ragtangal_Loaded = false;
                            medium_rect_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isAdx_Mediam_Ragtangal_Loaded) {
                        try {
                            if (adxMediam_Ragtangal.getParent() != null) {
                                ((ViewGroup) adxMediam_Ragtangal.getParent()).removeView(adxMediam_Ragtangal);
                            }
                            native_ad.addView(adxMediam_Ragtangal);
                            isAdx_Mediam_Ragtangal_Loaded = false;
                            medium_rect_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isFB_Mediam_Ragtangal_Loaded) {
                        try {
                            if (fb_Ragtangal_adView.getParent() != null) {
                                ((ViewGroup) fb_Ragtangal_adView.getParent()).removeView(fb_Ragtangal_adView);
                            }
                            native_ad.addView(fb_Ragtangal_adView);
                            isFB_Mediam_Ragtangal_Loaded = false;
                            medium_rect_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isApplovin_Mediam_Ragtangal_Loaded) {
                        try {
                            if (applovin_Medium_Ragtangal_adview.getParent() != null) {
                                ((ViewGroup) applovin_Medium_Ragtangal_adview.getParent()).removeView(applovin_Medium_Ragtangal_adview);
                            }
                            native_ad.addView(applovin_Medium_Ragtangal_adview);
                            isApplovin_Mediam_Ragtangal_Loaded = false;
                            medium_rect_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isWortise_medium_ragtangal_Loaded) {
                        try {
                            if (w_medium_ragtangal.getParent() != null) {
                                ((ViewGroup) w_medium_ragtangal.getParent()).removeView(w_medium_ragtangal);
                            }
                            native_ad.addView(w_medium_ragtangal);
                            isWortise_medium_ragtangal_Loaded = false;
                            medium_rect_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    if (app_data.get(0).getAd_native_type().equalsIgnoreCase("mrec")) {
                        try {
                            String[] adnetwork = app_data.get(0).getAd_medium_rect().split(",");
                            if (ad_medium_network < adnetwork.length) {
                                switch (adnetwork[ad_medium_network]) {
                                    case "admob":
                                        String[] admmob_mrec_id = app_data.get(0).getAdmobMediumRectangleid().split(",");
                                        if (current_admob_medium_rectId < admmob_mrec_id.length) {
                                            String placmentId = admmob_mrec_id[current_admob_medium_rectId];
                                            if (!placmentId.equalsIgnoreCase("")) {
                                                final AdView admob_Mediam_Ragtangal = new AdView(activity);
                                                admob_Mediam_Ragtangal.setAdSize(AdSize.MEDIUM_RECTANGLE);
                                                admob_Mediam_Ragtangal.setAdUnitId(placmentId);
                                                AdRequest adRequest = new AdRequest.Builder().build();
                                                admob_Mediam_Ragtangal.loadAd(adRequest);
                                                admob_Mediam_Ragtangal.setAdListener(new AdListener() {
                                                    @Override
                                                    public void onAdLoaded() {
                                                        Conts.log_debug(TAG, "admob mediam ragtangal show");
                                                        try {
                                                            if (admob_Mediam_Ragtangal.getParent() != null) {
                                                                ((ViewGroup) admob_Mediam_Ragtangal.getParent()).removeView(admob_Mediam_Ragtangal);
                                                            }
                                                            native_ad.addView(admob_Mediam_Ragtangal);
                                                        } catch (Exception e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }

                                                    @Override
                                                    public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                                                        super.onAdFailedToLoad(adError);
                                                        Conts.log_debug(TAG, "admob mediam ragtangal failed" + adError.getMessage());
                                                    }
                                                });
                                                current_admob_medium_rectId++;
                                                if (current_admob_medium_rectId == admmob_mrec_id.length) {
                                                    current_admob_medium_rectId = 0;
                                                }
                                            }
                                        }
                                        ad_medium_network++;
                                        break;
                                    case "adx":
                                        String[] adx_mrec_id = app_data.get(0).getAdxMediumRectangleid().split(",");
                                        if (current_adx_medium_rectId < adx_mrec_id.length) {
                                            String placmentId = adx_mrec_id[current_adx_medium_rectId];
                                            if (!placmentId.equalsIgnoreCase("")) {
                                                final AdManagerAdView adx_Mediam_Ragtangal = new AdManagerAdView(activity);
                                                adx_Mediam_Ragtangal.setAdSize(AdSize.MEDIUM_RECTANGLE);
                                                adx_Mediam_Ragtangal.setAdUnitId(placmentId);
                                                AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
                                                adx_Mediam_Ragtangal.loadAd(adRequest);
                                                adx_Mediam_Ragtangal.setAdListener(new AdListener() {
                                                    @Override
                                                    public void onAdLoaded() {
                                                        super.onAdLoaded();
                                                        Conts.log_debug(TAG, "adx mediam ragtangal show");
                                                        try {
                                                            if (adx_Mediam_Ragtangal.getParent() != null) {
                                                                ((ViewGroup) adx_Mediam_Ragtangal.getParent()).removeView(adx_Mediam_Ragtangal);
                                                            }
                                                            native_ad.addView(adx_Mediam_Ragtangal);
                                                        } catch (Exception e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }

                                                    @Override
                                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                                        super.onAdFailedToLoad(loadAdError);
                                                        Conts.log_debug(TAG, "adx mediam ragtangal failed" + loadAdError.getMessage());
                                                    }

                                                });
                                                current_adx_medium_rectId++;
                                                if (current_adx_medium_rectId == adx_mrec_id.length) {
                                                    current_adx_medium_rectId = 0;
                                                }
                                            }
                                        }
                                        ad_medium_network++;
                                        break;
                                    case "fb":
                                        String[] fb_mrec_id = app_data.get(0).getFbMediumRectangleId().split(",");
                                        if (current_fb_medium_rectId < fb_mrec_id.length) {
                                            String placmentId = fb_mrec_id[current_fb_medium_rectId];
                                            if (!placmentId.equalsIgnoreCase("")) {
                                                final com.facebook.ads.AdView fb_Ragtangal = new com.facebook.ads.AdView(activity, placmentId, com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
                                                com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                                                    @Override
                                                    public void onAdLoaded(Ad ad) {
                                                        Conts.log_debug(TAG, "FB mediam ragtangal show");
                                                        try {
                                                            if (fb_Ragtangal.getParent() != null) {
                                                                ((ViewGroup) fb_Ragtangal.getParent()).removeView(fb_Ragtangal);
                                                            }
                                                            native_ad.addView(fb_Ragtangal);
                                                        } catch (Exception e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }

                                                    @Override
                                                    public void onError(Ad ad, AdError adError) {
                                                        Conts.log_debug(TAG, "FB mediam ragtangal Failed" + adError.getErrorMessage());
                                                    }

                                                    @Override
                                                    public void onAdClicked(Ad ad) {
                                                    }

                                                    @Override
                                                    public void onLoggingImpression(Ad ad) {
                                                    }
                                                };
                                                fb_Ragtangal.loadAd(fb_Ragtangal.buildLoadAdConfig().withAdListener(adListener).build());
                                                current_fb_medium_rectId++;
                                                if (current_fb_medium_rectId == fb_mrec_id.length) {
                                                    current_fb_medium_rectId = 0;
                                                }
                                            }
                                        }
                                        ad_medium_network++;
                                        break;
                                    case "applovin":
                                        String[] applovin_mrec_id = app_data.get(0).getApplovin_medium_rectangle_id().split(",");
                                        if (current_applovin_medium_rectId < applovin_mrec_id.length) {
                                            String placmentId = applovin_mrec_id[current_applovin_medium_rectId];
                                            if (!placmentId.equalsIgnoreCase("")) {
                                                final MaxAdView applovin_medium_rect = new MaxAdView(placmentId, MaxAdFormat.MREC, activity);
                                                int widthPx = AppLovinSdkUtils.dpToPx(activity, 300);
                                                int heightPx = AppLovinSdkUtils.dpToPx(activity, 250);
                                                applovin_medium_rect.setLayoutParams(new ViewGroup.LayoutParams(widthPx, heightPx));
                                                applovin_medium_rect.loadAd();
                                                applovin_medium_rect.setListener(new MaxAdViewAdListener() {
                                                    @Override
                                                    public void onAdExpanded(MaxAd maxAd) {
                                                    }

                                                    @Override
                                                    public void onAdCollapsed(MaxAd maxAd) {
                                                    }

                                                    @Override
                                                    public void onAdLoaded(MaxAd maxAd) {
                                                        Conts.log_debug(TAG, "Applovin mediam ragtangal show");
                                                        try {
                                                            if (applovin_medium_rect.getParent() != null) {
                                                                ((ViewGroup) applovin_medium_rect.getParent()).removeView(applovin_medium_rect);
                                                            }
                                                            native_ad.addView(applovin_medium_rect);
                                                        } catch (Exception e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }

                                                    @Override
                                                    public void onAdDisplayed(MaxAd maxAd) {
                                                    }

                                                    @Override
                                                    public void onAdHidden(MaxAd maxAd) {
                                                    }

                                                    @Override
                                                    public void onAdClicked(MaxAd maxAd) {
                                                    }

                                                    @Override
                                                    public void onAdLoadFailed(String s, MaxError maxError) {
                                                        Conts.log_debug(TAG, "Applovin mediam ragtangal Failed " + maxError.getMessage());
                                                    }

                                                    @Override
                                                    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                                                    }
                                                });
                                                current_applovin_medium_rectId++;
                                                if (current_applovin_medium_rectId == applovin_mrec_id.length) {
                                                    current_applovin_medium_rectId = 0;
                                                }
                                            }
                                        }
                                        ad_medium_network++;
                                        break;
                                    case "wortise":
                                        String[] wortise_mrec_id = app_data.get(0).getWortiseMediumRectangleId().split(",");
                                        if (current_wortise_medium_rectId < wortise_mrec_id.length) {
                                            String placmentId = wortise_mrec_id[current_wortise_medium_rectId];
                                            if (!placmentId.equalsIgnoreCase("")) {
                                                final BannerAd wortise_medium_ragtangal = new BannerAd(activity);
                                                wortise_medium_ragtangal.setAdSize(com.wortise.ads.AdSize.HEIGHT_250);
                                                wortise_medium_ragtangal.setAdUnitId(placmentId);
                                                wortise_medium_ragtangal.loadAd();
                                                wortise_medium_ragtangal.setListener(new BannerAd.Listener() {
                                                    @Override
                                                    public void onBannerLoaded(@NonNull BannerAd bannerAd) {
                                                        Conts.log_debug(TAG, "Wortise Medium Ragtangal show");
                                                        try {
                                                            if (wortise_medium_ragtangal.getParent() != null) {
                                                                ((ViewGroup) wortise_medium_ragtangal.getParent()).removeView(wortise_medium_ragtangal);
                                                            }
                                                            native_ad.addView(wortise_medium_ragtangal);
                                                        } catch (Exception e) {
                                                            throw new RuntimeException(e);
                                                        }
                                                    }

                                                    @Override
                                                    public void onBannerClicked(@NonNull BannerAd bannerAd) {
                                                    }

                                                    @Override
                                                    public void onBannerFailed(@NonNull BannerAd bannerAd, @NonNull com.wortise.ads.AdError adError) {
                                                        Conts.log_debug(TAG, "Wortise Medium Ragtangal failed " + adError);
                                                    }
                                                });
                                                current_wortise_medium_rectId++;
                                                if (current_wortise_medium_rectId == wortise_mrec_id.length) {
                                                    current_wortise_medium_rectId = 0;
                                                }
                                            }
                                        }
                                        ad_medium_network++;
                                        break;
                                    default:
                                }
                                if (ad_medium_network == adnetwork.length) {
                                    ad_medium_network = 0;
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            String[] adnetwork = app_data.get(0).getAdNative().split(",");
                            if (ad_native_network < adnetwork.length) {
                                switch (adnetwork[ad_native_network]) {
                                    case "admob":
                                        String[] admob_native_id = app_data.get(0).getAdmobNativeid().split(",");
                                        if (current_admob_native_Id < admob_native_id.length) {
                                            String placementId = admob_native_id[current_admob_native_Id];
                                            if (!placementId.equalsIgnoreCase("")) {
                                                final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
                                                builder.forNativeAd(nativeAd -> {
                                                    Conts.log_debug(TAG, "Admob Native ad show");
                                                    new NativeAds(activity).Admob_NativeAd(nativeAd, native_ad);
                                                });
                                                builder.withAdListener(new AdListener() {
                                                    @Override
                                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                                        super.onAdFailedToLoad(loadAdError);
                                                        Conts.log_debug(TAG, "Admob Native Ad Failed" + loadAdError.getMessage());
                                                    }

                                                    public void onAdLoaded() {
                                                        super.onAdLoaded();
                                                    }
                                                }).build().loadAd(new AdRequest.Builder().build());
                                                current_admob_native_Id++;
                                                if (current_admob_native_Id == admob_native_id.length) {
                                                    current_admob_native_Id = 0;
                                                }
                                            }
                                        }
                                        ad_native_network++;
                                        break;
                                    case "adx":
                                        String[] adx_native_id = app_data.get(0).getAdxNativeId().split(",");
                                        if (current_adx_native_Id < adx_native_id.length) {
                                            String placementId = adx_native_id[current_adx_native_Id];
                                            if (!placementId.equalsIgnoreCase("")) {
                                                final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
                                                builder.forNativeAd(nativeAd -> {
                                                    Conts.log_debug(TAG, "Adx Native ad show");
                                                    new NativeAds(activity).Admob_NativeAd(nativeAd, native_ad);
                                                });
                                                builder.withAdListener(new AdListener() {
                                                    @Override
                                                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                                        super.onAdFailedToLoad(loadAdError);
                                                        Conts.log_debug(TAG, "Adx Native Ad Failed" + loadAdError.getMessage());
                                                    }

                                                    public void onAdLoaded() {
                                                        super.onAdLoaded();
                                                    }
                                                }).build().loadAd(new AdRequest.Builder().build());
                                                current_adx_native_Id++;
                                                if (current_adx_native_Id == adx_native_id.length) {
                                                    current_adx_native_Id = 0;
                                                }
                                            }
                                        }
                                        ad_native_network++;
                                        break;
                                    case "fb":
                                        String[] fb_native_id = app_data.get(0).getFbNativeId().split(",");
                                        if (current_fb_native_Id < fb_native_id.length) {
                                            String placementId = fb_native_id[current_fb_native_Id];
                                            if (!placementId.equalsIgnoreCase("")) {
                                                final com.facebook.ads.NativeAd fbnative_Ad = new com.facebook.ads.NativeAd(activity, placementId);
                                                NativeAdListener nativeAdListener = new NativeAdListener() {
                                                    @Override
                                                    public void onMediaDownloaded(Ad ad) {
                                                    }

                                                    @SuppressLint("MissingPermission")
                                                    @Override
                                                    public void onError(Ad ad, AdError adError) {
                                                        Conts.log_debug(TAG, "FB Native ad Failed " + adError.getErrorMessage());
                                                    }

                                                    @Override
                                                    public void onAdLoaded(Ad ad) {
                                                        Conts.log_debug(TAG, "FB Native ad show");
                                                        new NativeAds(activity).FB_Native(fbnative_Ad, native_ad);
                                                    }

                                                    @Override
                                                    public void onAdClicked(Ad ad) {
                                                    }

                                                    @Override
                                                    public void onLoggingImpression(Ad ad) {
                                                    }
                                                };
                                                // Request an ad
                                                fbnative_Ad.loadAd(fbnative_Ad.buildLoadAdConfig().withAdListener(nativeAdListener).withMediaCacheFlag(NativeAdBase.MediaCacheFlag.ALL).build());
                                                current_fb_native_Id++;
                                                if (current_fb_native_Id == fb_native_id.length) {
                                                    current_fb_native_Id = 0;
                                                }
                                            }
                                        }
                                        ad_native_network++;
                                        break;
                                    case "applovin":
                                        String[] applovin_native_id = app_data.get(0).getApplovin_nativeid().split(",");
                                        if (current_applovin_native_Id < applovin_native_id.length) {
                                            String placementId = applovin_native_id[current_applovin_native_Id];
                                            if (!placementId.equalsIgnoreCase("")) {
                                                final MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(placementId, activity);
                                                nativeAdLoader.setRevenueListener(ad -> {
                                                });
                                                nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                                                    @Override
                                                    public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                                                        Conts.log_debug(TAG, "Applovin Native ad show");
                                                        if (ad != null) {
                                                            native_ad.removeAllViews();
                                                        }
                                                        native_ad.removeAllViews();
                                                        native_ad.addView(nativeAdView);
                                                    }

                                                    @Override
                                                    public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                                                        Conts.log_debug(TAG, "Applovin Native ad Failed " + error.getMessage());
                                                    }

                                                    @Override
                                                    public void onNativeAdClicked(final MaxAd ad) {
                                                    }
                                                });
                                                nativeAdLoader.loadAd(new NativeAds(activity).createNativeAdView());
                                                current_applovin_native_Id++;
                                                if (current_applovin_native_Id == applovin_native_id.length) {
                                                    current_applovin_native_Id = 0;
                                                }
                                            }
                                        }
                                        ad_native_network++;
                                        break;
                                    case "wortise":
                                        String[] wortise_native_id = app_data.get(0).getWortiseNativeId().split(",");
                                        if (current_wortise_native_Id < wortise_native_id.length) {
                                            String placementId = wortise_native_id[current_wortise_native_Id];
                                            if (!placementId.equalsIgnoreCase("")) {
                                                final GoogleNativeAd wortise_google_native = new GoogleNativeAd(activity, placementId, new GoogleNativeAd.Listener() {
                                                    @Override
                                                    public void onNativeClicked(@NonNull GoogleNativeAd googleNativeAd) {
                                                    }

                                                    @SuppressLint("MissingPermission")
                                                    @Override
                                                    public void onNativeFailed(@NonNull GoogleNativeAd googleNativeAd, @NonNull com.wortise.ads.AdError adError) {
                                                        Conts.log_debug(TAG, "Wortise Native ad Failed" + adError);
                                                    }

                                                    @Override
                                                    public void onNativeImpression(@NonNull GoogleNativeAd googleNativeAd) {
                                                    }

                                                    @Override
                                                    public void onNativeLoaded(@NonNull GoogleNativeAd googleNativeAd, @NonNull NativeAd nativeAd) {
                                                        Conts.log_debug(TAG, "Wortise Native ad show");
                                                        new NativeAds(activity).Admob_NativeAd(nativeAd, native_ad);
                                                    }
                                                });
                                                wortise_google_native.load();
                                                current_wortise_native_Id++;
                                                if (current_wortise_native_Id == wortise_native_id.length) {
                                                    current_wortise_native_Id = 0;
                                                }
                                            }
                                        }
                                        ad_native_network++;
                                        break;
                                    case "local":
                                        @SuppressLint("InflateParams") ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(activity).inflate(R.layout.local_native_ad, null);
                                        native_ad.removeAllViews();
                                        native_ad.addView(viewGroup);
                                        Conts.log_debug(TAG, "Local Native ad show");
                                        RelativeLayout custm_native = viewGroup.findViewById(R.id.custm_native_ad);
                                        ImageView app_icon_native = viewGroup.findViewById(R.id.ad_app_icon);
                                        TextView app_name_native = viewGroup.findViewById(R.id.ad_headline);
                                        ImageView app_banner = viewGroup.findViewById(R.id.ad_banner);
                                        TextView app_ad_body = viewGroup.findViewById(R.id.ad_body);
                                        TextView ad_call_to_action = viewGroup.findViewById(R.id.ad_call_to_action);
                                        try {
                                            Glide.with(activity).load(app_data.get(0).getNew_app_icon()).into(app_icon_native);
                                            Glide.with(activity).load(app_data.get(0).getNew_app_banner()).into(app_banner);
                                            app_name_native.setText(app_data.get(0).getNew_app_name());
                                            app_name_native.setSelected(true);
                                            app_ad_body.setText(app_data.get(0).getNew_app_body());
                                            app_ad_body.setSelected(true);
                                            ad_call_to_action.setText("Install");
                                        } catch (Exception ignored) {
                                        }
                                        custm_native.setOnClickListener(view -> {
                                            try {
                                                Intent intent = new Intent("android.intent.action.VIEW").setData(Uri.parse(app_data.get(0).getNew_app_link()));
                                                activity.startActivity(intent);
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        });
                                        ad_native_network++;
                                        break;
                                    default:
                                }
                                if (ad_native_network == adnetwork.length) {
                                    ad_native_network = 0;
                                }
                            }
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    //-------------------------------------------- Inter Ads ----------------------------------------------------------------------------
    static getDataListner callback;
    int ad_inter_network = 0;

    int current_admob_IntrId = 0;
    int current_adx_IntrId = 0;
    int current_fb_IntrId = 0;
    int current_applovin_IntrId = 0;
    int current_wortise_IntrId = 0;
    int current_inmobi_IntrId = 0;
    Dialog ad_inter_dialog;
    long ad_dialog_time_in_second = 2;

    // TODO: 7/31/2023  Preload Inter Ads
    private void inter_Ads() {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAdInter().split(",");
                    if (ad_inter_network < adnetwork.length) {
                        switch (adnetwork[ad_inter_network]) {
                            case "admob":
                                String[] admob_inter = app_data.get(0).getAdmobInterid().split(",");
                                if (current_admob_IntrId < admob_inter.length) {
                                    Load_interAds_Admob(admob_inter[current_admob_IntrId]);
                                    current_admob_IntrId++;
                                    if (current_admob_IntrId == admob_inter.length) {
                                        current_admob_IntrId = 0;
                                    }
                                }
                                ad_inter_network++;
                                break;
                            case "adx":
                                String[] adx_inter = app_data.get(0).getAdxInterId().split(",");
                                if (current_adx_IntrId < adx_inter.length) {
                                    Load_interAds_Adx(adx_inter[current_adx_IntrId]);
                                    current_adx_IntrId++;
                                    if (current_adx_IntrId == adx_inter.length) {
                                        current_adx_IntrId = 0;
                                    }
                                }
                                ad_inter_network++;
                                break;
                            case "fb":
                                String[] fb_inter = app_data.get(0).getFbInterId().split(",");
                                if (current_fb_IntrId < fb_inter.length) {
                                    Load_interAds_FB(fb_inter[current_fb_IntrId]);
                                    current_fb_IntrId++;
                                    if (current_fb_IntrId == fb_inter.length) {
                                        current_fb_IntrId = 0;
                                    }
                                }
                                ad_inter_network++;
                                break;
                            case "applovin":
                                String[] applovin_inter = app_data.get(0).getApplovin_interid().split(",");
                                if (current_applovin_IntrId < applovin_inter.length) {
                                    Load_interAds_Applovin(applovin_inter[current_applovin_IntrId]);
                                    current_applovin_IntrId++;
                                    if (current_applovin_IntrId == applovin_inter.length) {
                                        current_applovin_IntrId = 0;
                                    }
                                }
                                ad_inter_network++;
                                break;
                            case "wortise":
                                String[] wortise_inter = app_data.get(0).getWortiseInterId().split(",");
                                if (current_wortise_IntrId < wortise_inter.length) {
                                    Load_interAds_Wortise(wortise_inter[current_wortise_IntrId]);
                                    current_wortise_IntrId++;
                                    if (current_wortise_IntrId == wortise_inter.length) {
                                        current_wortise_IntrId = 0;
                                    }
                                }
                                ad_inter_network++;
                                break;
                            case "inmobi":
                                String[] inmobi_inter = app_data.get(0).getInmobi_inter_id().split(",");
                                if (current_inmobi_IntrId < inmobi_inter.length) {
                                    Load_interAds_Inmobi(Long.valueOf(inmobi_inter[current_inmobi_IntrId]));
                                    current_inmobi_IntrId++;
                                    if (current_inmobi_IntrId == inmobi_inter.length) {
                                        current_inmobi_IntrId = 0;
                                    }
                                }
                                ad_inter_network++;
                                break;
                            case "local":
                                Load_interAds_Local();
                                ad_inter_network++;
                            default:
                        }
                        if (ad_inter_network == adnetwork.length) {
                            ad_inter_network = 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // TODO: 7/17/2023  Preload Inter Ads
    // Admob Mode
    private void Load_interAds_Admob(String placementId) {
        if (isGoogleInterLoaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(activity, placementId, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    Conts.log_debug(TAG, "Admob Inter Loaded");
                    ADMOBInterstitialAd = interstitialAd;
                    isGoogleInterLoaded = true;
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            Conts.log_debug(TAG, "Admob Inter Close");
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            Conts.log_debug(TAG, "Admob Inter failed to show" + adError.getMessage());
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            Conts.log_debug(TAG, "Admob Inter Show");
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Conts.log_debug(TAG, "Admob Inter Failed");
                    inter_Ads();
                }
            });
        }
    }

    // Adx Mode
    private void Load_interAds_Adx(String placementId) {
        if (isAdxInterLoaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            AdManagerInterstitialAd.load(activity, placementId, adRequest, new AdManagerInterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    Conts.log_debug(TAG, "Adx Inter Loaded");
                    ADXInterstitialAd = interstitialAd;
                    isAdxInterLoaded = true;
                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            Conts.log_debug(TAG, "Adx Inter Close");
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                            Conts.log_debug(TAG, "Adx Inter Show");
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Conts.log_debug(TAG, "Adx Inter Failed");
                    inter_Ads();
                }
            });
        }
    }

    // FB Mode
    private void Load_interAds_FB(String placementId) {
        if (isFBInterLoaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final com.facebook.ads.InterstitialAd FB_interstitial = new com.facebook.ads.InterstitialAd(activity, placementId);
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                    Conts.log_debug(TAG, "FB Inter Show");
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    Conts.log_debug(TAG, "FB Inter ad Close.");
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB Inter Failed");
                    inter_Ads();
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    Conts.log_debug(TAG, "FB Inter ad Loaded");
                    FB_interstitialAd = FB_interstitial;
                    isFBInterLoaded = true;
                }

                @Override
                public void onAdClicked(Ad ad) {
                    Conts.log_debug(TAG, "FB Interstitial ad clicked!");
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                    Conts.log_debug(TAG, "FB Interstitial ad impression logged!");
                }
            };
            FB_interstitial.loadAd(FB_interstitial.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
        }
    }

    // Applovin Mode
    private void Load_interAds_Applovin(String placementId) {
        if (isApplovinInterLoaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            MaxInterstitialAd interstitialAdmax = new MaxInterstitialAd(placementId, (Activity) activity);
            interstitialAdmax.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    Conts.log_debug(TAG, "Applovin Inter Loaded");
                    Applovin_maxInterstitialAd = interstitialAdmax;
                    isApplovinInterLoaded = true;
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {
                    Conts.log_debug(TAG, "Applovin Inter Show");
                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    Conts.log_debug(TAG, "Applovin Inter Close");
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onAdClicked(MaxAd ad) {
                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    Conts.log_debug(TAG, "Applovin Inter Failed");
                    inter_Ads();
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                }
            });
            interstitialAdmax.loadAd();
        }
    }

    // Wortise Mode
    private void Load_interAds_Wortise(String placementId) {
        if (isWortiseInterLoaded) {
            return;
        }
        if (!placementId.equalsIgnoreCase("")) {
            final com.wortise.ads.interstitial.InterstitialAd Wortise_inter_ad = new com.wortise.ads.interstitial.InterstitialAd(activity, placementId);
            Wortise_inter_ad.loadAd();
            Wortise_inter_ad.setListener(new com.wortise.ads.interstitial.InterstitialAd.Listener() {
                @Override
                public void onInterstitialClicked(@NonNull com.wortise.ads.interstitial.InterstitialAd ad) {
                }

                @Override
                public void onInterstitialDismissed(@NonNull com.wortise.ads.interstitial.InterstitialAd ad) {
                    Conts.log_debug(TAG, "Wortise Inter ad Close.");
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onInterstitialFailed(@NonNull com.wortise.ads.interstitial.InterstitialAd ad, @NonNull com.wortise.ads.AdError error) {
                    Conts.log_debug(TAG, "Wortise Inter Failed");
                    inter_Ads();
                }

                @Override
                public void onInterstitialLoaded(@NonNull com.wortise.ads.interstitial.InterstitialAd ad) {
                    Conts.log_debug(TAG, "Wortise Inter ad Loaded.");
                    Wortise_inter = Wortise_inter_ad;
                    isWortiseInterLoaded = true;
                }

                @Override
                public void onInterstitialShown(@NonNull com.wortise.ads.interstitial.InterstitialAd ad) {
                    Conts.log_debug(TAG, "Wortise Inter Show");
                }
            });
        }
    }

    private void Load_interAds_Inmobi(Long placementId) {
        if (isInmobiInterLoaded) {
            return;
        }
        if (!(placementId == 0)) {
            final InMobiInterstitial inMobiInterstitial = new InMobiInterstitial(activity, placementId, new InterstitialAdEventListener() {
                @Override
                public void onAdFetchFailed(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                    super.onAdFetchFailed(inMobiInterstitial, inMobiAdRequestStatus);
                }

                @Override
                public void onAdFetchSuccessful(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull AdMetaInfo adMetaInfo) {
                    super.onAdFetchSuccessful(inMobiInterstitial, adMetaInfo);
                    Conts.log_debug(TAG, "Inmobi Inter Show");
                }

                public void onAdDismissed(@NonNull InMobiInterstitial ad) {
                    Conts.log_debug(TAG, "Inmobi Inter ad Close.");
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onAdLoadSucceeded(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull AdMetaInfo adMetaInfo) {
                    super.onAdLoadSucceeded(inMobiInterstitial, adMetaInfo);
                    Conts.log_debug(TAG, "Inmobi Inter ad Loaded.");
                    Inmobi_inter = inMobiInterstitial;
                    isInmobiInterLoaded = true;
                }

                @Override
                public void onAdLoadFailed(@NonNull InMobiInterstitial inMobiInterstitial, @NonNull InMobiAdRequestStatus inMobiAdRequestStatus) {
                    super.onAdLoadFailed(inMobiInterstitial, inMobiAdRequestStatus);
                    Conts.log_debug(TAG, "Inmobi Inter ad Failed" + inMobiAdRequestStatus);
                    inter_Ads();
                }
            });
            inMobiInterstitial.load();
        }
    }

    // Local Mode
    private void Load_interAds_Local() {
        if (isLocalInterLoaded) {
            return;
        }
        isLocalInterLoaded = true;
    }

    // TODO: 7/17/2023 Show Inter Ads
    public void show_Interstitial(getDataListner callback2) {
        callback = callback2;
        ad_inter_dialog = new Dialog(activity);
        ad_inter_dialog.requestWindowFeature(1);
        this.ad_inter_dialog.setContentView(R.layout.ad_progress_dialog);
        ad_inter_dialog.setCancelable(false);
        this.ad_inter_dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(this.ad_inter_dialog.getWindow()).setSoftInputMode(3);
        this.ad_inter_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        this.ad_inter_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        if (app_data != null && app_data.size() > 0) {
            if (app_data.get(0).isAds_show()) {
                if (app_data.get(0).isPreload_inter_ads()) {
                    if (isGoogleInterLoaded) {
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000L, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        ADMOBInterstitialAd.show((Activity) activity);
                                        isGoogleInterLoaded = false;
                                        inter_Ads();
                                    }
                                }.start();
                            } else {
                                ADMOBInterstitialAd.show((Activity) activity);
                                isGoogleInterLoaded = false;
                                inter_Ads();
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (isAdxInterLoaded) {
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        ADXInterstitialAd.show((Activity) activity);
                                        isAdxInterLoaded = false;
                                        inter_Ads();
                                    }
                                }.start();
                            } else {
                                ADXInterstitialAd.show((Activity) activity);
                                isAdxInterLoaded = false;
                                inter_Ads();
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (isFBInterLoaded) {
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        FB_interstitialAd.show();
                                        isFBInterLoaded = false;
                                        inter_Ads();
                                    }
                                }.start();
                            } else {
                                FB_interstitialAd.show();
                                isFBInterLoaded = false;
                                inter_Ads();
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (isApplovinInterLoaded) {
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        Applovin_maxInterstitialAd.showAd();
                                        isApplovinInterLoaded = false;
                                        inter_Ads();
                                    }
                                }.start();
                            } else {
                                Applovin_maxInterstitialAd.showAd();
                                isApplovinInterLoaded = false;
                                inter_Ads();
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (isWortiseInterLoaded) {
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        Wortise_inter.showAd();
                                        isWortiseInterLoaded = false;
                                        inter_Ads();
                                    }
                                }.start();
                            } else {
                                Wortise_inter.showAd();
                                isWortiseInterLoaded = false;
                                inter_Ads();
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (isInmobiInterLoaded) {
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        Inmobi_inter.show();
                                        isInmobiInterLoaded = false;
                                        inter_Ads();
                                    }
                                }.start();
                            } else {
                                Inmobi_inter.show();
                                isInmobiInterLoaded = false;
                                inter_Ads();
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (isLocalInterLoaded) {
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        show_Interstitial_local(callback2);
                                        isLocalInterLoaded = false;
                                        inter_Ads();
                                    }
                                }.start();
                            } else {
                                show_Interstitial_local(callback2);
                                isLocalInterLoaded = false;
                                inter_Ads();
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (isadmob_appopen_Loaded) {
                        FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                admob_appOpenAd_inter = null;
                                if (callback != null) {
                                    callback.onSuccess();
                                    callback = null;
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }
                        };
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        admob_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback);
                                        admob_appOpenAd_inter.show((Activity) activity);
                                        Conts.log_debug(TAG, "Admob Appopen Show");
                                        isadmob_appopen_Loaded = false;
                                        appopen_Ads();
                                    }
                                }.start();
                            } else {
                                admob_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback);
                                admob_appOpenAd_inter.show((Activity) activity);
                                Conts.log_debug(TAG, "Admob Appopen Show");
                                isadmob_appopen_Loaded = false;
                                appopen_Ads();
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (isadx_appopen_Loaded) {
                        FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                adx_appOpenAd_inter = null;
                                if (callback != null) {
                                    callback.onSuccess();
                                    callback = null;
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }
                        };
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        adx_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback);
                                        adx_appOpenAd_inter.show((Activity) activity);
                                        Conts.log_debug(TAG, "Adx Appopen Show");
                                        isadx_appopen_Loaded = false;
                                        appopen_Ads();
                                    }
                                }.start();
                            } else {
                                adx_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback);
                                adx_appOpenAd_inter.show((Activity) activity);
                                Conts.log_debug(TAG, "Adx Appopen Show");
                                isadx_appopen_Loaded = false;
                                appopen_Ads();
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (iswortise_appopen_Loaded) {
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        if (wortise_open.isAvailable()) {
                                            wortise_open.showAd((Activity) activity);
                                            Conts.log_debug(TAG, "Wortise Appopen Show");
                                            iswortise_appopen_Loaded = false;
                                            appopen_Ads();
                                        }
                                    }
                                }.start();
                            } else {
                                if (wortise_open.isAvailable()) {
                                    wortise_open.showAd((Activity) activity);
                                    Conts.log_debug(TAG, "Wortise Appopen Show");
                                    iswortise_appopen_Loaded = false;
                                    appopen_Ads();
                                }
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (isapplovin_appopen_Loaded) {
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        if (applovin_appopen.isReady()) {
                                            applovin_appopen.showAd();
                                            Conts.log_debug(TAG, "Applovin Appopen Show");
                                            appopen_Ads();
                                        }
                                    }
                                }.start();
                            } else {
                                if (applovin_appopen.isReady()) {
                                    applovin_appopen.showAd();
                                    Conts.log_debug(TAG, "Applovin Appopen Show");
                                    appopen_Ads();
                                }
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else if (islocal_appopen_Loaded) {
                        adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                        if (adCounter == app_data.get(0).getInterCount()) {
                            Inter_Count.getInstance(activity).storeClicks(1);
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        show_local_Appopen_inter(callback2);
                                        Conts.log_debug(TAG, "Local Appopen Show");
                                        islocal_appopen_Loaded = false;
                                        appopen_Ads();
                                    }
                                }.start();
                            } else {
                                show_local_Appopen_inter(callback2);
                                Conts.log_debug(TAG, "Local Appopen Show");
                                islocal_appopen_Loaded = false;
                                appopen_Ads();
                            }
                        } else {
                            adCounter = adCounter + 1;
                            Inter_Count.getInstance(activity).storeClicks(adCounter);
                            if (callback != null) {
                                callback.onSuccess();
                                callback = null;
                            }
                        }
                    } else {
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                    }
                } else {
                    adCounter = Inter_Count.getInstance(activity).getNumberOfClicks();
                    if (adCounter == app_data.get(0).getInterCount()) {
                        Inter_Count.getInstance(activity).storeClicks(1);
                        if (app_data.get(0).getAd_inter_type().equalsIgnoreCase("appopen")) {
                            appopen_Ads();
                        } else {
                            inter_Ads();
                        }
                    } else {
                        adCounter = adCounter + 1;
                        Inter_Count.getInstance(activity).storeClicks(adCounter);
                    }
                    if (isGoogleInterLoaded) {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    ADMOBInterstitialAd.show((Activity) activity);
                                    isGoogleInterLoaded = false;
                                }
                            }.start();
                        } else {
                            ADMOBInterstitialAd.show((Activity) activity);
                            isGoogleInterLoaded = false;
                        }
                    } else if (isAdxInterLoaded) {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    ADXInterstitialAd.show((Activity) activity);
                                    isAdxInterLoaded = false;
                                }
                            }.start();
                        } else {
                            ADXInterstitialAd.show((Activity) activity);
                            isAdxInterLoaded = false;
                        }
                    } else if (isFBInterLoaded) {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    FB_interstitialAd.show();
                                    isFBInterLoaded = false;
                                }
                            }.start();
                        } else {
                            FB_interstitialAd.show();
                            isFBInterLoaded = false;
                        }
                    } else if (isApplovinInterLoaded) {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    Applovin_maxInterstitialAd.showAd();
                                    isApplovinInterLoaded = false;
                                }
                            }.start();
                        } else {
                            Applovin_maxInterstitialAd.showAd();
                            isApplovinInterLoaded = false;
                        }
                    } else if (isWortiseInterLoaded) {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    Wortise_inter.showAd();
                                    isWortiseInterLoaded = false;
                                }
                            }.start();
                        } else {
                            Wortise_inter.showAd();
                            isWortiseInterLoaded = false;
                        }
                    } else if (isInmobiInterLoaded) {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    Inmobi_inter.show();
                                    isInmobiInterLoaded = false;
                                }
                            }.start();
                        } else {
                            Inmobi_inter.show();
                            isInmobiInterLoaded = false;
                        }
                    } else if (isLocalInterLoaded) {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    show_Interstitial_local(callback2);
                                    isLocalInterLoaded = false;
                                }
                            }.start();
                        } else {
                            show_Interstitial_local(callback2);
                            isLocalInterLoaded = false;
                        }
                    } else if (isadmob_appopen_Loaded) {
                        FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                admob_appOpenAd_inter = null;
                                if (callback != null) {
                                    callback.onSuccess();
                                    callback = null;
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }
                        };
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    admob_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback);
                                    admob_appOpenAd_inter.show((Activity) activity);
                                    Conts.log_debug(TAG, "Admob Appopen Show");
                                    isadmob_appopen_Loaded = false;
                                }
                            }.start();
                        } else {
                            admob_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback);
                            admob_appOpenAd_inter.show((Activity) activity);
                            Conts.log_debug(TAG, "Admob Appopen Show");
                            isadmob_appopen_Loaded = false;
                        }
                    } else if (isadx_appopen_Loaded) {
                        FullScreenContentCallback fullScreenContentCallback = new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                adx_appOpenAd_inter = null;
                                if (callback != null) {
                                    callback.onSuccess();
                                    callback = null;
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }
                        };
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    adx_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback);
                                    adx_appOpenAd_inter.show((Activity) activity);
                                    Conts.log_debug(TAG, "Adx Appopen Show");
                                    isadx_appopen_Loaded = false;
                                }
                            }.start();
                        } else {
                            adx_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback);
                            adx_appOpenAd_inter.show((Activity) activity);
                            Conts.log_debug(TAG, "Adx Appopen Show");
                            isadx_appopen_Loaded = false;
                        }
                    } else if (iswortise_appopen_Loaded) {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    if (wortise_open.isAvailable()) {
                                        wortise_open.showAd((Activity) activity);
                                        Conts.log_debug(TAG, "Wortise Appopen Show");
                                        iswortise_appopen_Loaded = false;
                                    }
                                }
                            }.start();
                        } else {
                            if (wortise_open.isAvailable()) {
                                wortise_open.showAd((Activity) activity);
                                Conts.log_debug(TAG, "Wortise Appopen Show");
                                iswortise_appopen_Loaded = false;
                            }
                        }
                    } else if (isapplovin_appopen_Loaded) {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    if (applovin_appopen.isReady()) {
                                        applovin_appopen.showAd();
                                        Conts.log_debug(TAG, "Applovin Appopen Show");
                                    }
                                }
                            }.start();
                        } else {
                            if (applovin_appopen.isReady()) {
                                applovin_appopen.showAd();
                                Conts.log_debug(TAG, "Applovin Appopen Show");
                            }
                        }
                    } else if (islocal_appopen_Loaded) {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {

                                }

                                @Override
                                public void onFinish() {
                                    ad_inter_dialog.dismiss();
                                    show_local_Appopen_inter(callback2);
                                    Conts.log_debug(TAG, "Local Appopen Show");
                                    islocal_appopen_Loaded = false;
                                }
                            }.start();
                        } else {
                            show_local_Appopen_inter(callback2);
                            Conts.log_debug(TAG, "Local Appopen Show");
                            islocal_appopen_Loaded = false;
                        }
                    } else {
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                    }
                }
            } else {
                if (callback != null) {
                    callback.onSuccess();
                    callback = null;
                }
            }
        }
    }

    // Local Mode
    static Animation animZoomIn;

    @SuppressLint("SetTextI18n")
    private void show_Interstitial_local(getDataListner myCallback2) {
        callback = myCallback2;
        if (app_data != null && app_data.size() > 0) {
            Dialog dialog = new Dialog(activity, R.style.FullWidth_Dialog);
            @SuppressLint("InflateParams") View view = LayoutInflater.from(activity).inflate(R.layout.local_inter_ad, null);
            dialog.setContentView(view);
            dialog.setCancelable(false);
            Window window = dialog.getWindow();
            Objects.requireNonNull(window).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            animZoomIn = AnimationUtils.loadAnimation(activity, R.anim.slide_in_bottom);
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
                Glide.with(activity).load(app_data.get(0).getNew_app_icon()).into(appicon);
                Glide.with(activity).load(app_data.get(0).getNew_app_banner()).into(ad_banner);
                App_name.setText(app_data.get(0).getNew_app_name());
                App_name.setSelected(true);
                app_ad_body.setText(app_data.get(0).getNew_app_body());
                app_ad_body.setSelected(true);
                install.setText("Install");
            } catch (Exception ignored) {
            }
            install.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW").setData(Uri.parse(app_data.get(0).getNew_app_link()));
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            });

            lat1.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(app_data.get(0).getNew_app_link()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            });
            new Handler().postDelayed(() -> {
                ad_close.setVisibility(View.VISIBLE);
                ad_close.setOnClickListener(v -> dialog.dismiss());
            }, 2500);
            dialog.setOnDismissListener(dialog1 -> {
                if (callback != null) {
                    callback.onSuccess();
                    callback = null;
                }
            });
            dialog.show();
        }
    }

    //---------------------------------------------- Appopen Ads ---------------------------------------------------------

    // TODO: 7/17/2023  Appopen Ads
    // Admob
    public void show_Admob_Appopen(getDataListner callback2) {
        callback = callback2;
        if (app_data != null && app_data.size() > 0) {
            FullScreenContentCallback fullScreenContentCallback_admob = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    admob_appOpenAd = null;
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onAdShowedFullScreenContent() {
                }
            };
            AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    Conts.log_debug(TAG, "Admob Open Ad loaded");
                    admob_appOpenAd = appOpenAd;
                    appOpenAd.setFullScreenContentCallback(fullScreenContentCallback_admob);
                    Conts.log_debug(TAG, "Admob Open Ad show");
                    appOpenAd.show((Activity) activity);
                }

                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.e("Parth", "Admob Open Failed" + loadAdError.getMessage());
                }
            };
            AppOpenAd.load(activity, app_data.get(0).getAdmobAppopenid(), getAdRequest(), loadCallback);
        }
    }

    // Adx
    public void show_Adx_Appopen(getDataListner callback2) {
        callback = callback2;
        if (app_data != null && app_data.size() > 0) {
            FullScreenContentCallback fullScreenContentCallback_adx = new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    adx_appOpenAd = null;
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onAdShowedFullScreenContent() {
                }
            };
            AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                    super.onAdLoaded(appOpenAd);
                    Conts.log_debug(TAG, "Adx Open Ad loaded");
                    adx_appOpenAd = appOpenAd;
                    appOpenAd.setFullScreenContentCallback(fullScreenContentCallback_adx);
                    Conts.log_debug(TAG, "Adx Open Ad show");
                    appOpenAd.show((Activity) activity);
                }

                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Log.e("Parth", "Admob Open Failed" + loadAdError.getMessage());
                }
            };
            AppOpenAd.load(activity, app_data.get(0).getAdxAppopenId(), adManagerAdRequest(), loadCallback);
        }
    }

    // Wortise
    public void show_Wortise_Appopen(getDataListner callback2) {
        callback = callback2;
        if (app_data != null && app_data.size() > 0) {
            final com.wortise.ads.appopen.AppOpenAd wortise_open_ad = new com.wortise.ads.appopen.AppOpenAd(activity, app_data.get(0).getWortiseAppopenId());
            wortise_open_ad.loadAd();
            wortise_open_ad.setListener(new com.wortise.ads.appopen.AppOpenAd.Listener() {
                @Override
                public void onAppOpenShown(@NonNull com.wortise.ads.appopen.AppOpenAd appOpenAd) {
                }

                @Override
                public void onAppOpenLoaded(@NonNull com.wortise.ads.appopen.AppOpenAd appOpenAd) {
                    Conts.log_debug(TAG, "Wortise Open Ad loaded");
                    if (appOpenAd.isAvailable()) {
                        appOpenAd.showAd((Activity) activity);
                        Conts.log_debug(TAG, "Wortise Open Ad show");
                    }
                }

                @Override
                public void onAppOpenFailed(@NonNull com.wortise.ads.appopen.AppOpenAd appOpenAd, @NonNull com.wortise.ads.AdError adError) {
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onAppOpenDismissed(@NonNull com.wortise.ads.appopen.AppOpenAd appOpenAd) {
                    Conts.log_debug(TAG, "Wortise Open Ad Close");
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onAppOpenClicked(@NonNull com.wortise.ads.appopen.AppOpenAd appOpenAd) {
                }
            });
        }
    }

    // Applovin Appopen
    public void show_Applovin_Appopen(getDataListner dataListner) {
        callback = dataListner;
        if (app_data != null && app_data.size() > 0) {
            final MaxAppOpenAd applovin_appOpenAd = new MaxAppOpenAd(app_data.get(0).getApplovin_appopen_id(), activity);
            applovin_appOpenAd.loadAd();
            applovin_appOpenAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd maxAd) {
                    Conts.log_debug(TAG, "Applovin Open Ad loaded");
                    if (applovin_appOpenAd.isReady()) {
                        applovin_appOpenAd.showAd();
                        Conts.log_debug(TAG, "Applovin Open Ad show");
                    } else {
                        applovin_appOpenAd.loadAd();
                    }
                }

                @Override
                public void onAdDisplayed(MaxAd maxAd) {
                }

                @Override
                public void onAdHidden(MaxAd maxAd) {
                    Conts.log_debug(TAG, "Applovin Close Open Ad");
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onAdClicked(MaxAd maxAd) {
                }

                @Override
                public void onAdLoadFailed(String s, MaxError maxError) {
                    Log.e("Parth", "Applovin Failed Open Ad" + maxError.getMessage());
                    if (callback != null) {
                        callback.onSuccess();
                        callback = null;
                    }
                }

                @Override
                public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                }
            });
        }
    }

    // Local Appopen
    @SuppressLint("SetTextI18n")
    public void show_local_Appopen(getDataListner callback2) {
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
                Glide.with(activity).load(app_data.get(0).getNew_app_icon()).into(appicon);
                Glide.with(activity).load(app_data.get(0).getNew_app_banner()).into(ad_banner);
                App_name.setText(app_data.get(0).getNew_app_name());
                App_name.setSelected(true);
                app_ad_body.setText(app_data.get(0).getNew_app_body());
                app_ad_body.setSelected(true);
                install.setText("Install");
            } catch (Exception ignored) {
            }
            install.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(app_data.get(0).getNew_app_link()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            lay1.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(app_data.get(0).getNew_app_link()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            linearLayout.setOnClickListener(v -> dialog.dismiss());

            dialog.setOnDismissListener(dialog1 -> {
                if (callback != null) {
                    callback.onSuccess();
                    callback = null;
                }
            });
            dialog.show();
            Conts.log_debug(TAG, "Local Open Ad show");
        }
    }

    private AdRequest getAdRequest() {
        return new AdRequest.Builder().build();
    }

    @SuppressLint("VisibleForTests")
    private AdManagerAdRequest adManagerAdRequest() {
        return new AdManagerAdRequest.Builder().build();
    }

    // TODO: 8/22/2023  Appopen Inter Ads
    int ad_appopen_inter_network = 0;

    private void appopen_Ads() {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAd_open_inter().split(",");
                    if (ad_appopen_inter_network < adnetwork.length) {
                        switch (adnetwork[ad_appopen_inter_network]) {
                            case "admob":
                                get_admob_appopen_AdsLoad();
                                ad_appopen_inter_network++;
                                break;
                            case "adx":
                                get_adx_appopen_AdsLoad();
                                ad_appopen_inter_network++;
                                break;
                            case "applovin":
                                get_applovin_appopen_AdsLoad();
                                ad_appopen_inter_network++;
                                break;
                            case "wortise":
                                get_wortise_appopen_AdsLoad();
                                ad_appopen_inter_network++;
                                break;
                            case "local":
                                get_local_Appopen_AdLoad();
                                ad_appopen_inter_network++;
                            default:
                        }
                        if (ad_appopen_inter_network == adnetwork.length) {
                            ad_appopen_inter_network = 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Admob
    void get_admob_appopen_AdsLoad() {
        if (app_data != null && app_data.size() > 0) {
            if (isadmob_appopen_Loaded) {
                return;
            }
            String placementId = app_data.get(0).getAdmobAppopenid();
            if (!placementId.equalsIgnoreCase("")) {
                AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        super.onAdLoaded(appOpenAd);
                        Conts.log_debug(TAG, "Admob Open Ad loaded");
                        admob_appOpenAd_inter = appOpenAd;
                        isadmob_appopen_Loaded = true;
                    }

                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Log.e("Parth", "Admob Open Failed" + loadAdError.getMessage());
                        appopen_Ads();
                    }
                };
                AppOpenAd.load(activity, placementId, getAdRequest(), loadCallback);
            }
        }
    }

    // Adx
    void get_adx_appopen_AdsLoad() {
        if (app_data != null && app_data.size() > 0) {
            if (isadx_appopen_Loaded) {
                return;
            }
            String placementId = app_data.get(0).getAdxAppopenId();
            if (!placementId.equalsIgnoreCase("")) {
                AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        super.onAdLoaded(appOpenAd);
                        Conts.log_debug(TAG, "Adx Open Ad loaded");
                        adx_appOpenAd_inter = appOpenAd;
                        isadx_appopen_Loaded = true;
                    }

                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Log.e("Parth", "Adx Open Failed" + loadAdError.getMessage());
                        appopen_Ads();
                    }
                };
                AppOpenAd.load(activity, placementId, adManagerAdRequest(), loadCallback);
            }
        }
    }

    // Wortise
    void get_wortise_appopen_AdsLoad() {
        if (app_data != null && app_data.size() > 0) {
            if (iswortise_appopen_Loaded) {
                return;
            }
            String placementId = app_data.get(0).getWortiseAppopenId();
            if (!placementId.equalsIgnoreCase("")) {
                final com.wortise.ads.appopen.AppOpenAd wortise_open_ad = new com.wortise.ads.appopen.AppOpenAd(activity, placementId);
                wortise_open_ad.loadAd();
                wortise_open_ad.setListener(new com.wortise.ads.appopen.AppOpenAd.Listener() {
                    @Override
                    public void onAppOpenShown(@NonNull com.wortise.ads.appopen.AppOpenAd appOpenAd) {
                    }

                    @Override
                    public void onAppOpenLoaded(@NonNull com.wortise.ads.appopen.AppOpenAd appOpenAd) {
                        Conts.log_debug(TAG, "Wortise Open Ad loaded");
                        wortise_open = wortise_open_ad;
                        iswortise_appopen_Loaded = true;
                    }

                    @Override
                    public void onAppOpenFailed(@NonNull com.wortise.ads.appopen.AppOpenAd appOpenAd, @NonNull com.wortise.ads.AdError adError) {
                        Conts.log_debug(TAG, "Wortise Open Ad Failed" + adError);
                        appopen_Ads();
                    }

                    @Override
                    public void onAppOpenDismissed(@NonNull com.wortise.ads.appopen.AppOpenAd appOpenAd) {
                        Conts.log_debug(TAG, "Wortise Open Ad Close");
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                        iswortise_appopen_Loaded = false;
                    }

                    @Override
                    public void onAppOpenClicked(@NonNull com.wortise.ads.appopen.AppOpenAd appOpenAd) {
                    }
                });
            }
        }
    }

    // Applovin
    void get_applovin_appopen_AdsLoad() {
        if (app_data != null && app_data.size() > 0) {
            if (isapplovin_appopen_Loaded) {
                return;
            }
            String placementId = app_data.get(0).getApplovin_appopen_id();
            if (!placementId.equalsIgnoreCase("")) {
                final MaxAppOpenAd applovin_appOpenAd = new MaxAppOpenAd(placementId, activity);
                applovin_appOpenAd.loadAd();
                applovin_appOpenAd.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd maxAd) {
                        Conts.log_debug(TAG, "Applovin appopen Loaded");
                        applovin_appopen = applovin_appOpenAd;
                        isapplovin_appopen_Loaded = true;
                    }

                    @Override
                    public void onAdDisplayed(MaxAd maxAd) {
                    }

                    @Override
                    public void onAdHidden(MaxAd maxAd) {
                        Conts.log_debug(TAG, "Applovin Appopen Close");
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                        isapplovin_appopen_Loaded = false;
                    }

                    @Override
                    public void onAdClicked(MaxAd maxAd) {
                    }

                    @Override
                    public void onAdLoadFailed(String s, MaxError maxError) {
                        Log.e("Parth", "Applovin Failed Open Ad" + maxError.getMessage());
                        appopen_Ads();

                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                    }
                });
            }
        }
    }

    // Local
    void get_local_Appopen_AdLoad() {
        if (islocal_appopen_Loaded) {
            return;
        }
        islocal_appopen_Loaded = true;
    }

    // Local Appopen
    @SuppressLint("SetTextI18n")
    private void show_local_Appopen_inter(getDataListner callback2) {
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
                Glide.with(activity).load(app_data.get(0).getNew_app_icon()).into(appicon);
                Glide.with(activity).load(app_data.get(0).getNew_app_banner()).into(ad_banner);
                App_name.setText(app_data.get(0).getNew_app_name());
                App_name.setSelected(true);
                app_ad_body.setText(app_data.get(0).getNew_app_body());
                app_ad_body.setSelected(true);
                install.setText("Install");
            } catch (Exception ignored) {
            }
            install.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(app_data.get(0).getNew_app_link()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            lay1.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(app_data.get(0).getNew_app_link()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            linearLayout.setOnClickListener(v -> dialog.dismiss());

            dialog.setOnDismissListener(dialog1 -> {
                if (callback != null) {
                    callback.onSuccess();
                    callback = null;
                }
            });
            dialog.show();
        }
    }

    // TODO: 8/10/2023  Splash Inter Ads
    void show_splash_inter(getDataListner callback3) {
        callback = callback3;
        if (app_data != null && app_data.size() > 0) {
            String admob_splash_inter = app_data.get(0).getAdmob_splash_interid();
            String adx_splash_inter = app_data.get(0).getAdx_splash_inter_id();
            String fb_splash_inter = app_data.get(0).getFb_splash_inter_id();
            String applovin_splash_inter = app_data.get(0).getApplovin_splash_interid();
            String wortise_splash_inter = app_data.get(0).getWortise_splash_inter_id();
            if (!admob_splash_inter.equalsIgnoreCase("")) {
                final AdRequest adRequest = new AdRequest.Builder().build();
                InterstitialAd.load(activity, admob_splash_inter, adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        interstitialAd.show((Activity) activity);
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Conts.log_debug(TAG, "Admob Inter Close");
                                if (callback != null) {
                                    callback.onSuccess();
                                    callback = null;
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                Conts.log_debug(TAG, "Admob Inter failed to show" + adError.getMessage());
                                if (callback != null) {
                                    callback.onSuccess();
                                    callback = null;
                                }
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                Conts.log_debug(TAG, "Admob Inter Show");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Conts.log_debug(TAG, "Admob Inter Failed " + loadAdError);
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                    }
                });
            } else if (!adx_splash_inter.equalsIgnoreCase("")) {
                @SuppressLint("VisibleForTests") final AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
                AdManagerInterstitialAd.load(activity, adx_splash_inter, adRequest, new AdManagerInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AdManagerInterstitialAd adManagerInterstitialAd) {
                        super.onAdLoaded(adManagerInterstitialAd);
                        adManagerInterstitialAd.show((Activity) activity);
                        adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Conts.log_debug(TAG, "Admob Inter Close");
                                if (callback != null) {
                                    callback.onSuccess();
                                    callback = null;
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                Conts.log_debug(TAG, "Admob Inter failed to show" + adError.getMessage());
                                if (callback != null) {
                                    callback.onSuccess();
                                    callback = null;
                                }
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                Conts.log_debug(TAG, "Adx Inter Show");
                            }
                        });
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Conts.log_debug(TAG, "Adx Inter Failed " + loadAdError);
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                    }
                });
            } else if (!fb_splash_inter.equalsIgnoreCase("")) {
                final com.facebook.ads.InterstitialAd FB_interstitial = new com.facebook.ads.InterstitialAd(activity, fb_splash_inter);
                InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {
                        Conts.log_debug(TAG, "Fb Inter Show");
                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        Conts.log_debug(TAG, "Fb Inter Failed " + adError);
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        FB_interstitial.show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {
                    }
                };
                FB_interstitial.loadAd(FB_interstitial.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
            } else if (!applovin_splash_inter.equalsIgnoreCase("")) {
                final MaxInterstitialAd interstitialAdmax = new MaxInterstitialAd(applovin_splash_inter, (Activity) activity);
                interstitialAdmax.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd ad) {
                        Conts.log_debug(TAG, "Applovin Inter Loaded");
                        interstitialAdmax.showAd();
                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {
                        Conts.log_debug(TAG, "Applovin Inter Show");
                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {
                        Conts.log_debug(TAG, "Applovin Inter Close");
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {
                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        Conts.log_debug(TAG, "Applovin Inter Failed " + error);
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    }
                });
                interstitialAdmax.loadAd();
            } else if (!wortise_splash_inter.equalsIgnoreCase("")) {
                final com.wortise.ads.interstitial.InterstitialAd Wortise_inter_ad = new com.wortise.ads.interstitial.InterstitialAd(activity, app_data.get(0).getWortise_splash_inter_id());
                Wortise_inter_ad.loadAd();
                Wortise_inter_ad.setListener(new com.wortise.ads.interstitial.InterstitialAd.Listener() {
                    @Override
                    public void onInterstitialClicked(@NonNull com.wortise.ads.interstitial.InterstitialAd ad) {

                    }

                    @Override
                    public void onInterstitialDismissed(@NonNull com.wortise.ads.interstitial.InterstitialAd ad) {
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                    }

                    @Override
                    public void onInterstitialFailed(@NonNull com.wortise.ads.interstitial.InterstitialAd ad, @NonNull com.wortise.ads.AdError error) {
                        Conts.log_debug(TAG, "Wortise Inter Failed" + error);
                        if (callback != null) {
                            callback.onSuccess();
                            callback = null;
                        }
                    }

                    @Override
                    public void onInterstitialLoaded(@NonNull com.wortise.ads.interstitial.InterstitialAd ad) {
                        Conts.log_debug(TAG, "Wortise Inter ad Loaded.");
                        Wortise_inter_ad.showAd();
                    }

                    @Override
                    public void onInterstitialShown(@NonNull com.wortise.ads.interstitial.InterstitialAd ad) {
                        Conts.log_debug(TAG, "Wortise Inter Show");
                    }
                });
            }
        }
    }
}