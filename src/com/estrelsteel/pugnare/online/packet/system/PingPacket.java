package com.estrelsteel.pugnare.online.packet.system;

import java.net.DatagramPacket;

import com.estrelsteel.engine2.online.Client;
import com.estrelsteel.engine2.online.OnlineInfo;
import com.estrelsteel.engine2.online.Server;
import com.estrelsteel.engine2.online.exception.OnlineException;
import com.estrelsteel.engine2.online.packet.Packet;
import com.estrelsteel.engine2.online.packet.PacketID;
import com.estrelsteel.pugnare.online.request.PingRequest;

public class PingPacket implements Packet {

	private PacketID id;
	
	public PingPacket() {
		this.id = new PacketID("PING", "002");
	}
	
	@Override
	public PacketID getID() {
		return id;
	}

	@Override
	public Server execute(Server server, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
//		System.out.println("[SERVER][" + packet.getAddress() + ":" + packet.getPort() + "]: .");
		server.sendData(msg.getBytes(), packet.getAddress(), packet.getPort());
		return server;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Client execute(Client client, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
		String[] args = OnlineInfo.seperatePacket(msg);
		long start = Long.parseLong(args[1].trim() + "");
		int ping = (int) (System.currentTimeMillis() - start);
//		System.out.println("The ping for " + packet.getAddress() + ":" + packet.getPort() + " is " + ping + "ms");
		client.getRequest().add(new PingRequest(ping));
		return client;
	}

}
