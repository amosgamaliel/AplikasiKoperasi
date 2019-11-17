package com.amos.koperasi.Model;

public class ActivityModel {
    String idpinjaman,iduser,nama,jumlah,tanggal,tipe;

    public String getIdpinjaman() {
        return idpinjaman;
    }

    public void setIdpinjaman(String idpinjaman) {
        this.idpinjaman = idpinjaman;
    }

    public String getIduser() {
        return iduser;
    }

    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public ActivityModel(String idpinjaman, String iduser, String nama, String jumlah, String tanggal, String tipe) {
        this.idpinjaman = idpinjaman;
        this.iduser = iduser;
        this.nama = nama;
        this.jumlah = jumlah;
        this.tanggal = tanggal;
        this.tipe = tipe;
    }
}
