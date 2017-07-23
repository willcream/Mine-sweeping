package com.will.view;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.will.model.Cell;
import com.will.presenter.CVBoardPresenter;
import com.will.presenter.GCBoardPresenter;
import com.will.presenter.Player;

public class Board implements CVBoardPresenter,GCBoardPresenter{
	//这里的w和h都是尺寸，不是格子数
	private int w;
	private int h;
	private int rowNum;
	private int colNum;
	
	private Player player;
	private ArrayList<CellView> cvlist;
	private JPanel panel;
	
	private static Board board;
	
	public static Board getBoard(int width,int height){
		if(board == null){
			board = new Board(width,height);
			return board;
		}
		else
			return board;
	}
	
	public Board(int width,int height){
		w = width;
		h = height;
	}
	
	public void initialize(){
		
	}

	public JPanel getPanel(){
		return panel;
	}
	
	
	@Override
	public int getWidth() {
		return w;
	}

	@Override
	public int getHeight() {
		return h;
	}
	
	
	/*=====================For CellView============================*/
	@Override
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public boolean checkFlagAround(int x, int y) {
		ArrayList<CellView> surrond = (ArrayList<CellView>) player.getSurround(x, y);
		//TODO 判断:当四周的红旗数=该方格的数字时，开始挖周围9格
		return false;
	}

	@Override
	public void explode(int x, int y) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	/*=====================For GC==================================*/
	@Override
	public int diggingForGC(int x, int y) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public JPanel ready(int width, int height) {
		rowNum = h / CellView.CELL_WIDTH;
		colNum = w / CellView.CELL_WIDTH;
		
		panel = new JPanel();
		GridLayout gl = new GridLayout(rowNum,colNum);
		panel.setLayout(gl);
		
		for(int i = 1; i <= rowNum; i++){
			for(int j = 1; j <= colNum; j++){
				//TODO 录入格子信息,并将格子放到盘面中
				CellView cv = new CellView(new Cell(i,j));
				panel.add(cv);
			}
		}
		return panel;
	}

	@Override
	public void setPlayer(Player p) {
		this.player = p;
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
	public void putMineAndNumber() {
		// TODO Auto-generated method stub
		
	}

}
