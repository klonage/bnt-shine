package pl.bntsystems.bntshine.activities.settings;

import pl.bntsystems.bntshine.BntShineApplication;
import pl.bntsystems.bntshine.R;
import pl.bntsystems.bntshine.TCPClient;
import pl.bntsystems.bntshine.TCPClient.OnConnectionStatusChanged;
import pl.bntsystems.bntshine.activities.SettingsActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class NetworkActivity extends Activity implements OnConnectionStatusChanged {
	private EditText ipAddressET;
	private EditText maskET;
	private RadioGroup setSettingsRG;
	private RadioButton manualSelect;
	private RadioButton automaticSelect;
	private TCPClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_network);
		
		client = ((BntShineApplication) getApplication()).getTCPClient();
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

		client.setListener(null);
		client.stopClient();
		client.setAddress( ipAddressET.getText().toString());
		new ConnectTask().execute(-1);

		super.onStop();
	}
	
	public class ConnectTask extends AsyncTask<Integer,Integer,TCPClient> {

		@Override
		protected TCPClient doInBackground(Integer... message) {

			client.run();

			return null;
		}
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

	@Override
	public void connectionStatusChanged(int status) {

		if (status > 0) {
			Toast.makeText(this, "Nie można ustanowić połączenia z serwerem", Toast.LENGTH_LONG).show();
		}		
	}
}
