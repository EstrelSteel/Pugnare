package com.estrelsteel.pugnare.online.request;

public class MoveRequest {

	private String username;
	private double x;
	private double y;
	private int level;
	
	public MoveRequest(String username, double x, double y, int level) {
		this.username = username;
		this.x = x;
		this.y = y;
		this.level = level;
	}

	public String getUsername() {
		return username;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getLevel() {
		return level;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setLevel(int level) {
		this.level = level;
	}
	
}
