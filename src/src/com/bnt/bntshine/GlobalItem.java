package com.bnt.bntshine;


public class GlobalItem {
	private String name;
	private int group;
	private int address;
	private int type; //i know what enum is, but I think int should be easier to use here.
	private String userName;
	private MainGridAdapter parentAdapter;
	
	private static final int ICONS[];
	private static final String TYPE_NAME[];
	
	static {
		ICONS = new int [9]; TYPE_NAME = new String[9];
		
		ICONS[1] = R.drawable.ic_launcher;
		TYPE_NAME[1] = "Oświetlenie";
		
		ICONS[2] = R.drawable.ic_launcher;
		TYPE_NAME[2] = "Rolety";
		
		TYPE_NAME[3] = "WSZYSTKIE URZĄDZENIA";
		
		ICONS[4] = R.drawable.ic_launcher;
		TYPE_NAME[4] = "Oświetlenie";
		
		ICONS[8] = R.drawable.ic_launcher;
		TYPE_NAME[8] = "Rolety";
	}
	
	public GlobalItem(String name, int group, int address, int type, MainGridAdapter adapter) {
		this.name = name;
		this.group = group;
		this.address = address;
		this.type = type;
		this.parentAdapter = adapter;
		this.userName = "";
	}
	
	public GlobalItem clone() {
		return new GlobalItem(name, group, address, type, parentAdapter);
	}
	
	public String getName() {
		return name;
	}
	
	public String getUserName() {
		return userName;
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

	public void setUserName(String userName) {
		this.userName = userName;		
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public MainGridAdapter getAdapter() {
		return parentAdapter;
	}
	
	public void notifyAdapter() {
		parentAdapter.notifyParentOnChange();
	}
}
