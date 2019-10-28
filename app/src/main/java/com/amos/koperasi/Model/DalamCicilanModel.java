package com.amos.koperasi.Model;

public class DalamCicilanModel {
    String id;
    String iduser;
    String nama;
    String total;
    String sisaCicilan;
    String cicilanKe;
    String tenor;
    String cicilan;

    public DalamCicilanModel(String nama, String total, String sisaCicilan, String id,String tenor,String iduser) {
        this.nama = nama;
        this.total = total;
        this.tenor=tenor;
        this.id= id;
        this.sisaCicilan = sisaCicilan;
        this.iduser = iduser;
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
    public String getIduser() {
        return iduser;
    }
    public void setIduser(String iduser) {
        this.iduser = iduser;
    }

}
