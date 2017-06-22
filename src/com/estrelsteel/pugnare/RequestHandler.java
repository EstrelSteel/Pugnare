package com.estrelsteel.pugnare;

import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.online.Client;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.pugnare.actor.Player;
import com.estrelsteel.pugnare.online.ConnectionStatus;
import com.estrelsteel.pugnare.online.request.AnimationRequest;
import com.estrelsteel.pugnare.online.request.JoinRequest;
import com.estrelsteel.pugnare.online.request.LeaveRequest;
import com.estrelsteel.pugnare.online.request.LoginReturnRequest;
import com.estrelsteel.pugnare.online.request.MoveRequest;
import com.estrelsteel.pugnare.online.request.PingRequest;

public class RequestHandler implements TickListener {
	
	private Pugnare pugnare;
	private Client client;
	
	public RequestHandler(Pugnare pugnare, Client client) {
		this.pugnare = pugnare;
		this.client = client;
	}

	@Override
	public void tick() {
		if(client != null) {
			for(int i = 0; i < client.getRequest().size(); i++) {
				if(client.getRequest().get(i) instanceof LoginReturnRequest) {
//					((LoginRequest) client.getRequest().get(i))
					pugnare.getPlayer().getClientDetails().setConnectionStatus(((LoginReturnRequest) client.getRequest().get(i)).getConnectionStatus());
					client.getRequest().remove(i);
					i--;
				}
				else if(client.getRequest().get(i) instanceof PingRequest) {
//					((PingRequest) client.getRequest().get(i))
					pugnare.getPlayer().getClientDetails().setPing(((PingRequest) client.getRequest().get(i)).getPing());
					client.getRequest().remove(i);
					i--;
				}
				else if(client.getRequest().get(i) instanceof JoinRequest) {
//					((JoinRequest) client.getRequest().get(i))
					Player player = new Player(((JoinRequest) client.getRequest().get(i)).getUsername(), QuickRectangle.location(0, 0, 64, 64));
					pugnare.getWorld().getObjects().add(player);
					pugnare.getWorld().getPlayers().add(player);
					client.getRequest().remove(i);
					i--;
				}
				else if(client.getRequest().get(i) instanceof LeaveRequest) {
//					((LeaveRequest) client.getRequest().get(i))
					for(int j = 0; i < pugnare.getWorld().getPlayers().size(); j++) {
						if(((LeaveRequest) client.getRequest().get(i)).getUsername().equals(pugnare.getWorld().getPlayers().get(j).getName())) {
							Player p = pugnare.getWorld().getPlayers().remove(j);
							pugnare.getWorld().getObjects().remove(p);
							if(((LeaveRequest) client.getRequest().get(i)).getUsername().equals(pugnare.getPlayer().getName())) {
								pugnare.getPlayer().getClientDetails().setConnectionStatus(ConnectionStatus.KICKED_ADMIN);
							}
							break;
						}
					}
					client.getRequest().remove(i);
					i--;
				}
				else if(client.getRequest().get(i) instanceof MoveRequest) {
//					((MoveRequest) client.getRequest().get(i))
					if(!((MoveRequest) client.getRequest().get(i)).getUsername().equals(pugnare.getPlayer().getName())) {
						for(Player p : pugnare.getWorld().getPlayers()) {
							if(p.getName().equals(((MoveRequest) client.getRequest().get(i)).getUsername())) {
								p.setLocation(QuickRectangle.location(((MoveRequest) client.getRequest().get(i)).getX(), 
										((MoveRequest) client.getRequest().get(i)).getY(), 64, 64));
								break;
							}
						}
					}
					client.getRequest().remove(i);
					i--;
				}
				else if(client.getRequest().get(i) instanceof AnimationRequest) {
//					((AnimationRequest) client.getRequest().get(i))
					if(!((AnimationRequest) client.getRequest().get(i)).getUsername().equals(pugnare.getPlayer().getName())) {
						for(Player p : pugnare.getWorld().getPlayers()) {
							if(p.getName().equals(((AnimationRequest) client.getRequest().get(i)).getUsername())) {
								p.setRunningAnimationNumber(((AnimationRequest) client.getRequest().get(i)).getAnimationNumber());
								break;
							}
						}
					}
					client.getRequest().remove(i);
					i--;
				}
			}
		}
	}
}
