package com.example.ta.ponpes.Config.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by edy akbar on 26/06/2018.
 */

public class Value {
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
    private ResultAll[] result;

    public ResultAll[] getResult() {
        return result;
    }
}
