package com.example.henry.myapplication1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.myapplication1.util.MD5;
import com.example.henry.myapplication1.util.PropertitesUtil;
import com.example.henry.myapplication1.util.SysApplication;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.web.WebService;
import com.web.WebServicePost;

import org.apache.http.Header;
import org.apache.http.conn.ConnectTimeoutException;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    // 登陆按钮
    private Button logbtn;
    // 显示用户名和密码
    EditText username, password;
    // 创建等待框
    private static ProgressDialog dialog;
    CheckBox checkBox;
    // 返回主线程更新数据
    private Intent mIntent;
    private static Handler handler = new Handler();
    private String info;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将当前activity添加到activitylist
        SysApplication.getInstance().addActivity(this);
        username = (EditText) findViewById(R.id.accountText);
        password = (EditText) findViewById(R.id.passwordText);
        logbtn = (Button) findViewById(R.id.start);
        checkBox = (CheckBox) findViewById(R.id.login_checkbox);
        sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);//会自动生成config.xml

        // 设置按钮监听器
        logbtn.setOnClickListener(this);
        if (sharedPreferences.getBoolean("ischeck",false)){
            checkBox.setChecked(true);
            username.setText(sharedPreferences.getString("account",""));
            password.setText(sharedPreferences.getString("password",""));
        }
        //记住密码监听
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    sharedPreferences.edit().putBoolean("ischeck", true).commit();
                }else {
                    sharedPreferences.edit().putBoolean("ischeck", false).commit();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.start:
                // 检测网络，无法检测wifi
                if (!checkNetwork()){
                    Toast toast = Toast.makeText(MainActivity.this,"网络未连接", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    break;
                }
                // 提示框
                dialog = new ProgressDialog(this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();

                // 创建子线程，分别进行Get和Post传输
//                new Thread(new MyThread()).start();
                try {
                    loginByAsyncHttpClientPost(username.getText().toString(),password.getText().toString());
                }  catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
    }
    private boolean checkNetwork() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager.getActiveNetworkInfo() != null) {
            return connManager.getActiveNetworkInfo().isAvailable();
        }
        return false;
    }

    //使用AsyncHttpClientPost
    private void loginByAsyncHttpClientPost(final String username,final String password) throws UnsupportedEncodingException, NoSuchAlgorithmException, FileNotFoundException {
        AsyncHttpClient client = new AsyncHttpClient(); // 创建异步请求的客户端对象
        String url = PropertitesUtil.getNetConfigProperties(getApplicationContext()).getProperty("Login");
        String safeKey = PropertitesUtil.getNetConfigProperties(getApplicationContext()).getProperty("safeKey");
        RequestParams params = new RequestParams();
        params.put("msgtype","login");
        params.put("key",safeKey);
        params.put("username",username);
        //params.put("password", password);
        params.put("password", MD5.ecodeByMD5(password));
        // 发送get请求对象
        client.setTimeout(10000);
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            // statusCode:状态返回码，headers:头部请求信息，responseBody返回结果
            public void onSuccess(int statusCode, Header[] headers,
                                  byte[] responseBody) {
                if (statusCode == 200) {
                    String res = new String(responseBody);
                    if (res.equals("0")) {
                        Toast.makeText(getApplicationContext(), "账号或密码错误...", Toast.LENGTH_LONG).show();
                        dialog.dismiss(); //关闭进度条
                    } else {
                        dialog.dismiss(); //关闭进度条
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("account",username);
                        editor.putString("password",password);
                        editor.putString("level",res);
                        editor.putBoolean("checkBoolean",checkBox.isChecked());
                        editor.apply();
                        Intent intent = new Intent();
                        intent.setClass(MainActivity.this,Drawer_ListActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                // 输出错误信息
                error.printStackTrace();
                Toast.makeText(getApplicationContext(),"无法连接服务器...",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    // 子线程接收数据，主线程修改数据
    public class MyThread implements Runnable {
        @Override
        public void run() {
            //info = WebService.executeHttpGet(username.getText().toString(), password.getText().toString());
            info = WebServicePost.executeHttpPost(getApplicationContext(),username.getText().toString(), password.getText().toString());
            if (info == null){
                Toast.makeText(getApplicationContext(),"无法连接服务器...",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    if (info.equals("0")){
                        Toast.makeText(getApplicationContext(),"账号或密码错误...",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }else{
                        dialog.dismiss();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("account",username.getText().toString());
                        editor.putString("password",password.getText().toString());
                        editor.putBoolean("checkBoolean",checkBox.isChecked());
                        editor.apply();
                        mIntent=new Intent();
                        mIntent.setClass(MainActivity.this,Drawer_ListActivity.class);
                        startActivity(mIntent);
                    }
                }
            });
        }
    }
}
