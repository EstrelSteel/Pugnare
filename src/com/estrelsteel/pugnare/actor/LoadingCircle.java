package com.estrelsteel.pugnare.actor;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.image.Animation;
import com.estrelsteel.engine2.image.ConfinedImage;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;

public class LoadingCircle extends Actor {

	public LoadingCircle(String name, Rectangle loc) {
		super(name, loc);
		getAnimations().add(0, new Animation("LOADING_0", 0));
		getAnimations().get(0).setMaxWaitTime(10);
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(1 * 16, 0 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(2 * 16, 0 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(3 * 16, 0 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(4 * 16, 0 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(5 * 16, 0 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(6 * 16, 0 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(7 * 16, 0 * 16, 16, 16)));
		getAnimations().get(0).getFrames().add(new ConfinedImage(Engine2.devPath + "/res/img/hud.png", QuickRectangle.location(8 * 16, 0 * 16, 16, 16)));
	}

}
