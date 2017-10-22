package com.will.presenter;

import java.util.ArrayList;
import java.util.List;

import com.will.model.Cell;
import com.will.view.CellView;

public abstract class User {
	public final String name = "user";
	protected ArrayList<Integer> aroundIndexList;
	protected GCBoardPresenter bp;
	
	/**
	 * @param type 方块的类别：空、数字、雷
	 * 挖掘方法，挖开输入坐标的方块
	 * @return true--->空，可以继续挖	false--->为数字或者是雷，具体情况需要具体实现 
	 */
	public abstract boolean dig(int x, int y);
	public abstract void afterDug(int x, int y);
	
	
}
