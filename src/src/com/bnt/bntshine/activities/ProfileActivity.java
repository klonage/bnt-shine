package com.bnt.bntshine.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.bnt.bntshine.Common;
import com.bnt.bntshine.MyApplication;
import com.bnt.bntshine.R;

public class ProfileActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

	public void onRemoveProfileClick(View view) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("WYBÓR LOKALIZACJI PROFILU");
		String [] items = new String[]{"PROFIL LOKALNY", "PROFIL ZDALNY"};
		builder.setItems(items, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
				case 0:
					removeLocalProfileClick();
					break;
				case 1:
					removeRemoteProfileClick();
					break;
				}
				
			}
		});

		builder.create().show();
	}
	
	private void removeLocalProfileClick() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Czy na pewno chcesz usunąć profil lokalny?").setPositiveButton("TAK", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		        	((MyApplication) getApplication()).getProfileManager().removeLocalProfile();
		        	Toast.makeText(ProfileActivity.this, "Pomyślnie usunięto profil lokalny", Toast.LENGTH_SHORT).show();
		            break;
		        }
		    }
		}).setNegativeButton("NIE", null).show();
	}
	
	private void removeRemoteProfileClick() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Czy na pewno chcesz usunąć profil zdalny?").setPositiveButton("TAK", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		        	Toast.makeText(ProfileActivity.this, "Usuwam profil zdalny...", Toast.LENGTH_SHORT).show();
		            break;
		        }
		    }
		}).setNegativeButton("NIE", null).show();
	}
	
	public void customNamesClick(View view) {
		Intent intent = new Intent(this, CustomNamesActivity.class);
		startActivity(intent);
	}
	
	public void exportProfileClick(View view) {
		final CharSequence[] items = {"Filtr faz", "System plików"};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Wybierz lokalizację zapisu profilu");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		    	if (item == 0) { 
		        	Common.notImplementedFunctionAlert(ProfileActivity.this);
		        	return;
		        } 
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void importProfileClick(View view) {
		final CharSequence[] items = {"Filtr faz", "System plików"};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Wybierz lokalizację importu profilu");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        if (item == 0) { 
		        	Common.notImplementedFunctionAlert(ProfileActivity.this);
		        	return;
		        }
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();	
	}
}
