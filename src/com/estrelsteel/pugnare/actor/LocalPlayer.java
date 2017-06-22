package com.estrelsteel.pugnare.actor;

import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.pugnare.online.ClientDetails;

public class LocalPlayer extends Player {
	
	private ClientDetails client_details;
	private int ghostAnimationNumber;

	public LocalPlayer(String name, Rectangle loc) {
		super(name, loc);
		this.client_details = new ClientDetails();
	}

	public ClientDetails getClientDetails() {
		return client_details;
	}
	
	public int getGhostRunningAnimationNumber() {
		return ghostAnimationNumber;
	}

	public void setClientDetails(ClientDetails client_details) {
		this.client_details = client_details;
	}
	
	public void setRunningAnimationNumber(int animation) {
		this.ghostAnimationNumber = getRunningAnimationNumber();
		super.setRunningAnimationNumber(animation);
	}

	public void setGhostRunningAnimationNumber(int ghostAnimationNumber) {
		this.ghostAnimationNumber = ghostAnimationNumber;
	}
}
