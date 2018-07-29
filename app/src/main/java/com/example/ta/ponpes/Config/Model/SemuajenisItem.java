package com.example.ta.ponpes.Config.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by edy akbar on 24/07/2018.
 */

public class SemuajenisItem {
    @SerializedName("nama")
    private String nama;

    @SerializedName("id")
    private String id;

    @SerializedName("keterangan")
    private String keterangan;


    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setKeterangan(String keterangan){
        this.keterangan = keterangan;
    }

    public String getKeterangan(){
        return keterangan;
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
                "SemuajenisItem{" +
                        "nama = '" + nama + '\'' +
                        ",id = '" + id + '\'' +
                        ",keterangan = '" + keterangan + '\'' +

                        "}";
    }
}
