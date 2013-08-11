package com.bnt.bntshine;

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
	
	public void SaveToConfigFile()
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
			GlobalItem item = adapter.getPage(page).getItem(i);
			editor.putInt("profile.page_" + page + ".address", item.getAddress());
			editor.putInt("profile.page_" + page + ".group", item.getGroup());
			editor.putString("profile.page_" + page + ".user_name", item.getUserName());
		}

		editor.commit();
	}
	
	public void LoadFromConfigFile() {
		
	}
	
	public void ImportProfile(String fileName) {
		
	}
	
	public void ExportProfile(String fileName) {
		
	}
	
	public void RemoveProfile() {
		
	}
}
