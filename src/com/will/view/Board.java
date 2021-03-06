package com.will.view;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.will.model.Cell;
import com.will.model.GameInfo;
import com.will.model.GlobalMsg;
import com.will.presenter.CVBoardPresenter;
import com.will.presenter.GCBoardPresenter;
import com.will.presenter.GameController;
import com.will.presenter.MainWindowPresenter;
import com.will.presenter.Player;
public class Board implements CVBoardPresenter,GCBoardPresenter{
	//这里的w和h都是尺寸，不是格子数
	private int w;
	private int h;
	private int rowNum;
	private int colNum;
	private GameInfo gameInfo;
	private GameController gc;
	
	private int mineMarkNum;
	private int dugNum;
	
//	private Player player;
	private ArrayList<CellView> cvlist;
	private ArrayList<Integer> mineIndexList;//记录的是雷在cvlist中的位置
	private ArrayList<Integer> aroundIndexList;//主要是临时用,用于找到每个方块周围的方块，保存的是在cvlist中的位置
	private JPanel panel;
	private boolean firstBlood = true; //是否第一次点击，一血是我个人喜欢的叫法
//	private static Board board;
	
//	public static Board getBoard(){
//		if(board == null){
//			board = new Board();
//			return board;
//		}
//		else
//			return board;
//	}
	
	public Board(){
		initializeData();
		panel = new JPanel();
		gc = GameController.getGC();
		gc.putPresenter(GlobalMsg.S_CV_BOARD_P, this);
		gc.putPresenter(GlobalMsg.S_GC_BOARD_P, this);
	}
	
