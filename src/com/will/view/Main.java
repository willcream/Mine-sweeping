package com.will.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.will.model.GameInfo;
import com.will.presenter.GameController;
import com.will.presenter.Player;

public class Main {
	private static JFrame mainWindow; 
	private static JLabel label;
	private static int flagNum = 0;
	
	public static void main(String[] args) {
		mainWindow = new JFrame();
		mainWindow.setSize(300, 300);
		mainWindow.setLayout(new BorderLayout());
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//GameInfo一定要最先初始化
		GameInfo.createGameInfo(GameInfo.LEVEL_LOW);
		GameController gc = GameController.getGC();
		Player p = Player.newPlayer("Mike");
		flagNum = GameInfo.getGameInfo().getMineNum();

		JPanel cellPanel = gc.gamePreStart(p);
		mainWindow.add(cellPanel,BorderLayout.CENTER);
		
		
		label = new JLabel("    剩余红旗数："+flagNum);
		mainWindow.add(label,BorderLayout.SOUTH);

		mainWindow.setVisible(true);

	}
	
	public static void addFlagTag() {
		if(flagNum + 1 <= GameInfo.getGameInfo().getMineNum())
			flagNum++;
		label.setText("    剩余红旗数："+flagNum);
	}
	
	public static void subFlagTag() {
		flagNum--;
		label.setText("    剩余红旗数："+flagNum);
	}
	
	public static void resetFlagTag() {
		flagNum = GameInfo.getGameInfo().getMineNum();
		label.setText("    剩余红旗数："+flagNum);
	}
}
