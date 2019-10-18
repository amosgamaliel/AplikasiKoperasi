package com.amos.koperasi.Model;

public class DalamCicilanModel {
    String id;
    String nama;
    String total;
    String sisaCicilan;
    String cicilanKe;

    public DalamCicilanModel(String nama, String total, String sisaCicilan, String id, String cicilanKe) {
        this.nama = nama;
        this.total = total;
        this.sisaCicilan = sisaCicilan;
        this.cicilanKe = cicilanKe;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSisaCicilan() {
        return sisaCicilan;
    }

    public void setSisaCicilan(String sisaCicilan) {
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
}
