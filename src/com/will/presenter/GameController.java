package com.will.presenter;

import javax.swing.JPanel;

import com.will.view.Board;
import com.will.view.Main;

public class GameController extends User{
	public final String name = "GC";
	private static GameController gc;
	
	private boolean isOver = false;
	private boolean isComplete = false;
	
	private GCBoardPresenter bp;
	
	public static GameController getGC(){
		if(gc == null){
			gc = new GameController();
		}
		return gc;
	}
	
	private GameController(){
		isOver = false;
	}
	
	private GameController(GCBoardPresenter gcbp) {
		bp = gcbp;
		isOver = false;
	}
	

	@Override
	public boolean dig(int x, int y) {
		return false;
		
		
	}

	@Override
	public void afterDug(int x, int y) {
		digAround(x, y);
	}
	
	public JPanel gamePreStart(){
		bp = Board.getBoard();
		JPanel cellPanel = bp.ready();
		return cellPanel;
	}
	
	public void gameStart(int clickedX, int clickedY){
		bp.putMineAndNumber(clickedX, clickedY);
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}
	
	public void restart() {
		bp.changeLevel();		
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	
	
}
