package com.example.henry.myapplication1.model;

public class EnterpriseRecord {
	private String name;
	private String code;
	private String type;
	private String date;
	private String distribution_enable;
	public EnterpriseRecord() {
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDistribution_enable() {
		return distribution_enable;
	}
	public void setDistribution_enable(String distribution_enable) {
		this.distribution_enable = distribution_enable;
	}
	@Override
	public String toString() {
		return "EnterpriseRecord [name=" + name + ", code=" + code + ", type=" + type + ", date=" + date
				+ ", distribution_enable=" + distribution_enable + "]";
	}
	
}
