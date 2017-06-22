package com.estrelsteel.pugnare;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	public boolean up;
	public boolean down;
	public boolean left;
	public boolean right;
	
	public KeyHandler() {
		this.up = false;
		this.down = false;
		this.left = false;
		this.right = false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println(e.getKeyCode());
		if(e.getKeyCode() == 87 || e.getKeyCode() == 38) { // W
			up = true;
//			System.out.println("up = true");
		}
		if(e.getKeyCode() == 83|| e.getKeyCode() == 40) { // S
			down = true;
//			System.out.println("down = true");
		}
		if(e.getKeyCode() == 65 || e.getKeyCode() == 37) { // A
			left = true;
//			System.out.println("left = true");
		}
		if(e.getKeyCode() == 68 || e.getKeyCode() == 39) { // D
			right = true;
//			System.out.println("right = true");
		}
		e.consume();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 87 || e.getKeyCode() == 38) { // W
			up = false;
//			System.out.println("up = false");
		}
		if(e.getKeyCode() == 83 || e.getKeyCode() == 40) { // S
			down = false;
//			System.out.println("down = false");
		}
		if(e.getKeyCode() == 65 || e.getKeyCode() == 37) { // A
			left = false;
//			System.out.println("left = false");
		}
		if(e.getKeyCode() == 68 || e.getKeyCode() == 39) { // D
			right = false;
//			System.out.println("right = false");
		}
		e.consume();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
