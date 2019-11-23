package com.amos.koperasi.Model;

public class IuranWajibModel {
    String iduser;

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

    public String getNominal() {
        return nominal;
    }

    public void setNominal(String nominal) {
        this.nominal = nominal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    String nama;
    String nominal;
    String tanggal;

    public IuranWajibModel(String iduser, String nama, String nominal, String tanggal) {
        this.iduser = iduser;
        this.nama = nama;
        this.nominal = nominal;
        this.tanggal = tanggal;
    }


}
