package com.allads.library;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ads.data.AdsControl;
import com.ads.data.Conts;

public class Thrd_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Conts.StatusBar(this);
        setContentView(R.layout.activity_thrd);
        AdsControl.getInstance(Thrd_Activity.this).show_banner_ad(findViewById(R.id.banner_ads));
        AdsControl.getInstance(Thrd_Activity.this).show_native_ad(findViewById(R.id.native_ads));
        AdsControl.getInstance(Thrd_Activity.this).show_small_native_ad(findViewById(R.id.small_native_ads));
        AdsControl.getInstance(Thrd_Activity.this).show_small_native_banner_ad(findViewById(R.id.small_native_banner_ads));
        findViewById(R.id.btn).setOnClickListener(v -> AdsControl.getInstance(Thrd_Activity.this).show_Interstitial(() -> {
            Toast.makeText(this, "Inter Show", Toast.LENGTH_SHORT).show();
        }));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Thrd_Activity.this, Secound_Activity.class));
    }
}