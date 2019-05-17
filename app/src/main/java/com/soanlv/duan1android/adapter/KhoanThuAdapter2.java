package com.soanlv.duan1android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.soanlv.duan1android.ItemClickListener;
import com.soanlv.duan1android.R;
import com.soanlv.duan1android.database.KhoanThuDAO;
import com.soanlv.duan1android.model.KhoanThuMD;
import com.soanlv.duan1android.model.LoaiThuMD;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class KhoanThuAdapter2 extends RecyclerView.Adapter<KhoanThuAdapter2.ViewHolder> {
    public Context context;
    public LayoutInflater inflater;
    List<KhoanThuMD> arrkhoanthu;
    List<LoaiThuMD> loaiThuMDS;
    KhoanThuDAO khoanThuDAO;
    ItemClickListener clickListener;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private KhoanThuMD khoanThuMD;

    public KhoanThuAdapter2(Context context, List<KhoanThuMD> arrkhoanthu, ItemClickListener clickListener) {
        this.context = context;
        this.arrkhoanthu = arrkhoanthu;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_khoan_thu, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        KhoanThuMD khoanThuMD = arrkhoanthu.get(i);
        int number = khoanThuMD.getTienThu();
        NumberFormat formatter = new DecimalFormat("###,###");
        String resp = formatter.format(number);
        viewHolder.tv_Tienthu.setText(resp + " VND");
        viewHolder.tv_ngaythu.setText(sdf.format(khoanThuMD.getNgayThu()));
        viewHolder.tv_Loaithu.setText(khoanThuMD.getLoaiThu());
        viewHolder.tv_id.setText(khoanThuMD.getId()+"");
        viewHolder.imgDele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(i);
            }
        });
        viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick2(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrkhoanthu.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imgDele, imgEdit;
        public TextView tv_Tienthu, tv_ngaythu, tv_Loaithu, tv_id;


        public ViewHolder(@NonNull View view) {
            super(view);
            imgDele = view.findViewById(R.id.ivDeletethu);
            imgEdit = view.findViewById(R.id.ivEditthu);
            tv_Tienthu = view.findViewById(R.id.tv_soTienThu);
            tv_Loaithu = view.findViewById(R.id.tv_loaiThu);
            tv_ngaythu = view.findViewById(R.id.tv_NgayThu);
            tv_id = view.findViewById(R.id.tv_iD);
        }
    }

    public void changeDataset(List<KhoanThuMD> items) {
        this.arrkhoanthu = items;
        notifyDataSetChanged();
    }
}
