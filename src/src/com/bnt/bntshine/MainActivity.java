package com.bnt.bntshine;

import java.util.List;

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
import android.widget.Toast;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGrid;

public class MainActivity extends Activity implements OnClickListener {
	private GlobalOffAction globalOff;
	private PagedDragDropGrid gridview;
	private MenuBranch menuBranch;
	private MainGridAdapter mainGridAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		// TODO: sample data
		List<GlobalItem> items = ((MyApplication) getApplication()).getAllItems();
		items.clear();
		items.add(new GlobalItem("Lewa lampa", 12, 1));
		items.add(new GlobalItem("Roleta w kuchni", 42, 2));
		items.add(new GlobalItem("Prawa lampa", 13, 1));
		items.add(new GlobalItem("Roleta w łazience", 73, 2));
		items.add(new GlobalItem("Grupa lamp", 2, 4));
		items.add(new GlobalItem("Grupa rolet", 3, 8));
		
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void notImplementedFunctionClick(View view) {
		notImplementedFunctionAlert();
	}

	public void notImplementedFunctionAlert() {
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
		menuBranch = new MenuBranch(this);
		
		gridview = (PagedDragDropGrid) findViewById(R.id.gridview);		
		mainGridAdapter = new MainGridAdapter(this, gridview);
		gridview.setAdapter(mainGridAdapter);
		gridview.setClickListener(this);

		setLongClickToGridView();
	}
	
	private void setLongClickToGridView() {
		gridview.SetLongClickMethod(new CallerInterface() {
			
			@Override
			public void DoStuff() {
				menuBranch.headList();
			}
		});
	}
	
	@Override
    public void onClick(View v) {
        Toast.makeText(this, "Clicked View", Toast.LENGTH_SHORT).show();
    }
	
	public void addToCanvas(GlobalItem item) {
		mainGridAdapter.addToCurrentPage(item);
		gridview.notifyDataSetChanged();
		
		setLongClickToGridView(); // TODO: why I need to do it everytime when I change a model??
	}
	
	public void removeFromCanvas(GlobalItem item) {
		mainGridAdapter.deleteItem(0, item);
		gridview.notifyDataSetChanged();
		
		setLongClickToGridView();
	}
}
