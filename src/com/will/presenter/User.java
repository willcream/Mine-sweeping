package com.will.presenter;

import java.util.ArrayList;
import java.util.List;

import com.will.model.Cell;
import com.will.view.CellView;

public abstract class User {
	public final String name = "user";
	private ArrayList<CellView> aroundList;
	private GCBoardPresenter bp;
	
	/**
	 * @param type 方块的类别：空、数字、雷
	 * 挖掘方法，挖开输入坐标的方块
	 * @return true--->空，可以继续挖	false--->为数字或者是雷，具体情况需要具体实现 
	 */
	public abstract boolean dig(int x, int y);
	public abstract void afterDug(int x, int y);
	
	public void digAround(int oldx, int oldy){
		getSurround(oldx, oldy);
		if(aroundList == null || aroundList.size() == 0)
			return ;
		for(CellView cv : aroundList){
			Cell tempc = cv.getData();
			dig(tempc.x, tempc.y);
			digAround(tempc.x, tempc.y);
		}
		aroundList = null;
	}
	
	public List<CellView> getSurround(int oldx,int oldy){
		//初始化周围格的列表
		aroundList = new ArrayList<>();
		
		int w = bp.getWidth();
		int h = bp.getHeight();
		CellView tempcv = null;
		if((tempcv = bp.findCellView(oldx, oldy-1)) != null){
			aroundList.add(tempcv);
		}
		if((tempcv = bp.findCellView(oldx, oldy+1)) != null){
			aroundList.add(tempcv);
		}
		if((tempcv = bp.findCellView(oldx-1, oldy)) != null){
			aroundList.add(tempcv);
		}
		if((tempcv = bp.findCellView(oldx-1, oldy-1)) != null){
			aroundList.add(tempcv);
		}
		if((tempcv = bp.findCellView(oldx-1, oldy+1)) != null){
			aroundList.add(tempcv);
		}
		if((tempcv = bp.findCellView(oldx+1, oldy)) != null){
			aroundList.add(tempcv);
		}
		if((tempcv = bp.findCellView(oldx+1, oldy-1)) != null){
			aroundList.add(tempcv);
		}
		if((tempcv = bp.findCellView(oldx+1, oldy+1)) != null){
			aroundList.add(tempcv);
		}
		
		if(aroundList.size() > 8){
			System.err.println("周围格列表出事了");
		}
		return aroundList;
	}
}
