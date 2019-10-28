package com.amos.koperasi.Model;

public class NotifikasiDisetujui {
    private String nama, tanggal;
    private int jumlah,tenor,jatuh;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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

    public NotifikasiDisetujui(String nama, String tanggal, int jumlah, int tenor, int jatuh) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.tenor = tenor;
        this.jatuh = jatuh;
    }


}
