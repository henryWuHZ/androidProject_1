package com.example.henry.myapplication1.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by henry on 2017/1/16.
 */

public class ShopInfo implements Parcelable {
    private String shopName;
    private String companyName;

    protected ShopInfo(Parcel in) {
        Bundle bundle = in.readBundle();
        shopName = bundle.getString("shopName");
        companyName = bundle.getString("companyName");
    }

    public static final Creator<ShopInfo> CREATOR = new Creator<ShopInfo>() {
        @Override
        public ShopInfo createFromParcel(Parcel in) {
            return new ShopInfo(in);
        }

        @Override
        public ShopInfo[] newArray(int size) {
            return new ShopInfo[size];
        }
    };

    public String getShopName() {
        return shopName;
    }
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public ShopInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();
        bundle.putString("shopName",getShopName());
        bundle.putString("companyName",getCompanyName());
        dest.writeBundle(bundle);
    }
}
