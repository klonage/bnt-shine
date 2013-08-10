package com.bnt.bntshine;

import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGrid;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private GlobalOffAction globalOff;
	private PagedDragDropGrid gridview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void notImplementedFunctionClick(View view) {
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(this).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create();
		alertDialog.setTitle("Funkcja niedostępna");
		alertDialog.setMessage("Obecnie funkcjonalność nie jest dostępna.");
		alertDialog.show();
	}

	public void showSettingsActivityClick(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}
	
	public void doGlobalOffClick(View view) {
		globalOff.doGlobalOff();
	}
	
	public void setGlobalOffTouchListener(View.OnTouchListener listener) {
		Button globalOffBtn = (Button) findViewById(R.id.globalOffButton);
		globalOffBtn.setOnTouchListener(listener);
	}
	
	private void init() {
		globalOff = new GlobalOffAction(getApplicationContext(), this);
		
		gridview = (PagedDragDropGrid) findViewById(R.id.gridview);		
		gridview.setAdapter(new MainGridAdapter(this, gridview));
		gridview.setClickListener(this);
		
		gridview.setBackgroundColor(Color.LTGRAY);
		gridview.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Toast.makeText(MainActivity.this, "super", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		
		gridview.SetLongClickMethod(new CallerInterface() {
			
			@Override
			public void DoStuff() {
				showAddMenu();
			}
		});
	}
	
	@Override
    public void onClick(View v) {
        Toast.makeText(this, "Clicked View", Toast.LENGTH_SHORT).show();
    }
	
	private void showAddMenu() {
		final CharSequence[] items = {"DODAJ URZĄDZENIE", "DODAJ GRUPĘ URZĄDZEŃ", "DODAJ SCENĘ"};

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("OPCJE");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		         
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}
}
