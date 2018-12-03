package com.soanlv.duan1android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.soanlv.duan1android.R;
import com.soanlv.duan1android.database.KhoanThuDAO;
import com.soanlv.duan1android.model.KhoanThuMD;
import com.soanlv.duan1android.model.LoaiThuMD;

import java.util.List;

public class LoaiThuAdapter extends BaseAdapter {
    public Context context;
    public LayoutInflater inflater;
    List<LoaiThuMD> arrloaithu;
    KhoanThuDAO khoanThuDAO;
    private KhoanThuMD khoanThuMD;

    public LoaiThuAdapter(List<LoaiThuMD> arrLoaichi, Context context) {
        this.arrloaithu = arrLoaichi;
        this.context = context;
        this.inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        khoanThuDAO = new KhoanThuDAO(context);
    }

    @Override
    public int getCount() {
        return arrloaithu.size();
    }

    @Override
    public Object getItem(int position) {
        return arrloaithu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder {
        TextView tv_id, tvloaithu;
        ImageView imgDelete;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_loai_thu, null);
            holder.tv_id = (TextView)
                    convertView.findViewById(R.id.tvID);
            holder.tvloaithu = (TextView) convertView.findViewById(R.id.tv_loaithu);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.iv_Deleteloaithu);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        LoaiThuMD _entry = (LoaiThuMD) arrloaithu.get(position);
        holder.tv_id.setText(_entry.getId() + "");
        holder.tvloaithu.setText(_entry.getLoaiThu());

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<LoaiThuMD> items) {
        this.arrloaithu = items;
        notifyDataSetChanged();
    }
}
