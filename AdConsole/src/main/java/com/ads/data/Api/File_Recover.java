package com.ads.data.Api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class File_Recover {
    @SerializedName("success")
    @Expose
    private Integer success;
    @SerializedName("data")
    @Expose
    private List<All_File_Data> data;

    public Integer getSuccess() {
        return success;
    }
    public void setSuccess(Integer success) {
        this.success = success;
    }

    public List<All_File_Data> getData() {
        return data;
    }

    public void setData(List<All_File_Data> data) {
        this.data = data;
    }
}