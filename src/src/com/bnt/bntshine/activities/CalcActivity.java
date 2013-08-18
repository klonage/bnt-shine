package com.bnt.bntshine.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bnt.bntshine.R;

public class CalcActivity extends Activity {
	ToggleButton[] buttons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calc);
		
		buttons = new ToggleButton[]{
				(ToggleButton) findViewById(R.id.tgButton0),
				(ToggleButton) findViewById(R.id.tgButton1),
				(ToggleButton) findViewById(R.id.tgButton2),
				(ToggleButton) findViewById(R.id.tgButton3),
				(ToggleButton) findViewById(R.id.tgButton4),
				(ToggleButton) findViewById(R.id.tgButton5),
				(ToggleButton) findViewById(R.id.tgButton6),
				(ToggleButton) findViewById(R.id.tgButton7),
				(ToggleButton) findViewById(R.id.tgButton8),
				(ToggleButton) findViewById(R.id.tgButton9),
				(ToggleButton) findViewById(R.id.tgButton10),
				(ToggleButton) findViewById(R.id.tgButton11)
				
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.calc, menu);
		return true;
	}
	
	public void upClick(View view) {
		int address = 0;
		for (int i = 0; i < 7; i++) {
			address = (address << 1) | (buttons[i].isChecked() ? 1 : 0);
		}
		
		EditText et = (EditText) findViewById(R.id.addressEditText); 
		et.setText(Integer.toString(address));
		
		int group = 0;
		for (int i = 8; i < 12; i++) {
			group = (group << 1) | (buttons[i].isChecked() ? 1 : 0);
		}
		
		et = (EditText) findViewById(R.id.groupEditText);
		et.setText(Integer.toString(group));
		
		ToggleButton tg = (ToggleButton) findViewById(R.id.globalOffToggleButton);
		tg.setChecked(buttons[7].isChecked());
	}

	public void downClick(View view) {
		try {
			EditText et = (EditText) findViewById(R.id.addressEditText); 
			int address = Integer.parseInt(et.getText().toString());

			for (int i = 6; i >= 0; i--) {
				buttons[i].setChecked((address & 1) != 0);
				address = address >> 1;
			}
			
			et = (EditText) findViewById(R.id.groupEditText); 
			int group = Integer.parseInt(et.getText().toString());

			for (int i = 11; i >= 8; i--) {
				buttons[i].setChecked((group & 1) != 0);
				group = group >> 1;
			}
			
			ToggleButton tg = (ToggleButton) findViewById(R.id.globalOffToggleButton);
			buttons[7].setChecked(tg.isChecked());
		} catch (Exception e) {
			Toast.makeText(this, "Nie udało się wykonać obliczeń. Sprawdź dane wejściowe.", Toast.LENGTH_SHORT).show();
		}
	}
}
