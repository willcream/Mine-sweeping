package com.will.presenter;

public abstract class User {
	public final String name = "user";
	public abstract void dig(int x, int y);
	public abstract void afterDug(int x, int y);
}
