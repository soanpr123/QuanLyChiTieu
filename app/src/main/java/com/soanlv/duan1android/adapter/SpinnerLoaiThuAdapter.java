package com.soanlv.duan1android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.soanlv.duan1android.R;
import com.soanlv.duan1android.database.KhoanChiDAO;
import com.soanlv.duan1android.model.KhoanThuMD;
import com.soanlv.duan1android.model.LoaiThuMD;

import java.util.List;

public class SpinnerLoaiThuAdapter extends BaseAdapter {
    Context context;
    List<LoaiThuMD> loaiThuMDS;
    LayoutInflater inflter;
    private KhoanThuMD khoanThuMD;
    KhoanChiDAO khoanChiDAO;

    public SpinnerLoaiThuAdapter(Context context, List<LoaiThuMD> loaiCHiMDS) {
        this.context = context;
        this.loaiThuMDS = loaiCHiMDS;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return loaiThuMDS.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiThuMDS.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView name;

    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.item_spiner_loai_chi, null);
        TextView names = (TextView) view.findViewById(R.id.tv_sploaichi);
        LoaiThuMD loaiThuMD = loaiThuMDS.get(i);
        names.setText(loaiThuMD.getLoaiThu());
        return view;
    }
}
