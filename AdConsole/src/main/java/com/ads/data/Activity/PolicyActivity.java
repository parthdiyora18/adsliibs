package com.ads.data.Activity;

import static com.ads.data.AdsControl.app_data;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ads.data.Conts;
import com.ads.data.R;

public class PolicyActivity extends AppCompatActivity {
    WebView webPrivacyPolicy;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Conts.StatusBar(this);
        setContentView(R.layout.activity_privacypolicy);
        webPrivacyPolicy = findViewById(R.id.wvPrivacyPolicy);
        WebSettings webSettings = webPrivacyPolicy.getSettings();
        webSettings.setJavaScriptEnabled(true);// enable javascript
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webPrivacyPolicy.setInitialScale(1);
        webPrivacyPolicy.getSettings().setLoadWithOverviewMode(true);
        webPrivacyPolicy.getSettings().setUseWideViewPort(true);
        webPrivacyPolicy.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webPrivacyPolicy.setScrollbarFadingEnabled(true);
        webPrivacyPolicy.getSettings().setBuiltInZoomControls(true);
        webPrivacyPolicy.getSettings().setDisplayZoomControls(false);
        webPrivacyPolicy.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(PolicyActivity.this, description, Toast.LENGTH_SHORT).show();
            }
        });
        if (app_data != null && app_data.size() > 0) {
            webPrivacyPolicy.loadUrl(app_data.get(0).getPrivacyUrl());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}