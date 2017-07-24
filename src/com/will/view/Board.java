package com.will.view;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

import com.will.model.Cell;
import com.will.model.GameInfo;
import com.will.presenter.CVBoardPresenter;
import com.will.presenter.GCBoardPresenter;
import com.will.presenter.GameController;
import com.will.presenter.Player;
//TODO 不知为什么，cvlist为空
public class Board implements CVBoardPresenter,GCBoardPresenter{
	//这里的w和h都是尺寸，不是格子数
	private int w;
	private int h;
	private int rowNum;
	private int colNum;
	private GameInfo gameInfo;
	
	private Player player;
	private ArrayList<CellView> cvlist;
	private ArrayList<Integer> mineIndexList;//记录的是雷在cvlist中的位置
	private ArrayList<Integer> aroundIndexList;//主要是临时用,用于找到每个方块周围的方块，保存的是在cvlist中的位置
	private JPanel panel;
	private boolean firstBlood = true; //是否第一次点击，一血是我个人喜欢的叫法
	private static Board board;
	
	public static Board getBoard(){
		if(board == null){
			board = new Board();
			return board;
		}
		else
			return board;
	}
	
	private Board(){
		gameInfo = GameInfo.getGameInfo();
		w = gameInfo.getBoardWidth();
		h = gameInfo.getBoardHeight();
		rowNum = gameInfo.getRowNum();
		colNum = gameInfo.getColNum();
		initialize();
	}
	
