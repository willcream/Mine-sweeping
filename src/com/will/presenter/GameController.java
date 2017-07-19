package com.will.presenter;

import java.util.ArrayList;

import com.will.model.Cell;
import com.will.view.CellView;

public class GameController extends User{
	public final String name = "GC";
	
	private ArrayList<CellView> aroundList;
	private BoardPresenter bp;

	@Override
	public void dig(int x, int y) {
		//TODO 挖开方法待完善
	}

	@Override
	public void afterDug(int x, int y) {
		// TODO 挖开后的界面变化
		digAround(x, y);
	}
	
	private void digAround(int oldx, int oldy){
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
	
	private void getSurround(int oldx,int oldy){
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
	}
}
