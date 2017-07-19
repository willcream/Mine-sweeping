package com.will.view;

import java.util.ArrayList;

import com.will.model.Cell;
import com.will.presenter.BoardPresenter;
import com.will.presenter.Player;

public class Board implements BoardPresenter{
	//TODO private ArrayList<ArrayList<格子>> cellArray;
	private int w;
	private int h;
	private ArrayList<CellView> cvlist;
	
	public Board(int width,int height){
		w = width;
		h = height;
	}
	
	public void initialize(){
		for(int i = 0; i < w; i++){
			for(int j = 0; j < h; j++){
				//TODO 录入格子信息
			}
		}
	}

	@Override
	public int getWidth() {
		return w;
	}

	@Override
	public int getHeight() {
		return h;
	}

	@Override
	public int diggingForGC(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public CellView findCellView(int x, int y) {
		Cell tcell = new Cell(x,y);
		if(cvlist.contains(tcell)){
			int index = cvlist.indexOf(tcell);
			return cvlist.get(index);
		}
		else
			return null;
	}

	@Override
	public Player getPlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeCell2dug(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
