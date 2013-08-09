package com.bnt.bntshine;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NetworkSettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.network_settings, menu);
		return true;
	}

}
