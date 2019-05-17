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
import com.soanlv.duan1android.database.LoaiChiDAO;
import com.soanlv.duan1android.model.KhoanChiMD;
import com.soanlv.duan1android.model.KhoanThuMD;
import com.soanlv.duan1android.model.LoaiCHiMD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class LoaiChiAdapter extends BaseAdapter {
    public Context context;
    public LayoutInflater inflater;
    List<LoaiCHiMD> arrLoaichi;
    KhoanChiDAO khoanChiDAO;
    LoaiChiDAO loaiChiDAO;
    private KhoanChiMD khoanChiMD;
    private LoaiCHiMD loaiCHiMD;

    public LoaiChiAdapter(List<LoaiCHiMD> arrLoaichi, Context context) {
        this.arrLoaichi = arrLoaichi;
        this.context = context;
        this.inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        loaiChiDAO = new LoaiChiDAO(context);
    }
    @Override
    public int getCount() {
        return arrLoaichi.size();
    }

    @Override
    public Object getItem(int position) {
        return arrLoaichi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder {
        TextView tv_id,tvloaichi;
        ImageView imgDelete;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_loai_chi, null);
            holder.tv_id = (TextView)
                    convertView.findViewById(R.id.tv_ID);
            holder.tvloaichi = (TextView) convertView.findViewById(R.id.tv_loaichi);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.iv_Delete);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        LoaiCHiMD _entry = (LoaiCHiMD) arrLoaichi.get(position);
        holder.tv_id.setText(_entry.getId() + "");
        holder.tvloaichi.setText(_entry.getLoaiChi());
holder.imgDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        loaiCHiMD = new LoaiCHiMD();
        loaiCHiMD = arrLoaichi.get(position);
        loaiChiDAO.deleteLoaiChiByID(loaiCHiMD.getId());
        arrLoaichi.clear();
        arrLoaichi.addAll(loaiChiDAO.getAllLoaiChi());
        notifyDataSetChanged();
    }
});
        return convertView;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<LoaiCHiMD> items) {
        this.arrLoaichi = items;
        notifyDataSetChanged();
    }
}
