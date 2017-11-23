package com.example.henry.myapplication1.util;

public class MyStringUtil {

    public static String getString(String str){
        return str.replaceAll("\n","").replaceAll(" ","");
    }
}
