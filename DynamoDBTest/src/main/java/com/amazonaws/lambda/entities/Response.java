package com.amazonaws.lambda.entities;

import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;

public class Response {
private String status;
private String msg;
private List<Clients> clients_details;
private PutItemOutcome result;

public PutItemOutcome getResult() {
	return result;
}
public void setResult(PutItemOutcome result) {
	this.result = result;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public List<Clients> getClients_details() {
	return clients_details;
}
public void setClients_details(List<Clients> clients_details) {
	this.clients_details = clients_details;
}





}
