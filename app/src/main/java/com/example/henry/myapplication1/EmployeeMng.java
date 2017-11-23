package com.example.henry.myapplication1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by henry on 2017/2/10.
 */

public class EmployeeMng extends Fragment{
    private Button btn_add;
    private Button btn_search;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.employee_mng,container,false);
        btn_add = (Button) rootview.findViewById(R.id.employeeMng_btn_add);
        btn_search = (Button) rootview.findViewById(R.id.employeeMng_btn_search);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.employeeMng_fragment_content,new EmployeeAdd()).commit();
            }
        });
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.employeeMng_fragment_content,new EmployeeSearch()).commit();

            }
        });
        return rootview;
    }
}
