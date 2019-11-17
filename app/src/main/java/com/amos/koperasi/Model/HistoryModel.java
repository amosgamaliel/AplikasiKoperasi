package com.amos.koperasi.Model;

public class HistoryModel {
    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    String tenor;
    String nama;
    String idCicilan;
    String idPinjaman;
    String idUser;
    String tanggalMulai;
    String tanggalSelesai;
    String tanggalBayar,jumlah;

    public String getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(String tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }




    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getIdCicilan() {
        return idCicilan;
    }

    public void setIdCicilan(String idCicilan) {
        this.idCicilan = idCicilan;
    }

    public String getIdPinjaman() {
        return idPinjaman;
    }

    public void setIdPinjaman(String idPinjaman) {
        this.idPinjaman = idPinjaman;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getTanggalMulai() {
        return tanggalMulai;
    }

    public void setTanggalMulai(String tanggalMulai) {
        this.tanggalMulai = tanggalMulai;
    }

    public String getTanggalSelesai() {
        return tanggalSelesai;
    }

    public void setTanggalSelesai(String tanggalSelesai) {
        this.tanggalSelesai = tanggalSelesai;
    }

    public HistoryModel(String nama, String idCicilan, String idPinjaman, String idUser, String tanggalMulai, String tanggalSelesai,String tenor,String jumlah) {
        this.nama = nama;
        this.jumlah = jumlah;
        this.tenor = tenor;
        this.idCicilan = idCicilan;
        this.idPinjaman = idPinjaman;
        this.idUser = idUser;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
    }
    public HistoryModel(String tanggalBayar,String jumlah) {
        this.tanggalBayar = tanggalBayar;
        this.jumlah = jumlah;
    }

}
