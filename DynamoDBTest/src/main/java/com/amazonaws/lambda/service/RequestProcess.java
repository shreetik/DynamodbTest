package com.amazonaws.lambda.service;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.amazonaws.lambda.entities.Clients;
import com.amazonaws.lambda.entities.Response;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.ScanOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;


public class RequestProcess {

	public Response doProcess(String action,String region,Clients clients) {
		
		Response res = new Response();
		List<Clients> cList = new ArrayList<Clients>();
		Clients cobj = new Clients();	
			switch (action) {
			
			case "save":
			{
				System.out.println("saving....");
				System.out.println(clients);
				AmazonDynamoDB client = AmazonDynamoDBClientBuilder
										.standard()
										.withRegion(region)
										.build();
				
				DynamoDB dynamoDB = new DynamoDB(client);
				
				Table table =	dynamoDB.getTable("Test_Customer_Details");
				
				String customer_Id = clients.getCustomer_Id();
				System.out.println("id "+customer_Id);
				
			/*
			 * final Map<String, Object> infoMap = new HashMap<String, Object>();
			 * 
			 * infoMap.put("customer_Id", clients.getCustomer_Id());
			 * infoMap.put("client_name", clients.getClient_name());
			 * infoMap.put("company_name", clients.getCompany_name());
			 * infoMap.put("phone_no", clients.getPhone_no()); infoMap.put("email_id",
			 * clients.getEmail_id());
			 */
				
				
				try {
					
				//	PutItemOutcome outcome = table.putItem(new Item().withPrimaryKey("customer_Id",customer_Id).withMap("clients", infoMap));
					
					Item item = new Item().withPrimaryKey("customer_Id ",customer_Id)
										  .withString("client_name", clients.getClient_name())
										  .withString("company_name", clients.getCompany_name())
										  .withString("phone_no", clients.getPhone_no())
										  .withString("email_id", clients.getEmail_id());
					
					PutItemOutcome data = table.putItem(item);
						
						res.setStatus("200");
						res.setMsg("Data saved successfully");
						res.setResult(data);
						res.setClients_details(null);
						
						return res;
					
					
				} catch (Exception e) {
					e.printStackTrace();
					res.setStatus("400");
					res.setMsg("Data not saved");
					res.setResult(null);
					res.setClients_details(null);
					
					return res;
					
				}
					 
				
			}
			
			case "fetch":
			{
				AmazonDynamoDB client = AmazonDynamoDBClientBuilder
						.standard()
						.withRegion(region)
						.build();

				DynamoDB dynamoDB = new DynamoDB(client);

				Table table =	dynamoDB.getTable("Test_Customer_Details");

				String customer_Id = clients.getCustomer_Id();
				
				GetItemSpec spec = new GetItemSpec().withPrimaryKey("customer_Id ",customer_Id);
				
				try {
					 Item outcome = table.getItem(spec);
					 
					 if(Objects.nonNull(outcome)) {
						cobj.setCustomer_Id(outcome.get("customer_Id ").toString()); 
						cobj.setClient_name(outcome.get("client_name").toString());
						cobj.setCompany_name(outcome.get("company_name").toString());
						cobj.setPhone_no(outcome.get("phone_no").toString());
						cobj.setEmail_id(outcome.get("email_id").toString());
						
						cList.add(cobj);
					 }
					 
					 res.setStatus("200");
					 res.setMsg("Data retrieved successfully");
					 res.setClients_details(cList);
					 res.setResult(null);
					 
					 return res;
					 
				} catch (Exception e) {
					
					e.printStackTrace();
					res.setStatus("400");
					res.setMsg("Data retrive error");
					res.setResult(null);
					res.setClients_details(null);
					
					return res;	
				}
				
				
			}
			
			case "fetchAll" :
			{
				AmazonDynamoDB client = AmazonDynamoDBClientBuilder
						.standard()
						.withRegion(region)
						.build();

				DynamoDB dynamoDB = new DynamoDB(client);

				Table table =	dynamoDB.getTable("Test_Customer_Details");
				
				ScanSpec scanSpec = new ScanSpec().withProjectionExpression("customer_Id ,client_name,company_name,phone_no,email_id");
				
				try {
					
					ItemCollection<ScanOutcome> items = table.scan(scanSpec);
					
					Iterator<Item> iter = items.iterator();
					while (iter.hasNext()) {
						Item item = iter.next();
						
						cobj.setCustomer_Id(item.getString("customer_Id ")); 
						cobj.setClient_name(item.getString("client_name"));
						cobj.setCompany_name(item.getString("company_name"));
						cobj.setPhone_no(item.getString("phone_no"));
						cobj.setEmail_id(item.getString("email_id"));
						
						cList.add(cobj);
					}
					
					 res.setStatus("200");
					 res.setMsg("All Data retrieved successfully");
					 res.setClients_details(cList);
					 res.setResult(null);
					 
					 return res;
					
				}catch (Exception e) {
					e.printStackTrace();
					res.setStatus("400");
					res.setMsg("Data retrive error");
					res.setResult(null);
					res.setClients_details(null);
					
					return res;	
				}
			}
				
			case "update":
			{
				AmazonDynamoDB client = AmazonDynamoDBClientBuilder
						.standard()
						.withRegion(region)
						.build();

				DynamoDB dynamoDB = new DynamoDB(client);

				Table table =	dynamoDB.getTable("Test_Customer_Details");
				String customer_Id = clients.getCustomer_Id();
				
				UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey("customer_Id ",customer_Id)
									.withUpdateExpression("set client_name = :n, company_name = :c, phone_no = :p, email_id = :e")
									.withValueMap(new ValueMap().withString(":n", clients.getClient_name())
																.withString(":c", clients.getCompany_name())
																.withString(":p", clients.getPhone_no())
																.withString(":e", clients.getEmail_id()))
																.withReturnValues(ReturnValue.UPDATED_NEW);
				
				try {
					UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
					Item item = outcome.getItem();
					
					cobj.setCustomer_Id(item.getString("customer_Id ")); 
					cobj.setClient_name(item.getString("client_name"));
					cobj.setCompany_name(item.getString("company_name"));
					cobj.setPhone_no(item.getString("phone_no"));
					cobj.setEmail_id(item.getString("email_id"));
					
					cList.add(cobj);
					
					 res.setStatus("200");
					 res.setMsg("Data updated successfully");
					 res.setClients_details(cList);
					 res.setResult(null);
					 
					 return res;
					
					
				}catch (Exception e) {
					e.printStackTrace();
					res.setStatus("400");
					res.setMsg("error");
					res.setResult(null);
					res.setClients_details(null);
					
					return res;	
				}
			}
			
			case "delete" :{
				
				AmazonDynamoDB client = AmazonDynamoDBClientBuilder
						.standard()
						.withRegion(region)
						.build();

				DynamoDB dynamoDB = new DynamoDB(client);

				Table table =	dynamoDB.getTable("Test_Customer_Details");
				String customer_Id = clients.getCustomer_Id();
				
				DeleteItemSpec deleteItemSpec = new DeleteItemSpec()
						.withPrimaryKey("customer_Id ",customer_Id);
				
				try {
					
					table.deleteItem(deleteItemSpec);
					
					res.setStatus("200");
					 res.setMsg("Data removed successfully");
					 res.setClients_details(null);
					 res.setResult(null);
					 
					 return res;
					
				} catch (Exception e) {
					
					e.printStackTrace();
					res.setStatus("400");
					res.setMsg("error");
					res.setResult(null);
					res.setClients_details(null);
					
					return res;	
				}
																					
			}
			
			default:{
				
				res.setStatus("400");
				res.setMsg("Invalid Action");
				res.setResult(null);
				res.setClients_details(null);	
				break;
			}
			
			}		
					
			return res;		
	
	}
}