	public void initialize(){
		cvlist = new ArrayList<>();
		mineIndexList = new ArrayList<>();
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
	public boolean checkFlagAround(int x, int y) {
		ArrayList<Integer> surrondIndex = (ArrayList<Integer>) getSurround(x, y);
		//TODO 判断:当四周的红旗数=该方格的数字时，开始挖周围9格
		return false;
	}

	@Override
	public void explode(int x, int y) {
		if(mineIndexList == null || mineIndexList.size() == 0){
			System.out.println("爆炸函数跪了");
			return;
		}
		
		for(Integer cvpos : mineIndexList){
			cvlist.get(cvpos).boom();
		}
		
		//TODO 需要一个弹窗接口
	}
	
	@Override
	public void firstBloodReport(int clickedX, int clickedY) {
		if(firstBlood){
			putMineAndNumber(clickedX, clickedY);
			firstBlood = false;
		}
	}
	
	
	
	
	/*=====================For GC==================================*/
	/**
	 * GC挖开方块时调用这个接口来触发界面变化以及相关逻辑 
	 * @param x 待挖开的横坐标,从1开始
	 * @param y 待挖开的纵坐标,从1开始
	 * @return 挖开方块的值，参照Cell中的设定
	 */
	@Override
	public int diggingForGC(int x, int y) {
		CellView cv = findCellView(x, y);
		Cell data = cv.getData();
		if(data.getVal() == Cell.MINE){
			//埋回去，什么都不干
		}
		else if(data.getVal() == Cell.EMPTY){
			GameController.getGC().digAround(x, y);
			cv.setSelected(true);
		}
		else if(data.getVal() > 0 && data.getVal() < 9){
			cv.digChange();
		}
		return data.getVal();
	}
	
	@Override
	public JPanel ready() {
		panel = new JPanel();
		GridLayout gl = new GridLayout(rowNum,colNum,2,2);
		panel.setLayout(gl);
		
		for(int i = 1; i <= rowNum; i++){
			for(int j = 1; j <= colNum; j++){
				//TODO 录入格子信息,并将格子放到盘面中
				CellView cv = new CellView(new Cell(i,j));
				cvlist.add(cv);
				panel.add(cv);
			}
		}
		return panel;
	}

	@Override
	public void setPlayer(Player p) {
		this.player = p;
	}
	
	/**
	 * 通过输入坐标来直接获得CellView
	 * @param x
	 * @param y
	 * @return 需要获取的CellView
	 */
	@Override
	public CellView findCellView(int x, int y) {
		CellView tcv = new CellView(new Cell(x,y));
		if(cvlist.contains(tcv)){
			int index = cvlist.indexOf(tcv);
			return cvlist.get(index);
		}
		else
			return null;
	}
	
	private int getCellViewIndex(int x, int y){
		CellView tcv = new CellView(new Cell(x,y));
		boolean f = cvlist.contains(tcv);
		if(cvlist.contains(tcv)){
			int index = cvlist.indexOf(tcv);
			return index;
		}
		else
			return -1;
	}

	/**
	 * @param clickedX 点击的方块的横坐标
	 * @param clickedY 点击的方块的纵坐标
	 * 因为不能让玩家第一次点击就输，所以要拿到第一次点击的位置
	 * 确定Panel中哪些是雷，接着计算每个方块周围的雷的数量
	 */
	@Override
	public void putMineAndNumber(int clickedX, int clickedY) {
		// TODO 还要记录雷放在哪里，mineList
		int tempX = 0;
		int tempY = 0;
		int mineNum = gameInfo.getMineNum();
		Random random = new Random();
		
		//确保没有重复的雷
		for(int i = 0; i < mineNum; i++){
			//随机得出一个坐标
			do{
				tempX = random.nextInt(rowNum-1) +1;
				tempY = random.nextInt(colNum-1) +1;
			}while(clickedX == tempX && clickedY == tempY);
			
			//获取这个位置的雷在cvlist中的index
			int index = getCellViewIndex(tempX, tempY);
			if(!mineIndexList.contains(index)){
				mineIndexList.add(index);
				cvlist.get(index).setDataVal(Cell.MINE);
			}
			else{
				//这个坐标不能用，回退一次
				i--;
			}
		}
		
		System.out.println("Board---mine num： "+mineIndexList.size());
		
		//开始统计数字
		for(CellView cv : cvlist){
			getSurround(cv.getData().x, cv.getData().y);
			int mineCounter = 0;
			//判断周围9格中哪些是雷
			for(Integer i : aroundIndexList){
				if(cvlist.get(i).isMine())
					mineCounter++;
			}
			
			if(mineCounter < 9){
				if(cv.getData().getVal() == Cell.EMPTY)
					cv.setDataVal(mineCounter);
			}
			else{
				System.out.println("Board---统计雷时出错");
			}
			
		}
		
	}
	
	@Override
	public List<Integer> getSurround(int oldx,int oldy){
		//初始化周围格的列表
		aroundIndexList = new ArrayList<>();
		
		int w = getWidth();
		int h = getHeight();
		int tempcv = 0;
		if((tempcv = getCellViewIndex(oldx, oldy-1)) != -1){
			aroundIndexList.add(tempcv);
		}
		if((tempcv = getCellViewIndex(oldx, oldy+1)) != -1){
			aroundIndexList.add(tempcv);
		}
		if((tempcv = getCellViewIndex(oldx-1, oldy)) != -1){
			aroundIndexList.add(tempcv);
		}
		if((tempcv = getCellViewIndex(oldx-1, oldy-1)) != -1){
			aroundIndexList.add(tempcv);
		}
		if((tempcv = getCellViewIndex(oldx-1, oldy+1)) != -1){
			aroundIndexList.add(tempcv);
		}
		if((tempcv = getCellViewIndex(oldx+1, oldy)) != -1){
			aroundIndexList.add(tempcv);
		}
		if((tempcv = getCellViewIndex(oldx+1, oldy-1)) != -1){
			aroundIndexList.add(tempcv);
		}
		if((tempcv = getCellViewIndex(oldx+1, oldy+1)) != -1){
			aroundIndexList.add(tempcv);
		}
		
		if(aroundIndexList.size() > 8){
			System.err.println("Board---找周围格时出事了");
		}
		return aroundIndexList;
	}

}
