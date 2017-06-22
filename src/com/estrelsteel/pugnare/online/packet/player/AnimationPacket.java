package com.estrelsteel.pugnare.online.packet.player;

import java.net.DatagramPacket;

import com.estrelsteel.engine2.online.Client;
import com.estrelsteel.engine2.online.OnlineInfo;
import com.estrelsteel.engine2.online.Server;
import com.estrelsteel.engine2.online.exception.OnlineException;
import com.estrelsteel.engine2.online.packet.Packet;
import com.estrelsteel.engine2.online.packet.PacketID;
import com.estrelsteel.pugnare.online.request.AnimationRequest;

public class AnimationPacket implements Packet {

	private PacketID id;
	
	public AnimationPacket() {
		this.id = new PacketID("ANIMATION", "031");
	}
	
	@Override
	public PacketID getID() {
		return id;
	}

	@Override
	public Server execute(Server server, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
		String[] args = OnlineInfo.seperatePacket(msg);
		
		server.sendToAll((id.getID() + server.getOnlineInfo().getSplit() + args[1] + server.getOnlineInfo().getSplit() + args[2]).getBytes());
		return server;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Client execute(Client client, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
		String[] args = OnlineInfo.seperatePacket(msg);
		
		client.getRequest().add(new AnimationRequest(args[1], Integer.parseInt(args[2])));
		return client;
	}

}
