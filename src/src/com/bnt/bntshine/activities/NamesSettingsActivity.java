package com.bnt.bntshine.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.bnt.bntshine.Common;
import com.bnt.bntshine.R;

public class NamesSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_names_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.names_settings, menu);
		return true;
	}

	public void notImplementedFunctionClick(View view) {
		Common.notImplementedFunctionAlert(this);
	}
	
	public void onEditMainNamesClick(View view) {
		Intent intent = new Intent(this, DeviceListActivity.class);
		intent.putExtra("type", 3);
		startActivity(intent);
	}
	
	public void onEditGroupsClick(View view) {
		Intent intent = new Intent(this, DeviceListActivity.class);
		intent.putExtra("type", 12);
		startActivity(intent);
	}
}
