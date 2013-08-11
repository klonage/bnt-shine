package com.bnt.bntshine;

public class GlobalItem {
	private String name;
	private int group;
	private int type; //i know what enum is, but I think int should be easier to use here.
	private boolean onBoard;
	
	private static final int ICONS[] = {0, R.drawable.ic_launcher, R.drawable.ic_launcher};
	private static final String TYPE_NAME[] = {"", "OŚWIETLENIE", "ROLETY", "WSZYSTKIE URZĄDZENIA"};
	
	public GlobalItem(String name, int group, int type) {
		this.name = name;
		this.group = group;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public int getGroup() {
		return group;
	}
	
	public int getType() {
		return type;
	}
	
	public int getIcon() {
		return ICONS[type];
	}

	public static String getTypeName(int type) {
		return TYPE_NAME[type];
	}

	public boolean getOnBoard() {
		return onBoard;
	}
	
	public void setOnBoard(boolean value) {
		onBoard = value;
	}
}
