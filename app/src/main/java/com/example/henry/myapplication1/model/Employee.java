package com.example.henry.myapplication1.model;

public class Employee {
	private String name;
	private String sex;
	private String birthday;
	private String jobId;
	private String address;
	private String phone;
	private String identityCard;
	private String email;
	private String joinDate;
	private String department;
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", sex=" + sex + ", birthday=" + birthday + ", jobId=" + jobId + ", address="
				+ address + ", phone=" + phone + ", identityCard=" + identityCard + ", email=" + email + ", joinDate="
				+ joinDate + ", department=" + department + "]";
	}

}
