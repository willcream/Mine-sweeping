package com.will.view;

import java.util.ArrayList;

import com.will.model.Cell;

public class Board {
	//TODO private ArrayList<ArrayList<格子>> cellArray;
	private int w;
	private int h;
	
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
}
