package com.amos.koperasi.Model;

public class DetailCicilanModel {
    private int jmlCicilan;
    private String ke;

    public String getKe() {
        return ke;
    }
    public void setKe(String ke) {
        this.ke = ke;
    }
    public int getJmlCicilan(int position) {
        return jmlCicilan;
    }

    public void setJmlCicilan(Integer jmlCicilan) {
        this.jmlCicilan = jmlCicilan;
    }



}
