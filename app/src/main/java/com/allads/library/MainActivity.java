package com.allads.library;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.ads.data.AdsControl;
import com.ads.data.Conts;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Conts.StatusBar(this);
        setContentView(R.layout.activity_main);
        AdsControl.getInstance(MainActivity.this).file(MainActivity.this, getResources().getString(R.string.file_key),
                getPackageName(), getResources().getString(R.string.service), () -> {
                    Intent intent = new Intent(MainActivity.this, Secound_Activity.class);
                    startActivity(intent);
                    finish();
                });
    }
}