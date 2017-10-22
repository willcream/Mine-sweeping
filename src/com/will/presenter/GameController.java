package com.will.presenter;

import java.util.HashMap;

import javax.swing.JPanel;

import com.will.model.GlobalMsg;
import com.will.view.Board;
import com.will.view.Main;
import com.will.view.MainWindow;

public class GameController{
	public final String name = "GC";
	private static GameController gc;
	//用于存放presenter，不过太迟才想到，没能做到所有presenter在这里中心化处理
	private static HashMap<String,MyBasePresenter> presenterMap;
	
	private boolean isOver = false;
	private boolean isComplete = false;
	
	private GCBoardPresenter bp;
	
	public static GameController getGC(){
		if(gc == null){
			gc = new GameController();
			presenterMap = new HashMap<>();
		}
		return gc;
	}
	
	private GameController(){
		isOver = false;
	}
		
	public JPanel gamePreStart(){
		new Board();
		bp = (GCBoardPresenter) presenterMap.get(GlobalMsg.S_GC_BOARD_P);
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
		bp.restart();
//		Main.resetMainWindow();
	}

	public boolean isComplete() {
		return isComplete;
	}

	public void setComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}

	
	public int releaseMemory(){
		return bp.releaseMemory();
	}
	
	
	public void putPresenter(String name, MyBasePresenter presenter){
		presenterMap.put(name, presenter);
	}
	
	public MyBasePresenter getPresenter(String name){
		return presenterMap.get(name);
	}
	
	
}
