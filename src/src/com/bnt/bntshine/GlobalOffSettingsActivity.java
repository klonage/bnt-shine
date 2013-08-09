package com.bnt.bntshine;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class GlobalOffSettingsActivity extends Activity {
	private static final int choiceCount = 4;
	private static final int passChoice = 3;
	private RadioGroup authMethodRG;
	private EditText passET;
	private RadioButton[] choices = new RadioButton[choiceCount];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_global_off_settings);
		
		initPrivateFields();
		loadSettings();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.global_off_settings, menu);
		return true;
	}
	
	@Override
	protected void onStop() {
		saveSettings();
		
		super.onStop();
	}

	private void initPrivateFields() {
		authMethodRG = (RadioGroup) findViewById(R.id.authMethodRadioGroup);
		passET = (EditText) findViewById(R.id.passEditText);
		
		choices[0] = (RadioButton) findViewById(R.id.windowRadioButton);
		choices[1] = (RadioButton) findViewById(R.id.symbolRadioButton);
		choices[2] = (RadioButton) findViewById(R.id.tapRadioButton);
		choices[3] = (RadioButton) findViewById(R.id.passRadioButton);
		
		authMethodRG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	        @Override
	        public void onCheckedChanged(RadioGroup group, int checkedId) { 
	        	boolean enabling =  (checkedId == choices[3].getId());
	        	passET.setEnabled(enabling);
	        }
	        });
	}
	
	private void loadSettings() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		
		int selectedMethod = preferences.getInt("settings.global_off.method",  AppConfiguration.getDefaultGlobalOffMethod());
		
		for (int i = 0; i < choiceCount; i++) {
			choices[i].setChecked(i == selectedMethod);
		}
		
		passET.setEnabled(selectedMethod == passChoice);
		passET.setText(preferences.getString("settings.global_off.password", ""));
	}
	
	private void saveSettings() {
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = preferences.edit();
		
		int selectedMethod;
		
		for (selectedMethod = 0; selectedMethod < choiceCount; selectedMethod++) {
			if (choices[selectedMethod].isChecked()) {
				break;
			}
		}
		
		editor.putInt("settings.global_off.method", selectedMethod);

		if (selectedMethod == passChoice) {
			editor.putString("settings.global_off.password", passET.getText().toString());
		}
	
		editor.commit();
	}
}
