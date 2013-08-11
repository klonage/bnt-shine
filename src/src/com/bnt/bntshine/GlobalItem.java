package com.bnt.bntshine;

import android.widget.Adapter;

public class GlobalItem {
	private String name;
	private int group;
	private int address;
	private int type; //i know what enum is, but I think int should be easier to use here.
//	private boolean onBoard;
	private MainGridAdapter parentAdapter;
	
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
	
	public GlobalItem(String name, int group, int address, int type, MainGridAdapter adapter) {
		this.name = name;
		this.group = group;
		this.address = address;
		this.type = type;
		this.parentAdapter = adapter;
	}
	
	public String getName() {
		return name;
	}
	
	public int getGroup() {
		return group;
	}

	public int getAddress() {
		return address;
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
		return parentAdapter.hasItem(this);
	}
	
	public void setOnBoard(boolean value) {
		parentAdapter.addToCurrentPage(this);
	}
}
