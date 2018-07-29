package com.example.ta.ponpes.Config.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by edy akbar on 24/07/2018.
 */

public class SemuaprogramItem {
    @SerializedName("nama_program")
    private String nama_program;

    @SerializedName("id")
    private String id;


    public void setNama_program(String nama_program){
        this.nama_program = nama_program;
    }

    public String getNama_program(){
        return nama_program;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }



    @Override
    public String toString(){
        return
                "SemuaprogramItem{" +
                        "nama_program = '" + nama_program + '\'' +
                        ",id = '" + id + '\'' +

                        "}";
    }
}
