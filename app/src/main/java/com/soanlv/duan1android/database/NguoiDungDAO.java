package com.soanlv.duan1android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.soanlv.duan1android.model.NguoiDung;

import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {
    private SQLiteDatabase db;
    private Database database;
    public static final String TABLE_NAME = "NguoiDung";
    public static final String SQL_NGUOI_DUNG = "CREATE TABLE NguoiDung (id INTEGER PRIMARY KEY AUTOINCREMENT,Name text Username text,Password text);";
    public static final String TAG = "KhoanChiDAO";

    public NguoiDungDAO(Context context) {
        database = new Database(context);
        db = database.getWritableDatabase();
    }

    //insert
    public int insertNguoiDung(NguoiDung nguoiDung) {
        ContentValues values = new ContentValues();
        values.put("Name", nguoiDung.getName());
        values.put("Username", nguoiDung.getUserName());
        values.put("Password", nguoiDung.getPass());
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
    public List<NguoiDung> getALlData() {
        List<NguoiDung> nguoiDungs = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            NguoiDung ee = new NguoiDung();
            ee.setID(c.getInt(0));
            ee.setName(c.getString(1));
            ee.setUserName(c.getString(2));
            ee.setPass(c.getString(3));
            nguoiDungs.add(ee);
            Log.d("//=====", ee.toString());
            c.moveToNext();
        }
        c.close();
        return nguoiDungs;
    }

    //update
    public int update(NguoiDung nguoiDung) {
        ContentValues values = new ContentValues();
        values.put("Name", nguoiDung.getName());
        values.put("Username", nguoiDung.getUserName());
        values.put("Password", nguoiDung.getPass());
        return db.update(TABLE_NAME, values, "id=?", new String[]{nguoiDung.getID() + ""});
    }

    //deleete
    public int delete(int id) {
        int result = db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        if (result == 0)
            return -1;
        return 1;
    }
}
