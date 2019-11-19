package com.amos.koperasi.Model;

public class NamaPenyimpanModel {
    String id;
    String nama;

    public String getJumlahSimpanan() {
        return jumlahSimpanan;
    }

    public void setJumlahSimpanan(String jumlahSimpanan) {
        this.jumlahSimpanan = jumlahSimpanan;
    }

    String jumlahSimpanan;

    public NamaPenyimpanModel(String id, String nama,String jumlahSimpanan) {
        this.id = id;
        this.nama = nama;
        this.jumlahSimpanan = jumlahSimpanan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
