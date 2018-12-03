package com.soanlv.duan1android.model;

public class LoaiThuMD {
    private int id;
    private String LoaiThu;

    public LoaiThuMD() {
    }

    public LoaiThuMD(int id, String loaiChi) {
        this.id = id;
        LoaiThu = loaiChi;
    }

    public LoaiThuMD(String loaiChi) {
        LoaiThu = loaiChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoaiThu() {
        return LoaiThu;
    }

    public void setLoaiThu(String loaiThu) {
        LoaiThu = loaiThu;
    }
}
