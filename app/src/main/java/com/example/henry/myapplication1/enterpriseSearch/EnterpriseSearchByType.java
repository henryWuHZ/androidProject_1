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

public class EnterpriseSearchByType extends Fragment{
    private ClearEditText type;
    private Button btn;
    private ListView lv;
    private List<Map<String,Object>> lists;
    private EnterpriseRecordAdspter enterpriseRecordAdspter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_enterprise_search_contain, container, false);
        type = (ClearEditText) rootview.findViewById(R.id.enterpriseSearchContain_text);
        btn = (Button) rootview.findViewById(R.id.enterpriseSearchContain_btn);
        lv = (ListView) rootview.findViewById(R.id.enterpriseSearchContain_lv);
        type.setHint("类型:备货/集货/备货和集货");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.getText().toString().equals("")){
                    Toast.makeText(getContext(),"查询条件不能为空...",Toast.LENGTH_LONG).show();
                }else {
                    if (!(type.getText().toString().equals("备货")
                            ||type.getText().toString().equals("集货")
                            ||type.getText().toString().equals("备货和集货"))){
                        Toast.makeText(getContext(),"请输入正确的类型...",Toast.LENGTH_LONG).show();
                    }
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("msgtype","searchByType");
                    params.put("type",type.getText().toString());
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
                            map.put("date","备案日期");
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
