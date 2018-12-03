package com.soanlv.duan1android.activity;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.soanlv.duan1android.R;
import com.soanlv.duan1android.adapter.LoaiThuAdapter;
import com.soanlv.duan1android.database.LoaiThuDAO;
import com.soanlv.duan1android.model.LoaiThuMD;

import java.util.ArrayList;
import java.util.List;


public class LoaiThu extends Fragment {

    private List<LoaiThuMD> arrloaithu = new ArrayList<>();
    private LoaiThuDAO loaiThuDAO;
    private LoaiThuAdapter loaiThuAdapter;
    ListView lv_loaithu;

    public LoaiThu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loai_thu_activity, container, false);
        lv_loaithu = view.findViewById(R.id.lv_loaithu);
        loaiThuDAO = new LoaiThuDAO(getActivity());
        arrloaithu = loaiThuDAO.getAllLoaiThu();
        loaiThuAdapter = new LoaiThuAdapter(arrloaithu, getActivity());
        lv_loaithu.setAdapter(loaiThuAdapter);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fablthu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dialog = inflater.inflate(R.layout.add_loaithu, null);
                builder.setView(dialog);
                final EditText ed_loaithu = dialog.findViewById(R.id.ed_LOaiTHu);

                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String loaithu = ed_loaithu.getText().toString();
                        loaiThuDAO = new LoaiThuDAO(getActivity());
                        try {
                            if (validation() < 0) {
                                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            } else {
                                loaiThuDAO.insertLoaithu(new LoaiThuMD(loaithu));

                                updateData();
                                loaiThuAdapter.changeDataset(arrloaithu);
                                Toast.makeText(getActivity(), "Thêm thành công",
                                        Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                        }
                    }


                    private int validation() {
                        if (ed_loaithu.getText().toString().isEmpty()) {
                            return -1;
                        }
                        return 1;
                    }
                });


                builder.show();

            }

            private void updateData() {
                arrloaithu.clear();
                arrloaithu.addAll(loaiThuDAO.getAllLoaiThu());
                loaiThuAdapter.notifyDataSetChanged();
            }

        });
        return view;
    }

}
