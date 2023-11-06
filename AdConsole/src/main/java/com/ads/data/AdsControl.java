package com.ads.data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ads.data.Api.APIClient;
import com.ads.data.Api.APIInterface;
import com.ads.data.Api.All_File_Data;
import com.ads.data.Api.File_Recover;
import com.ads.data.Local_ads.MyAds;
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
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    AdView admob_banner_ad;
    AdManagerAdView adx_banner_ad;
    com.facebook.ads.AdView fb_banner_ad;
    @SuppressLint("StaticFieldLeak")
    MaxAdView applovin_banner_ad;

    // Mediam Ragtangal
    boolean isAdmob_Mrec_loaded;
    boolean isAdx_Mrec_loaded;
    boolean isFB_Mrec_loaded;
    boolean isApplovin_Mrec_loaded;
    AdView admob_Mrec_view;
    AdManagerAdView adx_Mrec_view;
    com.facebook.ads.AdView fb_Mrec_view;
    @SuppressLint("StaticFieldLeak")
    MaxAdView applovin_Mrec_view;

    // Native Ad
    boolean isadmob_native_Loaded;
    boolean isadx_native_Loaded;
    boolean isFB_Native_Loaded;
    boolean isApplovin_Native_Loaded;
    boolean isLocal_Native_Loaded;
    NativeAd Admob_native_Ad;
    NativeAd Adx_native_Ad;
    com.facebook.ads.NativeAd fb_native_Ad;
    MaxAd Applovin_native_ad;
    @SuppressLint("StaticFieldLeak")
    MaxNativeAdView applovin_maxnativeadview;

    // Small Native Ad
    boolean isAdmob_small_native_Loaded;
    boolean isadx_small_native_Loaded;
    boolean isFb_small_native_Loaded;
    boolean isapplovin_small_native_Loaded;
    boolean isLocal_small_Native_Loaded;
    NativeAd Admob_small_native_Ad;
    NativeAd Adx_small_native_Ad;
    NativeBannerAd fb_small_native_Ad;
    MaxAd Applovin_small_native_Ad;
    @SuppressLint("StaticFieldLeak")
    MaxNativeAdView applovin_small_native_Ad;

    // Small Naitive Banner Ad
    boolean isadmob_small_native_banner_Loaded;
    boolean isadx_small_native_banner_Loaded;
    boolean isFB_small_native_banner_Loaded;
    boolean isApplovin_small_native_banner_Loaded;
    boolean isLocal_small_Native_banner_Loaded;
    NativeAd Admob_small_native_banner_Ad;
    NativeAd Adx_small_native_banner_Ad;
    NativeBannerAd fb_small_native_banner_Ad;
    MaxAd Applovin_small_native_banner_Ad;
    @SuppressLint("StaticFieldLeak")
    MaxNativeAdView applovin_small_native_banner_Ad;

    // Inter
    boolean isAdmobInterLoaded;
    boolean isAdxInterLoaded;
    boolean isFBInterLoaded;
    boolean isApplovinInterLoaded;
    boolean isLocalInterLoaded;
    InterstitialAd ADMOB_InterstitialAd;
    AdManagerInterstitialAd ADX_InterstitialAd;
    com.facebook.ads.InterstitialAd FB_interstitialAd;
    MaxInterstitialAd Applovin_maxInterstitialAd;

    // Appopen
    boolean isadmob_appopen_Loaded;
    boolean isadx_appopen_Loaded;
    boolean isapplovin_appopen_Loaded;
    boolean islocal_appopen_Loaded;
    MaxAppOpenAd applovin_appopen;
    AppOpenAd admob_appOpenAd_inter;
    AppOpenAd adx_appOpenAd_inter;
    AppOpenAd admob_appOpenAd;
    AppOpenAd adx_appOpenAd;
    MyAds myAdsAdder;
    ViewGroup view_group = null;

    public AdsControl(Context context) {
        activity = context;
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

    private boolean isNetworkAvailable(Activity act) {
        ConnectivityManager connectivityManager = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static boolean isPackageInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return false;
        }
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return !list.isEmpty();
    }

    // TODO: 7/17/2023 Main Service Call
    public static ArrayList<All_File_Data> app_data = new ArrayList<>();

    public void file(final Activity act, String file_key, String packagename, String service, OnClickListener Callback) {
        boolean isBeingDebugged = Settings.Secure.getInt(act.getContentResolver(), Settings.Global.ADB_ENABLED, 0) == 1;
        if (isNetworkAvailable(act)) {
            try {
                APIInterface apiInterface = APIClient.get_file_Client(file_key + "Ads/").create(APIInterface.class);
                Call<File_Recover> call1 = apiInterface.get_file_ads_detail(packagename, service);
                call1.enqueue(new retrofit2.Callback<>() {
                    @Override
                    public void onResponse(@NotNull Call<File_Recover> call, @NotNull retrofit2.Response<File_Recover> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            if (response.body().getData() != null) {
                                app_data.clear();
                                Conts.log_debug(TAG, "Main_Respons_Data " + response.body().getData());
                                app_data.addAll(response.body().getData());
                                String ridirect_app = app_data.get(0).getRedirectApp();
                                if (!ridirect_app.equalsIgnoreCase("")) {
                                    Toast.makeText(act, "Please use our updated Application.", Toast.LENGTH_SHORT).show();
                                    boolean isAppInstalled = isPackageInstalled(act, ridirect_app);
                                    if (isAppInstalled) {
                                        Intent LaunchIntent = act.getPackageManager().getLaunchIntentForPackage(ridirect_app);
                                        act.startActivity(LaunchIntent);
                                    } else {
                                        act.startActivity(new Intent("android.intent.action.VIEW").setData(Uri.parse("https://play.google.com/store/apps/details?id=" + ridirect_app)));
                                    }
                                } else {
                                    String local_url = app_data.get(0).getLocal_ad_url();
                                    if (!local_url.equalsIgnoreCase("")) {
                                        myAdsAdder = new MyAds(act, local_url);
                                    }
                                    if (app_data.get(0).isVpn_option()) {
                                        Conts conts = new Conts(act);
                                        conts.check_VPN_App(act, () -> {
                                            if (isBeingDebugged && app_data.get(0).isDev_option()) {
                                                Conts conts1 = new Conts(act);
                                                conts1.Debugging(() -> preload_ads_call(act, Callback));
                                            } else {
                                                preload_ads_call(act, Callback);
                                            }
                                        });
                                    } else {
                                        if (isBeingDebugged && app_data.get(0).isDev_option()) {
                                            Conts conts1 = new Conts(act);
                                            conts1.Debugging(() -> preload_ads_call(act, Callback));
                                        } else {
                                            preload_ads_call(act, Callback);
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(act, "Server Data not Response", Toast.LENGTH_SHORT).show();
                                Callback.onClick();
                            }
                        } else {
                            Callback.onClick();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<File_Recover> call, @NonNull Throwable t) {
                        Toast.makeText(act, "Server not Response", Toast.LENGTH_SHORT).show();
                        call.cancel();
                        Callback.onClick();
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Conts conts = new Conts(act);
            conts.App_Data_File(act, file_key, packagename, service);
        } else {
            Conts.networkinfo(act);
        }
    }

    // TODO: 8/29/2023  Preload ads
    private void preload_ads_call(Activity activity, OnClickListener myCallback) {
        if (app_data != null && app_data.size() > 0) {
            if (app_data.get(0).isAds_show()) {
                try {
                    if (app_data.get(0).isPreload_native_ads()) {
                        if (app_data.get(0).getAd_native_type().equalsIgnoreCase("mrec")) {
                            medium_rect_Ads();
                        } else {
                            native_Ads();
                        }
                    }
                    if (app_data.get(0).isPreload_small_native_ads()) {
                        small_native_Ads();
                    }
                    if (app_data.get(0).isPreload_small_native_banner_ads()) {
                        small_native_banner_Ads();
                    }
                    if (app_data.get(0).isPreload_banner_ads()) {
                        banner_Ads();
                    }
                    if (app_data.get(0).isPreload_inter_ads()) {
                        if (app_data.get(0).getAd_inter_type().equalsIgnoreCase("appopen")) {
                            appopen_Ads(activity);
                        } else {
                            inter_Ads(activity);
                        }
                    }
                    call(activity, myCallback);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                Next_Call(myCallback);
            }
        } else {
            Next_Call(myCallback);
        }
    }

    // TODO: 7/17/2023 Call Intent
    private void call(Activity activity, OnClickListener myCallback) {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String adnetwork = app_data.get(0).getAdSplash();
                    switch (adnetwork) {
                        case "native":
                            new Handler().postDelayed(() -> open_native(activity, myCallback), 3000);
                            break;
                        case "inter":
                            AdsControl.getInstance(activity).show_splash_inter(activity, myCallback);
                            break;
                        case "admob":
                            AdsControl.getInstance(activity).Admob_Appopen_Show(activity, myCallback);
                            break;
                        case "adx":
                            AdsControl.getInstance(activity).Adx_Appopen_Show(activity, myCallback);
                            break;
                        case "applovin":
                            AdsControl.getInstance(activity).Applovin_Appopen_Show(activity, myCallback);
                            break;
                        case "local":
                            new Handler().postDelayed(() -> {
                                AdsControl.getInstance(activity).myAdsAdder.local_Appopen_show(activity, myCallback);
                            }, 2500);
                            break;
                        case "off":
                            new Handler().postDelayed(() -> {
                                Next_Call(myCallback);
                            }, 2500);
                            break;
                        case "":
                            new Handler().postDelayed(() -> {
                                Next_Call(myCallback);
                            }, 2500);
                            break;
                        default:
                            break;
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
    private void open_native(Activity act, OnClickListener callback) {
        Dialog ad_dialog = new Dialog(act);
        ad_dialog.requestWindowFeature(1);
        ad_dialog.setCancelable(false);
        ad_dialog.setContentView(R.layout.open_native);
        show_native_ad(ad_dialog.findViewById(R.id.ad_native));
        ImageView continuee = ad_dialog.findViewById(R.id.continuee);
        new Handler().postDelayed(() -> {
            continuee.setVisibility(View.VISIBLE);
            continuee.setOnClickListener(v -> {
                ad_dialog.dismiss();
                secound_splash_Ads(act, callback);
            });
        }, 2500);
        ad_dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(ad_dialog.getWindow()).setSoftInputMode(3);
        ad_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ad_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        ad_dialog.show();
    }

    private void secound_splash_Ads(Activity act, OnClickListener callback2) {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String adnetwork = app_data.get(0).getAd_secound_splash();
                    switch (adnetwork) {
                        case "inter":
                            AdsControl.getInstance(act).show_splash_inter(act, callback2);
                            break;
                        case "admob":
                            AdsControl.getInstance(act).Admob_Appopen_Show(act, callback2);
                            break;
                        case "adx":
                            AdsControl.getInstance(act).Adx_Appopen_Show(act, callback2);
                            break;
                        case "applovin":
                            AdsControl.getInstance(act).Applovin_Appopen_Show(act, callback2);
                            break;
                        case "local":
                            AdsControl.getInstance(act).myAdsAdder.local_Appopen_show(act, callback2);
                            break;
                        case "off":
                            Next_Call(callback2);
                            break;
                        case "":
                            Next_Call(callback2);
                            break;
                        default:
                            break;
                    }
                } else {
                    Next_Call(callback2);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void Next_Call(OnClickListener myCallback) {
        myCallback.onClick();
    }

    //-------------------------------------------------------- Banner Ads -------------------------------------------------------

    // TODO: 7/31/2023  Preload Banner Ads
    int ad_banner_network = 0;
    int current_admob_BannerId = 0;
    int current_adx_BannerId = 0;
    int current_fb_BannerId = 0;
    int current_applovin_BannerId = 0;

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
                                    Admob_Banner_Ad(admob_BannerId[current_admob_BannerId], view_group);
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
                                    Adx_Banner_Ad(adx_BannerId[current_adx_BannerId], view_group);
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
                                    Fb_Banner_Ad(fb_BannerId[current_fb_BannerId], view_group);
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
                                    Applovin_Banner_Ad(applovin_BannerId[current_applovin_BannerId], view_group);
                                    current_applovin_BannerId++;
                                    if (current_applovin_BannerId == applovin_BannerId.length) {
                                        current_applovin_BannerId = 0;
                                    }
                                }
                                ad_banner_network++;
                                break;
                            default:
                                break;
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
    private void Admob_Banner_Ad(String placementId, ViewGroup banner_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isGoogleBannerLoaded) {
                return;
            }
            final AdView admob_Banner = new AdView(activity);
            admob_Banner.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, AdSize.FULL_WIDTH));
            admob_Banner.setAdUnitId(placementId);
            AdRequest adRequest = new AdRequest.Builder().build();
            admob_Banner.loadAd(adRequest);
            admob_Banner.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (app_data.get(0).isPreload_banner_ads()) {
                        Conts.log_debug(TAG, "Admob Banner Loaded");
                        admob_banner_ad = admob_Banner;
                        isGoogleBannerLoaded = true;
                    } else {
                        Conts.log_debug(TAG, "Admob Banner Loaded and show");
                        try {
                            if (admob_Banner.getParent() != null) {
                                ((ViewGroup) admob_Banner.getParent()).removeView(admob_Banner);
                            }
                            banner_ad.addView(admob_Banner);
                            isGoogleBannerLoaded = false;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    super.onAdFailedToLoad(adError);
                    Conts.log_debug(TAG, "Admob Banner Failed " + adError.getMessage());
                    if (app_data.get(0).isPreload_banner_ads()) {
                        banner_Ads();
                    } else {
                        Admob_Banner_Ad(placementId, banner_ad);
                    }
                }
            });
        }
    }

    // Adx Mode
    @SuppressLint("MissingPermission")
    private void Adx_Banner_Ad(String placementId, ViewGroup banner_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isAdxBannerLoaded) {
                return;
            }
            final AdManagerAdView adx_Banner = new AdManagerAdView(activity);
            adx_Banner.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(activity, AdSize.FULL_WIDTH));
            adx_Banner.setAdUnitId(placementId);
            @SuppressLint("VisibleForTests") AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            adx_Banner.loadAd(adRequest);
            adx_Banner.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (app_data.get(0).isPreload_banner_ads()) {
                        Conts.log_debug(TAG, "Adx Banner Loaded");
                        adx_banner_ad = adx_Banner;
                        isAdxBannerLoaded = true;
                    } else {
                        Conts.log_debug(TAG, "Adx Banner Loaded and show");
                        try {
                            if (adx_Banner.getParent() != null) {
                                ((ViewGroup) adx_Banner.getParent()).removeView(adx_Banner);
                            }
                            banner_ad.addView(adx_Banner);
                            isAdxBannerLoaded = false;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Adx Banner Failed " + loadAdError.getMessage());
                    if (app_data.get(0).isPreload_banner_ads()) {
                        banner_Ads();
                    } else {
                        Adx_Banner_Ad(placementId, banner_ad);
                    }
                }
            });
        }
    }

    // FB Mode
    private void Fb_Banner_Ad(String placementId, ViewGroup banner_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isFBBannerLoaded) {
                return;
            }
            final com.facebook.ads.AdView fb_banner = new com.facebook.ads.AdView(activity, placementId, com.facebook.ads.AdSize.BANNER_HEIGHT_50);
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {

                @Override
                public void onAdLoaded(Ad ad) {
                    if (app_data.get(0).isPreload_banner_ads()) {
                        Conts.log_debug(TAG, "Fb Banner Loaded");
                        fb_banner_ad = fb_banner;
                        isFBBannerLoaded = true;
                    } else {
                        Conts.log_debug(TAG, "Fb Banner Loaded and show");
                        try {
                            if (fb_banner.getParent() != null) {
                                ((ViewGroup) fb_banner.getParent()).removeView(fb_banner);
                            }
                            banner_ad.addView(fb_banner);
                            isFBBannerLoaded = false;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB Banner Failed " + adError.getErrorMessage());
                    if (app_data.get(0).isPreload_banner_ads()) {
                        banner_Ads();
                    } else {
                        Fb_Banner_Ad(placementId, banner_ad);
                    }
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
    private void Applovin_Banner_Ad(String placementId, ViewGroup banner_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isApplovinBannerLoaded) {
                return;
            }
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
                    if (app_data.get(0).isPreload_banner_ads()) {
                        Conts.log_debug(TAG, "Applovin Banner Loaded");
                        applovin_banner_ad = applo_banner_ad;
                        isApplovinBannerLoaded = true;
                    } else {
                        Conts.log_debug(TAG, "Applovin Banner Loaded and show");
                        try {
                            if (applo_banner_ad.getParent() != null) {
                                ((ViewGroup) applo_banner_ad.getParent()).removeView(applo_banner_ad);
                            }
                            banner_ad.addView(applo_banner_ad);
                            isApplovinBannerLoaded = false;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
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
                    Conts.log_debug(TAG, "Applovin Banner Failed " + maxError.getMessage());
                    if (app_data.get(0).isPreload_banner_ads()) {
                        banner_Ads();
                    } else {
                        Applovin_Banner_Ad(placementId, banner_ad);
                    }
                }

                @Override
                public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                }
            });
            applo_banner_ad.loadAd();
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
                            if (admob_banner_ad.getParent() != null) {
                                ((ViewGroup) admob_banner_ad.getParent()).removeView(admob_banner_ad);
                            }
                            banner_container.addView(admob_banner_ad);
                            isGoogleBannerLoaded = false;
                            banner_Ads();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    } else if (isAdxBannerLoaded) {
                        try {
                            try {
                                if (adx_banner_ad.getParent() != null) {
                                    ((ViewGroup) adx_banner_ad.getParent()).removeView(adx_banner_ad);
                                }
                                banner_container.addView(adx_banner_ad);
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
                            if (fb_banner_ad.getParent() != null) {
                                ((ViewGroup) fb_banner_ad.getParent()).removeView(fb_banner_ad);
                            }
                            banner_container.addView(fb_banner_ad);
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
                    }
                } else {
                    try {
                        String[] adnetwork = app_data.get(0).getAdBanner().split(",");
                        if (ad_banner_network < adnetwork.length) {
                            switch (adnetwork[ad_banner_network]) {
                                case "admob":
                                    String[] admob_BannerId = app_data.get(0).getAdmobBannerid().split(",");
                                    if (current_admob_BannerId < admob_BannerId.length) {
                                        Admob_Banner_Ad(admob_BannerId[current_admob_BannerId], banner_container);
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
                                        Adx_Banner_Ad(adx_BannerId[current_adx_BannerId], banner_container);
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
                                        Fb_Banner_Ad(fb_BannerId[current_fb_BannerId], banner_container);
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
                                        Applovin_Banner_Ad(applovin_BannerId[current_applovin_BannerId], banner_container);
                                        current_applovin_BannerId++;
                                        if (current_applovin_BannerId == applovin_BannerId.length) {
                                            current_applovin_BannerId = 0;
                                        }
                                    }
                                    ad_banner_network++;
                                    break;
                                default:
                                    break;
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

    private void small_native_banner_Ads() {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAdSmallNativeBanner().split(",");
                    if (ad_small_native_banner_network < adnetwork.length) {
                        switch (adnetwork[ad_small_native_banner_network]) {
                            case "admob":
                                String[] admob_small__native_banner_id = app_data.get(0).getAdmob_small_native_bannerid().split(",");
                                if (current_admob_small_native_BannerId < admob_small__native_banner_id.length) {
                                    Admob_Native_Banner_Ad(admob_small__native_banner_id[current_admob_small_native_BannerId], view_group);
                                    current_admob_small_native_BannerId++;
                                    if (current_admob_small_native_BannerId == admob_small__native_banner_id.length) {
                                        current_admob_small_native_BannerId = 0;
                                    }
                                }
                                ad_small_native_banner_network++;
                                break;
                            case "adx":
                                String[] adx_small_native_banner_id = app_data.get(0).getAdx_small_native_banner_id().split(",");
                                if (current_adx_small_native_BannerId < adx_small_native_banner_id.length) {
                                    Adx_Native_Banner_Ad(adx_small_native_banner_id[current_adx_small_native_BannerId], view_group);
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
                                    Fb_Native_Banner_Ad(fb_native_banner_id[current_fb_small_native_BannerId], view_group);
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
                                    Applovin_Native_Banner_Ad(applovin_small_native_banner_id[current_applovin_small_native_BannerId], view_group);
                                    current_applovin_small_native_BannerId++;
                                    if (current_applovin_small_native_BannerId == applovin_small_native_banner_id.length) {
                                        current_applovin_small_native_BannerId = 0;
                                    }
                                }
                                ad_small_native_banner_network++;
                                break;
                            case "local":
                                Local_Native_Banner_Ad();
                                ad_small_native_banner_network++;
                                break;
                            default:
                                break;
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
    private void Admob_Native_Banner_Ad(String placementId, ViewGroup native_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isadmob_small_native_banner_Loaded) {
                return;
            }
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                if (app_data.get(0).isPreload_small_native_banner_ads()) {
                    Conts.log_debug(TAG, "Admob Small Native Banner Ad Loaded");
                    Admob_small_native_banner_Ad = nativeAd;
                    isadmob_small_native_banner_Loaded = true;
                } else {
                    Conts.log_debug(TAG, "Admob Small Native Banner Ad Loaded and show");
                    new NativeAds(activity).Admob_Small_Native_Banner_Ad(nativeAd, native_ad);
                    isadmob_small_native_banner_Loaded = false;
                }
            });
            builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Admob Small Native Banner Ad Failed " + loadAdError.getMessage());
                    if (app_data.get(0).isPreload_small_native_banner_ads()) {
                        small_native_banner_Ads();
                    } else {
                        Admob_Native_Banner_Ad(placementId, native_ad);
                    }
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
    }

    // Adx Mode
    @SuppressLint("MissingPermission")
    private void Adx_Native_Banner_Ad(String placementId, ViewGroup native_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isadx_small_native_banner_Loaded) {
                return;
            }
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                if (app_data.get(0).isPreload_small_native_banner_ads()) {
                    Conts.log_debug(TAG, "Adx Small Native Banner Ad Loaded");
                    Adx_small_native_banner_Ad = nativeAd;
                    isadx_small_native_banner_Loaded = true;
                } else {
                    Conts.log_debug(TAG, "Adx Small Native Banner Ad Loaded and show");
                    new NativeAds(activity).Admob_Small_Native_Banner_Ad(nativeAd, native_ad);
                    isadx_small_native_banner_Loaded = false;
                }
            });
            builder.withAdListener(new AdListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Adx Small Native Banner Ad Failed " + loadAdError.getMessage());
                    if (app_data.get(0).isPreload_small_native_banner_ads()) {
                        small_native_banner_Ads();
                    } else {
                        Adx_Native_Banner_Ad(placementId, native_ad);
                    }
                }
            }).build().loadAd(new AdManagerAdRequest.Builder().build());
        }
    }

    // FB Mode
    private void Fb_Native_Banner_Ad(String placementId, ViewGroup native_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isFB_small_native_banner_Loaded) {
                return;
            }
            final NativeBannerAd fb_nativeBanner_Ad = new NativeBannerAd(activity, placementId);
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {

                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB Native ad failed to load: " + adError.getErrorMessage());
                    if (app_data.get(0).isPreload_small_native_banner_ads()) {
                        small_native_banner_Ads();
                    } else {
                        Fb_Native_Banner_Ad(placementId, native_ad);
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (app_data.get(0).isPreload_small_native_banner_ads()) {
                        Conts.log_debug(TAG, "FB Native Banner ad is loaded");
                        fb_small_native_banner_Ad = fb_nativeBanner_Ad;
                        isFB_small_native_banner_Loaded = true;
                    } else {
                        Conts.log_debug(TAG, "FB Small Native Banner Ad Loaded and show");
                        new NativeAds(activity).FB_Smalle_Native_Banner(fb_nativeBanner_Ad, native_ad);
                        isFB_small_native_banner_Loaded = false;
                    }
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
        }
    }

    // Applovin Mode
    private void Applovin_Native_Banner_Ad(String placementId, ViewGroup native_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isApplovin_small_native_banner_Loaded) {
                return;
            }
            final MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(placementId, activity);
            nativeAdLoader.setRevenueListener(ad -> {
            });
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                    if (app_data.get(0).isPreload_small_native_banner_ads()) {
                        Conts.log_debug(TAG, "Applovin Small Native Banner ad Loaded");
                        applovin_small_native_banner_Ad = nativeAdView;
                        Applovin_small_native_banner_Ad = ad;
                        isApplovin_small_native_banner_Loaded = true;
                    } else {
                        if (ad != null) {
                            native_ad.removeAllViews();
                        }
                        native_ad.removeAllViews();
                        native_ad.addView(nativeAdView);
                        isApplovin_small_native_banner_Loaded = false;
                    }
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    Conts.log_debug(TAG, "Applovin Small Native Banner ad Failed " + error.getMessage());
                    if (app_data.get(0).isPreload_small_native_banner_ads()) {
                        small_native_banner_Ads();
                    } else {
                        Applovin_Native_Banner_Ad(placementId, native_ad);
                    }
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {
                }
            });
            nativeAdLoader.loadAd(new NativeAds(activity).create_Small_Native_Banner_AdView());
        }
    }

    // Local Mode
    private void Local_Native_Banner_Ad() {
        if (isLocal_small_Native_banner_Loaded) {
            return;
        }
        isLocal_small_Native_banner_Loaded = true;
    }

    // TODO: 7/17/2023 Show Small Native Banner Ads
    @SuppressLint("SetTextI18n")
    public void show_small_native_banner_ad(final ViewGroup native_banner_ad) {
        if (app_data != null && app_data.size() > 0) {
            if (app_data.get(0).isAds_show()) {
                if (app_data.get(0).isPreload_small_native_banner_ads()) {
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
                    } else if (isLocal_small_Native_banner_Loaded) {
                        myAdsAdder.small_local_Native_Banner(native_banner_ad);
                        Conts.log_debug(TAG, "Local Native Banner ad show");
                        isLocal_small_Native_banner_Loaded = false;
                        small_native_banner_Ads();
                    } else {
                        myAdsAdder.small_local_Native_Banner(native_banner_ad);
                        small_native_banner_Ads();
                    }
                } else {
                    try {
                        String[] adnetwork = app_data.get(0).getAdSmallNativeBanner().split(",");
                        if (ad_small_native_banner_network < adnetwork.length) {
                            switch (adnetwork[ad_small_native_banner_network]) {
                                case "admob":
                                    String[] admob_small__native_banner_id = app_data.get(0).getAdmob_small_native_bannerid().split(",");
                                    if (current_admob_small_native_BannerId < admob_small__native_banner_id.length) {
                                        Admob_Native_Banner_Ad(admob_small__native_banner_id[current_admob_small_native_BannerId], native_banner_ad);
                                        current_admob_small_native_BannerId++;
                                        if (current_admob_small_native_BannerId == admob_small__native_banner_id.length) {
                                            current_admob_small_native_BannerId = 0;
                                        }
                                    }
                                    ad_small_native_banner_network++;
                                    break;
                                case "adx":
                                    String[] adx_small_native_banner_id = app_data.get(0).getAdx_small_native_banner_id().split(",");
                                    if (current_adx_small_native_BannerId < adx_small_native_banner_id.length) {
                                        Adx_Native_Banner_Ad(adx_small_native_banner_id[current_adx_small_native_BannerId], native_banner_ad);
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
                                        Fb_Native_Banner_Ad(fb_native_banner_id[current_fb_small_native_BannerId], native_banner_ad);
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
                                        Applovin_Native_Banner_Ad(applovin_small_native_banner_id[current_applovin_small_native_BannerId], native_banner_ad);
                                        current_applovin_small_native_BannerId++;
                                        if (current_applovin_small_native_BannerId == applovin_small_native_banner_id.length) {
                                            current_applovin_small_native_BannerId = 0;
                                        }
                                    }
                                    ad_small_native_banner_network++;
                                    break;
                                case "local":
                                    myAdsAdder.small_local_Native_Banner(native_banner_ad);
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

    private void small_native_Ads() {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAdSmallNative().split(",");
                    if (ad_small_native_network < adnetwork.length) {
                        switch (adnetwork[ad_small_native_network]) {
                            case "admob":
                                String[] admob_small_native_id = app_data.get(0).getAdmob_small_nativeid().split(",");
                                if (current_admob_small_native_Id < admob_small_native_id.length) {
                                    Admob_Small_Native_Ad(admob_small_native_id[current_admob_small_native_Id], view_group);
                                    current_admob_small_native_Id++;
                                    if (current_admob_small_native_Id == admob_small_native_id.length) {
                                        current_admob_small_native_Id = 0;
                                    }
                                }
                                ad_small_native_network++;
                                break;
                            case "adx":
                                String[] adx_small_native_id = app_data.get(0).getAdx_small_native_id().split(",");
                                if (current_adx_small_native_Id < adx_small_native_id.length) {
                                    Adx_Small_Native_Ad(adx_small_native_id[current_adx_small_native_Id], view_group);
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
                                    Fb_Small_Native_Ad(fb_small_native_id[current_fb_small_native_Id], view_group);
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
                                    Applovin_Small_NativeAd(applovin_small_native_id[current_applovin_small_native_Id], view_group);
                                    current_applovin_small_native_Id++;
                                    if (current_applovin_small_native_Id == applovin_small_native_id.length) {
                                        current_applovin_small_native_Id = 0;
                                    }
                                }
                                ad_small_native_network++;
                                break;
                            case "local":
                                Local_Small_Native_Ad();
                                ad_small_native_network++;
                                break;
                            default:
                                break;
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
    private void Admob_Small_Native_Ad(String placementId, ViewGroup small_native) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isAdmob_small_native_Loaded) {
                return;
            }
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                if (app_data.get(0).isPreload_small_native_ads()) {
                    Conts.log_debug(TAG, "Admob Small Native Ad Loaded");
                    Admob_small_native_Ad = nativeAd;
                    isAdmob_small_native_Loaded = true;
                } else {
                    Conts.log_debug(TAG, "Admob Small Native Ad Loaded and show");
                    new NativeAds(activity).Admob_Small_Native_Ad(nativeAd, small_native);
                    isAdmob_small_native_Loaded = false;
                }
            });
            builder.withAdListener(new AdListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Admob Small Native Ad Failed " + loadAdError.getMessage());
                    if (app_data.get(0).isPreload_small_native_ads()) {
                        small_native_Ads();
                    } else {
                        Admob_Small_Native_Ad(placementId, small_native);
                    }
                }
            }).build().loadAd(new AdRequest.Builder().build());
        }
    }

    // Adx Mode
    private void Adx_Small_Native_Ad(String placementId, ViewGroup small_native) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isadx_small_native_Loaded) {
                return;
            }
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                if (app_data.get(0).isPreload_small_native_ads()) {
                    Conts.log_debug(TAG, "Adx Small Native Ad Loaded");
                    Adx_small_native_Ad = nativeAd;
                    isadx_small_native_Loaded = true;
                } else {
                    Conts.log_debug(TAG, "Adx Small Native Ad Loaded and show");
                    new NativeAds(activity).Admob_Small_Native_Ad(nativeAd, small_native);
                    isadx_small_native_Loaded = false;
                }
            });
            builder.withAdListener(new AdListener() {
                @SuppressLint("MissingPermission")
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Adx Small Native Ad Failed" + loadAdError.getMessage());
                    if (app_data.get(0).isPreload_small_native_ads()) {
                        small_native_Ads();
                    } else {
                        Adx_Small_Native_Ad(placementId, small_native);
                    }
                }
            }).build().loadAd(new AdManagerAdRequest.Builder().build());
        }
    }

    // FB Mode
    private void Fb_Small_Native_Ad(String placementId, ViewGroup small_native) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isFb_small_native_Loaded) {
                return;
            }
            final NativeBannerAd fb_small_native = new NativeBannerAd(activity, placementId);
            NativeAdListener nativeAdListener = new NativeAdListener() {

                @Override
                public void onAdLoaded(Ad ad) {
                    if (app_data.get(0).isPreload_small_native_ads()) {
                        Conts.log_debug(TAG, "FB Small Native Banner ad is loaded");
                        fb_small_native_Ad = fb_small_native;
                        isFb_small_native_Loaded = true;
                    } else {
                        Conts.log_debug(TAG, "FB Small Native Banner ad is loaded and show");
                        new NativeAds(activity).FB_Smalle_Native(fb_small_native, small_native);
                        isFb_small_native_Loaded = false;
                    }
                }

                @Override
                public void onMediaDownloaded(Ad ad) {
                    // Native ad finished downloading all assets
                }

                @SuppressLint("MissingPermission")
                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB Small Native ad failed to load: " + adError.getErrorMessage());
                    if (app_data.get(0).isPreload_small_native_ads()) {
                        small_native_Ads();
                    } else {
                        Fb_Small_Native_Ad(placementId, small_native);
                    }
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
    private void Applovin_Small_NativeAd(String placementId, ViewGroup small_native) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isapplovin_small_native_Loaded) {
                return;
            }
            final MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(placementId, activity);
            nativeAdLoader.setRevenueListener(ad -> {
            });
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                    if (app_data.get(0).isPreload_small_native_ads()) {
                        Conts.log_debug(TAG, "Applovin Small Native ad Loaded");
                        applovin_small_native_Ad = nativeAdView;
                        Applovin_small_native_Ad = ad;
                        isapplovin_small_native_Loaded = true;
                    } else {
                        Conts.log_debug(TAG, "Applovin Small Native ad Loaded and show");
                        if (ad != null) {
                            small_native.removeAllViews();
                        }
                        small_native.removeAllViews();
                        small_native.addView(nativeAdView);
                        isapplovin_small_native_Loaded = false;
                    }
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    Conts.log_debug(TAG, "Applovin Small Native ad Failed " + error.getMessage());
                    if (app_data.get(0).isPreload_small_native_ads()) {
                        small_native_Ads();
                    } else {
                        Applovin_Small_NativeAd(placementId, small_native);
                    }
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {
                }
            });
            nativeAdLoader.loadAd(new NativeAds(activity).create_Small_NativeAdView());
        }
    }

    // Local Mode
    private void Local_Small_Native_Ad() {
        if (isLocal_small_Native_Loaded) {
            return;
        }
        isLocal_small_Native_Loaded = true;
    }

    // TODO: 7/17/2023  Show Small Native Ads
    @SuppressLint({"MissingPermission", "SetTextI18n"})
    public void show_small_native_ad(final ViewGroup native_banner_ad) {
        if (app_data != null && app_data.size() > 0) {
            if (app_data.get(0).isAds_show()) {
                if (app_data.get(0).isPreload_small_native_ads()) {
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
                    } else if (isLocal_small_Native_Loaded) {
                        Conts.log_debug(TAG, "Local Small Native ad show");
                        myAdsAdder.small_local_Native(native_banner_ad);
                        isLocal_small_Native_Loaded = false;
                        small_native_Ads();
                    } else {
                        myAdsAdder.small_local_Native(native_banner_ad);
                        small_native_Ads();
                    }
                } else {
                    try {
                        String[] adnetwork = app_data.get(0).getAdSmallNative().split(",");
                        if (ad_small_native_network < adnetwork.length) {
                            switch (adnetwork[ad_small_native_network]) {
                                case "admob":
                                    String[] admob_small_native_id = app_data.get(0).getAdmob_small_nativeid().split(",");
                                    if (current_admob_small_native_Id < admob_small_native_id.length) {
                                        Admob_Small_Native_Ad(admob_small_native_id[current_admob_small_native_Id], native_banner_ad);
                                        current_admob_small_native_Id++;
                                        if (current_admob_small_native_Id == admob_small_native_id.length) {
                                            current_admob_small_native_Id = 0;
                                        }
                                    }
                                    ad_small_native_network++;
                                    break;
                                case "adx":
                                    String[] adx_small_native_id = app_data.get(0).getAdx_small_native_id().split(",");
                                    if (current_adx_small_native_Id < adx_small_native_id.length) {
                                        Adx_Small_Native_Ad(adx_small_native_id[current_adx_small_native_Id], native_banner_ad);
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
                                        Fb_Small_Native_Ad(fb_small_native_id[current_fb_small_native_Id], native_banner_ad);
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
                                        Applovin_Small_NativeAd(applovin_small_native_id[current_applovin_small_native_Id], native_banner_ad);
                                        current_applovin_small_native_Id++;
                                        if (current_applovin_small_native_Id == applovin_small_native_id.length) {
                                            current_applovin_small_native_Id = 0;
                                        }
                                    }
                                    ad_small_native_network++;
                                    break;
                                case "local":
                                    myAdsAdder.small_local_Native(native_banner_ad);
                                    ad_small_native_network++;
                                    break;
                                default:
                                    break;
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
                                    Admob_Native_Ad(admob_native_id[current_admob_native_Id], view_group);
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
                                    Adx_Native_Ad(adx_native_id[current_adx_native_Id], view_group);
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
                                    Fb_Native_Ad(fb_native_id[current_fb_native_Id], view_group);
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
                                    Applovin_Native_Ad(applovin_native_id[current_applovin_native_Id], view_group);
                                    current_applovin_native_Id++;
                                    if (current_applovin_native_Id == applovin_native_id.length) {
                                        current_applovin_native_Id = 0;
                                    }
                                }
                                ad_native_network++;
                                break;
                            case "local":
                                Local_Native_ad();
                                ad_native_network++;
                                break;
                            default:
                                break;
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
    private void Admob_Native_Ad(String placementId, ViewGroup native_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isadmob_native_Loaded) {
                return;
            }
            VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(false).build();
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                if (app_data.get(0).isPreload_native_ads()) {
                    Admob_native_Ad = nativeAd;
                    isadmob_native_Loaded = true;
                    Conts.log_debug(TAG, "Admob Native Ad Loaded");
                } else {
                    Conts.log_debug(TAG, "Admob Native Ad Loaded and show");
                    new NativeAds(activity).Admob_NativeAd(nativeAd, native_ad);
                    isadmob_native_Loaded = false;
                }
            });
            builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Admob Native Ad Failed " + loadAdError.getMessage());
                    if (app_data.get(0).isPreload_native_ads()) {
                        native_Ads();
                    } else {
                        Admob_Native_Ad(placementId, native_ad);
                    }
                }
            });
            builder.withNativeAdOptions(new NativeAdOptions.Builder().setRequestCustomMuteThisAd(false).setVideoOptions(videoOptions).build()).build().loadAd(new AdRequest.Builder().build());
        }
    }

    // Adx Mode
    @SuppressLint("MissingPermission")
    private void Adx_Native_Ad(String placementId, ViewGroup native_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isadx_native_Loaded) {
                return;
            }
            VideoOptions videoOptions = new VideoOptions.Builder().setStartMuted(false).build();
            final AdLoader.Builder builder = new AdLoader.Builder(activity, placementId);
            builder.forNativeAd(nativeAd -> {
                if (app_data.get(0).isPreload_native_ads()) {
                    Conts.log_debug(TAG, "Adx Native Ad Loaded");
                    Adx_native_Ad = nativeAd;
                    isadx_native_Loaded = true;
                } else {
                    Conts.log_debug(TAG, "Adx Native Ad Loaded and show");
                    new NativeAds(activity).Admob_NativeAd(nativeAd, native_ad);
                    isadx_native_Loaded = false;
                }
            });
            builder.withAdListener(new AdListener() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "Adx Native Ad Failed " + loadAdError.getMessage());
                    if (app_data.get(0).isPreload_native_ads()) {
                        native_Ads();
                    } else {
                        Adx_Native_Ad(placementId, native_ad);
                    }
                }
            });
            builder.withNativeAdOptions(new NativeAdOptions.Builder().setRequestCustomMuteThisAd(false).setVideoOptions(videoOptions).build()).build().loadAd(new AdRequest.Builder().build());
        }
    }

    // FB Mode
    private void Fb_Native_Ad(String placementId, ViewGroup native_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isFB_Native_Loaded) {
                return;
            }
            final com.facebook.ads.NativeAd fbnative_Ad = new com.facebook.ads.NativeAd(activity, placementId);
            NativeAdListener nativeAdListener = new NativeAdListener() {
                @Override
                public void onMediaDownloaded(Ad ad) {
                }

                @SuppressLint("MissingPermission")
                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB Native ad Failed " + adError.getErrorMessage());
                    if (app_data.get(0).isPreload_native_ads()) {
                        native_Ads();
                    } else {
                        Fb_Native_Ad(placementId, native_ad);
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (app_data.get(0).isPreload_native_ads()) {
                        Conts.log_debug(TAG, "FB Native ad loaded");
                        fb_native_Ad = fbnative_Ad;
                        isFB_Native_Loaded = true;
                    } else {
                        Conts.log_debug(TAG, "FB Native ad loaded and show");
                        new NativeAds(activity).FB_Native(fbnative_Ad, native_ad);
                        isFB_Native_Loaded = false;
                    }
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
    private void Applovin_Native_Ad(String placementId, ViewGroup native_ad) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isApplovin_Native_Loaded) {
                return;
            }
            final MaxNativeAdLoader nativeAdLoader = new MaxNativeAdLoader(placementId, activity);
            nativeAdLoader.setRevenueListener(ad -> {
            });
            nativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
                @Override
                public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad) {
                    if (app_data.get(0).isPreload_native_ads()) {
                        Conts.log_debug(TAG, "Applovin Native ad Loaded");
                        applovin_maxnativeadview = nativeAdView;
                        Applovin_native_ad = ad;
                        isApplovin_Native_Loaded = true;
                    } else {
                        Conts.log_debug(TAG, "Applovin Native ad Loaded and show");
                        if (ad != null) {
                            native_ad.removeAllViews();
                        }
                        native_ad.removeAllViews();
                        native_ad.addView(nativeAdView);
                        isApplovin_Native_Loaded = false;
                    }
                }

                @Override
                public void onNativeAdLoadFailed(final String adUnitId, final MaxError error) {
                    Conts.log_debug(TAG, "Applovin Native ad Failed " + error.getMessage());
                    if (app_data.get(0).isPreload_native_ads()) {
                        native_Ads();
                    } else {
                        Applovin_Native_Ad(placementId, native_ad);
                    }
                }

                @Override
                public void onNativeAdClicked(final MaxAd ad) {
                }
            });
            nativeAdLoader.loadAd(new NativeAds(activity).createNativeAdView());
        }
    }

    // Local Mode
    private void Local_Native_ad() {
        if (isLocal_Native_Loaded) {
            return;
        }
        isLocal_Native_Loaded = true;
    }

    // TODO: 8/3/2023  Medium Rect Ad
    int ad_medium_network = 0;
    int current_admob_mrec_Id = 0;
    int current_adx_mrec_Id = 0;
    int current_fb_mrec_Id = 0;
    int current_applovin_mrec_Id = 0;

    private void medium_rect_Ads() {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAd_medium_rect().split(",");
                    if (ad_medium_network < adnetwork.length) {
                        switch (adnetwork[ad_medium_network]) {
                            case "admob":
                                String[] admob_mrec_id = app_data.get(0).getAdmobMediumRectangleid().split(",");
                                if (current_admob_mrec_Id < admob_mrec_id.length) {
                                    Admob_mrec_Ad(admob_mrec_id[current_admob_mrec_Id], view_group);
                                    current_admob_mrec_Id++;
                                    if (current_admob_mrec_Id == admob_mrec_id.length) {
                                        current_admob_mrec_Id = 0;
                                    }
                                }
                                ad_medium_network++;
                                break;
                            case "adx":
                                String[] adx_mrec_id = app_data.get(0).getAdxMediumRectangleid().split(",");
                                if (current_adx_mrec_Id < adx_mrec_id.length) {
                                    Adx_mrec_Ad(adx_mrec_id[current_adx_mrec_Id], view_group);
                                    current_adx_mrec_Id++;
                                    if (current_adx_mrec_Id == adx_mrec_id.length) {
                                        current_adx_mrec_Id = 0;
                                    }
                                }
                                ad_medium_network++;
                                break;
                            case "fb":
                                String[] fb_mrec_id = app_data.get(0).getFbMediumRectangleId().split(",");
                                if (current_fb_mrec_Id < fb_mrec_id.length) {
                                    FB_mrec_Ad(fb_mrec_id[current_fb_mrec_Id], view_group);
                                    current_fb_mrec_Id++;
                                    if (current_fb_mrec_Id == fb_mrec_id.length) {
                                        current_fb_mrec_Id = 0;
                                    }
                                }
                                ad_medium_network++;
                                break;
                            case "applovin":
                                String[] applovin_mrec_id = app_data.get(0).getApplovin_medium_rectangle_id().split(",");
                                if (current_applovin_mrec_Id < applovin_mrec_id.length) {
                                    Applovin_mrec_Ad(applovin_mrec_id[current_applovin_mrec_Id], view_group);
                                    current_applovin_mrec_Id++;
                                    if (current_applovin_mrec_Id == applovin_mrec_id.length) {
                                        current_applovin_mrec_Id = 0;
                                    }
                                }
                                ad_medium_network++;
                                break;
                            default:
                                break;
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
    private void Admob_mrec_Ad(String placmentId, ViewGroup mrec_ad) {
        if (!placmentId.equalsIgnoreCase("")) {
            if (isAdmob_Mrec_loaded) {
                return;
            }
            final AdView admob_Mediam_Ragtangal = new AdView(activity);
            admob_Mediam_Ragtangal.setAdSize(AdSize.MEDIUM_RECTANGLE);
            admob_Mediam_Ragtangal.setAdUnitId(placmentId);
            AdRequest adRequest = new AdRequest.Builder().build();
            admob_Mediam_Ragtangal.loadAd(adRequest);
            admob_Mediam_Ragtangal.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    if (app_data.get(0).isPreload_native_ads()) {
                        Conts.log_debug(TAG, "admob mediam ragtangal loaded");
                        admob_Mrec_view = admob_Mediam_Ragtangal;
                        isAdmob_Mrec_loaded = true;
                    } else {
                        Conts.log_debug(TAG, "admob mediam ragtangal loaded and show");
                        try {
                            if (admob_Mediam_Ragtangal.getParent() != null) {
                                ((ViewGroup) admob_Mediam_Ragtangal.getParent()).removeView(admob_Mediam_Ragtangal);
                            }
                            mrec_ad.addView(admob_Mediam_Ragtangal);
                            isAdmob_Mrec_loaded = false;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                    super.onAdFailedToLoad(adError);
                    Conts.log_debug(TAG, "admob mediam ragtangal failed " + adError.getMessage());
                    if (app_data.get(0).isPreload_native_ads()) {
                        medium_rect_Ads();
                    } else {
                        Admob_mrec_Ad(placmentId, mrec_ad);
                    }
                }
            });
        }
    }

    // Adx Mode
    @SuppressLint("MissingPermission")
    private void Adx_mrec_Ad(String placmentId, ViewGroup mrec_ad) {
        if (!placmentId.equalsIgnoreCase("")) {
            if (isAdx_Mrec_loaded) {
                return;
            }
            final AdManagerAdView adx_Mediam_Ragtangal = new AdManagerAdView(activity);
            adx_Mediam_Ragtangal.setAdSize(AdSize.MEDIUM_RECTANGLE);
            adx_Mediam_Ragtangal.setAdUnitId(placmentId);
            AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            adx_Mediam_Ragtangal.loadAd(adRequest);
            adx_Mediam_Ragtangal.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (app_data.get(0).isPreload_native_ads()) {
                        Conts.log_debug(TAG, "adx mediam ragtangal loaded");
                        adx_Mrec_view = adx_Mediam_Ragtangal;
                        isAdx_Mrec_loaded = true;
                    } else {
                        Conts.log_debug(TAG, "adx mediam ragtangal loaded and show");
                        try {
                            if (adx_Mediam_Ragtangal.getParent() != null) {
                                ((ViewGroup) adx_Mediam_Ragtangal.getParent()).removeView(adx_Mediam_Ragtangal);
                            }
                            mrec_ad.addView(adx_Mediam_Ragtangal);
                            isAdx_Mrec_loaded = false;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    Conts.log_debug(TAG, "adx mediam ragtangal failed " + loadAdError.getMessage());
                    if (app_data.get(0).isPreload_native_ads()) {
                        medium_rect_Ads();
                    } else {
                        Adx_mrec_Ad(placmentId, mrec_ad);
                    }
                }

            });
        }
    }

    // FB Mode
    private void FB_mrec_Ad(String placmentId, ViewGroup mrec_ad) {
        if (!placmentId.equalsIgnoreCase("")) {
            if (isFB_Mrec_loaded) {
                return;
            }
            final com.facebook.ads.AdView fb_Ragtangal = new com.facebook.ads.AdView(activity, placmentId, com.facebook.ads.AdSize.RECTANGLE_HEIGHT_250);
            com.facebook.ads.AdListener adListener = new com.facebook.ads.AdListener() {
                @Override
                public void onAdLoaded(Ad ad) {
                    if (app_data.get(0).isPreload_native_ads()) {
                        Conts.log_debug(TAG, "FB mediam ragtangal Loaded");
                        fb_Mrec_view = fb_Ragtangal;
                        isFB_Mrec_loaded = true;
                    } else {
                        try {
                            if (fb_Ragtangal.getParent() != null) {
                                ((ViewGroup) fb_Ragtangal.getParent()).removeView(fb_Ragtangal);
                            }
                            mrec_ad.addView(fb_Ragtangal);
                            Conts.log_debug(TAG, "FB mediam ragtangal Loaded and show");
                            isFB_Mrec_loaded = false;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB mediam ragtangal Failed " + adError.getErrorMessage());
                    if (app_data.get(0).isPreload_native_ads()) {
                        medium_rect_Ads();
                    } else {
                        FB_mrec_Ad(placmentId, mrec_ad);
                    }
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
    private void Applovin_mrec_Ad(String placmentId, ViewGroup mrec_ad) {
        if (!placmentId.equalsIgnoreCase("")) {
            if (isApplovin_Mrec_loaded) {
                return;
            }
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
                    if (app_data.get(0).isPreload_native_ads()) {
                        Conts.log_debug(TAG, "Applovin mediam ragtangal Loaded");
                        applovin_Mrec_view = applovin_medium_rect;
                        isApplovin_Mrec_loaded = true;
                    } else {
                        Conts.log_debug(TAG, "Applovin mediam ragtangal Loaded and show");
                        try {
                            if (applovin_medium_rect.getParent() != null) {
                                ((ViewGroup) applovin_medium_rect.getParent()).removeView(applovin_medium_rect);
                            }
                            mrec_ad.addView(applovin_medium_rect);
                            isApplovin_Mrec_loaded = false;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
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
                    if (app_data.get(0).isPreload_native_ads()) {
                        medium_rect_Ads();
                    } else {
                        Applovin_mrec_Ad(placmentId, mrec_ad);
                    }
                }

                @Override
                public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
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
                    if (app_data.get(0).getAd_native_type().equalsIgnoreCase("mrec")) {
                        if (isAdmob_Mrec_loaded) {
                            try {
                                if (admob_Mrec_view.getParent() != null) {
                                    ((ViewGroup) admob_Mrec_view.getParent()).removeView(admob_Mrec_view);
                                }
                                native_ad.addView(admob_Mrec_view);
                                isAdmob_Mrec_loaded = false;
                                medium_rect_Ads();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else if (isAdx_Mrec_loaded) {
                            try {
                                if (adx_Mrec_view.getParent() != null) {
                                    ((ViewGroup) adx_Mrec_view.getParent()).removeView(adx_Mrec_view);
                                }
                                native_ad.addView(adx_Mrec_view);
                                isAdx_Mrec_loaded = false;
                                medium_rect_Ads();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else if (isFB_Mrec_loaded) {
                            try {
                                if (fb_Mrec_view.getParent() != null) {
                                    ((ViewGroup) fb_Mrec_view.getParent()).removeView(fb_Mrec_view);
                                }
                                native_ad.addView(fb_Mrec_view);
                                isFB_Mrec_loaded = false;
                                medium_rect_Ads();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else if (isApplovin_Mrec_loaded) {
                            try {
                                if (applovin_Mrec_view.getParent() != null) {
                                    ((ViewGroup) applovin_Mrec_view.getParent()).removeView(applovin_Mrec_view);
                                }
                                native_ad.addView(applovin_Mrec_view);
                                isApplovin_Mrec_loaded = false;
                                medium_rect_Ads();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
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
                        } else if (isLocal_Native_Loaded) {
                            myAdsAdder.local_Native(native_ad);
                            Conts.log_debug(TAG, "Local Native ad show");
                            isLocal_Native_Loaded = false;
                            native_Ads();
                        } else {
                            myAdsAdder.local_Native(native_ad);
                            native_Ads();
                        }
                    }
                } else {
                    if (app_data.get(0).getAd_native_type().equalsIgnoreCase("mrec")) {
                        try {
                            String[] adnetwork = app_data.get(0).getAd_medium_rect().split(",");
                            if (ad_medium_network < adnetwork.length) {
                                switch (adnetwork[ad_medium_network]) {
                                    case "admob":
                                        String[] admob_mrec_id = app_data.get(0).getAdmobMediumRectangleid().split(",");
                                        if (current_admob_mrec_Id < admob_mrec_id.length) {
                                            Admob_mrec_Ad(admob_mrec_id[current_admob_mrec_Id], native_ad);
                                            current_admob_mrec_Id++;
                                            if (current_admob_mrec_Id == admob_mrec_id.length) {
                                                current_admob_mrec_Id = 0;
                                            }
                                        }
                                        ad_medium_network++;
                                        break;
                                    case "adx":
                                        String[] adx_mrec_id = app_data.get(0).getAdxMediumRectangleid().split(",");
                                        if (current_adx_mrec_Id < adx_mrec_id.length) {
                                            Adx_mrec_Ad(adx_mrec_id[current_adx_mrec_Id], native_ad);
                                            current_adx_mrec_Id++;
                                            if (current_adx_mrec_Id == adx_mrec_id.length) {
                                                current_adx_mrec_Id = 0;
                                            }
                                        }
                                        ad_medium_network++;
                                        break;
                                    case "fb":
                                        String[] fb_mrec_id = app_data.get(0).getFbMediumRectangleId().split(",");
                                        if (current_fb_mrec_Id < fb_mrec_id.length) {
                                            FB_mrec_Ad(fb_mrec_id[current_fb_mrec_Id], native_ad);
                                            current_fb_mrec_Id++;
                                            if (current_fb_mrec_Id == fb_mrec_id.length) {
                                                current_fb_mrec_Id = 0;
                                            }
                                        }
                                        ad_medium_network++;
                                        break;
                                    case "applovin":
                                        String[] applovin_mrec_id = app_data.get(0).getApplovin_medium_rectangle_id().split(",");
                                        if (current_applovin_mrec_Id < applovin_mrec_id.length) {
                                            Applovin_mrec_Ad(applovin_mrec_id[current_applovin_mrec_Id], native_ad);
                                            current_applovin_mrec_Id++;
                                            if (current_applovin_mrec_Id == applovin_mrec_id.length) {
                                                current_applovin_mrec_Id = 0;
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
                                            Admob_Native_Ad(admob_native_id[current_admob_native_Id], native_ad);
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
                                            Adx_Native_Ad(adx_native_id[current_adx_native_Id], native_ad);
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
                                            Fb_Native_Ad(fb_native_id[current_fb_native_Id], native_ad);
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
                                            Applovin_Native_Ad(applovin_native_id[current_applovin_native_Id], native_ad);
                                            current_applovin_native_Id++;
                                            if (current_applovin_native_Id == applovin_native_id.length) {
                                                current_applovin_native_Id = 0;
                                            }
                                        }
                                        ad_native_network++;
                                        break;
                                    case "local":
                                        myAdsAdder.local_Native(native_ad);
                                        ad_native_network++;
                                        break;
                                    default:
                                        break;
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

    static OnClickListener callback;
    int ad_inter_network = 0;
    int current_admob_IntrId = 0;
    int current_adx_IntrId = 0;
    int current_fb_IntrId = 0;
    int current_applovin_IntrId = 0;
    Dialog ad_inter_dialog;
    long ad_dialog_time_in_second = 2;
    int countAdds = 0;

    // TODO: 7/31/2023  Preload Inter Ads
    private void inter_Ads(Activity activity) {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAdInter().split(",");
                    if (ad_inter_network < adnetwork.length) {
                        switch (adnetwork[ad_inter_network]) {
                            case "admob":
                                String[] admob_inter = app_data.get(0).getAdmobInterid().split(",");
                                if (current_admob_IntrId < admob_inter.length) {
                                    Admob_inter_Ad(activity, admob_inter[current_admob_IntrId]);
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
                                    Adx_inter_Ad(activity, adx_inter[current_adx_IntrId]);
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
                                    Fb_inter_Ad(activity, fb_inter[current_fb_IntrId]);
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
                                    Applovin_inter_Ad(activity, applovin_inter[current_applovin_IntrId]);
                                    current_applovin_IntrId++;
                                    if (current_applovin_IntrId == applovin_inter.length) {
                                        current_applovin_IntrId = 0;
                                    }
                                }
                                ad_inter_network++;
                                break;
                            case "local":
                                Local_inter_Ad();
                                ad_inter_network++;
                            default:
                                break;
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

    // Admob Mode
    private void Admob_inter_Ad(Activity act, String placementId) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isAdmobInterLoaded) {
                return;
            }
            final AdRequest adRequest = new AdRequest.Builder().build();
            InterstitialAd.load(act, placementId, adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    if (app_data.get(0).isPreload_inter_ads()) {
                        Conts.log_debug(TAG, "Admob Inter Loaded");
                        ADMOB_InterstitialAd = interstitialAd;
                        isAdmobInterLoaded = true;
                    } else {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000L, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    Conts.log_debug(TAG, "Admob Inter loaded and show");
                                    ad_inter_dialog.dismiss();
                                    interstitialAd.show(act);
                                    isAdmobInterLoaded = false;
                                }
                            }.start();
                        } else {
                            Conts.log_debug(TAG, "Admob Inter loaded and show");
                            interstitialAd.show(act);
                            isAdmobInterLoaded = false;
                        }
                    }

                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            Conts.log_debug(TAG, "Admob Inter Close");
                            if (callback != null) {
                                callback.onClick();
                                callback = null;
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Conts.log_debug(TAG, "Admob Inter Failed " + loadAdError.getMessage());
                    if (app_data.get(0).isPreload_inter_ads()) {
                        inter_Ads(act);
                    } else {
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }
                }
            });
        } else {
            if (app_data.get(0).isPreload_inter_ads()) {
                inter_Ads(act);
            } else {
                if (callback != null) {
                    callback.onClick();
                    callback = null;
                }
            }
        }
    }

    // Adx Mode
    private void Adx_inter_Ad(Activity act, String placementId) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isAdxInterLoaded) {
                return;
            }
            final AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
            AdManagerInterstitialAd.load(act, placementId, adRequest, new AdManagerInterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull AdManagerInterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);
                    if (app_data.get(0).isPreload_inter_ads()) {
                        Conts.log_debug(TAG, "Adx Inter Loaded");
                        ADX_InterstitialAd = interstitialAd;
                        isAdxInterLoaded = true;
                    } else {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    Conts.log_debug(TAG, "Adx Inter loaded and show");
                                    ad_inter_dialog.dismiss();
                                    interstitialAd.show(act);
                                    isAdxInterLoaded = false;
                                }
                            }.start();
                        } else {
                            Conts.log_debug(TAG, "Adx Inter loaded and show");
                            interstitialAd.show(act);
                            isAdxInterLoaded = false;
                        }
                    }

                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            Conts.log_debug(TAG, "Adx Inter Close");
                            if (callback != null) {
                                callback.onClick();
                                callback = null;
                            }
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    Conts.log_debug(TAG, "Adx Inter Failed " + loadAdError.getMessage());
                    if (app_data.get(0).isPreload_inter_ads()) {
                        inter_Ads(act);
                    } else {
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }
                }
            });
        } else {
            if (app_data.get(0).isPreload_inter_ads()) {
                inter_Ads(act);
            } else {
                if (callback != null) {
                    callback.onClick();
                    callback = null;
                }
            }
        }
    }

    // FB Mode
    private void Fb_inter_Ad(Activity act, String placementId) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isFBInterLoaded) {
                return;
            }
            final com.facebook.ads.InterstitialAd FB_interstitial = new com.facebook.ads.InterstitialAd(activity, placementId);
            InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {
                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    Conts.log_debug(TAG, "FB Inter ad Close");
                    if (callback != null) {
                        callback.onClick();
                        callback = null;
                    }
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    Conts.log_debug(TAG, "FB Inter Failed " + adError.getErrorMessage());
                    if (app_data.get(0).isPreload_inter_ads()) {
                        inter_Ads(act);
                    } else {
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }
                }

                @Override
                public void onAdLoaded(Ad ad) {
                    if (app_data.get(0).isPreload_inter_ads()) {
                        Conts.log_debug(TAG, "FB Inter ad Loaded");
                        FB_interstitialAd = FB_interstitial;
                        isFBInterLoaded = true;
                    } else {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    Conts.log_debug(TAG, "FB Inter loaded and Show");
                                    ad_inter_dialog.dismiss();
                                    FB_interstitial.show();
                                    isFBInterLoaded = false;
                                }
                            }.start();
                        } else {
                            Conts.log_debug(TAG, "FB Inter loaded and Show");
                            FB_interstitial.show();
                            isFBInterLoaded = false;
                        }
                    }
                }

                @Override
                public void onAdClicked(Ad ad) {
                }

                @Override
                public void onLoggingImpression(Ad ad) {
                }
            };
            FB_interstitial.loadAd(FB_interstitial.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
        } else {
            if (app_data.get(0).isPreload_inter_ads()) {
                inter_Ads(act);
            } else {
                if (callback != null) {
                    callback.onClick();
                    callback = null;
                }
            }
        }
    }

    // Applovin Mode
    private void Applovin_inter_Ad(Activity act, String placementId) {
        if (!placementId.equalsIgnoreCase("")) {
            if (isApplovinInterLoaded) {
                return;
            }
            MaxInterstitialAd interstitialAdmax = new MaxInterstitialAd(placementId, act);
            interstitialAdmax.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    if (app_data.get(0).isPreload_inter_ads()) {
                        Conts.log_debug(TAG, "Applovin Inter Loaded");
                        Applovin_maxInterstitialAd = interstitialAdmax;
                        isApplovinInterLoaded = true;
                    } else {
                        if (app_data.get(0).isApp_inter_dialog_show()) {
                            ad_inter_dialog.show();
                            new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                }

                                @Override
                                public void onFinish() {
                                    Conts.log_debug(TAG, "Applovin Inter Loaded and show");
                                    ad_inter_dialog.dismiss();
                                    interstitialAdmax.showAd();
                                    isApplovinInterLoaded = false;
                                }
                            }.start();
                        } else {
                            Conts.log_debug(TAG, "Applovin Inter Loaded and show");
                            interstitialAdmax.showAd();
                            isApplovinInterLoaded = false;
                        }
                    }
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {
                }

                @Override
                public void onAdHidden(MaxAd ad) {
                    Conts.log_debug(TAG, "Applovin Inter Close");
                    if (callback != null) {
                        callback.onClick();
                        callback = null;
                    }
                }

                @Override
                public void onAdClicked(MaxAd ad) {
                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    Conts.log_debug(TAG, "Applovin Inter Failed " + error.getMessage());
                    if (app_data.get(0).isPreload_inter_ads()) {
                        inter_Ads(act);
                    } else {
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                }
            });
            interstitialAdmax.loadAd();
        } else {
            if (app_data.get(0).isPreload_inter_ads()) {
                inter_Ads(act);
            } else {
                if (callback != null) {
                    callback.onClick();
                    callback = null;
                }
            }
        }
    }

    // Local Mode
    private void Local_inter_Ad() {
        if (isLocalInterLoaded) {
            return;
        }
        isLocalInterLoaded = true;
    }

    // TODO: 7/17/2023 Show Inter Ads
    public void show_Interstitial(Activity act, OnClickListener callback2) {
        callback = callback2;
        int interadds_count = countAdds + 1;
        countAdds = interadds_count;
        ad_inter_dialog = new Dialog(act);
        ad_inter_dialog.requestWindowFeature(1);
        ad_inter_dialog.setContentView(R.layout.ad_progress_dialog);
        ad_inter_dialog.setCancelable(false);
        ad_inter_dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(ad_inter_dialog.getWindow()).setSoftInputMode(3);
        ad_inter_dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        ad_inter_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        if (app_data != null && app_data.size() > 0) {
            if (app_data.get(0).isAds_show()) {
                if (app_data.get(0).isPreload_inter_ads()) {
                    if (interadds_count % app_data.get(0).getInterCount() == 0) {
                        countAdds = 0;
                        if (app_data.get(0).getAd_inter_type().equalsIgnoreCase("appopen")) {
                            if (isadmob_appopen_Loaded) {
                                if (app_data.get(0).isApp_inter_dialog_show()) {
                                    ad_inter_dialog.show();
                                    new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            ad_inter_dialog.dismiss();
                                            admob_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback_admob);
                                            admob_appOpenAd_inter.show(act);
                                            Conts.log_debug(TAG, "Admob Appopen Show");
                                            isadmob_appopen_Loaded = false;
                                            appopen_Ads(act);
                                        }
                                    }.start();
                                } else {
                                    admob_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback_admob);
                                    admob_appOpenAd_inter.show(act);
                                    Conts.log_debug(TAG, "Admob Appopen Show");
                                    isadmob_appopen_Loaded = false;
                                    appopen_Ads(act);
                                }
                            } else if (isadx_appopen_Loaded) {
                                if (app_data.get(0).isApp_inter_dialog_show()) {
                                    ad_inter_dialog.show();
                                    new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {

                                        }

                                        @Override
                                        public void onFinish() {
                                            ad_inter_dialog.dismiss();
                                            adx_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback_adx);
                                            adx_appOpenAd_inter.show(act);
                                            Conts.log_debug(TAG, "Adx Appopen Show");
                                            isadx_appopen_Loaded = false;
                                            appopen_Ads(act);
                                        }
                                    }.start();
                                } else {
                                    adx_appOpenAd_inter.setFullScreenContentCallback(fullScreenContentCallback_adx);
                                    adx_appOpenAd_inter.show(act);
                                    Conts.log_debug(TAG, "Adx Appopen Show");
                                    isadx_appopen_Loaded = false;
                                    appopen_Ads(act);
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
                                                isapplovin_appopen_Loaded = false;
                                                Conts.log_debug(TAG, "Applovin Appopen Show");
                                                appopen_Ads(act);
                                            }
                                        }
                                    }.start();
                                } else {
                                    if (applovin_appopen.isReady()) {
                                        applovin_appopen.showAd();
                                        isapplovin_appopen_Loaded = false;
                                        Conts.log_debug(TAG, "Applovin Appopen Show");
                                        appopen_Ads(act);
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
                                            myAdsAdder.local_Appopen_show(act, callback2);
                                            Conts.log_debug(TAG, "Local Appopen Show");
                                            islocal_appopen_Loaded = false;
                                            appopen_Ads(act);
                                        }
                                    }.start();
                                } else {
                                    myAdsAdder.local_Appopen_show(act, callback2);
                                    Conts.log_debug(TAG, "Local Appopen Show");
                                    islocal_appopen_Loaded = false;
                                    appopen_Ads(act);
                                }
                            } else {
                                if (callback != null) {
                                    callback.onClick();
                                    callback = null;
                                }
                            }
                        } else {
                            if (isAdmobInterLoaded) {
                                if (app_data.get(0).isApp_inter_dialog_show()) {
                                    ad_inter_dialog.show();
                                    new CountDownTimer(ad_dialog_time_in_second * 1000L, 10) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                        }

                                        @Override
                                        public void onFinish() {
                                            Conts.log_debug(TAG, "Admob Inter Show");
                                            ad_inter_dialog.dismiss();
                                            ADMOB_InterstitialAd.show(act);
                                            isAdmobInterLoaded = false;
                                            inter_Ads(act);
                                        }
                                    }.start();
                                } else {
                                    Conts.log_debug(TAG, "Admob Inter Show");
                                    ADMOB_InterstitialAd.show(act);
                                    isAdmobInterLoaded = false;
                                    inter_Ads(act);
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
                                            Conts.log_debug(TAG, "Adx Inter Show");
                                            ad_inter_dialog.dismiss();
                                            ADX_InterstitialAd.show(act);
                                            isAdxInterLoaded = false;
                                            inter_Ads(act);
                                        }
                                    }.start();
                                } else {
                                    Conts.log_debug(TAG, "Adx Inter Show");
                                    ADX_InterstitialAd.show(act);
                                    isAdxInterLoaded = false;
                                    inter_Ads(act);
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
                                            Conts.log_debug(TAG, "Fb Inter Show");
                                            ad_inter_dialog.dismiss();
                                            FB_interstitialAd.show();
                                            isFBInterLoaded = false;
                                            inter_Ads(act);
                                        }
                                    }.start();
                                } else {
                                    Conts.log_debug(TAG, "Fb Inter Show");
                                    FB_interstitialAd.show();
                                    isFBInterLoaded = false;
                                    inter_Ads(act);
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
                                            Conts.log_debug(TAG, "Applovin Inter Show");
                                            ad_inter_dialog.dismiss();
                                            Applovin_maxInterstitialAd.showAd();
                                            isApplovinInterLoaded = false;
                                            inter_Ads(act);
                                        }
                                    }.start();
                                } else {
                                    Conts.log_debug(TAG, "Applovin Inter Show");
                                    Applovin_maxInterstitialAd.showAd();
                                    isApplovinInterLoaded = false;
                                    inter_Ads(act);
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
                                            Conts.log_debug(TAG, "Local Inter Show");
                                            ad_inter_dialog.dismiss();
                                            myAdsAdder.show_local_InterAd(act, callback2);
                                            isLocalInterLoaded = false;
                                            inter_Ads(act);
                                        }
                                    }.start();
                                } else {
                                    Conts.log_debug(TAG, "Local Inter Show");
                                    myAdsAdder.show_local_InterAd(act, callback2);
                                    isLocalInterLoaded = false;
                                    inter_Ads(act);
                                }
                            } else {
                                if (callback != null) {
                                    callback.onClick();
                                    callback = null;
                                }
                            }
                        }
                    } else {
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }
                } else {
                    if (interadds_count % app_data.get(0).getInterCount() == 0) {
                        countAdds = 0;
                        if (app_data.get(0).getAd_inter_type().equalsIgnoreCase("appopen")) {
                            try {
                                String[] adnetwork = app_data.get(0).getAd_open_inter().split(",");
                                if (ad_appopen_inter_network < adnetwork.length) {
                                    switch (adnetwork[ad_appopen_inter_network]) {
                                        case "admob":
                                            String placementId = app_data.get(0).getAdmobAppopenid();
                                            if (!placementId.equalsIgnoreCase("")) {
                                                Admob_appopen_Ads(act, placementId);
                                            } else {
                                                if (callback != null) {
                                                    callback.onClick();
                                                    callback = null;
                                                }
                                            }
                                            ad_appopen_inter_network++;
                                            break;
                                        case "adx":
                                            String adx_placement = app_data.get(0).getAdxAppopenId();
                                            if (!adx_placement.equalsIgnoreCase("")) {
                                                Adx_appopen_Ads(act, adx_placement);
                                            } else {
                                                if (callback != null) {
                                                    callback.onClick();
                                                    callback = null;
                                                }
                                            }
                                            ad_appopen_inter_network++;
                                            break;
                                        case "applovin":
                                            String applovin_placement = app_data.get(0).getApplovin_appopen_id();
                                            if (!applovin_placement.equalsIgnoreCase("")) {
                                                Applovin_appopen_Ads(act, applovin_placement);
                                            } else {
                                                if (callback != null) {
                                                    callback.onClick();
                                                    callback = null;
                                                }
                                            }
                                            ad_appopen_inter_network++;
                                            break;
                                        case "local":
                                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                                ad_inter_dialog.show();
                                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {

                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        ad_inter_dialog.dismiss();
                                                        myAdsAdder.local_Appopen_show(act, callback2);
                                                        Conts.log_debug(TAG, "Local Appopen Show");
                                                    }
                                                }.start();
                                            } else {
                                                myAdsAdder.local_Appopen_show(act, callback2);
                                                Conts.log_debug(TAG, "Local Appopen Show");
                                            }
                                            ad_appopen_inter_network++;
                                            break;
                                        case "off":
                                            if (callback != null) {
                                                callback.onClick();
                                                callback = null;
                                            }
                                            break;
                                        case "":
                                            if (callback != null) {
                                                callback.onClick();
                                                callback = null;
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                    if (ad_appopen_inter_network == adnetwork.length) {
                                        ad_appopen_inter_network = 0;
                                    }
                                } else {
                                    if (callback != null) {
                                        callback.onClick();
                                        callback = null;
                                    }
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                String[] adnetwork = app_data.get(0).getAdInter().split(",");
                                if (ad_inter_network < adnetwork.length) {
                                    switch (adnetwork[ad_inter_network]) {
                                        case "admob":
                                            String[] admob_inter = app_data.get(0).getAdmobInterid().split(",");
                                            if (current_admob_IntrId < admob_inter.length) {
                                                Admob_inter_Ad(act, admob_inter[current_admob_IntrId]);
                                                current_admob_IntrId++;
                                                if (current_admob_IntrId == admob_inter.length) {
                                                    current_admob_IntrId = 0;
                                                }
                                            } else {
                                                if (callback != null) {
                                                    callback.onClick();
                                                    callback = null;
                                                }
                                            }
                                            ad_inter_network++;
                                            break;
                                        case "adx":
                                            String[] adx_inter = app_data.get(0).getAdxInterId().split(",");
                                            if (current_adx_IntrId < adx_inter.length) {
                                                Adx_inter_Ad(act, adx_inter[current_adx_IntrId]);
                                                current_adx_IntrId++;
                                                if (current_adx_IntrId == adx_inter.length) {
                                                    current_adx_IntrId = 0;
                                                }
                                            } else {
                                                if (callback != null) {
                                                    callback.onClick();
                                                    callback = null;
                                                }
                                            }
                                            ad_inter_network++;
                                            break;
                                        case "fb":
                                            String[] fb_inter = app_data.get(0).getFbInterId().split(",");
                                            if (current_fb_IntrId < fb_inter.length) {
                                                Fb_inter_Ad(act, fb_inter[current_fb_IntrId]);
                                                current_fb_IntrId++;
                                                if (current_fb_IntrId == fb_inter.length) {
                                                    current_fb_IntrId = 0;
                                                }
                                            } else {
                                                if (callback != null) {
                                                    callback.onClick();
                                                    callback = null;
                                                }
                                            }
                                            ad_inter_network++;
                                            break;
                                        case "applovin":
                                            String[] applovin_inter = app_data.get(0).getApplovin_interid().split(",");
                                            if (current_applovin_IntrId < applovin_inter.length) {
                                                Applovin_inter_Ad(act, applovin_inter[current_applovin_IntrId]);
                                                current_applovin_IntrId++;
                                                if (current_applovin_IntrId == applovin_inter.length) {
                                                    current_applovin_IntrId = 0;
                                                }
                                            } else {
                                                if (callback != null) {
                                                    callback.onClick();
                                                    callback = null;
                                                }
                                            }
                                            ad_inter_network++;
                                            break;
                                        case "local":
                                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                                ad_inter_dialog.show();
                                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        ad_inter_dialog.dismiss();
                                                        myAdsAdder.show_local_InterAd(act, callback2);
                                                    }
                                                }.start();
                                            } else {
                                                myAdsAdder.show_local_InterAd(act, callback2);
                                            }
                                            ad_inter_network++;
                                            break;
                                        case "off":
                                            if (callback != null) {
                                                callback.onClick();
                                                callback = null;
                                            }
                                            break;
                                        case "":
                                            if (callback != null) {
                                                callback.onClick();
                                                callback = null;
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                    if (ad_inter_network == adnetwork.length) {
                                        ad_inter_network = 0;
                                    }
                                } else {
                                    if (callback != null) {
                                        callback.onClick();
                                        callback = null;
                                    }
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }
                }
            } else {
                if (callback != null) {
                    callback.onClick();
                    callback = null;
                }
            }
        } else {
            if (callback != null) {
                callback.onClick();
                callback = null;
            }
        }
    }

    //---------------------------------------------- Appopen Ads ---------------------------------------------------------

    // TODO: 7/17/2023  Appopen Ads
    // Admob
    public void Admob_Appopen_Show(Activity activity, OnClickListener callback2) {
        callback = callback2;
        if (app_data != null && app_data.size() > 0) {
            String placement = app_data.get(0).getAdmobAppopenid();
            if (!placement.equalsIgnoreCase("")) {
                FullScreenContentCallback fullScreenContentCallback_admob = new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        admob_appOpenAd = null;
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                        if (callback != null) {
                            callback.onClick();
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
                        admob_appOpenAd = appOpenAd;
                        appOpenAd.setFullScreenContentCallback(fullScreenContentCallback_admob);
                        Conts.log_debug(TAG, "Admob Open Ad show");
                        appOpenAd.show(activity);
                    }

                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Conts.log_debug(TAG, "Admob Open Failed " + loadAdError.getMessage());
                    }
                };
                AppOpenAd.load(activity, placement, getAdRequest(), loadCallback);
            } else {
                if (callback != null) {
                    callback.onClick();
                    callback = null;
                }
            }
        } else {
            if (callback != null) {
                callback.onClick();
                callback = null;
            }
        }
    }

    // Adx
    public void Adx_Appopen_Show(Activity act, OnClickListener callback2) {
        callback = callback2;
        if (app_data != null && app_data.size() > 0) {
            String placement = app_data.get(0).getAdxAppopenId();
            if (!placement.equalsIgnoreCase("")) {
                FullScreenContentCallback fullScreenContentCallback_adx = new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        Conts.log_debug(TAG, "Adx Open Ad close");
                        adx_appOpenAd = null;
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                        if (callback != null) {
                            callback.onClick();
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
                        adx_appOpenAd = appOpenAd;
                        appOpenAd.setFullScreenContentCallback(fullScreenContentCallback_adx);
                        Conts.log_debug(TAG, "Adx Open Ad show");
                        appOpenAd.show(act);
                    }

                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Conts.log_debug(TAG, "Adx Open Ad Failed " + loadAdError.getMessage());
                    }
                };
                AppOpenAd.load(act, placement, adManagerAdRequest(), loadCallback);
            } else {
                if (callback != null) {
                    callback.onClick();
                    callback = null;
                }
            }
        } else {
            if (callback != null) {
                callback.onClick();
                callback = null;
            }
        }
    }

    // Applovin Appopen
    public void Applovin_Appopen_Show(Activity activity, OnClickListener dataListner) {
        callback = dataListner;
        if (app_data != null && app_data.size() > 0) {
            String placement = app_data.get(0).getApplovin_appopen_id();
            if (!placement.equalsIgnoreCase("")) {
                final MaxAppOpenAd applovin_appOpenAd = new MaxAppOpenAd(placement, activity);
                applovin_appOpenAd.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd maxAd) {
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
                            callback.onClick();
                            callback = null;
                        }
                    }

                    @Override
                    public void onAdClicked(MaxAd maxAd) {
                    }

                    @Override
                    public void onAdLoadFailed(String s, MaxError maxError) {
                        Conts.log_debug(TAG, "Applovin Failed Open Ad " + maxError.getMessage());
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                    }
                });
                applovin_appOpenAd.loadAd();
            } else {
                if (callback != null) {
                    callback.onClick();
                    callback = null;
                }
            }
        } else {
            if (callback != null) {
                callback.onClick();
                callback = null;
            }
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

    private void appopen_Ads(Activity activity) {
        try {
            if (app_data != null && app_data.size() > 0) {
                if (app_data.get(0).isAds_show()) {
                    String[] adnetwork = app_data.get(0).getAd_open_inter().split(",");
                    if (ad_appopen_inter_network < adnetwork.length) {
                        switch (adnetwork[ad_appopen_inter_network]) {
                            case "admob":
                                String placementId = app_data.get(0).getAdmobAppopenid();
                                Admob_appopen_Ads(activity, placementId);
                                ad_appopen_inter_network++;
                                break;
                            case "adx":
                                String adxplacement = app_data.get(0).getAdxAppopenId();
                                Adx_appopen_Ads(activity, adxplacement);
                                ad_appopen_inter_network++;
                                break;
                            case "applovin":
                                String applovinplacementId = app_data.get(0).getApplovin_appopen_id();
                                Applovin_appopen_Ads(activity, applovinplacementId);
                                ad_appopen_inter_network++;
                                break;
                            case "local":
                                Local_Appopen_Ads();
                                ad_appopen_inter_network++;
                            default:
                                break;
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

    FullScreenContentCallback fullScreenContentCallback_admob;

    // Admob
    void Admob_appopen_Ads(Activity act, String placementId) {
        if (app_data != null && app_data.size() > 0) {
            if (!placementId.equalsIgnoreCase("")) {
                if (isadmob_appopen_Loaded) {
                    return;
                }
                fullScreenContentCallback_admob = new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        if (callback != null) {
                            callback.onClick();
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
                AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        super.onAdLoaded(appOpenAd);
                        if (app_data.get(0).isPreload_inter_ads()) {
                            Conts.log_debug(TAG, "Admob Open Ad loaded");
                            admob_appOpenAd_inter = appOpenAd;
                            isadmob_appopen_Loaded = true;
                        } else {
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        appOpenAd.setFullScreenContentCallback(fullScreenContentCallback_admob);
                                        appOpenAd.show(act);
                                        Conts.log_debug(TAG, "Admob Appopen loaded and show");
                                        isadmob_appopen_Loaded = false;
                                    }
                                }.start();
                            } else {
                                appOpenAd.setFullScreenContentCallback(fullScreenContentCallback_admob);
                                appOpenAd.show(act);
                                Conts.log_debug(TAG, "Admob Appopen loaded and show");
                                isadmob_appopen_Loaded = false;
                            }
                        }
                    }

                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Conts.log_debug(TAG, "Admob Open Failed " + loadAdError.getMessage());
                        if (app_data.get(0).isPreload_inter_ads()) {
                            appopen_Ads(act);
                        } else {
                            if (callback != null) {
                                callback.onClick();
                                callback = null;
                            }
                        }
                    }
                };
                AppOpenAd.load(act, placementId, getAdRequest(), loadCallback);
            } else {
                if (app_data.get(0).isPreload_inter_ads()) {
                    appopen_Ads(act);
                }
            }
        }
    }

    FullScreenContentCallback fullScreenContentCallback_adx;

    // Adx
    void Adx_appopen_Ads(Activity act, String placementId) {
        if (app_data != null && app_data.size() > 0) {
            if (!placementId.equalsIgnoreCase("")) {
                if (isadx_appopen_Loaded) {
                    return;
                }
                fullScreenContentCallback_adx = new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        if (callback != null) {
                            callback.onClick();
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
                AppOpenAd.AppOpenAdLoadCallback loadCallback = new AppOpenAd.AppOpenAdLoadCallback() {
                    public void onAdLoaded(@NonNull AppOpenAd appOpenAd) {
                        super.onAdLoaded(appOpenAd);
                        if (app_data.get(0).isPreload_inter_ads()) {
                            Conts.log_debug(TAG, "Adx Open Ad loaded");
                            adx_appOpenAd_inter = appOpenAd;
                            isadx_appopen_Loaded = true;
                        } else {
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        appOpenAd.setFullScreenContentCallback(fullScreenContentCallback_adx);
                                        appOpenAd.show(act);
                                        Conts.log_debug(TAG, "Adx Appopen Show");
                                        isadx_appopen_Loaded = false;
                                    }
                                }.start();
                            } else {
                                appOpenAd.setFullScreenContentCallback(fullScreenContentCallback_adx);
                                appOpenAd.show(act);
                                Conts.log_debug(TAG, "Adx Appopen Show");
                                isadx_appopen_Loaded = false;
                            }
                        }
                    }

                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        Conts.log_debug(TAG, "Adx Open Failed " + loadAdError.getMessage());
                        if (app_data.get(0).isPreload_inter_ads()) {
                            appopen_Ads(act);
                        } else {
                            if (callback != null) {
                                callback.onClick();
                                callback = null;
                            }
                        }
                    }
                };
                AppOpenAd.load(act, placementId, adManagerAdRequest(), loadCallback);
            } else {
                if (app_data.get(0).isPreload_inter_ads()) {
                    appopen_Ads(act);
                } else {
                    if (callback != null) {
                        callback.onClick();
                        callback = null;
                    }
                }
            }
        }
    }

    // Applovin
    void Applovin_appopen_Ads(Activity act, String placementId) {
        if (app_data != null && app_data.size() > 0) {
            if (!placementId.equalsIgnoreCase("")) {
                if (isapplovin_appopen_Loaded) {
                    return;
                }
                final MaxAppOpenAd applovin_appOpenAd = new MaxAppOpenAd(placementId, act);
                applovin_appOpenAd.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd maxAd) {
                        if (app_data.get(0).isPreload_inter_ads()) {
                            Conts.log_debug(TAG, "Applovin appopen Loaded");
                            applovin_appopen = applovin_appOpenAd;
                            isapplovin_appopen_Loaded = true;
                        } else {
                            if (app_data.get(0).isApp_inter_dialog_show()) {
                                ad_inter_dialog.show();
                                new CountDownTimer(ad_dialog_time_in_second * 1000, 10) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {

                                    }

                                    @Override
                                    public void onFinish() {
                                        ad_inter_dialog.dismiss();
                                        if (applovin_appOpenAd.isReady()) {
                                            applovin_appOpenAd.showAd();
                                            Conts.log_debug(TAG, "Applovin Appopen loaded and Show");
                                            isapplovin_appopen_Loaded = false;
                                        }
                                    }
                                }.start();
                            } else {
                                if (applovin_appOpenAd.isReady()) {
                                    applovin_appOpenAd.showAd();
                                    Conts.log_debug(TAG, "Applovin Appopen loaded and Show");
                                    isapplovin_appopen_Loaded = false;
                                }
                            }
                        }
                    }

                    @Override
                    public void onAdDisplayed(MaxAd maxAd) {
                    }

                    @Override
                    public void onAdHidden(MaxAd maxAd) {
                        Conts.log_debug(TAG, "Applovin Appopen Close");
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }

                    @Override
                    public void onAdClicked(MaxAd maxAd) {
                    }

                    @Override
                    public void onAdLoadFailed(String s, MaxError maxError) {
                        Conts.log_debug(TAG, "Applovin Failed Open Ad " + maxError.getMessage());
                        if (app_data.get(0).isPreload_inter_ads()) {
                            appopen_Ads(act);
                        } else {
                            if (callback != null) {
                                callback.onClick();
                                callback = null;
                            }
                        }
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {
                    }
                });
                applovin_appOpenAd.loadAd();
            } else {
                if (app_data.get(0).isPreload_inter_ads()) {
                    appopen_Ads(act);
                } else {
                    if (callback != null) {
                        callback.onClick();
                        callback = null;
                    }
                }
            }
        }
    }

    // Local
    void Local_Appopen_Ads() {
        if (islocal_appopen_Loaded) {
            return;
        }
        islocal_appopen_Loaded = true;
    }

    // TODO: 8/10/2023  Splash Inter Ads
    void show_splash_inter(Activity act, OnClickListener splash_callback) {
        callback = splash_callback;
        if (app_data != null && app_data.size() > 0) {
            String admob_splash_inter = app_data.get(0).getAdmob_splash_interid();
            String adx_splash_inter = app_data.get(0).getAdx_splash_inter_id();
            String fb_splash_inter = app_data.get(0).getFb_splash_inter_id();
            String applovin_splash_inter = app_data.get(0).getApplovin_splash_interid();
            if (!admob_splash_inter.equalsIgnoreCase("")) {
                final AdRequest adRequest = new AdRequest.Builder().build();
                InterstitialAd.load(act, admob_splash_inter, adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        super.onAdLoaded(interstitialAd);
                        interstitialAd.show(act);
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Conts.log_debug(TAG, "Admob Inter Close");
                                if (callback != null) {
                                    callback.onClick();
                                    callback = null;
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                Conts.log_debug(TAG, "Admob Inter failed to show" + adError.getMessage());
                                if (callback != null) {
                                    callback.onClick();
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
                            callback.onClick();
                            callback = null;
                        }
                    }
                });
            } else if (!adx_splash_inter.equalsIgnoreCase("")) {
                @SuppressLint("VisibleForTests") final AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
                AdManagerInterstitialAd.load(act, adx_splash_inter, adRequest, new AdManagerInterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull AdManagerInterstitialAd adManagerInterstitialAd) {
                        super.onAdLoaded(adManagerInterstitialAd);
                        adManagerInterstitialAd.show(act);
                        adManagerInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                Conts.log_debug(TAG, "Adx Inter Close");
                                if (callback != null) {
                                    callback.onClick();
                                    callback = null;
                                }
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull com.google.android.gms.ads.AdError adError) {
                                Conts.log_debug(TAG, "Admob Inter failed to show" + adError.getMessage());
                                if (callback != null) {
                                    callback.onClick();
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
                            callback.onClick();
                            callback = null;
                        }
                    }
                });
            } else if (!fb_splash_inter.equalsIgnoreCase("")) {
                final com.facebook.ads.InterstitialAd FB_interstitial = new com.facebook.ads.InterstitialAd(act, fb_splash_inter);
                InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
                    @Override
                    public void onInterstitialDisplayed(Ad ad) {
                        Conts.log_debug(TAG, "Fb Inter Show");
                    }

                    @Override
                    public void onInterstitialDismissed(Ad ad) {
                        Conts.log_debug(TAG, "Fb Inter Close");
                        if (callback != null) {
                            callback.onClick();
                            callback = null;
                        }
                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        Conts.log_debug(TAG, "Fb Inter Failed " + adError);
                        if (callback != null) {
                            callback.onClick();
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
                final MaxInterstitialAd interstitialAdmax = new MaxInterstitialAd(applovin_splash_inter, act);
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
                            callback.onClick();
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
                            callback.onClick();
                            callback = null;
                        }
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    }
                });
                interstitialAdmax.loadAd();
            } else {
                new Handler().postDelayed(() -> myAdsAdder.show_local_InterAd(act, callback), 2500);
            }
        } else {
            if (callback != null) {
                callback.onClick();
                callback = null;
            }
        }
    }
}