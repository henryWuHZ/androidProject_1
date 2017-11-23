package com.example.henry.myapplication1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.henry.myapplication1.model.PreStorageInfo;
import com.example.henry.myapplication1.mycompnent.ClearEditText;
import com.example.henry.myapplication1.mycompnent.DropEditText;
import com.example.henry.myapplication1.util.PreStorageAdspter;
import com.example.henry.myapplication1.util.PropertitesUtil;
import com.example.henry.myapplication1.util.SetDateDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PreStorage extends Fragment {
    private DropEditText enterpriseName;
    private ClearEditText startDate;
    private ClearEditText endDate;
    private Button btn;
    private ListView lv;
    private String [] enterpriseNames;
    private List<PreStorageInfo> list;
    private List<Map<String,Object>> lists;
    private PreStorageAdspter preStorageAdspter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_pre_storage, container, false);
        enterpriseName = (DropEditText) rootview.findViewById(R.id.preStorage_enterpriseName);
        startDate = (ClearEditText) rootview.findViewById(R.id.preStorage_startDate);
        endDate = (ClearEditText) rootview.findViewById(R.id.preStorage_endDate);
        btn = (Button) rootview.findViewById(R.id.preStorage_btn);
        lv = (ListView) rootview.findViewById(R.id.preStorage_lv);
        //设置列表
        getEnterpriseName();
        //设置日期
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDateDialog sdt = new SetDateDialog(startDate);
                sdt.show(getFragmentManager(), "datePicker");
            }
        });
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDateDialog sdt = new SetDateDialog(endDate);
                sdt.show(getFragmentManager(), "datePicker");
            }
        });
        //设置提交
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterpriseName.getText().toString().equals("")
                        ||startDate.getText().toString().equals("")
                        ||endDate.getText().toString().equals("")){
                    Toast.makeText(getContext(),"选项不能为空...",Toast.LENGTH_LONG).show();
                }else{
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("msgtype", "getPreStorage");
                    params.put("enterpriseName",enterpriseName.getText().toString());
                    params.put("startDate",startDate.getText().toString());
                    params.put("endDate",endDate.getText().toString());
                    client.post(getURL(),params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            if (statusCode == 200){
                                String js = new String(responseBody);
                                list = JSON.parseArray(js,PreStorageInfo.class);
                                if (list.size()==0){
                                    Toast.makeText(getContext(),"无记录...",Toast.LENGTH_LONG).show();
                                }else {
                                    lists = new ArrayList<Map<String, Object>>();
                                    Map<String,Object> map = new HashMap<String, Object>();
                                    map.put("bonded_no","保税号");
                                    map.put("store_name","电商名称");
                                    map.put("freight_forwarding","货代名称");
                                    map.put("confirmed","入库状态");
                                    map.put("confirmed_date","入库日期");
                                    lists.add(map);
                                    for (PreStorageInfo preStorageInfo:list){
                                        Map<String,Object> map1 = new HashMap<String, Object>();
                                        map1.put("bonded_no",preStorageInfo.getBonded_no());
                                        map1.put("store_name",preStorageInfo.getStore_name());
                                        map1.put("freight_forwarding",preStorageInfo.getFreight_forwarding());
                                        if (preStorageInfo.getConfirmed().equals("1")){
                                            map1.put("confirmed","已入库");
                                        }else {
                                            map1.put("confirmed","未入库");
                                        }
                                        map1.put("confirmed_date",preStorageInfo.getConfirmed_date());
                                        lists.add(map1);
                                    }
                                    preStorageAdspter = new PreStorageAdspter(getContext(),lists);
                                    lv.setAdapter(preStorageAdspter);
                                }
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            super.onFailure(statusCode, headers, responseBody, error);
                        }
                    });
                }
            }
        });
        return rootview;
    }
    //获取URL
    public String getURL(){
        return PropertitesUtil.getNetConfigProperties(getContext().getApplicationContext()).getProperty("preStorage");
    }
    //获取备案企业名称
    public void getEnterpriseName (){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("msgtype", "getEnterpriseName");
        client.post(getURL(),params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    enterpriseNames = new String(responseBody).split(",");
                    ArrayAdapter<String> adapter_name = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,enterpriseNames);
                    enterpriseName.setAdapter(adapter_name);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }
}
