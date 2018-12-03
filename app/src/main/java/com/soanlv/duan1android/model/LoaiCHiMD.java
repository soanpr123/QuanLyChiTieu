package com.soanlv.duan1android.model;

public class LoaiCHiMD  {
    private int id;
    private String LoaiChi;

    public LoaiCHiMD() {
    }

    public LoaiCHiMD(int id, String loaiChi) {
        this.id = id;
        LoaiChi = loaiChi;
    }

    public LoaiCHiMD(String loaiChi) {
        LoaiChi = loaiChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoaiChi() {
        return LoaiChi;
    }

    public void setLoaiChi(String loaiChi) {
        LoaiChi = loaiChi;
    }
}
