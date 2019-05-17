package com.soanlv.duan1android.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.soanlv.duan1android.model.KhoanChiMD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class KhoanChiDAO {
    private SQLiteDatabase db;
    private Database database;
    public static final String TABLE_NAME = "KhoanChi";
    public static final String SQL_KHOAN_CHI = "CREATE TABLE KhoanChi (id INTEGER PRIMARY KEY AUTOINCREMENT, TienChi text,LoaiChi text,NgayChi date);";
    public static final String TAG = "KhoanChiDAO";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public KhoanChiDAO(Context context) {
        database = new Database(context);
        db = database.getWritableDatabase();
    }

    //insert
    public int insertKhoanchi(KhoanChiMD chiMD) {
        ContentValues values = new ContentValues();
        values.put("TienChi", chiMD.getTienChi());
        values.put("LoaiChi", chiMD.getLoaichi());
        values.put("NgayChi", sdf.format(chiMD.getNgayChi()));
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
    public List<KhoanChiMD> getAllKhoanChi() throws ParseException {
        List<KhoanChiMD> khoanChiMDS = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            KhoanChiMD ee = new KhoanChiMD();
            ee.setId(c.getInt(0));
            ee.setTienChi(c.getInt(1));
            ee.setLoaichi(c.getString(2));
            ee.setNgayChi(sdf.parse(c.getString(3)));
            khoanChiMDS.add(ee);
            Log.d("//=====", ee.toString());
            c.moveToNext();
        }
        c.close();
        return khoanChiMDS;
    }

    //update
    public int updateKhoanCHi(KhoanChiMD khoanChiMD) {
        ContentValues values = new ContentValues();
        values.put("TienChi", khoanChiMD.getTienChi());
        values.put("LoaiChi", khoanChiMD.getLoaichi());
        values.put("NgayChi", sdf.format(khoanChiMD.getNgayChi()));
        return db.update(TABLE_NAME, values, "id=?", new String[]{khoanChiMD.getId() + ""});
    }

    //deleete
    public int deleteKhoanChiByID(int id) {
        int result = db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});
        if (result == 0)
            return -1;
        return 1;
    }
    public int Tongkhoanchi(){
        int khoanthungay=0;
        String Sql="SELECT SUM(TienChi) AS 'tongtien' FROM KhoanChi ";
        Cursor c = db.rawQuery(Sql, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            khoanthungay = c.getInt(0);
            c.moveToNext();
        }
        c.close();
        return khoanthungay;
    }
    public int Khoanchitheothang(String month,String yeah){
        int khoanthuthang=0;
        String Sql2="SELECT SUM(TienChi) AS 'tongtien' FROM KhoanChi where NgayChi LIKE'__-"+month+"-"+yeah+"' ";
        Cursor c = db.rawQuery(Sql2, null);
        c.moveToFirst();
        while (c.isAfterLast()==false){
            khoanthuthang = c.getInt(0);
            c.moveToNext();
        }
        c.close();
        return khoanthuthang;
    }
    public int getKhoanchiTheoNam(String yeah) {
        int doanhThu = 0;
        String sSQL = "SELECT SUM(TienChi) AS 'tongtien' FROM KhoanChi where NgayChi LIKE'__-__-" + yeah + "' ";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getInt(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }
    public int getKhoanchiTheoNgay(String Day,String Month,String Yeah) {
        int doanhThu = 0;
        String sSQL = "SELECT SUM(TienChi) AS 'tongtien' FROM KhoanChi where NgayChi LIKE'"+Day+"-"+Month+"-"+Yeah+"'";
        Cursor c = db.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            doanhThu = c.getInt(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }
}
