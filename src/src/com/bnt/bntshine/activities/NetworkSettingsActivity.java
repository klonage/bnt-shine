package com.bnt.bntshine.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.bnt.bntshine.AppConfiguration;
import com.bnt.bntshine.MyApplication;
import com.bnt.bntshine.R;
import com.bnt.bntshine.Sender;

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

	@Override
	public void onStop() {
		saveSettings(); 

		Sender sender = ((MyApplication) getApplication()).getSender();		
		sender.setAddress( ipAddressET.getText().toString());

		if (!sender.isConnected()) {
			EstabilishingConnectionTask estTask = new EstabilishingConnectionTask();
			try {
				estTask.execute(sender);
				synchronized (estTask) {
					estTask.wait(2000);
				}
				estTask.cancel(true);
				if ( estTask.isConnected() ) {
					return;
				} else {
					Toast.makeText(this, "Przekroczono limit połączenia z serwerem.", Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Toast.makeText(this, "Nie udało się połączyć z serwerem.", Toast.LENGTH_LONG).show();
				return;
			}
		}

		super.onStop();
	}

	public void changeAddressClick(View view) {
		Toast.makeText(this, "Taa, jasne. Napisz ten serwer najpierw, synek.", Toast.LENGTH_SHORT).show();
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
