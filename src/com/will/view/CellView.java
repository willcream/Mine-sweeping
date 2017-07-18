package com.will.view;


import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.border.Border;

import com.will.model.Cell;

public class CellView extends JButton {
	private Cell data;
	
	public CellView(Cell cell) {
//		super("");
		// TODO Auto-generated constructor stub
		data = cell;
		this.setSize(25, 25);
	}

	@Override
	public boolean equals(Object obj) {
		int ox = ((CellView)obj).getData().x;
		int oy = ((CellView)obj).getData().y;
		if(data.x == ox && data.y == oy){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Cell getData(){
		return data;
	}
	
}
