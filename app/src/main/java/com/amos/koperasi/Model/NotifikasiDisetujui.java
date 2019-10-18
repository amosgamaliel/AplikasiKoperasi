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

    public NotifikasiDisetujui(String tanggal, int jumlah) {
        this.nama = nama;
        this.tanggal = tanggal;
        this.jumlah = jumlah;
        this.tenor = tenor;
        this.jatuh = jatuh;
    }

}
