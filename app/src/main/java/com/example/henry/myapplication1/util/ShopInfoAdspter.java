package com.example.henry.myapplication1.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henry.myapplication1.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by henry on 2017/2/4.
 */

public class ShopInfoAdspter extends BaseAdapter {
    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public ShopInfoAdspter(Context context,List<Map<String,Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public final class ViewHolder{
        public TextView shopName;
        public TextView companyName;
        public Button btn;
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
        ViewHolder viewHolder = new ViewHolder();
            if (position == 0){
                convertView = layoutInflater.inflate(R.layout.list_shopinfo,null);
            }else {
                convertView = layoutInflater.inflate(R.layout.lists_shopinfo,null);
            }
            viewHolder.shopName = (TextView) convertView.findViewById(R.id.shopName);
            viewHolder.companyName = (TextView) convertView.findViewById(R.id.companyName);
            viewHolder.btn = (Button) convertView.findViewById(R.id.delete);
            convertView.setTag(viewHolder);

        viewHolder.shopName.setText((String) data.get(position).get("shopName"));
        viewHolder.companyName.setText((String) data.get(position).get("companyName"));
        if (position != 0){
            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //删除店铺信息
                    new AlertDialog.Builder(context)
                            .setTitle("确认删除此店铺："+(String) data.get(position).get("shopName"))
                            .setPositiveButton("取消",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    closeDialog(dialog,true);
                                }
                            })
                            .setNegativeButton("确定",new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    AsyncHttpClient client = new AsyncHttpClient();
                                    RequestParams params = new RequestParams();
                                    params.put("msgtype", "deleteshop");
                                    params.put("shopName",(String) data.get(position).get("shopName"));
                                    client.post(getURL(),params,new AsyncHttpResponseHandler(){
                                        @Override
                                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                            String res = new String(responseBody);
                                            if (res.equals("1")){
                                                Toast.makeText(context.getApplicationContext(),"删除成功...",Toast.LENGTH_LONG).show();
                                            }else{
                                                Toast.makeText(context.getApplicationContext(),"此记录已不存在...",Toast.LENGTH_LONG).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                            super.onFailure(statusCode, headers, responseBody, error);
                                        }
                                    });
                                }
                            })
                            .setCancelable(false)
                            .show();
                }
            });
        }
        if(position % 2 == 0){
            convertView.setBackgroundResource(R.drawable.background_login);
        }
        return convertView;
    }
    //获取URL
    public String getURL() {
        return PropertitesUtil.getNetConfigProperties(context.getApplicationContext()).getProperty("GetShopInfo");
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
