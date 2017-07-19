package com.will.presenter;

import com.will.view.CellView;

public interface BoardPresenter {
	int getWidth();
	int getHeight();
	
	/**
	 * GC挖开方块时调用这个接口来触发界面变化以及相关逻辑 
	 * @param x 待挖开的横坐标
	 * @param y 待挖开的纵坐标
	 * @return 挖开方块的值，参照Cell中的设定
	 */
	int diggingForGC(int x, int y);
	
	CellView findCellView(int x, int y);
	
	Player getPlayer();
	
	/**
	 * 当方块被挖开时调用该方法，将CellView换成DugView（已挖开的方块） 
	 * @param x 待挖开的横坐标
	 * @param y 待挖开的纵坐标
	 */
	void changeCell2dug(int x,int y);
}
