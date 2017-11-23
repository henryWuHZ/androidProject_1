package com.example.henry.myapplication1.util;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.example.henry.myapplication1.mycompnent.ClearEditText;

import java.util.Calendar;

/**
 * Created by henry on 2017/1/6.
 */

public class SetDateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    ClearEditText shelfLife;
    public SetDateDialog(ClearEditText clearEditText){
        shelfLife=clearEditText;
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Calendar 是一个抽象类，是通过getInstance()来获得实例,设置成系统默认时间
        final Calendar c = Calendar.getInstance();
        //获取年，月，日
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day= c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year, month, day);

        return dpd;
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        shelfLife.setText(year+"-"+(month+1)+"-"+day);
    }

}
