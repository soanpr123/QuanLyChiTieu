package com.soanlv.duan1android.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.soanlv.duan1android.R;


public class ThongKe extends Fragment {


    public ThongKe() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thong_ke_activity, container, false);
        return view;
    }

}
