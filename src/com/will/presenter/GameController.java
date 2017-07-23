package com.will.presenter;

import javax.swing.JPanel;

import com.will.view.Board;

public class GameController extends User{
	public final String name = "GC";
	private static GameController gc;
	
	private GCBoardPresenter bp;
	
	public static GameController getGC(){
		if(gc == null){
			gc = new GameController();
		}
		return gc;
	}
	
	private GameController(){
		
	}
	
	private GameController(GCBoardPresenter gcbp) {
		bp = gcbp;
	}
	

	@Override
	public boolean dig(int x, int y) {
		//TODO 挖开方法待完善
		return false;
		
		
	}

	@Override
	public void afterDug(int x, int y) {
		// TODO 挖开后的界面变化
		digAround(x, y);
	}
	
	public JPanel gamePreStart(Player p){
		bp = new Board();
		JPanel cellPanel = bp.ready();
		bp.setPlayer(p);
		return cellPanel;
	}
	
	public void gameStart(int clickedX, int clickedY){
		bp.putMineAndNumber(clickedX, clickedY);
	}
	
	public void gameRestart(){
		
	}
	
}