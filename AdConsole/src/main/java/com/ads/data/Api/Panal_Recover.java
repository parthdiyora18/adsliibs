package com.ads.data.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Panal_Recover {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private All_File_Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public All_File_Data getData() {
        return data;
    }

    public void setData(All_File_Data data) {
        this.data = data;
    }
}