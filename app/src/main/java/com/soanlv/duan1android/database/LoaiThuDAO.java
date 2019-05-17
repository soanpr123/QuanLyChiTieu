package com.soanlv.duan1android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.soanlv.duan1android.model.LoaiThuMD;

import java.util.ArrayList;
import java.util.List;

public class LoaiThuDAO {
    private SQLiteDatabase db;
    private Database database;
    public static final String TABLE_NAME = "LOAI_THU";
    public static final String SQL_LOAI_THU = "CREATE TABLE LOAI_THU (id INTEGER PRIMARY KEY AUTOINCREMENT, LoaiThu text);";
    public static final String TAG = "LoaiThuDao";

    public LoaiThuDAO(Context context) {
        database = new Database(context);
        db = database.getWritableDatabase();
    }

    public int insertLoaithu(LoaiThuMD thuMD) {
        ContentValues values = new ContentValues();
        values.put("LoaiThu", thuMD.getLoaiThu());
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public List<LoaiThuMD> getAllLoaiThu() {
        List<LoaiThuMD> loaiThuMDS = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {

            LoaiThuMD ee = new LoaiThuMD();
            ee.setId(c.getInt(0));
            ee.setLoaiThu(c.getString(1));
            loaiThuMDS.add(ee);
            Log.d("//=====", ee.toString());
            c.moveToNext();
        }
        c.close();
        return loaiThuMDS;
    }

    public int updateloaithu(LoaiThuMD thuMD) {
        ContentValues values = new ContentValues();
        values.put("LoaiThu", thuMD.getLoaiThu());

        return db.update(TABLE_NAME, values, "id=?", new String[]{thuMD.getId() + ""});
    }

    //deleete
    public int deleteLoaithuByID(int id) {
        int result = db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        if (result == 0)
            return -1;
        return 1;
    }
}
