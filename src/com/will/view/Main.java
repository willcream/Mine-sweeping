package com.will.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.will.model.Cell;
import com.will.presenter.GameController;
import com.will.presenter.Player;

public class Main {

	public static void main(String[] args) {
		JFrame mainWindow = new JFrame();
		mainWindow.setSize(300, 300);
		mainWindow.setLayout(new BorderLayout());
		GameController gc = GameController.getGC();
		
		Player p = new Player();
		JPanel cellPanel = gc.gameStart(240, 240, p);
		mainWindow.add(cellPanel,BorderLayout.CENTER);
		mainWindow.add(new JPanel(),BorderLayout.SOUTH);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainWindow.setVisible(true);
		
	}

}
