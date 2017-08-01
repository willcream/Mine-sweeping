package com.will.view;


import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.will.model.Cell;
import com.will.model.GameInfo;
import com.will.presenter.CVBoardPresenter;
import com.will.presenter.GameController;
import com.will.presenter.Player;

public class CellView extends JButton{
	private Cell data;
	private int state;
	private CVBoardPresenter bp;
	
	private final static int STATE_NONE = 0;
	private final static int STATE_FLAG = 1;
	private final static int STATE_QUESTION_MARK = 2;
	private final static int STATE_DUG = 3;
	private final static int STATE_EXPLODE = 4;
	
	private final static int TO_NONE = 10;
	private final static int TO_FLAG = 11;
	private final static int TO_QUESTION_MARK = 12;
	
	public final static int CELL_WIDTH = 25;
	
	public CellView(Cell cell) {
		data = cell;
		state = STATE_NONE;
		bp = Board.getBoard();
		initializeView();
		int counter = 0;
		
	}

	private void initializeView(){
		//方便显示文字
		this.setMargin(new Insets(0,0,0,0));
		this.setSize(25, 25);
		this.addMouseListener(new MouseAdapter() {
			boolean isDouble = true;
			
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
			}

			@Override
			public void mouseReleased(java.awt.event.MouseEvent e) {
				if(isDouble){
					if(state != STATE_DUG)
						return;
					bp.digAround(data.x, data.y);
					return;
				}
				
				//挖开了就不管了。
				if(state == STATE_DUG || state == STATE_EXPLODE)
					return;
				
				int clicked = e.getButton();
				if(clicked == java.awt.event.MouseEvent.BUTTON1){
					System.out.println("左键");
					
					//要第一次点下去才算
					if(data.getVal() != Cell.MINE && state != STATE_FLAG)
						bp.firstBloodReport(data.x, data.y);
					dig();
					
				}
				
				//鼠标中键
				else if(clicked == java.awt.event.MouseEvent.BUTTON2){
					if(state != STATE_DUG)
						return;
					bp.digAround(data.x, data.y);
				}
				
				//鼠标右键
				else if(clicked == java.awt.event.MouseEvent.BUTTON3){
					if(state == STATE_QUESTION_MARK){
						markChange(TO_NONE);
					}
					else{
						markChange(state+11);
					}
				}
			}

			@Override
			public void mousePressed(java.awt.event.MouseEvent e) {
				boolean double1 = e.getModifiersEx() == (e.BUTTON1_DOWN_MASK | e.BUTTON3_DOWN_MASK);
				boolean double2 = e.getModifiersEx() == (e.BUTTON3_DOWN_MASK | e.BUTTON1_DOWN_MASK);
				isDouble = double1 || double2; 
				if(isDouble)
					System.out.println("左右键一起按");
			}
			
			
		});
	}
	
	@Override
	public boolean equals(Object obj) {
		try{
			int ox = ((CellView)obj).getData().x;
			int oy = ((CellView)obj).getData().y;
			if(data.x == ox && data.y == oy){
				return true;
			}
			else{
				return false;
			}
		}catch(Exception e){
			//目前遇到的问题是弹窗会调用到这个函数，但对于整体功能并不影响，为了控制台输出
			//信息好看，这里直接屏蔽掉。
			return true;
		}
	}
	
	public Cell getData(){
		return data;
	}
	
	public void dig() {
		//如果被旗标记了就不管
		if(state == STATE_FLAG)
			return;
		if(isMine()){
			boom();
			bp.explodeReport();
		}
		else{
			digChange();
		}
	}

	public void digChange(){
		if(state == STATE_DUG)
			return;
		this.setIcon(null);
		if(data.getVal() > 0 && data.getVal() < 9)
			this.setText(""+data.getVal());
		this.setEnabled(false);
		this.setBackground(Color.LIGHT_GRAY);
		state = STATE_DUG;
		data.setDug(true);
		
		bp.digReport();
		bp.autoDigAround(data.x, data.y);
		
		bp.winCheck();
	}
	
	private void markChange(int order){
		switch(order){
		case TO_NONE:
			this.setIcon(null);
			state = STATE_NONE;
			break;
			
		case TO_FLAG:
			this.setIcon(new ImageIcon("flag.png"));
			state = STATE_FLAG;
			Main.subFlagTag();
			bp.flagReport(true, isMine());
			bp.winCheck();
			break;
			
		case TO_QUESTION_MARK:
			this.setIcon(new ImageIcon("question-mark.png"));
			state = STATE_QUESTION_MARK;
			Main.addFlagTag();
			bp.flagReport(false, isMine());
			break;
			
		default:
			break;			
		}
	}
	
	public void boom(){
		this.setDisabledIcon(new ImageIcon("boom.png"));
		this.setIcon(new ImageIcon("boom.png"));
		this.setEnabled(false);
		state = STATE_EXPLODE;
	}
	
	public void setDataVal(int val){
		data.setVal(val);
	}
	
	public boolean isMine(){
		if(data.getVal() == Cell.MINE){
			return true;
		}
		else
			return false;
	}
	
	public boolean isFlagMarked(){
		if(state == STATE_FLAG)
			return true;
		else
			return false;
	}
	
	public boolean isDug(){
		if(state == STATE_DUG)
			return true;
		else
			return false;
	}
	
	public int getState(){
		return state;
	}
	
	public boolean isNumber(){
		if(data.getVal() > 0 && data.getVal() < 9)
			return true;
		else
			return false;
	}
	
	public void reset() {
		data.setVal(Cell.EMPTY);
		state = STATE_NONE;
		this.setText("");
		this.setDisabledIcon(null);
		this.setIcon(null);
		this.setEnabled(true);
		this.setBackground(null);
	}
}