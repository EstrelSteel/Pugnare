package com.estrelsteel.pugnare.online.packet.system;

import java.net.DatagramPacket;

import com.estrelsteel.engine2.online.Client;
import com.estrelsteel.engine2.online.OnlineInfo;
import com.estrelsteel.engine2.online.Server;
import com.estrelsteel.engine2.online.exception.InvalidPacketException;
import com.estrelsteel.engine2.online.exception.OnlineException;
import com.estrelsteel.engine2.online.packet.Packet;
import com.estrelsteel.engine2.online.packet.PacketID;
import com.estrelsteel.engine2.online.users.UserData;
import com.estrelsteel.pugnare.online.ConnectionStatus;
import com.estrelsteel.pugnare.online.request.LoginReturnRequest;

public class LoginReturnPacket implements Packet {

	private PacketID id;
	
	public LoginReturnPacket() {
		this.id = new PacketID("LOGIN_RETURN", "004");
	}
	
	@Override
	public PacketID getID() {
		return id;
	}

	@Override
	public Server execute(Server server, DatagramPacket packet) throws OnlineException {
		throw new InvalidPacketException(new String(packet.getData()));
	}

	
	//STATUS: 0=joined; 1=kicked(name)
	@SuppressWarnings("unchecked")
	@Override
	public Client execute(Client client, DatagramPacket packet) throws OnlineException {
		String msg = new String(packet.getData());
		String[] args = OnlineInfo.seperatePacket(msg);
		if(args[1].equalsIgnoreCase("0")) {
			client.getUsers().add(new UserData("", "", args[2]));
			System.out.println(args[2] + " has joined.");
			client.getRequest().add(new LoginReturnRequest(ConnectionStatus.CONNECTED));
			
		}
		else if(args[1].equalsIgnoreCase("1")) {
			System.out.println(args[1] + " is already online.");
			client.getRequest().add(new LoginReturnRequest(ConnectionStatus.KICKED_NAME));
		}
		return client;
	}

}
