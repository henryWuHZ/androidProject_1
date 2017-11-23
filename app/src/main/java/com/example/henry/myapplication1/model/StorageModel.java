package com.example.henry.myapplication1.model;

import java.util.Date;

public class StorageModel {
    private Integer storageId;
    private String companyName;
    private String goodsRecordCode;
    private String bondedNo;
    private int quantity;
    private String goodsDesc;
    private Date shiftTime;

    public StorageModel(){}

    public StorageModel(Integer storageId, String companyName, String goodsRecordCode, String bondedNo, int quantity, String goodsDesc, Date shiftTime) {
        this.storageId = storageId;
        this.companyName = companyName;
        this.goodsRecordCode = goodsRecordCode;
        this.bondedNo = bondedNo;
        this.quantity = quantity;
        this.goodsDesc = goodsDesc;
        this.shiftTime = shiftTime;
    }

    public Integer getStorageId() {
        return storageId;
    }

    public void setStorageId(Integer storageId) {
        this.storageId = storageId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getGoodsRecordCode() {
        return goodsRecordCode;
    }

    public void setGoodsRecordCode(String goodsRecordCode) {
        this.goodsRecordCode = goodsRecordCode;
    }

    public String getBondedNo() {
        return bondedNo;
    }

    public void setBondedNo(String bondedNo) {
        this.bondedNo = bondedNo;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Date getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(Date shiftTime) {
        this.shiftTime = shiftTime;
    }
}
