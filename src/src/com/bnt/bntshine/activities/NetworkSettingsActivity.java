package com.bnt.bntshine.activities;

import com.bnt.bntshine.AppConfiguration;
import com.bnt.bntshine.R;
import com.bnt.bntshine.R.id;
import com.bnt.bntshine.R.layout;
import com.bnt.bntshine.R.menu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NetworkSettingsActivity extends Activity {
	private EditText ipAddressET;
	private EditText maskET;
	private RadioGroup setSettingsRG;
	private RadioButton manualSelect;
	private RadioButton automaticSelect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network_settings);
		
		initPrivateFields();
		loadConfig();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.network_settings, menu);
		return true;
	}

	public void changeAddressClick(View view) {
		saveSettings();
	}
	
	private void initPrivateFields() {
		ipAddressET = (EditText) findViewById(R.id.ipAddressEditText);
		maskET = (EditText) findViewById(R.id.maskEditText);
		setSettingsRG = (RadioGroup) findViewById(R.id.serverLocalizationRadioGroup);
		manualSelect = (RadioButton) findViewById(R.id.manualLocalizeServerRadio);
		automaticSelect = (RadioButton) findViewById(R.id.automaticLocalizeServerRadio);
		
		setSettingsRG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) { 
            	boolean enabling =  (checkedId == manualSelect.getId());
            	ipAddressET.setEnabled(enabling);
            	maskET.setEnabled(enabling);
            }
            });
	}
	
	private void saveSettings() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = preferences.edit();
		
		editor.putString("settings.network.ip", ipAddressET.getText().toString());
		editor.putString("settings.network.mask", maskET.getText().toString());
		editor.putBoolean("settings.network.select_mode", manualSelect.isChecked());

		editor.commit();
	}

	private void loadConfig() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			
		ipAddressET.setText(preferences.getString("settings.network.ip", AppConfiguration.getDefaultIP()));
		maskET.setText(preferences.getString("settings.network.mask", AppConfiguration.getDefaultMask()));
		
		boolean selectionMode = preferences.getBoolean("settings.network.select_mode", AppConfiguration.getDefaultMode());
		manualSelect.setChecked(selectionMode);
		automaticSelect.setChecked(!selectionMode);
	}
}
