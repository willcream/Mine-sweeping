package com.will.model;

import com.will.presenter.Player;

public class GameInfo {
	public static final int LEVEL_LOW = 0;
	public static final int LEVEL_NORMAL = 1;
	public static final int LEVEL_HIGH = 2;
	
	//不同难度下的盘面规格
	//低级：9*9（ 10雷）    中级：16*16（40雷）    高级：16*30（99）雷
	private final int LOW_BOARD_WIDTH = 250;
	private final int LOW_BOARD_HEIGHT = 250;
	private final int LOW_ROW_NUM = 9;
	private final int LOW_COL_NUM = 9;
	private final int LOW_MINE_NUM = 10;
	
	private static GameInfo gameInfo;
	
	private int level;
	private int boardWidth;
	private int boardHeight;
	private int cellNum;	//方格总数
	private int rowNum;
	private int colNum;
	private int mineNum;
	
	private GameInfo(int level){
		this.level = level;
		produceBoardInfo();
	}
	
	/**
	 * 在Main.java中使用，初始化游戏信息。
	 * @param level 游戏难度，在菜单中选择
	 */
	public static GameInfo createGameInfo(int level){
		gameInfo = new GameInfo(level);
		return gameInfo;
	}
	
	/**
	 * 若没有初始化，千万不要调用这个方法
	 * @return 游戏信息
	 */
	public static GameInfo getGameInfo(){
		if(gameInfo == null){
			System.err.println("gameInfo没有初始化");
		}
		return gameInfo;
	}
	
	private void produceBoardInfo(){
		switch(level){
		case LEVEL_LOW:
			boardWidth = LOW_BOARD_WIDTH;
			boardHeight = LOW_BOARD_HEIGHT;
			rowNum = LOW_ROW_NUM;
			colNum = LOW_COL_NUM;
			mineNum = LOW_MINE_NUM;
			break;
			
		case LEVEL_NORMAL:
			break;
			
		case LEVEL_HIGH:
			break;
			
		}
		cellNum = rowNum * colNum;
	}

	public int getLevel() {
		return level;
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public int getBoardHeight() {
		return boardHeight;
	}

	public int getRowNum() {
		return rowNum;
	}

	public int getColNum() {
		return colNum;
	}

	public int getMineNum() {
		return mineNum;
	}
	
	public int getCellNum(){
		return cellNum;
	}
}
