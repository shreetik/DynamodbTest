package com.amazonaws.lambda.demo;

import com.amazonaws.lambda.entities.Clients;
import com.amazonaws.lambda.entities.Request;
import com.amazonaws.lambda.entities.Response;
import com.amazonaws.lambda.service.RequestProcess;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<Request, Response> {

    @Override
    public Response handleRequest(Request input, Context context) {
        
    	String action;
    	String region;
    	Clients clients = new Clients();
    	
    	Response response = new Response();
    	
    	RequestProcess requestProcess = new RequestProcess();
    	
    	context.getLogger().log("Input: " + input);
        
        
        try {
			
        	if(input.getAction() != "" && input.getRegion() != "") {
        		
        		action = input.getAction();
        		region = input.getRegion();
        		
        		clients.setCustomer_Id(input.getCustomer_Id());
        		clients.setClient_name(input.getClient_name());
        		clients.setCompany_name(input.getCompany_name());
        		clients.setPhone_no(input.getPhone_no());
        		clients.setEmail_id(input.getEmail_id());
        		
        	return requestProcess.doProcess(action, region, clients);
        		
        		
        	}
        	
        	
		} catch (Exception e) {
			
			e.printStackTrace();
			response.setStatus("400");
			response.setMsg("Error in handleRequest!");
			response.setResult(null);
			response.setClients_details(null);
			
			return response;
			
		}
        
       return null;        
    }

}
