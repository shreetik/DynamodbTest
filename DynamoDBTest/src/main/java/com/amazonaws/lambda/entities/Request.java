package com.amazonaws.lambda.entities;

public class Request {

	private String action;
	private String region;
	private String customer_Id;
	private String client_name;
	private String company_name;
	private String phone_no;
	private String email_id;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCustomer_Id() {
		return customer_Id;
	}

	public void setCustomer_Id(String customer_Id) {
		this.customer_Id = customer_Id;
	}

	public String getClient_name() {
		return client_name;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	@Override
	public String toString() {
		return "Request [action=" + action + ", region=" + region + ", customer_Id=" + customer_Id + ", client_name="
				+ client_name + ", company_name=" + company_name + ", phone_no=" + phone_no + ", email_id=" + email_id
				+ "]";
	}

	
}
