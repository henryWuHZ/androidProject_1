package com.example.henry.myapplication1.util;

import android.content.Context;

import com.example.henry.myapplication1.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertitesUtil {
    /**
     * 得到netconfig.properties配置文件中的所有配置属性
     *
     * @return Properties对象
     */
    public static Properties getNetConfigProperties(Context context){
        Properties props = new Properties();
        InputStream in = context.getResources().openRawResource(R.raw.netconfig);
        try {
           props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
