package com.estrelsteel.pugnare.online.packet.system;

import java.net.DatagramPacket;

import com.estrelsteel.engine2.online.Client;
import com.estrelsteel.engine2.online.OnlineInfo;
import com.estrelsteel.engine2.online.Server;
import com.estrelsteel.engine2.online.exception.OnlineException;
import com.estrelsteel.engine2.online.packet.Packet;
import com.estrelsteel.engine2.online.packet.PacketID;
import com.estrelsteel.pugnare.online.request.LeaveRequest;

public class LogoutPacket implements Packet {

	private PacketID id;
	
	public LogoutPacket() {
		this.id = new PacketID("LOGOUT", "001");
	}
	
	@Override
	public PacketID getID() {
		return id;
	}

	@Override
	public Server execute(Server server, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
		String[] args = OnlineInfo.seperatePacket(msg);
		server.getUsers().remove(args[1]);
		System.out.println("[SERVER][" + packet.getAddress() + ":" + packet.getPort() + "]: " + args[1] + " has left.");
		server.sendToAll((id.getID() + server.getOnlineInfo().getSplit() + args[1]).getBytes());
		return server;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Client execute(Client client, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
		String[] args = OnlineInfo.seperatePacket(msg);
		client.getUsers().remove(args[1]);
		System.out.println(args[1] + " has left.");
		client.getRequest().add(new LeaveRequest(args[1]));
		return client;
	}

}
