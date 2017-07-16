package com.will.model;

public class Cell{
	protected int x;
	protected int y;
	protected int val; //值：0为空，1~8为数字，9为雷
	protected boolean dug;
	
	public final static int EMPTY = 0;
	public final static int MINE = 9; 
	public final static int DUG = -1;
	
	public Cell(){
		dug = false;
	}
}
