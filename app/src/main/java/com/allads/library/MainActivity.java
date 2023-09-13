package com.allads.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

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
        AdsControl.getInstance(this).init(MainActivity.this, getPackageName(), () -> {
            try {
                IntentFilter filter = new IntentFilter(ACTION_CLOSE);
                firstReceiver = new FirstReceiver();
                registerReceiver(firstReceiver, filter);
                String INSTALL_PREF = "install_pref_vd";
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                if (!sharedPreferences.getBoolean(INSTALL_PREF, false)) {
                    AdsControl.getInstance(MainActivity.this).installcounter(getPackageName());
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
        });
    }

    class FirstReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("FirstReceiver", "FirstReceiver");
            if (intent.getAction().equals(ACTION_CLOSE)) {
                finish();
            }
        }
    }
}