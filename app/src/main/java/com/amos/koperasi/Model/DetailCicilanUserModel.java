package com.amos.koperasi.Model;

public class DetailCicilanUserModel {
    private int jmlCicilan;
    private String ke,status;

    public DetailCicilanUserModel(int jmlCicilan, String ke, String status) {
        this.jmlCicilan = jmlCicilan;
        this.ke = ke;
        this.status = status;
    }
    public DetailCicilanUserModel(int jmlCicilan, String ke) {
        this.jmlCicilan = jmlCicilan;
        this.ke = ke;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



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
