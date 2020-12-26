package com.learning.cqrs.user_service.dtos;


public class UserCreationResponse extends BaseResponse{

	private String id;
	public UserCreationResponse(String message,String id) {
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
