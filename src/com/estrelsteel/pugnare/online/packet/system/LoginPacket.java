package com.estrelsteel.pugnare.online.packet.system;

import java.net.DatagramPacket;

import com.estrelsteel.engine2.online.Client;
import com.estrelsteel.engine2.online.OnlineInfo;
import com.estrelsteel.engine2.online.Server;
import com.estrelsteel.engine2.online.exception.OnlineException;
import com.estrelsteel.engine2.online.packet.Packet;
import com.estrelsteel.engine2.online.packet.PacketID;
import com.estrelsteel.engine2.online.users.UserData;
import com.estrelsteel.pugnare.online.request.JoinRequest;

public class LoginPacket implements Packet {

	private PacketID id;
	
	public LoginPacket() {
		this.id = new PacketID("LOGIN", "000");
	}
	
	@Override
	public PacketID getID() {
		return id;
	}

	@Override
	public Server execute(Server server, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
		String[] args = OnlineInfo.seperatePacket(msg);
		for(UserData u : server.getUsers()) {
			if(u.getUsername().equalsIgnoreCase(args[1])) {
				System.out.println("[SERVER][" + packet.getAddress() + ":" + packet.getPort() + "]: " + args[1] + " attempted to join but is already online.");
				server.sendData((new LoginReturnPacket().getID().getID() + server.getOnlineInfo().getSplit() + 1 
						+ server.getOnlineInfo().getSplit() + args[1]).getBytes(), packet.getAddress(), packet.getPort());
				return server;
			}
		}
		System.out.println("[SERVER][" + packet.getAddress() + ":" + packet.getPort() + "]: " + args[1] + " has joined.");
		server.sendData((new LoginReturnPacket().getID().getID() + server.getOnlineInfo().getSplit() + 0
				+ server.getOnlineInfo().getSplit() + args[1]).getBytes(), packet.getAddress(), packet.getPort());
		server.sendToAll((id.getID() + server.getOnlineInfo().getSplit() + args[1] + server.getOnlineInfo().getSplit() + args[2]).getBytes());
		for(UserData u : server.getUsers()) {
			server.sendData((new LoginPacket().getID().getID() + server.getOnlineInfo().getSplit() + u.getUsername()
					+ server.getOnlineInfo().getSplit() + "false").getBytes(), packet.getAddress(), packet.getPort());
		}
		server.getUsers().add(new UserData(packet.getAddress().toString().substring(1), packet.getPort() + "", args[1]));
		return server;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Client execute(Client client, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
		String[] args = OnlineInfo.seperatePacket(msg);
		client.getUsers().add(new UserData("", "", args[1]));
		if(args[2].equalsIgnoreCase("true")) {
			System.out.println(args[1] + " has joined.");
		}
		client.getRequest().add(new JoinRequest(args[1]));
		return client;
	}

}
