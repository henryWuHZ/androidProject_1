package com.example.henry.myapplication1;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.henry.myapplication1.model.ShopInfo;
import com.example.henry.myapplication1.mycompnent.ClearEditText;

import java.util.ArrayList;


public class TestTwo extends Fragment {
    private Button bt;
    private ClearEditText clearEditText1;
    private ClearEditText clearEditText2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_test_two,container,false);
        bt = (Button) rootview.findViewById(R.id.testTwo_select);
        clearEditText1 = (ClearEditText) rootview.findViewById(R.id.testTwo_editText1);
        clearEditText2 = (ClearEditText) rootview.findViewById(R.id.testTwo_editText2);
        final FragmentManager fragmentManager = getFragmentManager();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyListView lv = new MyListView();
                Bundle bundle = new Bundle();
                bundle.putString("previous_position",clearEditText1.getText().toString().trim());
                bundle.putString("goodsBarCode",clearEditText2.getText().toString().trim());
                lv.setArguments(bundle);
                fragmentManager.beginTransaction().replace(R.id.frame_content,lv).commit();
            }
        });
        return rootview;
    }
}
