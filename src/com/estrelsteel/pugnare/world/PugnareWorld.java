package com.estrelsteel.pugnare.world;

import java.util.ArrayList;

import com.estrelsteel.engine2.grid.Grid;
import com.estrelsteel.engine2.world.World;
import com.estrelsteel.pugnare.actor.Player;

public class PugnareWorld extends World {

	private ArrayList<Player> players;
	
	public PugnareWorld(Grid grid) {
		super(grid);
		players = new ArrayList<Player>();
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

}
