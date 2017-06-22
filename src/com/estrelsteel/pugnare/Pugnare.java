package com.estrelsteel.pugnare;

import java.awt.Font;
import java.awt.Graphics2D;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import com.estrelsteel.engine2.Launcher;
import com.estrelsteel.engine2.actor.Actor;
import com.estrelsteel.engine2.events.listener.RenderListener;
import com.estrelsteel.engine2.events.listener.StartListener;
import com.estrelsteel.engine2.events.listener.StopListener;
import com.estrelsteel.engine2.events.listener.TickListener;
import com.estrelsteel.engine2.file.GameFile;
import com.estrelsteel.engine2.grid.PixelGrid;
import com.estrelsteel.engine2.online.Client;
import com.estrelsteel.engine2.online.OnlineInfo;
import com.estrelsteel.engine2.point.AbstractedPoint;
import com.estrelsteel.engine2.point.Point2;
import com.estrelsteel.engine2.point.PointMaths;
import com.estrelsteel.engine2.shape.rectangle.QuickRectangle;
import com.estrelsteel.engine2.shape.rectangle.Rectangle;
import com.estrelsteel.engine2.window.WindowSettings;
import com.estrelsteel.pugnare.actor.LocalPlayer;
import com.estrelsteel.pugnare.online.ConnectionStatus;
import com.estrelsteel.pugnare.online.packet.PacketList;
import com.estrelsteel.pugnare.online.packet.player.AnimationPacket;
import com.estrelsteel.pugnare.online.packet.player.MovePacket;
import com.estrelsteel.pugnare.online.packet.system.LoginPacket;
import com.estrelsteel.pugnare.online.packet.system.LogoutPacket;
import com.estrelsteel.pugnare.online.packet.system.PingPacket;
import com.estrelsteel.pugnare.world.PugnareWorld;

public class Pugnare implements StartListener, StopListener, RenderListener, TickListener {
	
	private Launcher l;
	private PugnareServer ps;
	private Client client;
	private PugnareWorld world;
	private LocalPlayer player;
	private KeyHandler key;
	private RequestHandler rh;
	private boolean debug;
	private int level;
	
	private AbstractedPoint temp_playerCentre;
	private double temp_x;
	private double temp_y;
	
	private final OnlineInfo temp_onlineInfo = new OnlineInfo();
	
	public static void main(String[] args) {
		new Pugnare();
	}
	
	@SuppressWarnings("static-access")
	public Pugnare() {
		l = new Launcher();
		Rectangle size;
		if(System.getProperty("os.name").startsWith("Windows")) {
			size = QuickRectangle.location(0, 0, 630, 630);
		}
		else {
			size = QuickRectangle.location(0, 0, 640, 640);
		}
		l.getEngine().setWindowSettings(new WindowSettings(size, "Pugnare", "MVP-v1.0a", 0));
		
		debug = true;
		level = -1;
		key = new KeyHandler();
		
		l.getEngine().requestFocusInWindow();
		l.getEngine().START_EVENT.addListener(this);
		l.getEngine().STOP_EVENT.addListener(this);
		l.getEngine().RENDER_EVENT.addListener(this);
		l.getEngine().TICK_EVENT.addListener(this);
		
		l.getEngine().development = true;
//		l.getEngine().devPath = System.getProperty("user.home") + "/Documents/usb/Pugnare/Pugnare";
		l.getEngine().devPath = GameFile.getCurrentPath();
		
		l.boot();

		configureGame();
	}

	@Override
	public void start() {
		PacketList.load(temp_onlineInfo);
		
		world = new PugnareWorld(new PixelGrid());
		player = new LocalPlayer("null", QuickRectangle.location(0, 0, 64, 64));
		player.getClientDetails().setConnectionStatus(ConnectionStatus.DISCONNECTED);
		l.getEngine().addKeyListener(key);
//		world.getObjects().add(new LoadingCircle("connecting", QuickRectangle.location(320, 320, 64, 64)));
	}
	
