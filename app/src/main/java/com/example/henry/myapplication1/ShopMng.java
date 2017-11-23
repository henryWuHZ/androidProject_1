package com.example.henry.myapplication1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.henry.myapplication1.model.ShopInfo;
import com.example.henry.myapplication1.mycompnent.ClearEditText;
import com.example.henry.myapplication1.util.PropertitesUtil;
import com.example.henry.myapplication1.util.ShopInfoAdspter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShopMng extends Fragment {
    private ListView listView;
    private Button check;
    private Button check2;
    private Button addNew;
    private SearchView searchView;
    private SearchView searchView2;
    private ShopInfoAdspter shopInfoAdspter;
    private List<Map<String,Object>> lists;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_shop_mng, container, false);
        check = (Button) rootView.findViewById(R.id.shopMng_check);
        searchView = (SearchView) rootView.findViewById(R.id.shopMng_sv);
        searchView2 = (SearchView) rootView.findViewById(R.id.shopMng_sv2);
        check2 = (Button) rootView.findViewById(R.id.shopMng_check2);
        addNew = (Button) rootView.findViewById(R.id.addNewShop);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutinflater = LayoutInflater.from(getContext());
                final View addView = layoutinflater.inflate(R.layout.shopmng_addshop,null);
                final ClearEditText shopname = (ClearEditText)addView.findViewById(R.id.addNewShop_shopname);
                final ClearEditText companyname = (ClearEditText) addView.findViewById(R.id.addNewShop_companyname);
                new AlertDialog.Builder(getContext())
                        .setView(addView)
                        .setCancelable(false)
                        .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                closeDialog(dialog,true);
                            }
                        })
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog, int which) {
                                if (TextUtils.isEmpty(shopname.getText().toString().trim())){
                                    Toast.makeText(getContext(),"店铺名不能为空...",Toast.LENGTH_LONG).show();
                                    closeDialog(dialog,false);
                                    return;
                                }
                                if (TextUtils.isEmpty(companyname.getText().toString().trim())){
                                    Toast.makeText(getContext(),"备案企业名不能为空...",Toast.LENGTH_LONG).show();
                                    closeDialog(dialog,false);
                                    return;
                                }
                                AsyncHttpClient client = new AsyncHttpClient();
                                RequestParams params = new RequestParams();
                                params.put("msgtype","addshop");
                                params.put("shopName",shopname.getText().toString());
                                params.put("companyName",companyname.getText().toString());
                                client.post(getURL(),params,new AsyncHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        String res = new String(responseBody);
                                        if (res.equals("1")){
                                            Toast.makeText(getContext(),"添加成功...",Toast.LENGTH_LONG).show();
                                            closeDialog(dialog,true);
                                        }else {
                                            Toast.makeText(getContext(),"添加失败,确认填写信息...",Toast.LENGTH_LONG).show();
                                            closeDialog(dialog,true);
                                        }
                                    }
                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        super.onFailure(statusCode, headers, responseBody, error);
                                    }
                                });
                            }
                        })
                        .show();
            }
        });
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchView.getQuery().toString().equals("")){
                    Toast.makeText(getContext(),"内容不能为空...",Toast.LENGTH_LONG).show();
                }else {
                    getInfoByshop(searchView.getQuery().toString(),rootView);
                }
            }
        });
        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchView2.getQuery().toString().equals("")){
                    Toast.makeText(getContext(),"内容不能为空...",Toast.LENGTH_LONG).show();
                }else {
                    getInfoBycomp(searchView2.getQuery().toString(),rootView);
                }
            }
        });
        return rootView;
    }
    //根据企业名访问服务端获取店铺及企业信息
    private void getInfoBycomp(String arg, final View rootView) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("msgtype", "getinfoBycompany");
        params.put("companyname",arg);
        client.post(getURL(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                List<ShopInfo> list = JSON.parseArray(jsonString, ShopInfo.class);
                lists = new ArrayList<Map<String, Object>>();
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("shopName","店铺名称");
                map.put("companyName","公司名称");
                lists.add(map);
                for (ShopInfo shopInfo:list){
                    Map<String,Object> map1 = new HashMap<String, Object>();
                    map1.put("shopName",shopInfo.getShopName());
                    map1.put("companyName",shopInfo.getCompanyName());
                    lists.add(map1);
                }
                listView = (ListView) rootView.findViewById(R.id.shopMng_lv);
                shopInfoAdspter = new ShopInfoAdspter(getContext(),lists);
                listView.setAdapter(shopInfoAdspter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }

    //根据店铺名访问服务端获取店铺及企业信息
    public void getInfoByshop (String shopName, final View rootView) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("msgtype", "getinfoByshop");
        params.put("shopname",shopName);
        client.post(getURL(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String jsonString = new String(responseBody);
                List<ShopInfo> list = JSON.parseArray(jsonString, ShopInfo.class);
                lists = new ArrayList<Map<String, Object>>();
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("shopName","店铺名称");
                map.put("companyName","公司名称");
                lists.add(map);
                for (ShopInfo shopInfo:list){
                    Map<String,Object> map1 = new HashMap<String, Object>();
                    map1.put("shopName",shopInfo.getShopName());
                    map1.put("companyName",shopInfo.getCompanyName());
                    lists.add(map1);
                }
                listView = (ListView) rootView.findViewById(R.id.shopMng_lv);
                shopInfoAdspter = new ShopInfoAdspter(getContext(),lists);
                listView.setAdapter(shopInfoAdspter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                super.onFailure(statusCode, headers, responseBody, error);
            }
        });
    }

    //获取URL
    public String getURL() {
        return PropertitesUtil.getNetConfigProperties(getContext().getApplicationContext()).getProperty("GetShopInfo");
    }
    //手动关闭对话框(true为关闭,false为不关闭)
    private void closeDialog(android.content.DialogInterface dialog,boolean flag){
        try
        {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing" );
            field.setAccessible( true );
            field.set(dialog, flag);
            dialog.dismiss();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
