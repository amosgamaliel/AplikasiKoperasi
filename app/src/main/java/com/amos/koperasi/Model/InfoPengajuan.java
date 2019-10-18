package com.amos.koperasi.Model;

public class InfoPengajuan {

    private String nama, tanggal;
    private int id,jumlah,tenor,jatuh;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public InfoPengajuan(int id, String nama, int jumlah, int tenor, int jatuh) {
        this.id= id;
        this.nama = nama;
        this.jumlah = jumlah;
        this.tenor = tenor;
        this.jatuh = jatuh;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getTenor() {
        return tenor;
    }

    public void setTenor(int tenor) {
        this.tenor = tenor;
    }

    public int getJatuh() {
        return jatuh;
    }

    public void setJatuh(int jatuh) {
        this.jatuh = jatuh;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
