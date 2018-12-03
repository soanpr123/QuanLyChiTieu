package com.soanlv.duan1android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.soanlv.duan1android.model.KhoanChiMD;
import com.soanlv.duan1android.model.LoaiCHiMD;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class LoaiChiDAO {
    private SQLiteDatabase db;
    private Database database;
    public static final String TABLE_NAME = "LOAI_CHI";
    public static final String SQL_LOAI_CHI = "CREATE TABLE LOAI_CHI (id INTEGER PRIMARY KEY AUTOINCREMENT, LoaiChi text);";
    public static final String TAG = "LoaiChiDAO";
    public LoaiChiDAO(Context context){
        database = new Database(context);
        db = database.getWritableDatabase();
    }
    public int insertLoaichi(LoaiCHiMD chiMD) {
        ContentValues values = new ContentValues();
        values.put("LoaiChi", chiMD.getLoaiChi());
        try {
            if (db.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }
    public List<LoaiCHiMD> getAllLoaiChi() {
        List<LoaiCHiMD> loaiCHiMDS = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {

            LoaiCHiMD ee = new LoaiCHiMD();
            ee.setId(c.getInt(0));
            ee.setLoaiChi(c.getString(1));
            loaiCHiMDS.add(ee);
            Log.d("//=====", ee.toString());
            c.moveToNext();
        }
        c.close();
        return loaiCHiMDS;
    }
    public int updateLoaiCHi(LoaiCHiMD loaiCHiMD) {
        ContentValues values = new ContentValues();
        values.put("TienChi", loaiCHiMD.getLoaiChi());

        return db.update(TABLE_NAME,values,"id=?",new String[]{loaiCHiMD.getId()+""});
    }

    //deleete
    public int deleteLoaiChiByID(int id) {
        int result = db.delete(TABLE_NAME,"id=?",new String[]{String.valueOf(id)});
        if (result == 0)
            return -1;
        return 1;
    }
}
