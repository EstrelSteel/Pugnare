package com.estrelsteel.pugnare.actor;

public enum PlayerType {
	BASE(0);
	
	private int animationStart;
	
	PlayerType(int animationStart) {
		this.animationStart = animationStart;
	}
	
	public int getAnimationStart() {
		return animationStart;
	}
}
