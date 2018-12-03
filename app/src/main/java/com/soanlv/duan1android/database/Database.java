package com.soanlv.duan1android.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SQLiteOpenHelper";
    public static final int VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KhoanChiDAO.SQL_KHOAN_CHI);
        db.execSQL(LoaiChiDAO.SQL_LOAI_CHI);
        db.execSQL(KhoanThuDAO.SQL_KHOAN_THU);
        db.execSQL(LoaiThuDAO.SQL_LOAI_THU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + KhoanChiDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + LoaiChiDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + KhoanThuDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + LoaiThuDAO.TABLE_NAME);
        onCreate(db);
    }
}
