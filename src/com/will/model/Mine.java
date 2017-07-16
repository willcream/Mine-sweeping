package com.will.model;

import com.will.presenter.User;
import com.will.receiver.DigReceiver;

public class Mine extends Cell implements DigReceiver{
	public Mine(){
		this.dug = false;
		this.val = -1;
	}

	@Override
	public int dug(User user) {
		if(this.dug){
			return Cell.DUG;
		}else{
			//TODO 显示出来?
			dug = true;
			return this.val;
		}
	}
	
}
