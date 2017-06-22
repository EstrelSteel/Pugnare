package com.estrelsteel.pugnare.online.packet.system;

import java.net.DatagramPacket;

import com.estrelsteel.engine2.online.Client;
import com.estrelsteel.engine2.online.OnlineInfo;
import com.estrelsteel.engine2.online.Server;
import com.estrelsteel.engine2.online.exception.OnlineException;
import com.estrelsteel.engine2.online.packet.Packet;
import com.estrelsteel.engine2.online.packet.PacketID;
import com.estrelsteel.pugnare.online.request.LeaveRequest;

public class KickedPacket implements Packet {

	private PacketID id;
	
	public KickedPacket() {
		this.id = new PacketID("KICKED", "003");
	}
	
	@Override
	public PacketID getID() {
		return id;
	}

	@Override
	public Server execute(Server server, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
		String[] args = OnlineInfo.seperatePacket(msg);
		server.sendToAll((id.getID() + server.getOnlineInfo().getSplit() + args[1]).getBytes());
		server.getUsers().remove(args[1]);
		if(args.length > 2) {
			String end = "for reason:";
			for(int i = 2; i < args.length; i++) {
				end = end + " " + args[i];
			}
			end = end + ".";
			System.out.println("[SERVER][" + packet.getAddress() + ":" + packet.getPort() + "]: " + args[1] + " has been kicked " + end);
		}
		else {
			System.out.println("[SERVER][" + packet.getAddress() + ":" + packet.getPort() + "]: " + args[1] + " has been kicked.");
		}
		
		return server;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Client execute(Client client, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
		String[] args = OnlineInfo.seperatePacket(msg);
		client.getUsers().remove(args[1]);
		if(args.length > 2) {
			String end = "for reason:";
			for(int i = 2; i < args.length; i++) {
				end = end + " " + args[i];
			}
			end = end + ".";
			System.out.println(args[1] + " has been kicked " + end);
		}
		else {
			System.out.println(args[1] + " has been kicked.");
		}
		client.getRequest().add(new LeaveRequest(args[1]));
		return client;
	}

}
