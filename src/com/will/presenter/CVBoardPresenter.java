package com.will.presenter;

import com.will.view.CellView;

public interface CVBoardPresenter extends BoardPresenter{
	Player getPlayer();
	
	/**
	 * 检测四周红旗数与该方格的数字是否相符，相符则开始挖9格
	 */
	boolean checkFlagAround(int x, int y);
	
	void explode(int x, int y);
	
}
