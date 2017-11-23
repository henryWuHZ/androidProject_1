package com.example.henry.myapplication1.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.myapplication1.R;
import com.example.henry.myapplication1.mycompnent.ClearEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by henry on 2017/1/11.
 */

public class MyAdspter extends BaseAdapter{
    private List<Map<String, Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;

    public MyAdspter(Context context,List<Map<String, Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public final class ViewHolder{
        public TextView quantity;
        public TextView goodsDesc;
        public TextView shifttime;
        public Button viewBtn;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            if(position == 0){
                convertView = layoutInflater.inflate(R.layout.list_changebox,null);
            }else {
                convertView = layoutInflater.inflate(R.layout.lists_changebox,null);
            }
            viewHolder.goodsDesc = (TextView) convertView.findViewById(R.id.goodsDesc);
            viewHolder.quantity = (TextView) convertView.findViewById(R.id.quantity);
            viewHolder.shifttime = (TextView) convertView.findViewById(R.id.shifttime);
            viewHolder.viewBtn = (Button) convertView.findViewById(R.id.view_btn);
            sharedPreferences = context.getSharedPreferences("config",Context.MODE_PRIVATE);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.goodsDesc.setText((String) data.get(position).get("goodsDesc"));
        viewHolder.quantity.setText(data.get(position).get("quantity").toString());
        viewHolder.shifttime.setText((String) data.get(position).get("shifttime"));
        if (position != 0){
            viewHolder.viewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //编辑按钮监听器
                    showinfo(data.get(position),MyAdspter.this);
                }
            });
        }
        if(position % 2 == 0){
            convertView.setBackgroundResource(R.drawable.background_login);
        }
        return convertView;
    }
    //调仓按钮事件
    public void showinfo(final Map data, final MyAdspter adapter){
        final View view = layoutInflater.inflate(R.layout.change_cangwei,null);
        ((TextView)view.findViewById(R.id.bondedNo)).setText("保税号:"+data.get("bondedNo").toString());
        ((TextView)view.findViewById(R.id.goodsRecordCode)).setText("货号:"+data.get("goodsRecordCode").toString());
        final ClearEditText targetPosition = (ClearEditText) view.findViewById(R.id.targetPosition);
        final ClearEditText quantity = (ClearEditText) view.findViewById(R.id.quantity);
        final ClearEditText remark = (ClearEditText) view.findViewById(R.id.remark);
        //去除输入的空格跟回车
        targetPosition.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (KeyEvent.KEYCODE_ENTER == keyCode) {
                    targetPosition.setText(MyStringUtil.getString(targetPosition.getText().toString()));
                }
                return false;
            }
        });
        quantity.setInputType(InputType.TYPE_NULL);
        new AlertDialog.Builder(context)
                .setTitle("调仓(电商名称:"+data.get("companyName").toString()+")")
                .setView(view)
                .setPositiveButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        closeDialog(dialog,true);
                    }
                })
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        if (TextUtils.isEmpty(targetPosition.getText().toString().trim())){
                            Toast.makeText(context,"请输入目标仓位",Toast.LENGTH_LONG).show();
                            closeDialog(dialog,false);
                            return;
                        }
                        if (TextUtils.isEmpty(quantity.getText().toString().trim())){
                            Toast.makeText(context,"请输入数量",Toast.LENGTH_LONG).show();
                            closeDialog(dialog,false);
                            return;
                        }
                        String account = sharedPreferences.getString("account","");
                        AsyncHttpClient client = new AsyncHttpClient();
                        String storageId = data.get("storageId").toString();
                        String targetPositin = targetPosition.getText().toString().trim();
                        final String quantitys = quantity.getText().toString().trim();
                        String rek = remark.getText().toString();
                        if (TextUtils.isEmpty(rek)){
                            rek="null";
                        }
                        String url = PropertitesUtil.getNetConfigProperties(context).getProperty("Login");
                        RequestParams params = new RequestParams();
                        params.put("msgtype","dochangewarhouse");
                        params.put("storageId",storageId);
                        params.put("quantity",quantitys);
                        params.put("targetPosition",targetPositin);
                        params.put("remark",rek);
                        params.put("account",account);
                        progressDialog = ProgressDialog.show(context,"","正在提交...");
                        client.post(url,params,new AsyncHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                if (statusCode == 200){
                                    closeDialog(dialog,true);
                                    Toast.makeText(context,new String(responseBody),Toast.LENGTH_LONG).show();
                                    if ("调仓成功".equals(new String(responseBody))){
                                        data.put("quantity",Integer.parseInt(data.get("quantity").toString())-Integer.parseInt(quantitys));
                                        adapter.notifyDataSetChanged();
                                    }
                                    progressDialog.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                error.printStackTrace();
                                progressDialog.dismiss();
                            }
                        });
                    }
                })
                .setCancelable(false)
                .show();
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
