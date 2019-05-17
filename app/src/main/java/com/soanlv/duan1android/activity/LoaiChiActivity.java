package com.soanlv.duan1android.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.soanlv.duan1android.adapter.LoaiChiAdapter;
import com.soanlv.duan1android.database.LoaiChiDAO;
import com.soanlv.duan1android.model.LoaiCHiMD;

import java.util.ArrayList;
import java.util.List;

public class LoaiChiActivity extends Fragment {
    private List<LoaiCHiMD> arrloaichi = new ArrayList<>();
    private LoaiChiDAO loaiChiDAO;
    private LoaiChiAdapter loaiChiAdapter;
    ListView lv_loaichi;

    public LoaiChiActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_loai_chi, container, false);
        lv_loaichi = view.findViewById(R.id.lv_loaichi);
         loaiChiDAO= new LoaiChiDAO(getActivity());
            arrloaichi = loaiChiDAO.getAllLoaiChi();
        loaiChiAdapter = new LoaiChiAdapter(arrloaichi, getActivity());
        lv_loaichi.setAdapter(loaiChiAdapter);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fablchi);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dialog = inflater.inflate(R.layout.add_loaichi, null);
                builder.setView(dialog);
                final EditText ed_Loaichi = dialog.findViewById(R.id.ed_loaichi);

                builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String sotien = ed_Loaichi.getText().toString();
                        loaiChiDAO = new LoaiChiDAO(getActivity());
                        try {
                            if (validation() < 0) {
                                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            } else {
                                loaiChiDAO.insertLoaichi(new LoaiCHiMD(sotien));

                                updateData();
                                loaiChiAdapter.changeDataset(arrloaichi);
                                Toast.makeText(getActivity(), "Thêm thành công",
                                        Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception ex) {
                            Log.e("Error", ex.toString());
                        }
                    }


                    private int validation() {
                        if (ed_Loaichi.getText().toString().isEmpty()) {
                            return -1;
                        }
                        return 1;
                    }
                });


                builder.show();

            }

            private void updateData() {
                arrloaichi.clear();
                arrloaichi.addAll(loaiChiDAO.getAllLoaiChi());
                loaiChiAdapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
