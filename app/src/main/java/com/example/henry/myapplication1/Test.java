package com.example.henry.myapplication1;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.myapplication1.mycompnent.ClearEditText;
import com.example.henry.myapplication1.util.PropertitesUtil;
import com.example.henry.myapplication1.util.SetDateDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;


public class Test extends Fragment implements
        SearchView.OnQueryTextListener{

    private SearchView sv;
    private ListView lv;
    private ClearEditText goodsBarCode;
    private ClearEditText goodsRecordNo;
    private EditText position;
    private EditText quantity;
    private CheckBox checkBox;
    private ClearEditText shelfLife;
    private String[] m;         //调用后台的得到未入库的预入库数据
    private ArrayAdapter<String> adapter;
    private Button submit;
    private SharedPreferences sharedPreferences;
    public Test(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootview = inflater.inflate(R.layout.fragment_test,container,false);
        //设置商品条码输入栏
        goodsBarCode = (ClearEditText) rootview.findViewById(R.id.editText2);
        //设置货号输入栏
        goodsRecordNo = (ClearEditText)rootview.findViewById(R.id.editText3);
        //设置仓位输入栏
        position = (EditText)rootview.findViewById(R.id.editText4);
        //设置数量输入栏
        quantity = (EditText)rootview.findViewById(R.id.editText5);
        //设置勾选按钮
        checkBox = (CheckBox)rootview.findViewById(R.id.cb_check);
        //设置生产日期输入栏
        shelfLife = (ClearEditText) rootview.findViewById(R.id.editText6);
        //设置商品条码输入栏监听事件
        sv = (SearchView) rootview.findViewById(R.id.sv);
        //设置提交按钮
        submit = (Button) rootview.findViewById(R.id.button);
        //获取用户名
        sharedPreferences = getContext().getApplicationContext().getSharedPreferences("config", Context.MODE_PRIVATE);

        final String account = sharedPreferences.getString("account","k");
        //条形码监听事件
        goodsBarCode.setOnKeyListener(new EditText.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(KeyEvent.KEYCODE_ENTER==keyCode){
                    String newStr = getNewString(goodsBarCode.getText().toString());
                    if (!TextUtils.isEmpty(newStr)){
                        String str = sv.getQuery().toString();
                        //检验保税号是否输入
                        if (TextUtils.isEmpty(str.trim())){
                            Toast.makeText(getContext().getApplicationContext(),"保税号不能为空",Toast.LENGTH_LONG).show();
                            return false;
                        }
                        goodsBarCode.setText(newStr);
                        //匹配对应货号

                        //创建异步请求对象，表示一次请求
                        AsyncHttpClient client = new AsyncHttpClient();
                        //创建请求参数的封装对象
                        RequestParams params = new RequestParams();
                        params.put("msgtype", "getgoodsbar");
                        params.put("goodsBarCode", newStr);
                        params.put("bondedNo", str);
                        //执行POST
                        client.post(getURL(),params,new AsyncHttpResponseHandler(){
                            /**
                                * 成功处理的方法
                                * statusCode:响应的状态码; headers:相应的头信息 比如 响应的时间，响应的服务器 ;
                                * responseBody:响应内容的字节
                             */
                            @Override
                            public void onSuccess(int statusCode, Header[] headers,
                                                  byte[] responseBody) {
                                if (statusCode == 200) {
                                    if (!TextUtils.isEmpty(new String(responseBody))) {
                                        goodsRecordNo.setText(new String(responseBody));
                                        position.requestFocus();
                                    } else {
                                        Toast.makeText(getContext().getApplicationContext(), "未找到货号或者货号有多个,请手动输入!", Toast.LENGTH_LONG).show();
                                        goodsRecordNo.setText("");
                                    }
                                }
                            }
                            /**
                             * 失败处理的方法
                             * error：响应失败的错误信息封装到这个异常对象中
                             */
                            @Override
                            public void onFailure(int statusCode, Header[] headers,
                                                  byte[] responseBody, Throwable error) {
                                error.printStackTrace();// 把错误信息打印出轨迹来
                            }
                        });
                    }
                }
                return false;
            }
        });
        //设置保质期
        shelfLife.setOnClickListener(new EditText.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    SetDateDialog sdt = new SetDateDialog(shelfLife);
                    sdt.show(getFragmentManager(), "datePicker");
                }else {
                    shelfLife.setText("");
                }
            }
        }
        );
        //设置提交事件
        submit.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncHttpClient client = new AsyncHttpClient();
                //创建请求参数的封装对象
                RequestParams params = new RequestParams();
                params.put("msgtype", "storage");
                params.put("bondedNo",sv.getQuery().toString());//保税号
                params.put("itemNo",goodsRecordNo.getText().toString());//货号
                params.put("storageBox",position.getText().toString());//仓位
                params.put("quantity",quantity.getText().toString());//数量
                params.put("goodsBarCode",goodsBarCode.getText().toString());//条形码
                params.put("account",account);//用户
                params.put("remark",shelfLife.getText().toString());//保质期
                client.post(getURL(),params,new AsyncHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Toast.makeText(getContext().getApplicationContext(),new String(responseBody),Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        error.printStackTrace();
                    }
                });
            }
        });
        //初始化SearchView,MyListView
        getSpinnerValue(rootview);
        return rootview;
    }


    public void getSpinnerValue(final View rootview) {
        //调用后台的得到未入库的预入库数据
        AsyncHttpClient client = new AsyncHttpClient();
        // 创建请求参数的封装的对象
        RequestParams params = new RequestParams();
        params.put("msgtype", "getprestorage");
        // 执行post方法
        client.post(getURL(), params, new AsyncHttpResponseHandler() {
            /**
             * 成功处理的方法
             * statusCode:响应的状态码; headers:相应的头信息 比如 响应的时间，响应的服务器 ;
             * responseBody:响应内容的字节
             */
            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] responseBody) {
                if (statusCode == 200) {
                    m = new String(responseBody).split(",");
                    doInitSearch(m,rootview);
                }
            }

            /**
             * 失败处理的方法
             * error：响应失败的错误信息封装到这个异常对象中
             */
            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                error.printStackTrace();// 把错误信息打印出轨迹来
            }
        });
    }

    private void doInitSearch(final String[] m,View rootview) {
        lv = (ListView) rootview.findViewById(R.id.lv);
        lv.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, m));
        lv.setTextFilterEnabled(true);//设置lv可以被过虑
        sv = (SearchView) rootview.findViewById(R.id.sv);
        // 设置该SearchView默认是否自动缩小为图标
        sv.setIconifiedByDefault(false);
        // 为该SearchView组件设置事件监听器
        sv.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        sv.setSubmitButtonEnabled(true);
        // 设置该SearchView内默认显示的提示文本
        sv.setQueryHint("查找");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                sv.setQuery(((TextView) view).getText(), false);
                lv.setAdapter(null);
                sv.clearFocus();
            }
        });
    }

    //去掉String的空格键或者回车键
    private String getNewString(String oldStr){
        return oldStr.replaceAll("\n","").replaceAll(" ","");
    }
    //获取URL
    public String getURL(){
        return PropertitesUtil.getNetConfigProperties(getContext().getApplicationContext()).getProperty("Login");
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            // 清除ListView的过滤
            lv.clearTextFilter();
        } else {
            // 使用用户输入的内容对ListView的列表项进行过滤
            List<String> mList = new ArrayList<String>();
            for (String p: m) {
                if (p.contains(newText)) {
                    mList.add(p);
                }
            }
            adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,mList);
            lv.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        return true;
    }

}


