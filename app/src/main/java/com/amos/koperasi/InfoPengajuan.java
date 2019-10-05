package com.amos.koperasi;

public class InfoPengajuan {

    private String nama;
    private int jumlah,tenor,jatuh;

    public InfoPengajuan(String nama, int jumlah, int tenor, int jatuh) {
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
}
