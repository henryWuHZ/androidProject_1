package com.example.henry.myapplication1;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.henry.myapplication1.mycompnent.ClearEditText;
import com.example.henry.myapplication1.util.PropertitesUtil;
import com.example.henry.myapplication1.util.SetDateDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by henry on 2017/2/10.
 */

public class EmployeeAdd extends Fragment{
    private ClearEditText name;
    private ClearEditText sex;
    private ClearEditText birthday;
    private ClearEditText jobId;
    private ClearEditText address;
    private ClearEditText phone;
    private ClearEditText identitycard;
    private ClearEditText email;
    private ClearEditText joindate;
    private ClearEditText department;
    private Button btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.employeemng_addemp,container,false);
        name = (ClearEditText) rootview.findViewById(R.id.employee_add_name);
        sex = (ClearEditText) rootview.findViewById(R.id.employee_add_sex);
        birthday = (ClearEditText) rootview.findViewById(R.id.employee_add_birthday);
        jobId = (ClearEditText) rootview.findViewById(R.id.employee_add_jobid);
        address = (ClearEditText) rootview.findViewById(R.id.employee_add_address);
        phone = (ClearEditText) rootview.findViewById(R.id.employee_add_phone);
        identitycard = (ClearEditText) rootview.findViewById(R.id.employee_add_identitycard);
        email = (ClearEditText) rootview.findViewById(R.id.employee_add_email);
        department = (ClearEditText) rootview.findViewById(R.id.employee_add_department);
        joindate = (ClearEditText) rootview.findViewById(R.id.employee_add_joindate);
        btn = (Button) rootview.findViewById(R.id.employee_add_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")
                        || sex.getText().toString().equals("")
                        ||birthday.getText().toString().equals("")
                        ||jobId.getText().toString().equals("")
                        ||phone.getText().toString().equals("")
                        ||identitycard.getText().equals("")
                        ||joindate.getText().toString().equals("")
                        ||department.getText().toString().equals("")){
                    Toast.makeText(getContext(),"带*的选项不能为空...",Toast.LENGTH_LONG).show();
                }
                else if(!(department.getText().toString().equals("1100")
                        ||department.getText().toString().equals("1101")
                        ||department.getText().toString().equals("1102")
                        ||department.getText().toString().equals("1103")
                        ||department.getText().toString().equals("1000"))){
                    Toast.makeText(getContext(),"请输入正确部门编号...",Toast.LENGTH_LONG).show();
                }
                else {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("msgtype","addemp");
                    params.put("name",name.getText().toString());
                    params.put("sex",sex.getText().toString());
                    params.put("birthday",birthday.getText().toString());
                    params.put("jobId",jobId.getText().toString());
                    params.put("address",address.getText().toString());
                    params.put("phone",phone.getText().toString());
                    params.put("identitycard",identitycard.getText().toString());
                    params.put("email",email.getText().toString());
                    params.put("department",department.getText().toString());
                    params.put("joinDate",joindate.getText().toString());
                    client.post(getURL(),params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String res = new String(responseBody);
                            if (res.equals("1")){
                                Toast.makeText(getContext(),"添加成功",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getContext(),"添加失败,请确认填写信息...",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
        joindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDateDialog sdt = new SetDateDialog(joindate);
                sdt.show(getFragmentManager(), "datePicker");
            }
        });
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDateDialog sdt = new SetDateDialog(birthday);
                sdt.show(getFragmentManager(), "datePicker");
            }
        });
        return rootview;
    }
    //获取URL
    public String getURL(){
        return PropertitesUtil.getNetConfigProperties(getContext().getApplicationContext()).getProperty("employeeMng");
    }
}
