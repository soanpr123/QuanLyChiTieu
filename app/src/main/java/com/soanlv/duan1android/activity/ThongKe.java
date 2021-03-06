package com.soanlv.duan1android.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.soanlv.duan1android.R;
import com.soanlv.duan1android.database.KhoanChiDAO;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class ThongKe extends Fragment {
    TextView tv_tong, tv_day, tv_month, tv_yeah;
    Spinner sp_ngay, sp_thang, sp_nam;
    private List<String> arrngay = new ArrayList<>();
    private List<String> arrthang = new ArrayList<>();
    private List<String> arrnam = new ArrayList<>();
    ArrayAdapter<String> adapter;
    KhoanChiDAO khoanChiDAO;

    public ThongKe() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thong_ke_activity, container, false);
        tv_tong = view.findViewById(R.id.tv_tongSoTien);
        tv_day = view.findViewById(R.id.tv_ngay);
        tv_month = view.findViewById(R.id.tv_thang);
        tv_yeah = view.findViewById(R.id.tv_nam);
        sp_ngay = view.findViewById(R.id.day);
        sp_thang = view.findViewById(R.id.month);
        sp_nam = view.findViewById(R.id.yeah);
        khoanChiDAO = new KhoanChiDAO(getActivity());
        int number = khoanChiDAO.Tongkhoanchi();
        NumberFormat formatter = new DecimalFormat("###,###");
        String resp = formatter.format(number);
        tv_tong.setText("Tổng Tiền Đã Chi Tiêu:  " + resp + " VND");

        thongkengay();
        thongkethang();
        thongkenam();
        return view;
    }

    private void thongkenam() {
        arrnam.add("2018");
        arrnam.add("2019");
        arrnam.add("2020");
        arrnam.add("2021");
        arrnam.add("2022");
        arrnam.add("2023");
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arrnam);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_nam.setAdapter(adapter);
        sp_nam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String yeah = arrnam.get(position);
                int number = khoanChiDAO.getKhoanchiTheoNam(yeah);
                NumberFormat formatter = new DecimalFormat("###,###");
                String resp = formatter.format(number);
                tv_yeah.setText("Khoản Chi Theo Năm " + yeah + " Là: " + resp + " VND");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void thongkethang() {
        arrthang.add("01");
        arrthang.add("02");
        arrthang.add("03");
        arrthang.add("04");
        arrthang.add("05");
        arrthang.add("06");
        arrthang.add("07");
        arrthang.add("08");
        arrthang.add("09");
        arrthang.add("10");
        arrthang.add("11");
        arrthang.add("12");

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arrthang);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_thang.setAdapter(adapter);
        sp_thang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ngay = arrthang.get(position);
                String yeah = (String) sp_nam.getSelectedItem();
                int number = khoanChiDAO.Khoanchitheothang(ngay, yeah);
                NumberFormat formatter = new DecimalFormat("###,###");
                String resp = formatter.format(number);
                tv_month.setText("Khoản Chi Theo Tháng " + ngay + " Năm " + yeah + "  Là: " + resp + " VND");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void thongkengay() {
        arrngay.add("01");
        arrngay.add("02");
        arrngay.add("03");
        arrngay.add("04");
        arrngay.add("05");
        arrngay.add("06");
        arrngay.add("07");
        arrngay.add("08");
        arrngay.add("09");
        arrngay.add("10");
        arrngay.add("11");
        arrngay.add("12");
        arrngay.add("13");
        arrngay.add("14");
        arrngay.add("15");
        arrngay.add("16");
        arrngay.add("17");
        arrngay.add("18");
        arrngay.add("19");
        arrngay.add("20");
        arrngay.add("21");
        arrngay.add("22");
        arrngay.add("23");
        arrngay.add("24");
        arrngay.add("25");
        arrngay.add("26");
        arrngay.add("27");
        arrngay.add("28");
        arrngay.add("29");
        arrngay.add("30");
        arrngay.add("31");

        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, arrngay);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp_ngay.setAdapter(adapter);
        sp_ngay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ngay = arrngay.get(position);
                String month = (String) sp_thang.getSelectedItem();
                String yeah = (String) sp_nam.getSelectedItem();
                int number = khoanChiDAO.getKhoanchiTheoNgay(ngay, month, yeah);
                NumberFormat formatter = new DecimalFormat("###,###");
                String resp = formatter.format(number);
                tv_day.setText("Khoản Chi Theo Ngày " + ngay + "Là: " + resp + " VND");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
