package com.bnt.bntshine.activities;

import android.app.Activity;
import android.content.Intent;
import android.location.GpsStatus.NmeaListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.bnt.bntshine.NamesSettingsActivity;
import com.bnt.bntshine.R;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	public void showHelpActivityClick(View view) {
		Intent intent = new Intent(this, HelpActivity.class);
		startActivity(intent);
	}
	
	public void showNetworkSettingsActivity(View view) {
		Intent intent = new Intent(this, NetworkSettingsActivity.class);
		startActivity(intent);
	}
	
	public void showGlobalOffSettingsActivityClick(View view) {
		Intent intent = new Intent(this, GlobalOffSettingsActivity.class);
		startActivity(intent);
	}
	
	public void showProfileSettingsClick(View view) {
		Intent intent = new Intent(this, ProfileActivity.class);
		startActivity(intent);
	}
	
	public void showEditNamesSettingsClick(View view) {
		Intent intent = new Intent(this, NamesSettingsActivity.class);
		startActivity(intent);
	}

}
