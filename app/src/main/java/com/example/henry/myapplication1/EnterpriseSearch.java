package com.example.henry.myapplication1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.henry.myapplication1.enterpriseSearch.EnterpriseSearchByCode;
import com.example.henry.myapplication1.enterpriseSearch.EnterpriseSearchByDate;
import com.example.henry.myapplication1.enterpriseSearch.EnterpriseSearchByName;
import com.example.henry.myapplication1.enterpriseSearch.EnterpriseSearchByType;


public class EnterpriseSearch extends Fragment {
    private Button searchByName;
    private Button searchByCode;
    private Button searchByType;
    private Button searchByDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_enterprise_search, container, false);
        searchByName = (Button) rootview.findViewById(R.id.enterpriseSearch_searchByName);
        searchByCode = (Button) rootview.findViewById(R.id.enterpriseSearch_searchByCode);
        searchByType = (Button) rootview.findViewById(R.id.enterpriseSearch_searchByType);
        searchByDate = (Button) rootview.findViewById(R.id.enterpriseSearch_searchByDate);
        searchByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.enterpriseSearch_fragment_content,new EnterpriseSearchByName()).commit();
            }
        });
        searchByCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.enterpriseSearch_fragment_content,new EnterpriseSearchByCode()).commit();
            }
        });
        searchByType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.enterpriseSearch_fragment_content,new EnterpriseSearchByType()).commit();
            }
        });
        searchByDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.enterpriseSearch_fragment_content,new EnterpriseSearchByDate()).commit();

            }
        });
        return rootview;
    }
}
