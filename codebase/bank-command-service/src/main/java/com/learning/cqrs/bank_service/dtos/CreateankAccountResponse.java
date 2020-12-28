package com.learning.cqrs.bank_service.dtos;


public class CreateankAccountResponse extends BaseResponse{

	private String id;
	public CreateankAccountResponse(String message,String id) {
		super(message);
		this.id=id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	

}