	private void configureGame() {
		int host = JOptionPane.showConfirmDialog(l.getEngine(), "Do you want to host?", "Pugnare", JOptionPane.YES_NO_OPTION);
		try {
			if(host == 0) { // YES
				ps = new PugnareServer(temp_onlineInfo);
				l.getEngine().STOP_EVENT.addListener(ps);
				Thread s = new Thread(ps, l.getEngine().getWindowSettings().getTitle() + " Server " + l.getEngine().getWindowSettings().getVersion() + " (" + l.getEngine().getWindowSettings().getBuild() + ")");
				s.start();
				createClient(true);
			}
			else { 			// NO
				createClient(false);
			}
		} 
		catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	private void createClient(boolean host) throws UnknownHostException {
		String ip = "0.0.0.0";
		String string_port = "24900";
		if(!host) {
			ip = JOptionPane.showInputDialog(l.getEngine(), "Enter the ip", "0.0.0.0");
			if(ip == null) {
				l.getEngine().stop();
			}
			string_port = JOptionPane.showInputDialog(l.getEngine(), "Enter the port", "24900");
			if(string_port == null) {
				l.getEngine().stop();
			}
		}
		if(ip.equalsIgnoreCase("")|| ip.equalsIgnoreCase("0.0.0.0") || ip.equalsIgnoreCase("localhost")) {
			ip = InetAddress.getLocalHost().toString();
			ip = ip.split("/")[1];
		}
		if(string_port.equalsIgnoreCase("") || string_port.substring(0, 1).equalsIgnoreCase("-") || string_port.equalsIgnoreCase("0")) {
			string_port = "24900";
		}

		String username = JOptionPane.showInputDialog(l.getEngine(), "Enter your username", "Player");
		if(username == null || username.equalsIgnoreCase("")) {
			l.getEngine().stop();
		}
		username = username.replaceAll("~", "-");
		System.out.println("Setting username to " + username);
		player.setName(username);
		world.getObjects().add(player);
		world.getPlayers().add(player);
		
		System.out.println("[CLIENT]: Connecting to " + ip + ":" + string_port);
		player.getClientDetails().setConnectionStatus(ConnectionStatus.CONNECTING);
		client = new Client(ip, Integer.parseInt(string_port));
		client.setOnlineInfo(temp_onlineInfo);
		rh = new RequestHandler(this, client);
		l.getEngine().TICK_EVENT.addListener(rh);
		client.sendData((new LoginPacket().getID().getID() + client.getOnlineInfo().getSplit() + username + client.getOnlineInfo().getSplit() + "true").getBytes());
		player.getClientDetails().updateLastPacketTime();
		client.run();
	}

	@Override
	public void stop() {
		if(client != null) {
			client.sendData((new LogoutPacket().getID().getID() + client.getOnlineInfo().getSplit() + player.getName()).getBytes());
			player.getClientDetails().setConnectionStatus(ConnectionStatus.DISCONNECTED);
			client.setRunning(false);
			client = null;
		}
	}
	
	@Override
	public void tick() {
		if(world != null && player != null) {			
			if(key.up) {
				temp_y = temp_y - player.getWalkspeed();
				player.setRunningAnimationNumber(player.getPlayerType().getAnimationStart() + 1);
			}
			if(key.down) {
				temp_y = temp_y + player.getWalkspeed();
				player.setRunningAnimationNumber(player.getPlayerType().getAnimationStart() + 0);
			}
			if(key.left) {
				temp_x = temp_x - player.getWalkspeed();
				player.setRunningAnimationNumber(player.getPlayerType().getAnimationStart() + 3);
			}
			if(key.right) {
				temp_x = temp_x + player.getWalkspeed();
				player.setRunningAnimationNumber(player.getPlayerType().getAnimationStart() + 2);
			}
			
			if(temp_x != 0 || temp_y != 0) {
				player.setLocation(QuickRectangle.translate(temp_x, temp_y, player.getLocation()));
				temp_x = 0;
				temp_y = 0;
				client.sendData((new MovePacket().getID().getID() + client.getOnlineInfo().getSplit() + player.getName() 
						+ client.getOnlineInfo().getSplit() + player.getLocation().getX() + client.getOnlineInfo().getSplit() 
						+ player.getLocation().getY() + client.getOnlineInfo().getSplit() + level).getBytes());
				if(player.getGhostRunningAnimationNumber() != player.getRunningAnimationNumber()) {
					client.sendData((new AnimationPacket().getID().getID() + client.getOnlineInfo().getSplit() + player.getName() 
							+ client.getOnlineInfo().getSplit() + player.getRunningAnimationNumber()).getBytes());
				}
			}
			temp_playerCentre = PointMaths.getCentre(player.getLocation());
			world.getMainCamera().setLocation(new Point2(temp_playerCentre.getX() - l.getEngine().getWidth() / 2, 
					temp_playerCentre.getY() - l.getEngine().getHeight() / 2, world.getGrid()));
		}
	}

	@Override
	public Graphics2D render(Graphics2D ctx) {
		ctx = world.simpleRenderWorld(ctx);
		for(int i = 0; i < world.getObjects().size(); i++) {
			if(world.getObjects().get(i) instanceof Actor) {
				((Actor) world.getObjects().get(i)).getRunningAnimation().run();
			}
		}
		if(player != null) {
			if(player.getClientDetails().getConnectionStatus() == ConnectionStatus.CONNECTING 
					&& System.currentTimeMillis() - player.getClientDetails().getLastPacketTime() >= 20000) {
				System.out.println("No response from server... Sending client info.");
				client.sendData((new LoginPacket().getID().getID() + client.getOnlineInfo().getSplit() + player.getName()
						+ client.getOnlineInfo().getSplit() + "true").getBytes());
				player.getClientDetails().updateLastPacketTime();
			}
			else if(player.getClientDetails().getConnectionStatus() == ConnectionStatus.CONNECTED 
					&& System.currentTimeMillis() - player.getClientDetails().getLastPingTime() >= 5000) {
				client.sendData((new PingPacket().getID().getID() + client.getOnlineInfo().getSplit() + System.currentTimeMillis()).getBytes());
				player.getClientDetails().updateLastPingTime();
			}
			if(debug) {
				ctx.setFont(new Font(ctx.getFont().getName(), 10, 10));
				String msg = "Status: ";
				if(player.getClientDetails().getConnectionStatus() == ConnectionStatus.DISCONNECTED) {
					msg = msg + " DISCONNECTED";
				}
				else if(player.getClientDetails().getConnectionStatus() == ConnectionStatus.CONNECTING) {
					msg = msg + " CONNECTING...";
				}
				else if(player.getClientDetails().getConnectionStatus() == ConnectionStatus.CONNECTED) {
					msg = msg + " CONNECTED";
				}
				else if(player.getClientDetails().getConnectionStatus() == ConnectionStatus.KICKED_NAME) {
					msg = msg + " KICKED (already online)";
				}
				else if(player.getClientDetails().getConnectionStatus() == ConnectionStatus.KICKED_ADMIN) {
					msg = msg + " KICKED";
				}
				else if(player.getClientDetails().getConnectionStatus() == ConnectionStatus.LOST) {
					msg = msg + " LOST";
				}
				ctx.drawString(msg, 5, 10);
				ctx.drawString("Hosting: " + (ps != null), 5, 20);
				ctx.drawString("Username: " + player.getName(), 5, 30);
				ctx.drawString("Ping: " + player.getClientDetails().getPing() + "ms", 5, 40);
				if(temp_playerCentre != null) { 
					ctx.drawString("x: " + temp_playerCentre.getX() + " y: " + temp_playerCentre.getY(), 5, 50);
				}
				ctx.drawString("Players: " + world.getPlayers().size(), 5, 60);
			}
		}
		return ctx;
	}
	
	public LocalPlayer getPlayer() {
		return player;
	}
	
	public PugnareWorld getWorld() {
		return world;
	}
}
