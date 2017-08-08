package com.will.model;

import com.will.presenter.Player;
import com.will.view.CellView;

public class GameInfo {
	public static final int LEVEL_LOW = 0;
	public static final int LEVEL_NORMAL = 1;
	public static final int LEVEL_HIGH = 2;
	
	//不同难度下的盘面规格
	//低级：9*9（ 10雷）    中级：16*16（40雷）    高级：16*30（99）雷
	private final int LOW_ROW_NUM = 9;
	private final int LOW_COL_NUM = 9;
	private final int LOW_MINE_NUM = 10;
	
	private final int NORMAL_ROW_NUM = 16;       
	private final int NORMAL_COL_NUM = 16;       
	private final int NORMAL_MINE_NUM = 300;     
	
	private final int HIGH_ROW_NUM = 16;       
	private final int HIGH_COL_NUM = 30;       
	private final int HIGH_MINE_NUM = 99;     
	
	
	
	private static GameInfo gameInfo;
	
	private int level;
	private int boardWidth;
	private int boardHeight;
	private int cellNum;	//方格总数
	private int rowNum;
	private int colNum;
	private int mineNum;
	
	private int windowHeight;
	private int windowWidth;
	
	
	/**
	 * 在Main.java中使用，初始化游戏信息。
	 * @param level 游戏难度，在菜单中选择
	 */
	public static GameInfo createGameInfo(int level){
		if(gameInfo == null)
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
	
	
	private GameInfo(int level){
		this.level = level;
		produceBoardInfo();
	}
	
	private void produceBoardInfo(){
		switch(level){
		case LEVEL_LOW:
			rowNum = LOW_ROW_NUM;
			colNum = LOW_COL_NUM;
			mineNum = LOW_MINE_NUM;
			break;
			
		case LEVEL_NORMAL:
			rowNum = NORMAL_ROW_NUM;
			colNum = NORMAL_COL_NUM;
			mineNum = NORMAL_MINE_NUM;
			break;
			
		case LEVEL_HIGH:
			rowNum = HIGH_ROW_NUM;
			colNum = HIGH_COL_NUM;
			mineNum = HIGH_MINE_NUM;
			break;
			
		}
		boardWidth = calBoardWidth(colNum);
		boardHeight = calBoardHeight(rowNum);
		windowHeight = boardHeight + 40;
		windowWidth = boardWidth;
		cellNum = rowNum * colNum;
	}
	
	public void reset(int level){
		this.level = level;
		produceBoardInfo();
	}
	
	private int calBoardWidth(int numInCol){
		return numInCol * CellView.CELL_WIDTH;
	}
	
	private int calBoardHeight(int numInRow){
		return numInRow * CellView.CELL_WIDTH;
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

	public int getWindowHeight() {
		return windowHeight;
	}

	public int getWindowWidth() {
		return windowWidth;
	}

	
	
}
