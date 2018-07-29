package com.example.ta.ponpes.Config.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by edy akbar on 24/07/2018.
 */

public class ResponseJenis {
    @SerializedName("result")
    private List<SemuajenisItem> result;


    @SerializedName("status")
    private String status;

    public void setResult(List<SemuajenisItem> result){
        this.result = result;
    }

    public List<SemuajenisItem> getResult(){
        return result;
    }


    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    @Override
    public String toString(){
        return
                "ResponseJenis{" +
                        "result = '" + result + '\'' +

                        ",status = '" + status + '\'' +
                        "}";
    }
}
