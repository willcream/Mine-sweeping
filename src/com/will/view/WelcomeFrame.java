package com.will.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.will.model.GameInfo;
import com.will.presenter.GameController;
import com.will.presenter.WelcomePresenter;

public class WelcomeFrame extends JFrame implements WelcomePresenter {
	private JButton bLow;
	private JButton bNormal;
	private JButton bHigh;
	private JButton bExit;
	private GameInfo gameInfo;
	private GameController gc = GameController.getGC();
	private WelcomeFrame frame;//用于Listener里面指示窗口
	
	public WelcomeFrame(GameInfo gi){
		//初始化数据
		gameInfo = gi;
		frame = this;
		
		this.setTitle("扫雷--14级软件2班伍天赐制作");
		this.setSize(360, 130);
		this.setLocation(300, 100);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeView();
		this.setVisible(true);
		
		
	}
	
	public void initializeView(){
		JLabel info = new JLabel("请选择游戏难度",JLabel.CENTER);
		this.add(info,BorderLayout.NORTH);
		
		//button Panel
		JPanel buttonPanel = new JPanel();
		bLow = new JButton("简单");
		bNormal = new JButton("普通");
		bHigh = new JButton("高级");
		bExit = new JButton("退出");
		
		bLow.addActionListener(new LevelOnClickListener(GameInfo.LEVEL_LOW));
		bNormal.addActionListener(new LevelOnClickListener(GameInfo.LEVEL_NORMAL));
		bHigh.addActionListener(new LevelOnClickListener(GameInfo.LEVEL_HIGH));
		bExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		buttonPanel.add(bLow);
		buttonPanel.add(bNormal);
		buttonPanel.add(bHigh);
		buttonPanel.add(bExit);
		
		this.add(buttonPanel, BorderLayout.CENTER);
		
	}
	
	class LevelOnClickListener implements ActionListener{
		private int level;
		
		public LevelOnClickListener(int level) {
			this.level = level;					
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			gameInfo.reset(level);
			MainWindow.createMainWindow(gameInfo, gc, frame);
			setVisible(false);
		}
		
	}

	@Override
	public void showWelcomeFrame() {
		setVisible(true);
	}
	
}

