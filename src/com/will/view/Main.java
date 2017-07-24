package com.will.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.will.model.Cell;
import com.will.model.GameInfo;
import com.will.presenter.GameController;
import com.will.presenter.Player;

public class Main {

	public static void main(String[] args) {
		JFrame mainWindow = new JFrame();
		mainWindow.setSize(300, 300);
		mainWindow.setLayout(new BorderLayout());
		//GameInfo一定要最先初始化
		GameInfo.createGameInfo(GameInfo.LEVEL_LOW);
		GameController gc = GameController.getGC();
		Player p = Player.newPlayer("Mike");
		
		
				
		
		
		
		JPanel cellPanel = gc.gamePreStart(p);
		mainWindow.add(cellPanel,BorderLayout.CENTER);
		mainWindow.add(new JPanel(),BorderLayout.SOUTH);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		mainWindow.setVisible(true);
		
	}

}
