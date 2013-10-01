package pl.bntsystems.bntshine.activities;

import pl.bntsystems.bntshine.BntShineApplication;
import pl.bntsystems.bntshine.R;
import pl.bntsystems.bntshine.TCPClient;
import pl.bntsystems.bntshine.TCPClient.OnConnectionStatusChanged;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class LoaderActivity extends Activity implements OnConnectionStatusChanged {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loader);

		new ConnectTask().execute(-1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.loader, menu);
		return true;
	}

	@Override
	public void connectionStatusChanged(int status) {
		Intent intent = new Intent(LoaderActivity.this, MainActivity.class);
		intent.putExtra("connectionStatus", status);
		LoaderActivity.this.startActivity(intent);
		LoaderActivity.this.finish();		
	}
	
	public class ConnectTask extends AsyncTask<Integer,Integer,TCPClient> {

		@Override
		protected TCPClient doInBackground(Integer... message) {

			TCPClient client = ((BntShineApplication) getApplication()).getTCPClient();
			client.setListener(LoaderActivity.this);
			client.run();

			return null;
		}
	}
}
