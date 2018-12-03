package com.soanlv.duan1android.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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
import com.soanlv.duan1android.adapter.KhoanChiAdapter;
import com.soanlv.duan1android.adapter.SpinnerAdapter;
import com.soanlv.duan1android.database.KhoanChiDAO;
import com.soanlv.duan1android.database.LoaiChiDAO;
import com.soanlv.duan1android.model.KhoanChiMD;
import com.soanlv.duan1android.model.LoaiCHiMD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class KhoanChiActivity extends Fragment {
    private List<KhoanChiMD> arrkhoanchi = new ArrayList<>();
    private List<LoaiCHiMD> loaiCHiMDS;
    private KhoanChiDAO khoanChiDAO;
    private LoaiChiDAO loaiChiDAO;
    private KhoanChiAdapter khoanChiAdapter;
    ListView lv_khoanchi;
    private String loaiChi="";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");

    public KhoanChiActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_khoan_chi, container, false);
        lv_khoanchi = view.findViewById(R.id.lv_khoanchi);
        khoanChiDAO = new KhoanChiDAO(getActivity());
        try {
            arrkhoanchi = khoanChiDAO.getAllKhoanChi();
        } catch (ParseException e) {
            Log.d("Error: ", e.toString());
        }
        khoanChiAdapter = new KhoanChiAdapter(arrkhoanchi, getActivity());
        lv_khoanchi.setAdapter(khoanChiAdapter);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabkchi);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final View dialog = inflater.inflate(R.layout.add, null);
                builder.setView(dialog);
                final EditText ed_sotien = dialog.findViewById(R.id.ed_tienchi);
                final EditText ed_ngaymua = dialog.findViewById(R.id.edDate);
                final Button btn_date = dialog.findViewById(R.id.picDate);
                final Spinner spinner = dialog.findViewById(R.id.sp_khoanchi);
                loaiChiDAO = new LoaiChiDAO(getActivity());
                loaiCHiMDS = new ArrayList<>();
                loaiCHiMDS = loaiChiDAO.getAllLoaiChi();

                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), loaiCHiMDS);
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
                        LoaiCHiMD loaiCHiMD= (LoaiCHiMD) spinner.getSelectedItem();
                        khoanChiDAO = new KhoanChiDAO(getActivity());
                        try {
                            if (validation() < 0) {
                                Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                            } else {
                                khoanChiDAO.insertKhoanchi(new KhoanChiMD(sotien, String.valueOf(loaiCHiMD.getLoaiChi()), sdf.parse(edngaymua)));
                                updateData();
                                khoanChiAdapter.changeDataset(arrkhoanchi);
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

//            private void getloaichi() throws ParseException {
//
//            }

            private void updateData() throws ParseException {
                arrkhoanchi.clear();
                arrkhoanchi.addAll(khoanChiDAO.getAllKhoanChi());
                khoanChiAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }

    }

}
