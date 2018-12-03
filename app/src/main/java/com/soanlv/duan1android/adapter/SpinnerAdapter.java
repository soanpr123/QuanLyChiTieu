package com.soanlv.duan1android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soanlv.duan1android.R;
import com.soanlv.duan1android.database.KhoanChiDAO;
import com.soanlv.duan1android.model.KhoanChiMD;
import com.soanlv.duan1android.model.LoaiCHiMD;

import java.text.ParseException;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    Context context;
    List<LoaiCHiMD> loaiCHiMDS;
    List<KhoanChiMD> khoanChiMDS;
    LayoutInflater inflter;
    private KhoanChiMD khoanChiMD;
    KhoanChiDAO khoanChiDAO;
    public SpinnerAdapter(Context context, List<LoaiCHiMD> loaiCHiMDS) {
        this.context = context;
        this.loaiCHiMDS = loaiCHiMDS;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return loaiCHiMDS.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiCHiMDS.get(position);
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
        LoaiCHiMD loaiCHiMD = loaiCHiMDS.get(i);
        names.setText(loaiCHiMD.getLoaiChi());
        return view;
    }
}
