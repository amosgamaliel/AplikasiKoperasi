package com.amos.koperasi.Model;

public class DalamCicilanModel {
    String id;
    String nama;
    String total;

    public String getCicilan() {
        return cicilan;
    }

    public void setCicilan(String cicilan) {
        this.cicilan = cicilan;
    }

    String cicilan;

    public String getTenor() {
        return tenor;
    }

    public void setTenor(String tenor) {
        this.tenor = tenor;
    }

    String sisaCicilan;
    String cicilanKe;
    String tenor;

    public DalamCicilanModel(String nama, String total, String sisaCicilan, String id,String tenor) {
        this.nama = nama;
        this.total = total;
        this.tenor=tenor;
        this.sisaCicilan = sisaCicilan;
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
