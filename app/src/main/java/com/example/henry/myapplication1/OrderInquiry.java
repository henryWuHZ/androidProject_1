package com.example.henry.myapplication1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.example.henry.myapplication1.model.OrderState;
import com.example.henry.myapplication1.mycompnent.ClearEditText;
import com.example.henry.myapplication1.util.OrderInfoAdspter;
import com.example.henry.myapplication1.util.PropertitesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OrderInquiry extends Fragment {
    private ClearEditText externalNum;
    private ClearEditText expressNum;
    private Button btn_express;
    private Button btn_external;
    private ListView lv;
    private List<Map<String,Object>> lists;
    private OrderInfoAdspter orderInfoAdspter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview = inflater.inflate(R.layout.fragment_order_inquiry, container, false);
        expressNum = (ClearEditText) rootview.findViewById(R.id.orderInquiry_expressNum);
        externalNum = (ClearEditText) rootview.findViewById(R.id.orderInquiry_externalNum);
        btn_express = (Button) rootview.findViewById(R.id.orderInquiry_btn_express);
        btn_external = (Button) rootview.findViewById(R.id.orderInquiry_btn_external);
        lv = (ListView) rootview.findViewById(R.id.orderInquiry_lv);
        btn_express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expressNum.getText().toString().equals("")){
                    Toast.makeText(getContext(),"内容不能为空...",Toast.LENGTH_LONG).show();
                }else {
                    getInfoByExpress(expressNum.getText().toString(),rootview);
                }
            }
        });
        btn_external.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (externalNum.getText().toString().equals("")){
                    Toast.makeText(getContext(),"内容不能为空...",Toast.LENGTH_LONG).show();
                }else {
                    getInfoByExternal(externalNum.getText().toString(),rootview);
                }
            }
        });
        return rootview;
    }
    public void getInfoByExternal(String external,View rootview){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("msgtype", "getOrderInfoByExternal");
        params.put("externalId",external);
        client.post(getURL(),params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                List<OrderState> list = JSON.parseArray(jsonString,OrderState.class);
                if (list.size()==0){
                    Toast.makeText(getContext(),"无此记录...",Toast.LENGTH_LONG).show();
                }else {
                    lists = new ArrayList<Map<String, Object>>();
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("expressId","快递单号");
                    map.put("externalId","订单号");
                    map.put("shopName","店铺名称");
                    lists.add(map);
                    for (OrderState orderState:list){
                        Map<String,Object> map1 = new HashMap<String, Object>();
                        map1.put("expressId",orderState.getExpressId());
                        map1.put("externalId",orderState.getExternalId());
                        map1.put("shopName",orderState.getShopName());
                        if (orderState.getOrder_state().equals("1")){
                            map1.put("orderState","正常");
                        }else {
                            map1.put("orderState","不正常");
                        }
                        if (orderState.getOut_state().equals("1")){
                            map1.put("outState","正常");
                        }else {
                            map1.put("outState","不正常");
                        }
                        if (orderState.getRecheck_state().equals("1")){
                            map1.put("recheckState","正常");
                        }else {
                            map1.put("recheckState","不正常");
                        }
                        switch (orderState.getCustoms_state()){
                            case "0":
                                map1.put("customsState","未申报");
                                break;
                            case "1":
                                map1.put("customsState","已申报");
                                break;
                            case "2":
                                map1.put("customsState","单证放行");
                                break;
                            case "3":
                                map1.put("customsState","单证审核未通过");
                                break;
                            case "4":
                                map1.put("customsState","货物放行");
                                break;
                            case "5":
                                map1.put("customsState","查验未通过");
                                break;
                        }
                        switch (orderState.getInspection_state()){
                            case "0":
                                map1.put("inspectionState","未申报");
                                break;
                            case "1":
                                map1.put("inspectionState","已申报");
                                break;
                            case "2":
                                map1.put("inspectionState","已放行");
                                break;
                            case "3":
                                map1.put("inspectionState","审核不通过");
                                break;
                            case "4":
                                map1.put("inspectionState","抽检");
                                break;
                        }
                        switch (orderState.getDistribution_status()){
                            case "0":
                                map1.put("distributionState","等待配货");
                                break;
                            case "1":
                                map1.put("distributionState","已配货");
                                break;
                            case "2":
                                map1.put("distributionState","库存不足");
                                break;
                            case "3":
                                map1.put("distributionState","部分商品库存不足");
                                break;
                            case "4":
                                map1.put("distributionState","拣货失败");
                                break;
                        }
                        lists.add(map1);
                    }
                    orderInfoAdspter = new OrderInfoAdspter(getContext(),lists);
                    lv.setAdapter(orderInfoAdspter);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }
    public void getInfoByExpress(String express,View rootview){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("msgtype", "getOrderInfoByExpress");
        params.put("expressId",express);
        client.post(getURL(),params,new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                List<OrderState> list = JSON.parseArray(jsonString,OrderState.class);
                if (list.size() == 0){
                    Toast.makeText(getContext(),"无此记录...",Toast.LENGTH_LONG).show();
                }else {
                    lists = new ArrayList<Map<String, Object>>();
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("expressId","快递单号");
                    map.put("externalId","订单号");
                    map.put("shopName","店铺名称");
                    lists.add(map);
                    for (OrderState orderState:list){
                        Map<String,Object> map1 = new HashMap<String, Object>();
                        map1.put("expressId",orderState.getExpressId());
                        map1.put("externalId",orderState.getExternalId());
                        map1.put("shopName",orderState.getShopName());
                        if (orderState.getOrder_state().equals("1")){
                            map1.put("orderState","正常");
                        }else {
                            map1.put("orderState","不正常");
                        }
                        if (orderState.getOut_state().equals("1")){
                            map1.put("outState","正常");
                        }else {
                            map1.put("outState","不正常");
                        }
                        if (orderState.getRecheck_state().equals("1")){
                            map1.put("recheckState","正常");
                        }else {
                            map1.put("recheckState","不正常");
                        }
                        switch (orderState.getCustoms_state()){
                            case "0":
                                map1.put("customsState","未申报");
                                break;
                            case "1":
                                map1.put("customsState","已申报");
                                break;
                            case "2":
                                map1.put("customsState","单证放行");
                                break;
                            case "3":
                                map1.put("customsState","单证审核未通过");
                                break;
                            case "4":
                                map1.put("customsState","货物放行");
                                break;
                            case "5":
                                map1.put("customsState","查验未通过");
                                break;
                        }
                        switch (orderState.getInspection_state()){
                            case "0":
                                map1.put("inspectionState","未申报");
                                break;
                            case "1":
                                map1.put("inspectionState","已申报");
                                break;
                            case "2":
                                map1.put("inspectionState","已放行");
                                break;
                            case "3":
                                map1.put("inspectionState","审核不通过");
                                break;
                            case "4":
                                map1.put("inspectionState","抽检");
                                break;
                        }
                        switch (orderState.getDistribution_status()){
                            case "0":
                                map1.put("distributionState","等待配货");
                                break;
                            case "1":
                                map1.put("distributionState","已配货");
                                break;
                            case "2":
                                map1.put("distributionState","库存不足");
                                break;
                            case "3":
                                map1.put("distributionState","部分商品库存不足");
                                break;
                            case "4":
                                map1.put("distributionState","拣货失败");
                                break;
                        }
                        lists.add(map1);
                    }
                    orderInfoAdspter = new OrderInfoAdspter(getContext(),lists);
                    lv.setAdapter(orderInfoAdspter);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }
    //获取URL
    public String getURL(){
        return PropertitesUtil.getNetConfigProperties(getContext().getApplicationContext()).getProperty("orderInquiry");
    }
}
