package com.example.ta.ponpes.Config.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by edy akbar on 15/07/2018.
 */

public class ValueEvent {
    @SerializedName("status")
    @Expose
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("result")
    @Expose
    private ResultAllEvent[] result;

    public ResultAllEvent[] getResult() {
        return result;
    }
}
