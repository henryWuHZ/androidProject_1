package com.example.henry.myapplication1.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.henry.myapplication1.R;

import java.util.List;
import java.util.Map;

/**
 * Created by henry on 2017/2/14.
 */

public class EnterpriseRecordAdspter extends BaseAdapter{
    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;
    public EnterpriseRecordAdspter(Context context,List<Map<String,Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public final class ViewHolder{
        public TextView name;
        public TextView code;
        public TextView type;
        public TextView date;
        public TextView distribution_enable;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        convertView = layoutInflater.inflate(R.layout.list_enterprise_record,null);
        viewHolder.name = (TextView) convertView.findViewById(R.id.enterpriseSearch_name);
        viewHolder.code = (TextView) convertView.findViewById(R.id.enterpriseSearch_code);
        viewHolder.type = (TextView) convertView.findViewById(R.id.enterpriseSearch_type);
        viewHolder.date = (TextView) convertView.findViewById(R.id.enterpriseSearch_date);
        viewHolder.distribution_enable = (TextView) convertView.findViewById(R.id.enterpriseSearch_distribution_enable);
        convertView.setTag(viewHolder);

        viewHolder.name.setText((String)data.get(position).get("name"));
        viewHolder.code.setText((String)data.get(position).get("code"));
        viewHolder.type.setText((String)data.get(position).get("type"));
        viewHolder.date.setText((String)data.get(position).get("date"));
        viewHolder.distribution_enable.setText((String)data.get(position).get("distribution_enable"));
        if(position % 2 == 0){
            convertView.setBackgroundResource(R.drawable.background_login);
        }
        return convertView;
    }
}
