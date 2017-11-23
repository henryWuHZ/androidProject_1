package com.example.henry.myapplication1.model;

public class OrderState {
	private String distribution_status;
	private String customs_state;
	private String inspection_state;
	private String out_state;
	private String order_state;
	private String recheck_state;
	private String expressId;
	private String externalId;
	private String shopName;
	
	public String getDistribution_status() {
		return distribution_status;
	}
	public void setDistribution_status(String distribution_status) {
		this.distribution_status = distribution_status;
	}
	public String getOut_state() {
		return out_state;
	}
	public void setOut_state(String out_state) {
		this.out_state = out_state;
	}
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	public String getRecheck_state() {
		return recheck_state;
	}
	public void setRecheck_state(String recheck_state) {
		this.recheck_state = recheck_state;
	}
	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getCustoms_state() {
		return customs_state;
	}
	public void setCustoms_state(String customs_state) {
		this.customs_state = customs_state;
	}
	public String getInspection_state() {
		return inspection_state;
	}
	public void setInspection_state(String inspection_state) {
		this.inspection_state = inspection_state;
	}
	@Override
	public String toString() {
		return "OrderState [distribution_status=" + distribution_status + ", customs_state=" + customs_state
				+ ", inspection_state=" + inspection_state + ", out_state=" + out_state + ", order_state=" + order_state
				+ ", recheck_state=" + recheck_state + ", expressId=" + expressId + ", externalId=" + externalId
				+ ", shopName=" + shopName + "]";
	}

	
}
