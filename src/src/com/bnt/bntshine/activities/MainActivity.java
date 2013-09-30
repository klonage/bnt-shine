package com.bnt.bntshine.activities;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
		items.add(new GlobalItem("Urządzenie 1", 0, 1, 1, mainGridAdapter));
		items.add(new GlobalItem("Urządzenie 2", 0, 2, 1, mainGridAdapter));
		items.add(new GlobalItem("Urządzenie 3", 0, 3, 1, mainGridAdapter));
		items.add(new GlobalItem("Urządzenie 4", 0, 4, 1, mainGridAdapter));
		items.add(new GlobalItem("Urządzenie 5", 0, 5, 1, mainGridAdapter));
		items.add(new GlobalItem("Urządzenie 6", 1, 6, 1, mainGridAdapter));
		items.add(new GlobalItem("Roleta w kuchni", 2, 10, 2, mainGridAdapter));
		items.add(new GlobalItem("Testowa scena", -1, -1, 16, mainGridAdapter));
		groups.put(0, "Grupa 0");
		groups.put(1, "Grupa 1");
		sender = ((MyApplication) getApplication()).getSender();
		init();		

		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		sender.setAddress(preferences.getString("settings.network.ip", AppConfiguration.getDefaultIP()));
		if (!sender.isConnected()) {
			EstabilishingConnectionTask estTask = new EstabilishingConnectionTask();
			try {
				estTask.execute(sender);
				synchronized (estTask) {
					estTask.wait(2000);
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
		globalOff = new GlobalOffAction(getApplicationContext(), this, sender);
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

	private AlertDialog generateRoletaDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Ustawienia rolety");
		CharSequence [] seq = {"Otwórz roletę", "Skok w górę", "Skok w dół", "Zamknij roletę"};
		builder.setItems(seq, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				boolean ok = true;
				switch (which) {
				case 0:
					ok = sender.sendRoletaCommand(9);
					break;
				case 1:
					ok = sender.sendRoletaCommand(12);
					break;
				case 2:
					ok = sender.sendRoletaCommand(13);
					break;
				case 3:
					ok = sender.sendRoletaCommand(10);
					break;
				}
				
				if (!ok) {
					Toast.makeText(MainActivity.this, "Nie można wysłać polecenia do serwera.", Toast.LENGTH_SHORT).show();
				}
			}
		});
		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}
	
	@Override
	public void onClick(View v) {
		int it = gridview.getClickedItem();

		GlobalItem currItem = mainGridAdapter.getItem(0, it);

		if (currItem == null)
			return;

		if (currItem.getType() == 2) { // roleta, hacky guy!
			generateRoletaDialog().show();
		} else if (currItem.getType() == 16) {
			boolean isOk = true;
			isOk = isOk && sender.sendRoletaCommand(10);
			isOk = isOk && sender.sendCustomCommand(0, 1, 10, 2);
			isOk = isOk && sender.sendCustomCommand(0, 2, 10, 2);
			if (!isOk) {
				Toast.makeText(this, "Nie można wysłać polecenia do serwera.", Toast.LENGTH_SHORT).show();
			}
		} else {
			if (!sender.sendToggleCommand(currItem.getGroup(), currItem.getAddress(), currItem.getType())) {
				Toast.makeText(this, "Nie można wysłać polecenia do serwera.", Toast.LENGTH_SHORT).show();
			}
		}
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

		connected = client[0].connect(7);
		return connected;
	}

	public boolean isConnected() {
		return connected;
	}
}
