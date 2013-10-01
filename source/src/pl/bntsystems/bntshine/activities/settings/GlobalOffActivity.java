package pl.bntsystems.bntshine.activities.settings;

import pl.bntsystems.bntshine.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class GlobalOffActivity extends Activity {
	private static final int choiceCount = 4;
	private static final int passChoice = 3;
	private RadioGroup authMethodRG;
	private EditText passET;
	private RadioButton[] choices = new RadioButton[choiceCount];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_global_off);

		initPrivateFields();
		loadSettings();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.global_off, menu);
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

		choices[0] = (RadioButton) findViewById(R.id.windowRadio);
		choices[1] = (RadioButton) findViewById(R.id.symbolRadio);
		choices[2] = (RadioButton) findViewById(R.id.longTapRadio);
		choices[3] = (RadioButton) findViewById(R.id.passRadio);

		authMethodRG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) { 
				boolean enabling =  (checkedId == choices[passChoice].getId());
				passET.setEnabled(enabling);
			}
		});
	}

	private void loadSettings() {
		// TODO: implement settings!
	}

	private void saveSettings() {
		// TODO: implement settings!
	}
}
