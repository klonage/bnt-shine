package pl.bntsystems.bntshine.activities;

import pl.bntsystems.bntshine.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		int status = getIntent().getIntExtra("connectionStatus", 0);
		
		if (status > 0) {
			Toast.makeText(this, "Nie można ustanowić połączenia z serwerem", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void showSettingsActivityClick(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);

		startActivity(intent);
	}
}
