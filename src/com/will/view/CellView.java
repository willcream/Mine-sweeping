package com.will.view;


import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

import org.w3c.dom.events.MouseEvent;

import com.will.model.Cell;

public class CellView extends JButton implements MouseListener{
	private Cell data;
	private int state;
	
	private final static int STATE_NONE = 0;
	private final static int STATE_FLAG = 1;
	private final static int STATE_QUESTION_MARK = 2;
	private final static int STATE_DUG = 3;
	
	
	public CellView(Cell cell) {
//		super("");
		data = cell;
		state = STATE_NONE;
		this.setSize(25, 25);
		this.addMouseListener(this);
		
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
	
	public void change2flag(){
		this.setIcon(new ImageIcon("flag.png"));
	}
	
	public void change2QuestionMark(){
		this.setIcon(new ImageIcon("question-mark.png"));
	}
	
	
	@Override
	public void mouseClicked(java.awt.event.MouseEvent e) {
		//挖开了就不管了。
		if(state == STATE_DUG)
			return;
		if(e.getClickCount() == 1){
			int clicked = e.getButton();
			if(clicked == java.awt.event.MouseEvent.BUTTON1){
				//TODO 开挖
				this.setEnabled(false);
				state = STATE_DUG;
				System.out.println("123");
				
			}
			else if(clicked == java.awt.event.MouseEvent.BUTTON2){
				//TODO 挖9格
			}else if(clicked == java.awt.event.MouseEvent.BUTTON3){
				//TODO 标记
			}
		}
		else if(e.getClickCount() == 2){
			//TODO 挖9格
		}
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
	}
	
	
}