	public void initializeData(){
		gameInfo = GameInfo.getGameInfo();
		w = gameInfo.getBoardWidth();
		h = gameInfo.getBoardHeight();
		rowNum = gameInfo.getRowNum();
		colNum = gameInfo.getColNum();
		
		cvlist = new ArrayList<>();
		mineIndexList = new ArrayList<>();
		dugNum = 0;
		mineMarkNum = 0;
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
	

	private void gameover(){
		int res= JOptionPane.showConfirmDialog(panel, "游戏结束，是否重来？", "", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.NO_OPTION || res == JOptionPane.CLOSED_OPTION)
			System.exit(0);
		else if(res == JOptionPane.YES_OPTION){
			//重新开始
			restart();
			GameController.getGC().setOver(false);
		}
		
		
	}
	
	private void complete(){
		System.out.println(GameController.getGC().isOver());
		
		int res= JOptionPane.showConfirmDialog(panel, "恭喜你，通关了！是否再来？", "", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.NO_OPTION || res == JOptionPane.CLOSED_OPTION)
			System.exit(0);
		else if(res == JOptionPane.YES_OPTION){
			//重新开始
			restart();
			GameController.getGC().setOver(false);
		}
	}
	
	
	/*=====================For CellView============================*/
	@Override
	public boolean checkFlagAround(int x, int y) {
		ArrayList<Integer> surrondIndex = (ArrayList<Integer>) getSurround(x, y);
		int val = findCellView(x, y).getData().getVal();
		int flagCounter = 0;
		for(Integer i : surrondIndex){
			if(cvlist.get(i).isFlagMarked())
				flagCounter++;
		}
		if(val == flagCounter)
			return true;
		else
			return false;
	}

	@Override
	public void firstBloodReport(int clickedX, int clickedY) {
		if(firstBlood){
			System.out.println("firstBlood");
			putMineAndNumber(clickedX, clickedY);
			firstBlood = false;
		}
	}
	
	@Override
	public void explodeReport() {
		if(mineIndexList == null || mineIndexList.size() == 0)
			System.err.println("mineIndexList is null!");
		
		try {
			new Thread(new Runnable() {
				@Override
				public void run() {
					for(Integer i : mineIndexList){
						cvlist.get(i).boom();
					}
				}
			}).start();
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		gameover();
	}
	
	
	/**
	 * 当方块被旗标记或者取消旗标记时需要用这方法来更新记录
	 * @param isMarked
	 */
	@Override
	public void flagReport(boolean isMarked, boolean isMine) {
		if(isMarked){
			if(isMine)
				mineMarkNum++;
		}
		//取消旗标识
		else{
			if(isMine)
				mineMarkNum--;
		}
		
	}
	

	/**
	 * 挖开一个就记录一个。
	 */
	@Override
	public void digReport() {
		dugNum++;
	}
	

	/**
	 * 玩家点了一个方格后开始自动挖掘周围的空白格,需要确保该方法会在CellView.digChange()后使用
	 * @param x
	 * @param y
	 */
	@Override
	public void autoDigAround(int x, int y) {
		//如果这个方格挖开后是数字，那么它的周围不再挖
		CellView thiscv = findCellView(x, y);
		
		if(thiscv.isNumber())
			return;
		
		List<Integer> surroundList = getSurround(x, y);
		//挖周围的
		for(Integer i : surroundList){
			CellView tempcv = cvlist.get(i);
			//挖过就不管了
			if(tempcv.isDug())
				continue;
			
			//不挖的情况：1.雷　　2.旗　　3.数字　　4.已挖掘
			Cell data = tempcv.getData();
			if(data.getVal() == Cell.MINE || tempcv.isFlagMarked() || tempcv.isDug()){
				//什么都不干
			}
			else if(!tempcv.isDug()){
				tempcv.digChange();
			}
		}
	}
	
	
	/**
	 * 用来满足鼠标中键的点击和左右双键的点击
	 * @param centerX 中心方块的横坐标
	 * @param centerY 中心方块的纵坐标
	 */
	@Override
	public void digAround(int centerX, int centerY) {
		List<Integer> surroundList = getSurround(centerX, centerY);
		//挖周围的，能挖的情况：当前格的数字能够与其周围的红旗数匹配
		if(checkFlagAround(centerX, centerY)){
			//这种情况下，相当于是玩家自己点，但不应该继续递归下去。
			for(Integer i : surroundList){
				CellView cv = cvlist.get(i);
				if(cv.isMine() && !cv.isFlagMarked()){
					cv.boom();
					explodeReport();
					break;
				}
				else {
					cv.dig();
				}
			}
		}
		else {
		}
	}
	
	
	/**
	 * 每次点击之后都查看一下，看有没有达成赢的条件。
	 */
	@Override
	public void winCheck() {
		if(mineMarkNum + dugNum == gameInfo.getCellNum())
			complete();
		else
			return;
	}
	
	
	
	
	
	
	

	/*=====================For GC==================================*/	

	@Override
	public JPanel ready() {
		rowNum = gameInfo.getRowNum();
		colNum = gameInfo.getColNum();
		panel.removeAll();
		GridLayout gl = new GridLayout(rowNum,colNum,2,2);
		panel.setLayout(gl);
		
		for(int i = 1; i <= rowNum; i++){
			for(int j = 1; j <= colNum; j++){
				//录入格子信息,并将格子放到盘面中
				CellView cv = new CellView(new Cell(i,j));
				cvlist.add(cv);
				panel.add(cv);
			}
		}
		return panel;
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
		
		System.out.println("cvlist size="+cvlist.size());
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

	
	/**
	 * 需要做：重置所有方格，firstBlood重置
	 */
	@Override
	public void restart() {
		System.out.println("restart……");
		for(CellView cv : cvlist) {
			cv.resetData();
			cv.resetView();
		}
		firstBlood = true;
		GameController.getGC().setOver(false);
		mineIndexList = new ArrayList<>();
		aroundIndexList = new ArrayList<>();
		dugNum = 0;
		mineMarkNum = 0;
		//通知MainWindow重置红旗数
		((MainWindowPresenter)gc.getPresenter(GlobalMsg.S_MAIN_WINDOW_P)).resetFlagTag();
		
	}

	@Override
	public void changeLevel() {
		panel = ready();
		panel.updateUI();
		firstBlood = true;
		GameController.getGC().setOver(false);
		mineIndexList = new ArrayList<>();
		aroundIndexList = new ArrayList<>();
		dugNum = 0;
		mineMarkNum = 0;
	}


	
	@Override
	public int releaseMemory() {
		cvlist.removeAll(cvlist);
		return cvlist.size();
	}
	
	
	

}
