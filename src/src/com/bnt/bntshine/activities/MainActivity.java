package com.bnt.bntshine.activities;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGrid;

import com.bnt.bntshine.AppConfiguration;
import com.bnt.bntshine.CallerInterface;
import com.bnt.bntshine.Common;
import com.bnt.bntshine.GlobalItem;
import com.bnt.bntshine.GlobalOffAction;
import com.bnt.bntshine.MainGridAdapter;
import com.bnt.bntshine.MenuBranch;
import com.bnt.bntshine.MyApplication;
import com.bnt.bntshine.ProfileManager;
import com.bnt.bntshine.R;
import com.bnt.bntshine.Sender;

public class MainActivity extends Activity implements OnClickListener {
	private GlobalOffAction globalOff;
	private PagedDragDropGrid gridview;
	private MenuBranch menuBranch;
	private MainGridAdapter mainGridAdapter;
	private ProfileManager profileManager;
	private Sender sender;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createMainGrid();

		// TODO: sample data
		List<GlobalItem> items = ((MyApplication) getApplication()).getAllItems();
		Map<Integer, String> groups = ((MyApplication) getApplication()).getGroupNames();
		items.clear();
		items.add(new GlobalItem("Lewa lampa z dluga nazwa", 12, 1, 1, mainGridAdapter));
		items.add(new GlobalItem("Roleta w kuchni", 1, 2, 2, mainGridAdapter));
		items.add(new GlobalItem("Prawa lampa z jeszcze dłuższą nazwą", 12, 5, 1, mainGridAdapter));
		items.add(new GlobalItem("Środkowa lampa", 5, 3, 1, mainGridAdapter));
		items.add(new GlobalItem("Roleta w łazience", 1, 4, 2, mainGridAdapter));
		groups.put(12, "Lampy");
		groups.put(5, "Inne lampy");
		groups.put(1, "Rolety");
		init();
		sender = new Sender();


		EstabilishingConnectionTask estTask = new EstabilishingConnectionTask();
		try {
			estTask.execute(sender);
			synchronized (estTask) {
				estTask.wait(1000);
			}
			estTask.cancel(true);
			if ( estTask.isConnected() ) {
				return;
			} else {
				Toast.makeText(this, "Przekroczono limit połączenia z serwerem.", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(this, "Nie udało się połączyć z serwerem.", Toast.LENGTH_LONG).show();
			return;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		setLongClickToGridView();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void notImplementedFunctionClick(View view) {
		Common.notImplementedFunctionAlert(this);
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
		profileManager = ((MyApplication) getApplication()).getProfileManager();
		profileManager.setActivity(this);
		profileManager.setAdapter(mainGridAdapter);
		profileManager.loadFromConfigFile();
		mainGridAdapter.setProfileManager(profileManager);
		gridview.setAdapter(mainGridAdapter);
		gridview.setClickListener(this);

		setLongClickToGridView();
	}

	private void createMainGrid() {
		gridview = (PagedDragDropGrid) findViewById(R.id.gridview);		
		mainGridAdapter = new MainGridAdapter(this, gridview, AppConfiguration.getPagesCount());
		((MyApplication) getApplication()).setPages(mainGridAdapter.getPages());
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
		int it = gridview.getClickedItem();

		GlobalItem currItem = mainGridAdapter.getItem(0, it);

		if (currItem == null)
			return;

		sender.sendCommand(currItem.getGroup(), currItem.getAddress(), currItem.getType());
	}

	public void addToCurrentPage(GlobalItem item) {
		if (!mainGridAdapter.hasItem(item)) {
			mainGridAdapter.addToPage(gridview.currentPage(), item);
			gridview.notifyDataSetChanged();

			setLongClickToGridView(); // TODO: why I need to do it everytime when I change a model??
		}
	}

	public void removeFromCanvas(GlobalItem item) {
		mainGridAdapter.deleteItem(item);
		gridview.notifyDataSetChanged();
		setLongClickToGridView();
	}

	public void refreshMainView() {
		gridview.notifyDataSetChanged();
	}
}


class EstabilishingConnectionTask extends
AsyncTask<Sender, Integer, Boolean> {
	private boolean connected = false;

	@Override
	protected void onPostExecute(Boolean result) {
		connected = result;
	}

	@Override
	protected Boolean doInBackground(Sender... client) {
		if (client.length < 1) {
			connected = false;
			return false;
		}

		connected = client[0].connect(4444);
		return connected;
	}

	public boolean isConnected() {
		return connected;
	}
}
