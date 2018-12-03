package com.soanlv.duan1android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.soanlv.duan1android.model.KhoanThuMD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class KhoanThuDAO {
    private SQLiteDatabase db;
    private Database database;
    public static final String TABLE_NAME = "KHOANTHU";
    public static final String SQL_KHOAN_THU = "CREATE TABLE KHOANTHU (id INTEGER PRIMARY KEY AUTOINCREMENT, TienThu text,LoaiThu text,NgayThu date);";
    public static final String TAG = "KhoanThuDao";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public KhoanThuDAO(Context context) {
        database = new Database(context);
        db = database.getWritableDatabase();
    }

    //insert
    public int insertKhoanThu(KhoanThuMD thuMD) {
        ContentValues values = new ContentValues();
        values.put("TienThu", thuMD.getTienThu());
        values.put("LoaiThu", thuMD.getLoaiThu());
        values.put("NgayThu", sdf.format(thuMD.getNgayThu()));
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    //getALl
    public List<KhoanThuMD> getAllKhoanThu() throws ParseException {
        List<KhoanThuMD> khoanThuMDS = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            KhoanThuMD ee = new KhoanThuMD();
            ee.setId(c.getInt(0));
            ee.setTienThu(c.getString(1));
            ee.setLoaiThu(c.getString(2));
            ee.setNgayThu(sdf.parse(c.getString(3)));
            khoanThuMDS.add(ee);
            Log.d("//=====", ee.toString());
            c.moveToNext();
        }
        c.close();
        return khoanThuMDS;
    }

    //update
    public int updateKhoanThu(KhoanThuMD khoanThuMD) {
        ContentValues values = new ContentValues();
        values.put("TienThu", khoanThuMD.getTienThu());
        values.put("LoaiThu", khoanThuMD.getLoaiThu());
        values.put("NgayThu", sdf.format(khoanThuMD.getNgayThu()));
        return db.update(TABLE_NAME, values, "id=?", new String[]{khoanThuMD.getId() + ""});
    }

    //deleete
    public int deleteKhoanTHuByID(int id) {
        int result = db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        if (result == 0)
            return -1;
        return 1;
    }
   
}
