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
import com.soanlv.duan1android.adapter.KhoanChiAdapter2;
import com.soanlv.duan1android.adapter.SpinnerAdapter;
import com.soanlv.duan1android.adapter.SpinnerLoaiThuAdapter;
import com.soanlv.duan1android.database.KhoanChiDAO;
import com.soanlv.duan1android.database.KhoanThuDAO;
import com.soanlv.duan1android.database.LoaiChiDAO;
import com.soanlv.duan1android.database.LoaiThuDAO;
import com.soanlv.duan1android.model.KhoanChiMD;
import com.soanlv.duan1android.model.KhoanThuMD;
import com.soanlv.duan1android.model.LoaiCHiMD;
import com.soanlv.duan1android.model.LoaiThuMD;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class KhoanChiActivity extends Fragment implements ItemClickListener {
    private List<KhoanChiMD> arrkhoanchi = new ArrayList<>();
    private List<LoaiCHiMD> loaiCHiMDS;
    private KhoanChiDAO khoanChiDAO;
    private LoaiChiDAO loaiChiDAO;
    private KhoanChiMD khoanChiMD;
    private KhoanChiAdapter2 khoanChiAdapter;
    ListView lv_khoanchi;
    private String loaiChi="";
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
private RecyclerView rv_item;
    public KhoanChiActivity() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_khoan_chi, container, false);
        rv_item = view.findViewById(R.id.rv_khoanchi);
        khoanChiDAO = new KhoanChiDAO(getActivity());
        try {
            arrkhoanchi = khoanChiDAO.getAllKhoanChi();
        } catch (ParseException e) {
            Log.d("Error: ", e.toString());
        }
        khoanChiAdapter = new KhoanChiAdapter2( getActivity(),arrkhoanchi,this);
        rv_item.setAdapter(khoanChiAdapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getActivity());
        rv_item.setLayoutManager(manager);
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
                        int sotien = Integer.parseInt(ed_sotien.getText().toString());
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
                    khoanChiMD = new KhoanChiMD();
                    khoanChiMD = arrkhoanchi.get(position);
                    khoanChiDAO.deleteKhoanChiByID(khoanChiMD.getId());
                    arrkhoanchi.clear();
                    try {
                        arrkhoanchi.addAll(khoanChiDAO.getAllKhoanChi());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                   khoanChiAdapter.notifyDataSetChanged();
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

    @Override
    public void onLongClick(int position) {

    }
private void  EditItem(int position){
    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
    LayoutInflater inflater = LayoutInflater.from(getContext());
    final View dialog = inflater.inflate(R.layout.edit, null);
    builder.setView(dialog);
    final EditText id = dialog.findViewById(R.id.edid);
    final EditText ed_sotien = dialog.findViewById(R.id.edtienchi);
    final EditText ed_ngaymua = dialog.findViewById(R.id.ed_Date);
    final Button btn_date = dialog.findViewById(R.id.pic_Date);
    final Spinner ed_spinner = dialog.findViewById(R.id.sp_edkhoanchi);
    final SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
    khoanChiMD = new KhoanChiMD();
    khoanChiMD = arrkhoanchi.get(position);

    id.setText(khoanChiMD.getId() + "");
    ed_sotien.setText(String.valueOf(khoanChiMD.getTienChi()));
    ed_ngaymua.setText(sdf2.format(khoanChiMD.getNgayChi()));
    LoaiChiDAO loaiChiDAO = new LoaiChiDAO(getActivity());
    loaiCHiMDS = new ArrayList<>();
    loaiCHiMDS = loaiChiDAO.getAllLoaiChi();
    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(getActivity(), loaiCHiMDS);
    ed_spinner.setAdapter(spinnerAdapter);
    builder.setPositiveButton("Sửa", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            String Id = id.getText().toString();
            String sotien = ed_sotien.getText().toString();
            String edngaymua = ed_ngaymua.getText().toString();
            LoaiCHiMD loaiCHiMD = (LoaiCHiMD) ed_spinner.getSelectedItem();
            khoanChiDAO = new KhoanChiDAO(getContext());


            try {
                if (validation() < 0) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {

                    khoanChiMD.setId(Integer.parseInt(Id));

                        khoanChiMD.setTienChi(Integer.valueOf(sotien));




                    khoanChiMD.setNgayChi(sdf2.parse(edngaymua));
                    khoanChiMD.setLoaichi(String.valueOf(loaiCHiMD.getLoaiChi()));
                    khoanChiDAO.updateKhoanCHi(khoanChiMD);
                    updatedata();
                    khoanChiAdapter.changeDataset(arrkhoanchi);
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
        arrkhoanchi.clear();
        arrkhoanchi.addAll(khoanChiDAO.getAllKhoanChi());
        khoanChiAdapter.notifyDataSetChanged();
    }

    ;
    
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
