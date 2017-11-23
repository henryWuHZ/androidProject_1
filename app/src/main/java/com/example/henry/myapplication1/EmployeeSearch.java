package com.example.henry.myapplication1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.henry.myapplication1.model.Employee;
import com.example.henry.myapplication1.mycompnent.ClearEditText;
import com.example.henry.myapplication1.util.PropertitesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

/**
 * Created by henry on 2017/2/10.
 */

public class EmployeeSearch extends Fragment{
    private ClearEditText jobId;
    private Button btn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.employeemng_searchemp,container,false);
        jobId = (ClearEditText) rootview.findViewById(R.id.employee_search_jobId);
        btn = (Button) rootview.findViewById(R.id.employee_search_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jobid = jobId.getText().toString();
                if (jobid.equals("")){
                    Toast.makeText(getContext(),"工号不能为空...",Toast.LENGTH_LONG).show();
                }else {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("msgtype","getemp");
                    params.put("jobId",jobid);
                    client.post(getURL(),params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String jsString = new String(responseBody);
                            List<Employee> list = JSON.parseArray(jsString,Employee.class);
                            if (list.size()== 0){
                                Toast.makeText(getContext(),"无此员工信息,请核对工号...",Toast.LENGTH_LONG).show();
                            }else {
                                Employee employee = list.get(0);
                                EmployeeInfo employeeInfo = new EmployeeInfo();
                                Bundle bundle = new Bundle();
                                bundle.putString("name",employee.getName());
                                bundle.putString("sex",employee.getSex());
                                bundle.putString("birthday",employee.getBirthday());
                                bundle.putString("jobId",employee.getJobId());
                                bundle.putString("address",employee.getAddress());
                                bundle.putString("phone",employee.getPhone());
                                bundle.putString("identitycard",employee.getIdentityCard());
                                bundle.putString("email",employee.getEmail());
                                bundle.putString("department",employee.getDepartment());
                                bundle.putString("joinDate",employee.getJoinDate());
                                employeeInfo.setArguments(bundle);
                                getFragmentManager().beginTransaction().replace(R.id.employeeMng_fragment_content,employeeInfo).commit();
                            }
                        }
                    });
                }
            }
        });

        return rootview;
    }
    //获取URL
    public String getURL(){
        return PropertitesUtil.getNetConfigProperties(getContext().getApplicationContext()).getProperty("employeeMng");
    }
}
