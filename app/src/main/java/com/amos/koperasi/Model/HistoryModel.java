package com.amos.koperasi.Model;

public class HistoryModel {
    String nama;
    String idCicilan;
    String idPinjaman;
    String idUser;
    String tanggalMulai;
    String tanggalSelesai;

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

    public HistoryModel(String nama, String idCicilan, String idPinjaman, String idUser, String tanggalMulai, String tanggalSelesai) {
        this.nama = nama;
        this.idCicilan = idCicilan;
        this.idPinjaman = idPinjaman;
        this.idUser = idUser;
        this.tanggalMulai = tanggalMulai;
        this.tanggalSelesai = tanggalSelesai;
    }

}
