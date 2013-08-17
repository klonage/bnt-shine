package com.bnt.bntshine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;

public class MyApplication extends Application {
	private ArrayList<GlobalItem> allItems;
	private Map<Integer, String> groupNames;
	private ProfileManager profileManager;
	private List<Page> pages;
	
	@Override
	public void onCreate() {
		allItems = new ArrayList<GlobalItem>();
		groupNames = new HashMap<Integer, String>();
		profileManager = new ProfileManager();
		super.onCreate();
	}
	
	public ProfileManager getProfileManager() {
		return profileManager;
	}

	public Map<Integer, String> getGroupNames() {
		return groupNames;
	}
	
	public ArrayList<GlobalItem> getAllItems() {
		return allItems;
	}
	
	public void setPages(List<Page> pages) {
		this.pages = pages;
	}
	
	public List<Page> getPages() {
		return pages;
	}
	
	public List<GlobalItem> getFromType(int type) {
		if (type >= 4)
			return getGroups(type);
		else
			return getSingleDevices(type);
		
	}

	private List<GlobalItem> getGroups(int type) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		List<GlobalItem> selected = new ArrayList<GlobalItem>();
		
		for (GlobalItem globalItem : allItems) {
			if (!map.containsKey(globalItem.getGroup()) && globalItem.getType() * 4 == type) {
				map.put(globalItem.getGroup(), globalItem.getType() * 4);
				selected.add(new GlobalItem(groupNames.get(globalItem.getGroup()), globalItem.getGroup(), -1,
						globalItem.getType() * 4, globalItem.getAdapter()));
			}
		}
		
		return selected;
	}

	private List<GlobalItem> getSingleDevices(int type) {
		List<GlobalItem> selected = new ArrayList<GlobalItem>();
		
		for (GlobalItem globalItem : allItems) {
			if ((globalItem.getType() & type) != 0)
				selected.add(globalItem);
		}
		
		return selected;
	}
}
