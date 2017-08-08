package com.will.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.will.model.GameInfo;
import com.will.presenter.GameController;
import com.will.presenter.Player;

public class MainWindow extends JFrame {
	private static MainWindow mainWindow; 
	private int gameLevel = GameInfo.LEVEL_LOW;
	private int flagNum = 0;
	private GameController gc;
	private GameInfo info;
	
	private JPanel cellPanel;
	private JMenuBar jmb;
	private JLabel label;
	

	private MainWindow(){
		dataInitialize();
		viewInitialize();
		
		this.setVisible(true);
	}

	
	public  MainWindow(GameInfo info, GameController gc){
		//GameInfo一定要最先初始化
		this.info = info;
		this.gc = gc;
		flagNum = info.getMineNum();
		
		viewInitialize();
		this.setVisible(true);
	}
	
	public static MainWindow createMainWindow(GameInfo info, GameController gc){
		mainWindow = new MainWindow(info, gc);
		return mainWindow;
	}
	
	public static MainWindow getMainWindow(){
		if(mainWindow == null)
			mainWindow = new MainWindow();
		return mainWindow;
	}
	

	private void dataInitialize(){
		
	}

	private void viewInitialize(){
		this.setSize(info.getWindowWidth(), info.getWindowHeight());
		System.out.println("width="+info.getWindowWidth()+" height="+info.getWindowHeight());
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		cellPanel = gc.gamePreStart();
		this.add(cellPanel,BorderLayout.CENTER);

		label = new JLabel("  剩余红旗数："+flagNum);
		this.add(label,BorderLayout.SOUTH);

		
		//设置菜单栏
		jmb = new JMenuBar();
		JMenu optionMenu = getOptionMenu();
		jmb.add(optionMenu);
		
		this.setJMenuBar(jmb);
	}

	
	private JMenu getOptionMenu(){
		JMenu optionMenu = new JMenu("选项");
		//设置选项菜单：重开、初级难度、中级难度、高级难度、退出
		
		JMenuItem restartItem = new JMenuItem("重开");
		restartItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameController.getGC().restart();
			}
		});
		
//		JMenuItem low = new JMenuItem("低级难度");
//		low.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				info.reset(GameInfo.LEVEL_LOW);
//				mainWindow.setSize(info.getWindowWidth(), info.getWindowHeight());
//				GameController.getGC().restart();
//			}
//		});
//		
//		JMenuItem normal = new JMenuItem("中级难度");
//		normal.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				info.reset(GameInfo.LEVEL_NORMAL);
//				mainWindow.setSize(info.getWindowWidth(), info.getWindowHeight());
//				GameController.getGC().restart();
//			}
//		});
//		
//		JMenuItem high = new JMenuItem("高级难度");
//		high.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				info.reset(GameInfo.LEVEL_HIGH);
//				mainWindow.setSize(info.getWindowWidth(), info.getWindowHeight());
//				GameController.getGC().restart();
//			}
//		});
//		
		optionMenu.add(restartItem);
//		optionMenu.addSeparator();
//		optionMenu.add(low);
//		optionMenu.add(normal);
//		optionMenu.add(high);
		
		return optionMenu;
	}
	
	
	
	
	
	
	
	public void addFlagTag() {
		if(flagNum + 1 <= GameInfo.getGameInfo().getMineNum())
			flagNum++;
		label.setText("    剩余红旗数："+flagNum);
	}

	public void subFlagTag() {
		flagNum--;
		label.setText("    剩余红旗数："+flagNum);
	}

	public void resetFlagTag() {
		flagNum = GameInfo.getGameInfo().getMineNum();
		label.setText("    剩余红旗数："+flagNum);
	}
	
}
