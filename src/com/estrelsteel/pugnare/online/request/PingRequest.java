package com.estrelsteel.pugnare.online.request;

public class PingRequest {

	private int ping;
	
	public PingRequest(int ping) {
		this.ping = ping;
	}

	public int getPing() {
		return ping;
	}

	public void setPing(int ping) {
		this.ping = ping;
	}
	
}
