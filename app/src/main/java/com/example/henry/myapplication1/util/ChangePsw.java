package com.example.henry.myapplication1.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.henry.myapplication1.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by henry on 2017/2/9.
 */

public class ChangePsw extends Fragment{
    private SharedPreferences sharedPreferences;
    private Button btn;
    private EditText editText;
    private EditText editText1;
    private String firstpsw;
    private String secondpsw;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.changepsw,container,false);
        editText = (EditText) rootview.findViewById(R.id.changepsw_editText);
        editText1 = (EditText) rootview.findViewById(R.id.changepsw_editText2);
        btn = (Button) rootview.findViewById(R.id.changepsw_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstpsw = editText.getText().toString();
                secondpsw = editText1.getText().toString();
                if (firstpsw.equals("")||secondpsw.equals("")){
                    Toast.makeText(getContext(),"请输入密码...",Toast.LENGTH_LONG).show();
                }
                else {
                    if (firstpsw.equals(secondpsw)){
                        AsyncHttpClient client = new AsyncHttpClient();
                        RequestParams params = new RequestParams();
                        params.put("msgtype","changpsw");
                        sharedPreferences = getContext().getSharedPreferences("config", Context.MODE_PRIVATE);
                        params.put("username",sharedPreferences.getString("account",""));
                        try {
                            params.put("newpsw",MD5.ecodeByMD5(firstpsw));
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        client.post(getURL(),params,new AsyncHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                String res = new String(responseBody);
                                if (res.equals("1")){
                                    Toast.makeText(getContext(),"修改成功...",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getContext(),"修改失败...",Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                super.onFailure(statusCode, headers, responseBody, error);
                            }
                        });
                    }
                    else {
                        Toast.makeText(getContext(),"两次密码不匹配...",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        return rootview;
    }
        //获取URL
        public String getURL() {
            return PropertitesUtil.getNetConfigProperties(getContext()).getProperty("Login");
        }
}
