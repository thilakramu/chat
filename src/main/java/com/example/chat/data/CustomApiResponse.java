package com.example.chat.data;

public class CustomApiResponse {
	private Boolean success;
	private Boolean error;
	private String message;
	
	public CustomApiResponse(Boolean success, String message, Boolean error) {
		this.success = success;
		this.error = error;
		this.message = message;
	}
	
	public Boolean getSuccess() {
		return success;
	}
	
	public Boolean getError() {
		return error;
	}
	
	public String getMessage() {
		return message;
	}

}

