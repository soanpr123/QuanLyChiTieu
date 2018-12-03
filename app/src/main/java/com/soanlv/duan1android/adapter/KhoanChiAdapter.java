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
import com.soanlv.duan1android.database.KhoanChiDAO;
import com.soanlv.duan1android.model.KhoanChiMD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class KhoanChiAdapter extends BaseAdapter {
    public Context context;
    public LayoutInflater inflater;
    List<KhoanChiMD> arrKhoanchi;
    KhoanChiDAO khoanChiDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    private KhoanChiMD khoanChiMD;

    public KhoanChiAdapter(List<KhoanChiMD> arrKhoanchi, Context context) {
        this.arrKhoanchi = arrKhoanchi;
        this.context = context;
        this.inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        khoanChiDAO = new KhoanChiDAO(context);
    }

    @Override
    public int getCount() {
        return arrKhoanchi.size();
    }

    @Override
    public Object getItem(int position) {
        return arrKhoanchi.get(position);
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
            convertView = inflater.inflate(R.layout.item_khoan_chi, null);
            holder.tv_id = (TextView)
                    convertView.findViewById(R.id.tv_ID);
            holder.tv_ngay = (TextView) convertView.findViewById(R.id.tv_Ngaychi);
            holder.tv_tienchi = (TextView) convertView.findViewById(R.id.tv_soTien);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
            holder.imgedit = (ImageView) convertView.findViewById(R.id.ivEdit);
            holder.tv_loaichi = convertView.findViewById(R.id.tv_loaiChi);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        KhoanChiMD _entry = (KhoanChiMD) arrKhoanchi.get(position);
        holder.tv_id.setText(_entry.getId() + "");
        holder.tv_tienchi.setText(_entry.getTienChi() + " VND");
        holder.tv_ngay.setText(sdf.format(_entry.getNgayChi()));
        holder.tv_loaichi.setText(_entry.getLoaichi());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                khoanChiMD = new KhoanChiMD();
                khoanChiMD = arrKhoanchi.get(position);
                khoanChiDAO.deleteKhoanChiByID(khoanChiMD.getId());
                arrKhoanchi.clear();
                try {
                    arrKhoanchi.addAll(khoanChiDAO.getAllKhoanChi());
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
                khoanChiMD = new KhoanChiMD();
                khoanChiMD = arrKhoanchi.get(position);
                id.setText(khoanChiMD.getId() + "");
                ed_sotien.setText(khoanChiMD.getTienChi());
                ed_ngaymua.setText(sdf2.format(khoanChiMD.getNgayChi()));
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String Id = id.getText().toString();
                        String sotien = ed_sotien.getText().toString();
                        String edngaymua = ed_ngaymua.getText().toString();
                        khoanChiDAO = new KhoanChiDAO(context);
                        try {
                            if (validation() < 0) {
                                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            } else {

                                khoanChiMD.setId(Integer.parseInt(Id));
                                khoanChiMD.setTienChi(sotien);
                                khoanChiMD.setNgayChi(sdf2.parse(edngaymua));
                                khoanChiDAO.updateKhoanCHi(khoanChiMD);
                                updatedata();
                                changeDataset(arrKhoanchi);
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
                arrKhoanchi.clear();
                arrKhoanchi.addAll(khoanChiDAO.getAllKhoanChi());
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<KhoanChiMD> items) {
        this.arrKhoanchi = items;
        notifyDataSetChanged();
    }

    public static class ViewHolder {
        TextView tv_id;
        TextView tv_ngay, tv_tienchi, tv_loaichi;
        ImageView imgDelete, imgedit;

    }

}

