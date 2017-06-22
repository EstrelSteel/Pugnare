package com.estrelsteel.pugnare.online.packet;

import com.estrelsteel.engine2.online.OnlineInfo;
import com.estrelsteel.pugnare.online.packet.player.AnimationPacket;
import com.estrelsteel.pugnare.online.packet.player.MovePacket;
import com.estrelsteel.pugnare.online.packet.system.KickedPacket;
import com.estrelsteel.pugnare.online.packet.system.LoginPacket;
import com.estrelsteel.pugnare.online.packet.system.LoginReturnPacket;
import com.estrelsteel.pugnare.online.packet.system.LogoutPacket;
import com.estrelsteel.pugnare.online.packet.system.PingPacket;

public class PacketList {
	public static OnlineInfo load(OnlineInfo info) {
															//SERVER PACKETS
		info.getPackets().add(new LoginPacket());				//LOGIN			000~USERNAME~NEW
		info.getPackets().add(new LogoutPacket());				//LOGOUT		001~USERNAME
		info.getPackets().add(new PingPacket());				//PING			002~TIME
		info.getPackets().add(new KickedPacket());				//KICKED		003~USERNAME~REASON
		info.getPackets().add(new LoginReturnPacket());			//LOGIN_RTN		004~STATUS~USERNAME
		info.getPackets().add(null);							//NONE			005
		info.getPackets().add(null);							//NONE			006
		info.getPackets().add(null);							//NONE			007
		info.getPackets().add(null);							//NONE			008
		info.getPackets().add(null);							//NONE			009
															//FILE TRANSFER PACKET RESERVE
		info.getPackets().add(null);							//NONE			010
		info.getPackets().add(null);							//NONE			011
		info.getPackets().add(null);							//NONE			012
		info.getPackets().add(null);							//NONE			013
		info.getPackets().add(null);							//NONE			014
		info.getPackets().add(null);							//NONE			015
		info.getPackets().add(null);							//NONE			016
		info.getPackets().add(null);							//NONE			017
		info.getPackets().add(null);							//NONE			018
		info.getPackets().add(null);							//NONE			019
															//WORLD PACKETS
		info.getPackets().add(null);							//LEVEL			020~LEVEL_ID
		info.getPackets().add(null);							//NONE			021
		info.getPackets().add(null);							//NONE			022
		info.getPackets().add(null);							//NONE			023
		info.getPackets().add(null);							//NONE			024
		info.getPackets().add(null);							//NONE			025
		info.getPackets().add(null);							//NONE			026
		info.getPackets().add(null);							//NONE			027
		info.getPackets().add(null);							//NONE			028
		info.getPackets().add(null);							//NONE			029
															//PLAYER PACKETS
		info.getPackets().add(new MovePacket());				//MOVE			030~USERNAME~X~Y~LEVEL_ID
		info.getPackets().add(new AnimationPacket());			//ANIMATE		031~USERNAME~ANIMATION_ID
		info.getPackets().add(null);							//NONE			032
		info.getPackets().add(null);							//NONE			033
		info.getPackets().add(null);							//NONE			034
		info.getPackets().add(null);							//NONE			035
		info.getPackets().add(null);							//NONE			036
		info.getPackets().add(null);							//NONE			037
		info.getPackets().add(null);							//NONE			038
		info.getPackets().add(null);							//NONE			039
		return info;
	}
}
