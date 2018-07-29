package com.example.ta.ponpes.Config.Model;

/**
 * Created by edy akbar on 15/07/2018.
 */

public class ResultAllEvent {
    String id;
    String id_pondok;
    String nama_kegiatan;
    String deskripsi;
    String foto;
    String tgl_kegiatan;

    public String getNama_ponpes() {
        return nama_ponpes;
    }

    public void setNama_ponpes(String nama_ponpes) {
        this.nama_ponpes = nama_ponpes;
    }

    String nama_ponpes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_pondok() {
        return id_pondok;
    }

    public void setId_pondok(String id_pondok) {
        this.id_pondok = id_pondok;
    }

    public String getNama_kegiatan() {
        return nama_kegiatan;
    }

    public void setNama_kegiatan(String nama_kegiatan) {
        this.nama_kegiatan = nama_kegiatan;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTgl_kegiatan() {
        return tgl_kegiatan;
    }

    public void setTgl_kegiatan(String tgl_kegiatan) {
        this.tgl_kegiatan = tgl_kegiatan;
    }
}
