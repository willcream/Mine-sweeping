package com.will.presenter;

import com.will.view.CellView;

public interface CVBoardPresenter extends BoardPresenter{
	/**
	 * 检测四周红旗数与该方格的数字是否相符，相符则开始挖9格
	 */
	boolean checkFlagAround(int x, int y);
	
	
	
	/**
	 * 点了第一下后通知棋盘，为后面布置雷和数字做准备
	 * @param clickedX
	 * @param clickedY
	 */
	void firstBloodReport(int clickedX, int clickedY);

	
	
	/**
	 * 玩家挖到炸弹后调用该方法，用于结束游戏
	 */
	void explodeReport();
	
	
	
	/**
	 * 玩家点了一个方格后开始自动挖掘周围的空白格
	 * @param x
	 * @param y
	 */
	void autoDigAround(int x, int y);
	
	
	
	/**
	 * 用来满足鼠标中键的点击和左右双键的点击
	 * @param centerX 中心方块的横坐标
	 * @param centerY 中心方块的纵坐标
	 */
	void digAround(int centerX, int centerY);
	
}
