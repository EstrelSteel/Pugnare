package com.estrelsteel.pugnare.actor;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class Player extends Actor {

	private double walkspeed;
	private PlayerType type;
	
	public Player(String name, Rectangle loc) {
		super(name, loc);
		getAnimations().add(new Animation("T0_FRONT", 0));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(0 * 16, 0 * 16, 16, 16)));
		getAnimations().add(new Animation("T0_BACK", 1));
		getAnimations().get(1).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(1 * 16, 0 * 16, 16, 16)));
		getAnimations().add(new Animation("T0_RIGHT", 2));
		getAnimations().get(2).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(2 * 16, 0 * 16, 16, 16)));
		getAnimations().add(new Animation("T0_LEFT", 3));
		getAnimations().get(3).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/player0.png", QuickRectangle.location(3 * 16, 0 * 16, 16, 16)));
		
		this.walkspeed = 5.0;
		this.type = PlayerType.BASE;
	}
	
	public double getWalkspeed() {
		return walkspeed;
	}
	
	public PlayerType getPlayerType() {
		return type;
	}
	
	public void setWalkspeed(double walkspeed) {
		this.walkspeed = walkspeed;
	}
	
	public void setPlayerType(PlayerType type) {
		this.type = type;
	}

}
