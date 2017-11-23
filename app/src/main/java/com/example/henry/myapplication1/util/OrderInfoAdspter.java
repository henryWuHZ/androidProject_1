package com.example.henry.myapplication1.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.henry.myapplication1.OrderInquiry;
import com.example.henry.myapplication1.R;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * Created by henry on 2017/2/17.
 */

public class OrderInfoAdspter extends BaseAdapter{
    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;


    public OrderInfoAdspter(Context context,List<Map<String,Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public final class ViewHolder{
        public TextView expressID;
        public TextView externalID;
        public TextView shopName;
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
        final ViewHolder viewHolder = new ViewHolder();
        if (position == 0){
            convertView = layoutInflater.inflate(R.layout.list_orderinfo,null);
        }else {
            convertView = layoutInflater.inflate(R.layout.lists_orderinfo,null);
        }
        viewHolder.expressID = (TextView) convertView.findViewById(R.id.orderInfo_expressId);
        viewHolder.externalID = (TextView) convertView.findViewById(R.id.orderInfo_externalId);
        viewHolder.shopName = (TextView) convertView.findViewById(R.id.orderInfo_shopname);
        viewHolder.btn = (Button) convertView.findViewById(R.id.orderInfo_btn);
        convertView.setTag(viewHolder);
        if (position != 0){
            viewHolder.expressID.setText((String)data.get(position).get("expressId"));
            viewHolder.externalID.setText((String)data.get(position).get("externalId"));
            viewHolder.shopName.setText((String)data.get(position).get("shopName"));
            viewHolder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = layoutInflater.inflate(R.layout.order_status_detail,null);
                    ((TextView)view.findViewById(R.id.customs_state)).setText("海关审核状态:"+data.get(position).get("customsState").toString());
                    ((TextView)view.findViewById(R.id.inspection_state)).setText("国检审核状态:"+data.get(position).get("inspectionState").toString());
                    ((TextView)view.findViewById(R.id.order_state)).setText("订单状态:"+data.get(position).get("orderState").toString());
                    ((TextView)view.findViewById(R.id.distribution_state)).setText("配货状态:"+data.get(position).get("distributionState").toString());
                    ((TextView)view.findViewById(R.id.fuhe_state)).setText("复核状态:"+data.get(position).get("recheckState").toString());
                    ((TextView)view.findViewById(R.id.out_state)).setText("出库状态:"+data.get(position).get("outState").toString());
                    new AlertDialog.Builder(context)
                            .setTitle("订单状态详情")
                            .setCancelable(true)
                            .setView(view)
                            .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    closeDialog(dialog,true);

                                }
                })
                            .show();
                }
            });
        }
        return convertView;
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
