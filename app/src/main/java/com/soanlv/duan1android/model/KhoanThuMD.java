package com.soanlv.duan1android.model;

import java.util.Date;

public class KhoanThuMD {
    private int id;
    private String TienThu,LoaiThu;
    private Date NgayThu;

    public KhoanThuMD(String tienThu, String loaiThu, Date ngayChi) {
        TienThu = tienThu;
        LoaiThu = loaiThu;
        NgayThu = ngayChi;
    }

    public KhoanThuMD(int id, String tienThu, String loaiThu, Date ngayChi) {
        this.id = id;
        TienThu = tienThu;
        LoaiThu = loaiThu;
        NgayThu = ngayChi;
    }

    public KhoanThuMD() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTienThu() {
        return TienThu;
    }

    public void setTienThu(String tienThu) {
        TienThu = tienThu;
    }

    public String getLoaiThu() {
        return LoaiThu;
    }

    public void setLoaiThu(String loaiThu) {
        LoaiThu = loaiThu;
    }

    public Date getNgayThu() {
        return NgayThu;
    }

    public void setNgayThu(Date ngayThu) {
        NgayThu = ngayThu;
    }
}
