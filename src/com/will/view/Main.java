package com.will.view;

import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.will.model.Cell;

public class Main {

	public static void main(String[] args) {
		JFrame mainWindow = new JFrame();
		mainWindow.setSize(240, 260);
		GridLayout gl = new GridLayout(10, 10);
		mainWindow.setLayout(gl);
		for(int i = 0; i < 10; i++)
			for(int j = 0; j < 10; j++){
				CellView jb1 = new CellView(new Cell());
				jb1.setSize(22, 22);
				jb1.change2flag();
				mainWindow.add(jb1);
			}
		
//		JButton jb2 = new JButton(new ImageIcon("question-mark.png"));
//		jb2.setSize(16, 16);
//		
//		mainWindow.add(jb2);
		
		
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainWindow.setVisible(true);
		
	}

}
