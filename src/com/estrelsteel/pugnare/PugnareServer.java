package com.estrelsteel.pugnare;

import java.net.InetAddress;
import java.net.UnknownHostException;

import com.estrelsteel.engine2.Engine2;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.online.OnlineInfo;
import com.estrelsteel.engine2.online.Server;
import com.estrelsteel.engine2.setting.Settings;
import com.estrelsteel.pugnare.online.packet.system.KickedPacket;

public class PugnareServer implements StopListener, TickListener, Runnable {
	
	private Settings server_settings;
	private Server server;
	private OnlineInfo temp_onlineInfo;
	
	public PugnareServer(OnlineInfo temp_onlineInfo) {
		server_settings = new Settings("server_settings", Engine2.devPath + "/server_settings.txt");
		server_settings.load();
		this.temp_onlineInfo = temp_onlineInfo;
	}
	
	public synchronized void createServer() throws UnknownHostException {
		System.out.println("IP (local): " + InetAddress.getLocalHost().toString().split("/")[1] + "\tPort: " + server_settings.findSetting("port").getValue());
		server = new Server((Integer) server_settings.findSetting("port").getValue());
		server.setOnlineInfo(temp_onlineInfo);
		System.out.println("[SERVER]: Listening on port " + (Integer) server_settings.findSetting("port").getValue());
		server.setRunning(true);
		server.run();
	}

	@Override
	public void stop() {
		if(server != null) {
			for(int i = 0; i < server.getUsers().size(); i++) {
				server.sendToAll((new KickedPacket().getID().getID() + server.getOnlineInfo().getSplit() + server.getUsers().get(i).getUsername()
						+ server.getOnlineInfo().getSplit() + "Server Closed").getBytes());
			}
			server.setRunning(false);
			server = null;
		}
	}

	@Override
	public void run() {
		try {
			createServer();
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		
	}
}
