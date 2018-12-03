package com.soanlv.duan1android.activity;


import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

import com.soanlv.duan1android.R;
import com.soanlv.duan1android.adapter.KhoanThuAdapter;
import com.soanlv.duan1android.adapter.SpinnerLoaiThuAdapter;
import com.soanlv.duan1android.database.KhoanThuDAO;
import com.soanlv.duan1android.database.LoaiThuDAO;
import com.soanlv.duan1android.model.KhoanThuMD;
import com.soanlv.duan1android.model.LoaiCHiMD;
import com.soanlv.duan1android.model.LoaiThuMD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class KhoanThu extends Fragment {
    private List<KhoanThuMD> arrkhoanthu = new ArrayList<>();
    private List<LoaiThuMD> loaiThuMDS;
    private KhoanThuDAO khoanThuDAO;
    private LoaiThuDAO loaiThuDAO;
    private KhoanThuAdapter khoanThuAdapter;
    ListView lv_khoanhu;
    private String loaiChi = "";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");


    public KhoanThu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.khoan_thu_activity, container, false);
        lv_khoanhu = view.findViewById(R.id.lv_khoanthu);
        khoanThuDAO = new KhoanThuDAO(getActivity());
        try {
            arrkhoanthu = khoanThuDAO.getAllKhoanThu();
        } catch (ParseException e) {
            Log.d("Error: ", e.toString());
        }
        khoanThuAdapter = new KhoanThuAdapter(arrkhoanthu, getActivity());
        lv_khoanhu.setAdapter(khoanThuAdapter);
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
                        String sotien = ed_sotien.getText().toString();
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

}
