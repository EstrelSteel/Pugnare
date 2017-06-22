package com.estrelsteel.pugnare.online.request;

public class LeaveRequest {

	private String username;
	
	public LeaveRequest(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
