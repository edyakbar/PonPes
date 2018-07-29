package com.example.ta.ponpes.Config.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by edy akbar on 24/07/2018.
 */

public class ResponseProgram {
    @SerializedName("result")
    private List<SemuaprogramItem> result;


    @SerializedName("status")
    private String status;

    public void setResult(List<SemuaprogramItem> result){
        this.result = result;
    }

    public List<SemuaprogramItem> getResult(){
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
                "ResponseProgram{" +
                        "result = '" + result + '\'' +

                        ",status = '" + status + '\'' +
                        "}";
    }

}
