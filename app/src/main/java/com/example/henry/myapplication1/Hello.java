package com.example.henry.myapplication1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ant.liao.GifView;

/**
 * Created by henry on 2017/1/6.
 */

public class Hello extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.hello,container,false);
        //GifView gifView = (GifView) rootview.findViewById(R.id.gif1);
        //gifView.setGifImage(R.drawable.gif1);
        return rootview;
    }
}
