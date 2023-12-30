package com.ads.data.Local_ads;

public class Appdetail {
    String app_name;
    String app_banner;
    String app_icon;
    String app_body;
    String pakag;

    public Appdetail(String appName, String appBanner, String appIcon, String appBody, String pakage) {
        this.app_name = appName;
        this.app_banner = appBanner;
        this.app_icon = appIcon;
        this.app_body = appBody;
        this.pakag = pakage;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_banner() {
        return app_banner;
    }

    public void setApp_banner(String app_banner) {
        this.app_banner = app_banner;
    }

    public String getApp_icon() {
        return app_icon;
    }

    public void setApp_icon(String app_icon) {
        this.app_icon = app_icon;
    }

    public String getApp_body() {
        return app_body;
    }

    public void setApp_body(String app_body) {
        this.app_body = app_body;
    }

    public String getPakag() {
        return pakag;
    }

    public void setPakag(String pakag) {
        this.pakag = pakag;
    }
}