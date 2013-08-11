package com.bnt.bntshine;

public class GlobalItem {
	private String name;
	private int group;
	private int type; //i know what enum is, but I think int should be easier to use here.
	private boolean onBoard;
	
	private static final int ICONS[];
	private static final String TYPE_NAME[];
	
	static {
		ICONS = new int [9]; TYPE_NAME = new String[9];
		
		ICONS[1] = R.drawable.ic_launcher;
		TYPE_NAME[1] = "OŚWIETLENIE";
		
		ICONS[2] = R.drawable.ic_launcher;
		TYPE_NAME[2] = "ROLETY";
		
		TYPE_NAME[3] = "WSZYSTKIE URZĄDZENIA";
		
		ICONS[4] = R.drawable.ic_launcher;
		TYPE_NAME[4] = "OŚWIETLENIE";
		
		ICONS[8] = R.drawable.ic_launcher;
		TYPE_NAME[8] = "ROLETY";
	}
	
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
