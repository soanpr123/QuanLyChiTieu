package com.soanlv.duan1android.model;

import java.util.Date;

public class KhoanChiMD {
    private int id;
    private String TienChi,Loaichi;
    private Date NgayChi;

    public KhoanChiMD(String tienChi, String loaichi, Date ngayChi) {
        TienChi = tienChi;
        Loaichi = loaichi;
        NgayChi = ngayChi;
    }

    public KhoanChiMD(int id, String tienChi, String loaichi, Date ngayChi) {
        this.id = id;
        TienChi = tienChi;
        Loaichi = loaichi;
        NgayChi = ngayChi;
    }

    public KhoanChiMD() {
    }

    public String getLoaichi() {
        return Loaichi;
    }

    public void setLoaichi(String loaichi) {
        Loaichi = loaichi;
    }

    public int getId() {
        return id;
    }

    public int setId(int id) {
        this.id = id;
        return id;
    }

    public String getTienChi() {
        return TienChi;
    }

    public String setTienChi(String tienChi) {
        TienChi = tienChi;
        return tienChi;
    }

    public Date getNgayChi() {
        return NgayChi;
    }

    public String setNgayChi(Date ngayChi) {
        NgayChi = ngayChi;
        return null;
    }
}
