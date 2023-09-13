package com.ads.data.VPN_Block;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

public class Vpn_Block_Detector {
    WeakReference<Context> context;

    public Vpn_Block_Detector(Context context) {
        this.context = new WeakReference<>(context);
    }

    public void detectvpnBlockers(vpn_Block.VPNBlockerCallback callback) {
        new Vpn_Block_Detector.DetectTask(callback).execute();
    }

    public boolean detectAdBlockers(vpn_Block.Info info) {
        if (info != null) {
            info.method = vpn_Block.Method.NONE;
            info.details1 = "";
            info.details2 = "";
        }
        return detectAppNames(info);
    }

    private boolean detectAppNames(vpn_Block.Info info) {
        if (context != null) {
            for (final String app : vpn_Block.BLOCKERS_APP_NAMES) {
                if (isAppInstalled(app)) {
                    if (info != null) {
                        info.method = vpn_Block.Method.BY_APP_NAME;
                        info.details1 = app;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isAppInstalled(String packageName) {
        try {
            final Context c = context.get();
            return (c != null) && (c.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES) != null);
        } catch (Exception e) {
            return false;
        }
    }

    private class DetectTask extends AsyncTask<Void, Void, Boolean> {
         WeakReference<vpn_Block.VPNBlockerCallback> callback;
        private vpn_Block.Info info;

        DetectTask(vpn_Block.VPNBlockerCallback c) {
            callback = new WeakReference<>(c);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                info = new vpn_Block.Info();
                return detectAdBlockers(info);
            } catch (Throwable t) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean r) {
            final vpn_Block.VPNBlockerCallback c = callback.get();
            if (c != null && r != null)
                c.onResult(r, info);
        }
    }
}