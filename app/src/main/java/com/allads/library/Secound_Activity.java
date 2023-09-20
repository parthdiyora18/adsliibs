package com.allads.library;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ads.data.AdsControl;
import com.ads.data.Conts;

public class Secound_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Conts.StatusBar(this);
        setContentView(R.layout.activity_secound);
        AdsControl.getInstance(Secound_Activity.this).show_banner_ad(findViewById(R.id.banner_ads));
        AdsControl.getInstance(Secound_Activity.this).show_native_ad(findViewById(R.id.native_ads));
        AdsControl.getInstance(Secound_Activity.this).show_small_native_ad(findViewById(R.id.small_native_ads));
        AdsControl.getInstance(Secound_Activity.this).show_small_native_banner_ad(findViewById(R.id.small_native_banner_ads));
        findViewById(R.id.btn).setOnClickListener(v ->
                AdsControl.getInstance(Secound_Activity.this).show_Interstitial(Secound_Activity.this, () -> {
            startActivity(new Intent(Secound_Activity.this, Thrd_Activity.class));
        }));
    }
}