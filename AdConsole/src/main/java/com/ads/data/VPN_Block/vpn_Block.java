package com.ads.data.VPN_Block;

public class vpn_Block {
    public interface VPNBlockerCallback {
        void onResult(boolean vpnBlockerFound, vpn_Block.Info info);
    }

    public enum Method {
        NONE, BY_APP_NAME
    }

    public static final String[] BLOCKERS_APP_NAMES = {
            "com.minhui.networkcapture",
            "com.minhui.networkcapture.pro",
            "jp.co.taosoftware.android.packetcapture",
            "app.greyshirts.sslcapture",
            "com.emanuelef.remote_capture",
            "eu.faircode.netguard",
            "com.fulldive.extension.tordnscrypt",
            "tech.httptoolkit.android.v1"
    };

    public static class Info {
        public vpn_Block.Method method;
        public String details1;
        public String details2;
    }
}