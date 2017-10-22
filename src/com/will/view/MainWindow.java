package com.will.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.will.model.GameInfo;
import com.will.model.GlobalMsg;
import com.will.presenter.GameController;
import com.will.presenter.MainWindowPresenter;
import com.will.presenter.Player;
import com.will.presenter.WelcomePresenter;

public class MainWindow extends JFrame implements MainWindowPresenter{
	private int gameLevel = GameInfo.LEVEL_LOW;
	private int flagNum = 0;
	private GameController gc;
	private GameInfo info;
	private WelcomePresenter wp;
	
	private JPanel cellPanel;
	private JMenuBar jmb;
	private JLabel label;
	

	private MainWindow(){
		dataInitialize();
		viewInitialize();
		
		this.setVisible(true);
	}

	
	public  MainWindow(GameInfo info, GameController gc, WelcomePresenter wp){
		//GameInfo一定要最先初始化
		this.info = info;
		this.gc = gc;
		this.wp = wp;
		flagNum = info.getMineNum();
		
		viewInitialize();
		this.setVisible(true);
		//在GC中加入这个Presenter，方便后面调用
		gc.putPresenter(GlobalMsg.S_MAIN_WINDOW_P, this);
		
	}
	
	
	private void dataInitialize(){
		
	}

	private void viewInitialize(){
		//设置大小、位置、布局、关闭方式
		this.setSize(info.getWindowWidth(), info.getWindowHeight());
		this.setLocation(300, 100);
		this.setLayout(new BorderLayout());
		this.addWindowListener(new MyWindowAdapter());
		

		//初始化窗口内的组件
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
		//设置选项菜单：重开、退出
		
		JMenuItem restartItem = new JMenuItem("重开");
		restartItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GameController.getGC().restart();
			}
		});
		
		JMenuItem exitItem = new JMenuItem("选择难度");
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				int testCvlistSize = gc.releaseMemory();
				System.out.println("after dispose, cvlist size="+testCvlistSize);
				wp.showWelcomeFrame();
			}
		});
		
		optionMenu.add(restartItem);
		optionMenu.add(exitItem);
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
	
	class MyWindowAdapter extends WindowAdapter{

		@Override
		public void windowClosing(WindowEvent e) {
			super.windowClosing(e);
			wp.showWelcomeFrame();
		}
		
		
	}
}
