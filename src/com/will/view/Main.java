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
	
	
	public static void main(String[] args) {
		GameInfo info = GameInfo.createGameInfo(GameInfo.LEVEL_LOW);
		
		new WelcomeFrame(info);
		
//		MainWindow.createMainWindow(info, gc);
	}

}
