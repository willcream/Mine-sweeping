package com.will.model;

public class Cell{
	public int x;
	public int y;
	protected int val; //值：0为空，1~8为数字，9为雷
	protected boolean dug;
	
	public final static int EMPTY = 0;
	public final static int MINE = 9; 
	public final static int DUG = -1;
	
	public Cell(){
		dug = false;
	}
	
	/**
	 * 这个构造方法是用来生成一个临时的Cell，用于在List中寻找有该坐标的CellView
	 * @param x
	 * @param y
	 */
	public Cell(int x, int y){
		this.x = x;
		this.y = y;
		dug = false;
	}
	
	/**
	 * 判断是否可以挖掘
	 * @return 
	 */
	public boolean canGoOnDig(){
		if(val == 0 && !dug){
			return true;
		}else{
			System.out.println("Cell---val:"+val);
			return false;
		}
				
	}

	public boolean isDug() {
		return dug;
	}

	public void setDug(boolean dug) {
		this.dug = dug;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getVal() {
		return val;
	}

	public void setVal(int val) {
		this.val = val;
	}
	
	
	
}
