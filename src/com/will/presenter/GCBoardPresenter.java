/**
 * 
 */
package com.will.presenter;

import javax.swing.JPanel;

import com.will.view.CellView;

/**
 * @author Will
 *
 */
public interface GCBoardPresenter extends BoardPresenter {
	/**
	 * 玩家挖开方块之后，就得让GC来挖周围方块，GC挖开方块时调用这个接口来触发界面变化以及相关逻辑 
	 * @param x 待挖开的横坐标
	 * @param y 待挖开的纵坐标
	 * @return 挖开方块的值，参照Cell中的设定
	 */
	int diggingForGC(int x, int y);
	
	void setPlayer(Player p);
	
	CellView findCellView(int x, int y);
	
	/**
	 * 确定盘面大小，并初始化所有方格,在玩家点下第一格之前并不布置雷
	 * @param width 横向的方格总数
	 * @param height 纵向的方格总数
	 */
	JPanel ready();
	
	/**
	 * @param clickedX 点击的方块的横坐标
	 * @param clickedY 点击的方块的纵坐标
	 * 确定Panel中哪些是雷，接着计算每个方块周围的雷的数量
	 */
	void putMineAndNumber(int clickedX, int clickedY);
	
}
