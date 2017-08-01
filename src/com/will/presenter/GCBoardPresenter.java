/**
 * 
 */
package com.will.presenter;

import java.util.List;

import javax.swing.JPanel;

import com.will.view.CellView;

/**
 * @author Will
 *
 */
public interface GCBoardPresenter extends BoardPresenter {
	void setPlayer(Player p);
	
	CellView findCellView(int x, int y);
	
	List<Integer> getSurround(int oldx,int oldy);
	
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
	
	
	/**
	 * 需要做：重置所有方格，firstBlood重置
	 */
	void restart();
}
