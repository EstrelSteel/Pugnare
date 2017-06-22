package com.estrelsteel.pugnare.online;

public class ClientDetails {
	private ConnectionStatus connection;
	private long lastPacketTime;
	private long lastPing;
	private int ping;
	
	public ClientDetails() {
		this.connection = ConnectionStatus.DISCONNECTED;
		this.lastPacketTime = -1;
		this.lastPing = -1;
		this.ping = -1;
	}
	
	public ConnectionStatus getConnectionStatus() {
		return connection;
	}
	
	public long getLastPacketTime() {
		return lastPacketTime;
	}
	
	public long getLastPingTime() {
		return lastPing;
	}
	
	public int getPing() {
		return ping;
	}
	
	public void updateLastPacketTime() {
		this.lastPacketTime = System.currentTimeMillis();
	}
	
	public void updateLastPingTime() {
		this.lastPing = System.currentTimeMillis();
	}
	
	public void setConnectionStatus(ConnectionStatus connection) {
		this.connection = connection;
	}
	
	public void setLastPacketTime(long lastPacketTime) {
		this.lastPacketTime = lastPacketTime;
	}
	
	public void setLastPingTime(long lastPing) {
		this.lastPing = lastPing;
	}
	
	public void setPing(int ping) {
		this.ping = ping;
	}
}
