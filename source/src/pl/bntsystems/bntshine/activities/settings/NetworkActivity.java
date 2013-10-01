package pl.bntsystems.bntshine.activities.settings;

import pl.bntsystems.bntshine.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NetworkActivity extends Activity {
	private EditText ipAddressET;
	private EditText maskET;
	private RadioGroup setSettingsRG;
	private RadioButton manualSelect;
	private RadioButton automaticSelect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network);
		
		initPrivateFields();
		loadConfig();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.network, menu);
		return true;
	}

	@Override
	public void onStop() {
		saveSettings(); 

		// TODO reload connection! (see v1.0)

		super.onStop();
	}
	
	public void changeAddressClick(View view) {
		// TODO implement me!
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
		// TODO implement settings!
	}

	private void loadConfig() {
		// TODO implement settings
	}
}
