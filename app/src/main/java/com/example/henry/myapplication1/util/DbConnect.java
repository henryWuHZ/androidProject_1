package com.example.henry.myapplication1.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by henry on 2016/12/8.
 */

public class DbConnect implements Runnable{
    boolean isSuccess=false;
    @Override
    public void run() {
        Connection connection=null;
        try {
            String driver="com.mysql.jdbc.Driver";
            Class.forName(driver);
            String url="jdbc:mysql://172.22.0.248:3306/wms_henry";
            String username="root";
            String pwd="root";
            connection= DriverManager.getConnection(url,username,pwd);
            if (connection!=null){
                isSuccess=true;
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void start(){
        Thread t = new Thread(this);
        t.start();
    }
}
