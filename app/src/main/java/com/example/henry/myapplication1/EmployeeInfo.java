package com.example.henry.myapplication1;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.myapplication1.util.PropertitesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.lang.reflect.Field;


public class EmployeeInfo extends Fragment {
    private TextView name;
    private TextView sex;
    private TextView birthday;
    private TextView jobId;
    private TextView address;
    private TextView phone;
    private TextView identitycard;
    private TextView email;
    private TextView joindate;
    private TextView department;
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_employee_info, container, false);
        name = (TextView) rootview.findViewById(R.id.employee_info_name);
        sex = (TextView) rootview.findViewById(R.id.employee_info_sex);
        birthday = (TextView) rootview.findViewById(R.id.employee_info_birthday);
        jobId = (TextView) rootview.findViewById(R.id.employee_info_jobid);
        address = (TextView) rootview.findViewById(R.id.employee_info_address);
        phone = (TextView) rootview.findViewById(R.id.employee_info_phone);
        identitycard = (TextView) rootview.findViewById(R.id.employee_info_identitycard);
        email = (TextView) rootview.findViewById(R.id.employee_info_email);
        joindate = (TextView) rootview.findViewById(R.id.employee_info_joindate);
        department = (TextView) rootview.findViewById(R.id.employee_info_department);
        btn = (Button) rootview.findViewById(R.id.employee_info_submit);
        Bundle bundle = getArguments();
        name.setText(bundle.getString("name"));
        sex.setText(bundle.getString("sex"));
        birthday.setText(bundle.getString("birthday"));
        jobId.setText(bundle.getString("jobId"));
        address.setText(bundle.getString("address"));
        phone.setText(bundle.getString("phone"));
        identitycard.setText(bundle.getString("identitycard"));
        email.setText(bundle.getString("email"));
        joindate.setText(bundle.getString("joinDate"));
        department.setText(bundle.getString("department"));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext())
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
                                AsyncHttpClient client = new AsyncHttpClient();
                                RequestParams params = new RequestParams();
                                params.put("msgtype","deleteemp");
                                params.put("jobId",jobId.getText().toString());
                                client.post(getURL(),params,new AsyncHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        String res = new String(responseBody);
                                        if (res.equals("1")){
                                            Toast.makeText(getContext(),"删除成功...",Toast.LENGTH_LONG).show();
                                            closeDialog(dialog,true);
                                        }else {
                                            Toast.makeText(getContext(),"操作失败,用户已不存在...",Toast.LENGTH_LONG).show();
                                            closeDialog(dialog,true);
                                        }
                                    }
                                });
                            }
                        })
                        .setTitle("确认删除？(此操作不可复原！)")
                        .show();
            }
        });
        return rootview;
    }
    //获取URL
    public String getURL(){
        return PropertitesUtil.getNetConfigProperties(getContext().getApplicationContext()).getProperty("employeeMng");
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
