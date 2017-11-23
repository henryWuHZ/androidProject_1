package com.example.henry.myapplication1;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.henry.myapplication1.mycompnent.ClearEditText;
import com.example.henry.myapplication1.util.PropertitesUtil;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;


public class EnterpriseRecord extends Fragment {
    private ClearEditText enterpriseName;
    private ClearEditText simpleName;
    private ClearEditText enterpriseType;
    private ClearEditText property;
    private ClearEditText contactName;
    private ClearEditText contactTel;
    private ClearEditText address;
    private ClearEditText legalName;
    private ClearEditText legalTel;
    private ClearEditText website;
    private ClearEditText customsCode;
    private ClearEditText country;
    private ClearEditText city;
    private ClearEditText email;
    private ClearEditText faxTel;
    private ClearEditText taxCode;
    private ClearEditText busiLicense;
    private Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_enterprise_record, container, false);
        enterpriseName = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_name);
        simpleName = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_simpleName);
        enterpriseType = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_type);
        property = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_property);
        contactName = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_contactName);
        contactTel = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_contactPhone);
        address = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_address);

        legalName = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_legalName);
        legalTel = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_legalPhone);
        website = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_website);
        customsCode = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_customsCode);
        country = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_country);
        city = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_city);
        email = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_email);
        faxTel = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_faxTel);
        taxCode = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_taxCode);
        busiLicense = (ClearEditText) rootview.findViewById(R.id.enterpriseRecord_busiLicense);
        btn = (Button) rootview.findViewById(R.id.enterpriseRecord_submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (enterpriseName.getText().toString().equals("")
                        ||enterpriseType.getText().toString().equals("")
                        ||simpleName.getText().toString().equals("")
                        ||property.getText().toString().equals("")
                        ||contactName.getText().toString().equals("")
                        ||contactTel.getText().toString().equals("")
                        ||address.getText().toString().equals("")){
                    Toast.makeText(getContext(),"必填项不能为空...",Toast.LENGTH_LONG).show();
                }else {
                    AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.put("msgtype","addEnterprise");
                    params.put("enterpriseName",enterpriseName.getText().toString());
                    params.put("simpleName",simpleName.getText().toString());
                    params.put("enterpriseType",enterpriseType.getText().toString());
                    params.put("property",property.getText().toString());
                    params.put("contactName",contactName.getText().toString());
                    params.put("contactTel",contactTel.getText().toString());
                    params.put("address",address.getText().toString());

                    params.put("legalName",legalName.getText().toString());
                    params.put("legalTel",legalTel.getText().toString());
                    params.put("website",website.getText().toString());
                    params.put("customsCode",customsCode.getText().toString());
                    params.put("country",country.getText().toString());
                    params.put("city",city.getText().toString());
                    params.put("email",email.getText().toString());
                    params.put("faxTel",faxTel.getText().toString());
                    params.put("taxCode",taxCode.getText().toString());
                    params.put("busiLicense",busiLicense.getText().toString());

                    client.post(getURL(),params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            String res = new String(responseBody);
                            if (res.equals("1")){
                                Toast.makeText(getContext(),"备案成功...",Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(getContext(),"备案失败，请核对信息...",Toast.LENGTH_LONG).show();
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
        return PropertitesUtil.getNetConfigProperties(getContext().getApplicationContext()).getProperty("enterpriseMng");
    }
}
