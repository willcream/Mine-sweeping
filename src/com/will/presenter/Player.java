package com.will.presenter;

import java.util.ArrayList;

import com.will.view.Board;

public class Player extends User {
	private static Player player;
	
	private String name;
	
	public static Player newPlayer(String name){
		player = new Player(name);
		return player;
	}

	private Player(String name){
		this.name = name;
		bp = Board.getBoard();
	}
	
	/**
	 * 若没有初始化，千万不要调用这个方法
	 * @return 玩家信息
	 */
	public static Player getPlayer(){
		if(player == null){
			System.err.println("player没有初始化");
		}
		return player;
	}
	
	@Override
	public boolean dig(int x, int y) {
		return false;

	}

	@Override
	public void afterDug(int x, int y) {
	}

}
