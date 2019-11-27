package com.amos.koperasi.Model;

public class DalamCicilanModel {
    String tanggal;
    String id;
    String iduser;
    String nama;
    int jumlahpinjaman;
    int sisaCicilan;
    String cicilanKe;
    String tenor;
    String cicilan;

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    String jumlah,jatuhTempo,idCicilan;

    public DalamCicilanModel(String jatuhTempo, String idCicilan, String tanggal, String nama, int jumlahpinjaman, int sisaCicilan, String id, String tenor, String iduser, String jumlah) {
        this.jatuhTempo = jatuhTempo;
        this.idCicilan = idCicilan;
        this.tanggal = tanggal;
        this.nama = nama;
        this.jumlahpinjaman = jumlahpinjaman;
        this.tenor=tenor;
        this.id= id;
        this.sisaCicilan = sisaCicilan;
        this.iduser = iduser;
        this.jumlah = jumlah;
    }
    public String getCicilan() {
        return cicilan;
    }
    public void setCicilan(String cicilan) {
        this.cicilan = cicilan;
    }
    public String getTenor() {
        return tenor;
    }
    public void setTenor(String tenor) {
        this.tenor = tenor;
    }
    public String getNama() {
        return nama;
    }
    public void setNama(String nama) {
        this.nama = nama;
    }
    public int getJumlahpinjaman() {
        return jumlahpinjaman;
    }
    public void setJumlahpinjaman(int total) {
        this.jumlahpinjaman = total;
    }
    public int getSisaCicilan() {
        return sisaCicilan;
    }
    public void setSisaCicilan(int sisaCicilan) {
        this.sisaCicilan = sisaCicilan;
    }
    public String getCicilanKe() {
        return cicilanKe;
    }
    public void setCicilanKe(String cicilanKe) {
        this.cicilanKe = cicilanKe;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIduser() {
        return iduser;
    }
    public void setIduser(String iduser) {
        this.iduser = iduser;
    }
    public String getJatuhTempo() {
        return jatuhTempo;
    }

    public void setJatuhTempo(String jatuhTempo) {
        this.jatuhTempo = jatuhTempo;
    }

    public String getIdCicilan() {
        return idCicilan;
    }

    public void setIdCicilan(String idCicilan) {
        this.idCicilan = idCicilan;
    }
    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
