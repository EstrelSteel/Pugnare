package com.estrelsteel.pugnare.online.request;

public class JoinRequest {

	private String username;
	
	public JoinRequest(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
