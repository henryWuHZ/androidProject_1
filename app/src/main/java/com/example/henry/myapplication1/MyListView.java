package com.example.henry.myapplication1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.example.henry.myapplication1.model.StorageModel;
import com.example.henry.myapplication1.util.MyAdspter;
import com.example.henry.myapplication1.util.PropertitesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyListView extends Fragment {
    private String previous_position;
    private String goodsBarCode;
    private List<Map<String,Object>> mapList;
    private ListView listView;
    private MyAdspter adspter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_list_view,container,false);
        Bundle bundle = getArguments();
        previous_position = bundle.getString("previous_position");
        goodsBarCode = bundle.getString("goodsBarCode");
        //根据条形码和库位查询storage的列表
        AsyncHttpClient client = new AsyncHttpClient();
        String url = PropertitesUtil.getNetConfigProperties(getContext().getApplicationContext()).getProperty("Login");
        // 创建请求参数的封装的对象
        RequestParams params = new RequestParams();
        params.put("msgtype","getstorage");
        params.put("prePosition",previous_position);
        params.put("goodsBarCode",goodsBarCode);
        // 执行post方法
        client.post(url, params, new AsyncHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200){
                    mapList = new ArrayList<Map<String, Object>>();
                    String jsonString = new String(responseBody);
                    List<StorageModel> lists = JSON.parseArray(jsonString, StorageModel.class);
                    Map<String,Object> map = new HashMap<String, Object>();
                    map.put("storageId", "");
                    map.put("goodsDesc", "商品描述");
                    map.put("quantity", "数量");
                    map.put("shifttime", "保质期");
                    mapList.add(map);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    for (StorageModel storageModel : lists){
                        Map<String, Object> map1 = new HashMap<String, Object>();
                        map1.put("storageId", storageModel.getStorageId());
                        map1.put("companyName",storageModel.getCompanyName());
                        map1.put("goodsRecordCode",storageModel.getGoodsRecordCode());
                        map1.put("bondedNo",storageModel.getBondedNo());
                        map1.put("goodsDesc", storageModel.getGoodsDesc());
                        map1.put("quantity", storageModel.getQuantity());
                        if (storageModel.getShiftTime()!=null) {
                            map1.put("shifttime", simpleDateFormat.format(storageModel.getShiftTime()));
                        }
                        mapList.add(map1);
                    }
                    listView = (ListView) rootView.findViewById(R.id.listView_two);
                    adspter = new MyAdspter(getContext(),mapList);
                    listView.setAdapter(adspter);
                }
                super.onSuccess(statusCode, headers, responseBody);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });
        return rootView;
    }
}
