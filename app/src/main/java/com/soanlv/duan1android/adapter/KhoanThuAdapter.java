package com.soanlv.duan1android.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.soanlv.duan1android.R;
import com.soanlv.duan1android.database.KhoanThuDAO;
import com.soanlv.duan1android.model.KhoanThuMD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class KhoanThuAdapter extends BaseAdapter {
    public Context context;
    public LayoutInflater inflater;
    List<KhoanThuMD> arrkhoanthu;
    KhoanThuDAO khoanThuDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private KhoanThuMD khoanThuMD;

    public KhoanThuAdapter(List<KhoanThuMD> arrkhoanthu, Context context) {
        this.arrkhoanthu = arrkhoanthu;
        this.context = context;
        this.inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        khoanThuDAO = new KhoanThuDAO(context);
    }

    @Override
    public int getCount() {
        return arrkhoanthu.size();
    }

    @Override
    public Object getItem(int position) {
        return arrkhoanthu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_khoan_thu, null);
            holder.tv_id = (TextView)
                    convertView.findViewById(R.id.tv_iD);
            holder.tv_ngay = (TextView) convertView.findViewById(R.id.tv_NgayThu);
            holder.tv_tienthu = (TextView) convertView.findViewById(R.id.tv_soTienThu);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.ivDeletethu);
            holder.imgedit = (ImageView) convertView.findViewById(R.id.ivEditthu);
            holder.tv_loaithu = convertView.findViewById(R.id.tv_loaiThu);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        KhoanThuMD _entry = (KhoanThuMD) arrkhoanthu.get(position);
        holder.tv_id.setText(_entry.getId() + "");
        holder.tv_tienthu.setText(_entry.getTienThu() + " VND");
        holder.tv_ngay.setText(sdf.format(_entry.getNgayThu()));
        holder.tv_loaithu.setText(_entry.getLoaiThu());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khoanThuMD = new KhoanThuMD();
                khoanThuMD = arrkhoanthu.get(position);
                khoanThuDAO.deleteKhoanTHuByID(khoanThuMD.getId());
                arrkhoanthu.clear();
                try {
                    arrkhoanthu.addAll(khoanThuDAO.getAllKhoanThu());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                notifyDataSetChanged();

            }
        });
        holder.imgedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final View dialog = inflater.inflate(R.layout.edit, null);
                builder.setView(dialog);
                final EditText id = dialog.findViewById(R.id.edid);
                final EditText ed_sotien = dialog.findViewById(R.id.edtienchi);
                final EditText ed_ngaymua = dialog.findViewById(R.id.ed_Date);
                final Button btn_date = dialog.findViewById(R.id.pic_Date);
                final SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
                khoanThuMD = new KhoanThuMD();
                khoanThuMD = arrkhoanthu.get(position);
                id.setText(khoanThuMD.getId() + "");
                ed_sotien.setText(khoanThuMD.getTienThu());
                ed_ngaymua.setText(sdf2.format(khoanThuMD.getNgayThu()));
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String Id = id.getText().toString();
                        String sotien = ed_sotien.getText().toString();
                        String edngaymua = ed_ngaymua.getText().toString();
                        khoanThuDAO = new KhoanThuDAO(context);
                        try {
                            if (validation() < 0) {
                                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            } else {

                                khoanThuMD.setId(Integer.parseInt(Id));
                                khoanThuMD.setTienThu(sotien);
                                khoanThuMD.setNgayThu(sdf2.parse(edngaymua));
                                khoanThuDAO.updateKhoanThu(khoanThuMD);
                                updatedata();
                                changeDataset(arrkhoanthu);
                                Toast.makeText(context, "Thêm thành công",
                                        Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                        }
                    }


                    private int validation() {
                        if (ed_sotien.getText().toString().isEmpty() || ed_ngaymua.getText().toString().isEmpty()) {
                            return -1;
                        }
                        return 1;
                    }
                });
                btn_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int ngay = cal.get(Calendar.DAY_OF_MONTH);
                        int thang = cal.get(Calendar.MONTH);
                        int nam = cal.get(Calendar.YEAR);
                        DatePickerDialog datet = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                                setdate(cal);
                            }

                            private void setdate(Calendar calendar) {
                                ed_ngaymua.setText(sdf.format(calendar.getTime()));
                            }
                        }, ngay, thang, nam);
                        datet.show();
                    }
                });

                builder.show();
            }

            private void updatedata() throws ParseException {
                arrkhoanthu.clear();
                arrkhoanthu.addAll(khoanThuDAO.getAllKhoanThu());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<KhoanThuMD> items) {
        this.arrkhoanthu = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        TextView tv_id;
        TextView tv_ngay, tv_tienthu, tv_loaithu;
        ImageView imgDelete, imgedit;

    }
}
