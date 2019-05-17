package com.soanlv.duan1android.activity;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.soanlv.duan1android.ItemClickListener;
import com.soanlv.duan1android.R;
import com.soanlv.duan1android.adapter.KhoanThuAdapter2;
import com.soanlv.duan1android.adapter.SpinnerLoaiThuAdapter;
import com.soanlv.duan1android.database.KhoanThuDAO;
import com.soanlv.duan1android.database.LoaiThuDAO;
import com.soanlv.duan1android.model.KhoanThuMD;
import com.soanlv.duan1android.model.LoaiThuMD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class KhoanThu extends Fragment implements ItemClickListener {
    private List<KhoanThuMD> arrkhoanthu = new ArrayList<>();
    private List<LoaiThuMD> loaiThuMDS;
    private KhoanThuDAO khoanThuDAO;
    private LoaiThuDAO loaiThuDAO;

    private KhoanThuAdapter2 khoanThuAdapter;
    ListView lv_khoanhu;
    KhoanThuMD khoanThuMD;
    RecyclerView rv_Khoanthu;
    private String loaiChi = "";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
    public LayoutInflater Inflater;


    public KhoanThu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.khoan_thu_activity, container, false);
//        lv_khoanhu = view.findViewById(R.id.lv_khoanthu);
        rv_Khoanthu = view.findViewById(R.id.lv_khoanthu);
        khoanThuDAO = new KhoanThuDAO(getActivity());
        try {
            arrkhoanthu = khoanThuDAO.getAllKhoanThu();
        } catch (ParseException e) {
            Log.d("Error: ", e.toString());
        }
        khoanThuAdapter = new KhoanThuAdapter2(getActivity(), arrkhoanthu, this);
        rv_Khoanthu.setAdapter(khoanThuAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        rv_Khoanthu.setLayoutManager(manager);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabkthu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dialog = inflater.inflate(R.layout.addkhoanthu, null);
                builder.setView(dialog);
                final EditText ed_sotien = dialog.findViewById(R.id.ed_tienthu);
                final EditText ed_ngaymua = dialog.findViewById(R.id.edDatethu);
                final Button btn_date = dialog.findViewById(R.id.picDatethu);
                final Spinner spinner = dialog.findViewById(R.id.sp_khoanthu);
                loaiThuDAO = new LoaiThuDAO(getActivity());
                loaiThuMDS = new ArrayList<>();
                loaiThuMDS = loaiThuDAO.getAllLoaiThu();
                SpinnerLoaiThuAdapter spinnerAdapter = new SpinnerLoaiThuAdapter(getActivity(), loaiThuMDS);
                spinner.setAdapter(spinnerAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int sotien = Integer.parseInt(ed_sotien.getText().toString());
                        String edngaymua = ed_ngaymua.getText().toString();
                        LoaiThuMD loaiThuMD = (LoaiThuMD) spinner.getSelectedItem();
                        khoanThuDAO = new KhoanThuDAO(getActivity());
                        try {
                            if (validation() < 0) {
                                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            } else {
                                khoanThuDAO.insertKhoanThu(new KhoanThuMD(sotien, String.valueOf(loaiThuMD.getLoaiThu()), sdf.parse(edngaymua)));
                                updateData();
                                khoanThuAdapter.changeDataset(arrkhoanthu);
                                Toast.makeText(getActivity(), "Thêm thành công",
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
                builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                btn_date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar cal = Calendar.getInstance();
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        int month = cal.get(Calendar.MONTH);
                        int year = cal.get(Calendar.YEAR);
                        DatePickerDialog datet = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                                setdate(cal);
                            }

                            private void setdate(Calendar calendar) {
                                ed_ngaymua.setText(sdf.format(calendar.getTime()));
                            }
                        }, day, month, year);
                        datet.show();
                    }
                });

                builder.show();
            }

            private void updateData() throws ParseException {
                arrkhoanthu.clear();
                arrkhoanthu.addAll(khoanThuDAO.getAllKhoanThu());
                khoanThuAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    @Override
    public void onClick(int position) {

        DeleteItem(position);

    }

    private void DeleteItem(final int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("bạn có muốn xóa khoản mục này ! ");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                khoanThuMD = new KhoanThuMD();
                khoanThuMD = arrkhoanthu.get(position);
                khoanThuDAO.deleteKhoanTHuByID(khoanThuMD.getId());
                arrkhoanthu.clear();
                try {
                    arrkhoanthu.addAll(khoanThuDAO.getAllKhoanThu());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                khoanThuAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    @Override
    public void onClick2(int position) {
        EditItem(position);

    }

    private void EditItem(int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getContext());

        final View dialog = inflater.inflate(R.layout.edit, null);
        builder.setView(dialog);
        final EditText id = dialog.findViewById(R.id.edid);
        final EditText ed_sotien = dialog.findViewById(R.id.edtienchi);
        final EditText ed_ngaymua = dialog.findViewById(R.id.ed_Date);
        final Button btn_date = dialog.findViewById(R.id.pic_Date);
        final Spinner ed_spinner = dialog.findViewById(R.id.sp_edkhoanchi);
        final SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        khoanThuMD = new KhoanThuMD();
        khoanThuMD = arrkhoanthu.get(position);
        id.setText(khoanThuMD.getId() + "");
        ed_sotien.setText(String.valueOf(khoanThuMD.getTienThu()));

        ed_ngaymua.setText(sdf2.format(khoanThuMD.getNgayThu()));
        loaiThuDAO = new LoaiThuDAO(getActivity());
        loaiThuMDS = new ArrayList<>();
        loaiThuMDS = loaiThuDAO.getAllLoaiThu();
        SpinnerLoaiThuAdapter spinnerAdapter = new SpinnerLoaiThuAdapter(getActivity(), loaiThuMDS);
        ed_spinner.setAdapter(spinnerAdapter);

        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                String Id = id.getText().toString();
                String sotien = ed_sotien.getText().toString();
                String edngaymua = ed_ngaymua.getText().toString();
                khoanThuDAO = new KhoanThuDAO(getContext());
                try {
                    if (validation() < 0) {
                        Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    } else {

                        khoanThuMD.setId(Integer.parseInt(Id));
                        khoanThuMD.setTienThu(Integer.valueOf(sotien));
                        khoanThuMD.setNgayThu(sdf2.parse(edngaymua));
                        LoaiThuMD loaiThuMD = (LoaiThuMD) ed_spinner.getSelectedItem();
                        khoanThuDAO = new KhoanThuDAO(getActivity());
                        khoanThuMD.setLoaiThu(String.valueOf(loaiThuMD.getLoaiThu()));
                        khoanThuDAO.updateKhoanThu(khoanThuMD);
                        updatedata();
                        khoanThuAdapter.changeDataset(arrkhoanthu);
                        Toast.makeText(getContext(), "Sửa thành công",
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
        builder.setNegativeButton("hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int ngay = cal.get(Calendar.DAY_OF_MONTH);
                int thang = cal.get(Calendar.MONTH);
                int nam = cal.get(Calendar.YEAR);
                DatePickerDialog datet = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
        khoanThuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClick(int position) {

    }

}
