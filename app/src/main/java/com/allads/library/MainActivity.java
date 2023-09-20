package com.allads.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.ads.data.AdsControl;
import com.ads.data.Conts;

public class MainActivity extends AppCompatActivity {
    public static final String ACTION_CLOSE = "ACTION_CLOSE";
    FirstReceiver firstReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Conts.StatusBar(this);
        setContentView(R.layout.activity_main);
        // Panal Call
        /*AdsControl.getInstance(this).init_panal(MainActivity.this,
                getResources().getString(R.string.panal_key), getPackageName(), () -> {
                    try {
                        IntentFilter filter = new IntentFilter(ACTION_CLOSE);
                        firstReceiver = new FirstReceiver();
                        registerReceiver(firstReceiver, filter);
                        String INSTALL_PREF = "install_pref_vd";
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                        if (!sharedPreferences.getBoolean(INSTALL_PREF, false)) {
                            AdsControl.getInstance(MainActivity.this).installcounter(getResources().getString(R.string.panal_key), getPackageName());
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean(INSTALL_PREF, true);
                            editor.apply();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(MainActivity.this, Secound_Activity.class);
                    startActivity(intent);
                    finish();
                });*/

        // FileZilla Call
        AdsControl.getInstance(MainActivity.this).init_file(MainActivity.this, getResources().getString(R.string.file_key),
                getPackageName(), getResources().getString(R.string.service), () -> {
                    Intent intent = new Intent(MainActivity.this, Secound_Activity.class);
                    startActivity(intent);
                    finish();
                });
    }

    class FirstReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Conts.log_debug("FirstReceiver", "FirstReceiver");
            if (intent.getAction().equals(ACTION_CLOSE)) {
                finish();
            }
        }
    }
}