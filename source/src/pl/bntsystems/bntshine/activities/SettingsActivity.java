package pl.bntsystems.bntshine.activities;

import pl.bntsystems.bntshine.R;
import pl.bntsystems.bntshine.activities.settings.HelpActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class SettingsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	public void namesClick(View view) {
		
	}

	public void profilesClick(View view) {
		
	}
	
	public void globalOffClick(View view) {
		
	}
	
	public void networkSettingsClick(View view) {
		
	}

	public void helpClick(View view) {
		Intent intent = new Intent(this, HelpActivity.class);
		startActivity(intent);
	}
}
