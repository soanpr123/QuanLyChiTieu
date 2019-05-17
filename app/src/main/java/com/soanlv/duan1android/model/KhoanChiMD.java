package com.soanlv.duan1android.model;

import java.util.Date;

public class KhoanChiMD {
    private int id;
    private String Loaichi;
    private int TienChi;
    private Date NgayChi;

    public KhoanChiMD(int tienChi, String loaichi, Date ngayChi) {
        Loaichi = loaichi;
        TienChi = tienChi;
        NgayChi = ngayChi;
    }

    public KhoanChiMD(int id, int tienChi, String loaichi, Date ngayChi) {
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

    public int getTienChi() {
        return TienChi;
    }

    public void setTienChi(int tienChi) {
        TienChi = tienChi;
    }

    public Date getNgayChi() {
        return NgayChi;
    }

    public String setNgayChi(Date ngayChi) {
        NgayChi = ngayChi;
        return null;
    }
}
