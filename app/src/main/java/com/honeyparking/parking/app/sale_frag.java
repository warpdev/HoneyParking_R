package com.honeyparking.parking.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class sale_frag extends Fragment {

    public sale_frag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_sale_frag, container, false);

        TabLayout tabs1= (TabLayout)v.findViewById(R.id.tabs1);
        tabs1.addTab(tabs1.newTab().setText("주차쿠폰"));
        tabs1.addTab(tabs1.newTab().setText("제휴쿠폰"));
        return v;
    }
}
