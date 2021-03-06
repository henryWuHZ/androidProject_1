package com.example.henry.myapplication1.enterpriseSearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.henry.myapplication1.R;
import com.example.henry.myapplication1.model.EnterpriseRecord;
import com.example.henry.myapplication1.mycompnent.ClearEditText;
import com.example.henry.myapplication1.util.EnterpriseRecordAdspter;
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

/**
 * Created by henry on 2017/2/15.
 */

public class EnterpriseSearchByDate extends Fragment{
    private ClearEditText startDate;
    private ClearEditText endDate;
    private Button btn;
    private ListView lv;
    private List<Map<String,Object>> lists;
    private EnterpriseRecordAdspter enterpriseRecordAdspter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_enterprise_search_by_date, container, false);
        startDate = (ClearEditText) rootview.findViewById(R.id.enterpriseSearchContain_startDate);
        endDate = (ClearEditText) rootview.findViewById(R.id.enterpriseSearchContain_endDate);
        btn = (Button) rootview.findViewById(R.id.enterpriseSearchContain_btn_date);
        lv = (ListView) rootview.findViewById(R.id.enterpriseSearchContain_lv_date);
        startDate.setHint("输入备案起始日期");
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDateDialog sdt = new SetDateDialog(startDate);
                sdt.show(getFragmentManager(), "datePicker");
            }
        });
        endDate.setHint("输入备案截止日期");
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDateDialog sdt = new SetDateDialog(endDate);
                sdt.show(getFragmentManager(), "datePicker");
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startDate.getText().toString().equals("")||endDate.getText().toString().equals("")){
                    Toast.makeText(getContext(),"查询条件不能为空...",Toast.LENGTH_LONG).show();
                }else {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("msgtype","searchByDate");
                    params.put("startdate",startDate.getText().toString());
                    params.put("enddate",endDate.getText().toString());
                    client.post(getURL(),params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String jsonString = new String(responseBody);
                            List<EnterpriseRecord> list = JSON.parseArray(jsonString, EnterpriseRecord.class);
                            lists = new ArrayList<Map<String, Object>>();
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put("name","企业名称");
                            map.put("code","企业编码");
                            map.put("type","采货类型");
                            map.put("date","备案时间");
                            map.put("distribution_enable","自动配货");
                            lists.add(map);
                            for (EnterpriseRecord enterpriseRecord:list){
                                Map<String,Object> map1 = new HashMap<String, Object>();
                                map1.put("name",enterpriseRecord.getName());
                                map1.put("code",enterpriseRecord.getCode());
                                map1.put("date",enterpriseRecord.getDate());
                                String type = enterpriseRecord.getType();
                                if (type.equals("1")){
                                    map1.put("type","备货");
                                } else if (type.equals("2")){
                                    map1.put("type","集货");
                                } else {
                                    map1.put("type","备货和集货");
                                }
                                if (enterpriseRecord.getDistribution_enable().equals("0")){
                                    map1.put("distribution_enable","否");
                                }else {
                                    map1.put("distribution_enable","是");
                                }
                                lists.add(map1);
                            }
                            enterpriseRecordAdspter = new EnterpriseRecordAdspter(getContext(),lists);
                            lv.setAdapter(enterpriseRecordAdspter);
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
    public String getURL() {
        return PropertitesUtil.getNetConfigProperties(getContext().getApplicationContext()).getProperty("enterpriseMng");
    }
}
