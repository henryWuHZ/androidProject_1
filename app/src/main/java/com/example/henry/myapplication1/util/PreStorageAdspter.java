package com.example.henry.myapplication1.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.henry.myapplication1.R;

import java.util.List;
import java.util.Map;

/**
 * Created by henry on 2017/2/21.
 */

public class PreStorageAdspter extends BaseAdapter{
    private List<Map<String,Object>> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public PreStorageAdspter(Context context,List<Map<String,Object>> data){
        this.context = context;
        this.data = data;
        this.layoutInflater = LayoutInflater.from(context);
    }
    public final class ViewHolder{
        public TextView bonded_no;
        public TextView store_name;
        public TextView freight_forwarding;
        public TextView confirmed;
        public TextView confirmed_date;
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
        convertView = layoutInflater.inflate(R.layout.list_pre_storage,null);
        viewHolder.bonded_no = (TextView) convertView.findViewById(R.id.preStorage_bonded_no);
        viewHolder.store_name = (TextView) convertView.findViewById(R.id.preStorage_store_name);
        viewHolder.freight_forwarding = (TextView) convertView.findViewById(R.id.preStorage_freight_forwarding);
        viewHolder.confirmed = (TextView) convertView.findViewById(R.id.preStorage_confirmed);
        viewHolder.confirmed_date = (TextView) convertView.findViewById(R.id.preStorage_confirmed_date);
        convertView.setTag(viewHolder);

        viewHolder.bonded_no.setText((String)data.get(position).get("bonded_no"));
        viewHolder.store_name.setText((String)data.get(position).get("store_name"));
        viewHolder.freight_forwarding.setText((String)data.get(position).get("freight_forwarding"));
        viewHolder.confirmed.setText((String)data.get(position).get("confirmed"));
        viewHolder.confirmed_date.setText((String)data.get(position).get("confirmed_date"));
        if(position % 2 == 0){
            convertView.setBackgroundResource(R.drawable.background_login);
        }
        return convertView;
    }


}
