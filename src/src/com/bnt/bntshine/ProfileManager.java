package com.bnt.bntshine;

import java.util.List;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ProfileManager {
	MainGridAdapter adapter;
	MainActivity activity;
	
	public ProfileManager(MainGridAdapter adapter, MainActivity activity) 
	{
		this.adapter = adapter;
		this.activity = activity;
	}
	
	public void saveToConfigFile()
	{
		for (int i = 0; i < adapter.pageCount(); i++) {
			savePageToConfigFile(i);
		}
	}
	
	private void savePageToConfigFile(int page) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putInt("profile.page_items_count_" + page, adapter.getPage(page).itemsCount());
		
		for (int i = 0; i < adapter.getPage(page).itemsCount(); i++) {
			GlobalItem item = adapter.getPage(page).getItem(i); // TODO Write function for generating this names!
			editor.putInt("profile.page_" + page + "_" + "_" + i + ".address", item.getAddress());
			editor.putInt("profile.page_" + page + "_" + "_" + i + ".group", item.getGroup());
			editor.putString("profile.page_" + page + "_" + "_" + i + ".user_name", item.getUserName());
		}

		editor.commit();
	}
	
	public void loadFromConfigFile() {
		for (int i = 0; i < AppConfiguration.getPagesCount(); i++) {
			loadPageFromConfigFile(i);
		}
	}
	
	private void loadPageFromConfigFile(int page) {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
		int cnt = preferences.getInt("profile.page_items_count_" + page, 0);
		
		for (int i = 0; i < cnt; i++) {
			int address = preferences.getInt("profile.page_" + page  + "_" + "_" + i + ".address", -1);
			int group = preferences.getInt("profile.page_" + page  + "_" + "_" + i + ".group", -1);
			String userName = preferences.getString("profile.page_" + page + "_" + "_" + i +  ".user_name", "");
			
			GlobalItem item = getGlobalItem(address, group);
			
			if (item != null) {
				item.setUserName(userName);
				adapter.getPage(page).addItem(item);
			}
		}
	}
	
	private GlobalItem getGlobalItem(int address, int group) {
		List<GlobalItem> items = ((MyApplication) activity.getApplication()).getAllItems();
		
		for (GlobalItem item : items) {
			if (item.getGroup() == group && item.getAddress() == address) {
				return item;
			}
		}
		
		return null;
	}
	
	public void ImportProfile(String fileName) {
		
	}
	
	public void ExportProfile(String fileName) {
		
	}
	
	public void RemoveProfile() {
		
	}
}
