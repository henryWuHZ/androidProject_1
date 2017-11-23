package com.example.henry.myapplication1.model;

public class PreStorageInfo {
//保税号，电商名，货代名，入库状态，入库时间
	private String bonded_no;
	private String store_name;
	private String freight_forwarding;
	private String confirmed;
	private String confirmed_date;
	public String getBonded_no() {
		return bonded_no;
	}
	public void setBonded_no(String bonded_no) {
		this.bonded_no = bonded_no;
	}
	public String getStore_name() {
		return store_name;
	}
	public void setStore_name(String store_name) {
		this.store_name = store_name;
	}
	public String getFreight_forwarding() {
		return freight_forwarding;
	}
	public void setFreight_forwarding(String freight_forwarding) {
		this.freight_forwarding = freight_forwarding;
	}
	public String getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(String confirmed) {
		this.confirmed = confirmed;
	}
	public String getConfirmed_date() {
		return confirmed_date;
	}
	public void setConfirmed_date(String confirmed_date) {
		this.confirmed_date = confirmed_date;
	}
	@Override
	public String toString() {
		return "PreStorageInfo [bonded_no=" + bonded_no + ", store_name=" + store_name + ", freight_forwarding="
				+ freight_forwarding + ", confirmed=" + confirmed + ", confirmed_date=" + confirmed_date + "]";
	}
	
}
