package com.estrelsteel.pugnare.online.request;

public class AnimationRequest {
	
	private String username;
	private int animationNumber;
	
	public AnimationRequest(String username, int animationNumber) {
		this.username = username;
		this.animationNumber = animationNumber;
	}

	public String getUsername() {
		return username;
	}

	public int getAnimationNumber() {
		return animationNumber;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAnimationNumber(int animationNumber) {
		this.animationNumber = animationNumber;
	}
}
