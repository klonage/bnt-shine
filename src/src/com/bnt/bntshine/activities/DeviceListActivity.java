package com.bnt.bntshine.activities;

import com.bnt.bntshine.R;
import com.bnt.bntshine.R.layout;
import com.bnt.bntshine.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class DeviceListActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.device_list, menu);
		return true;
	}

}
