package com.estrelsteel.pugnare.online.request;

import com.estrelsteel.pugnare.online.ConnectionStatus;

public class LoginReturnRequest {
	
	private ConnectionStatus connection;
	
	public LoginReturnRequest(ConnectionStatus connection) {
		this.connection = connection;
	}

	public ConnectionStatus getConnectionStatus() {
		return connection;
	}

	public void setConnectionStatus(ConnectionStatus connection) {
		this.connection = connection;
	}
}
