package com.bnt.bntshine;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}
	
	public void openWWWPageClick(View view) {
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfiguration.getWWW()));
		startActivity(browserIntent);
	}

	public void sendMailClick(View view) {
		Intent emailIntent = new Intent(Intent.ACTION_SEND);
		emailIntent.setType("plain/text");
		emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{AppConfiguration.getMailAddress()});
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, AppConfiguration.getMailSubject());
		emailIntent.putExtra(Intent.EXTRA_TEXT, "");
		startActivity(Intent.createChooser(emailIntent, ""));
	}
	
	public void callToSupportClick(View view) {
		try {
		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:"+AppConfiguration.getPhoneNumber()));
		callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		startActivity(callIntent);
		} catch (Exception e) {
			Toast.makeText(this, "Nie można wykonać połączenia. " +
					"Spróbuj ręcznie wybrać numer: " + AppConfiguration.getPhoneNumber(),
					Toast.LENGTH_LONG).show();
		}
	}
}
