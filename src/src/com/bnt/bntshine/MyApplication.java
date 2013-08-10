package com.bnt.bntshine;

import java.util.ArrayList;

import android.app.Application;

public class MyApplication extends Application {
	private ArrayList<GlobalItem> allItems;

	@Override
	public void onCreate() {
		allItems = new ArrayList<GlobalItem>();
		super.onCreate();
	}

	public ArrayList<GlobalItem> getAllItems() {
		return allItems;
	}
}
