package com.will.presenter;

public interface MainWindowPresenter extends MyBasePresenter{

	/**
	 * 红旗数增加
	 */
	void addFlagTag();
	
	
	/**
	 * 红旗数减少
	 */
	void subFlagTag();
	
	
	/**
	 * 重置红旗数 
	 */
	void resetFlagTag();
}
