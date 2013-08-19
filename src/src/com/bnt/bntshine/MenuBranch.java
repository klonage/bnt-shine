package com.bnt.bntshine;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.bnt.bntshine.activities.MainActivity;

public class MenuBranch {
	MainActivity activity;

	public MenuBranch(MainActivity parentActivity) {
		activity = parentActivity;
	}

	public void headList() {
		final CharSequence[] items = { "DODAJ URZĄDZENIE",
				"DODAJ GRUPĘ URZĄDZEŃ", "DODAJ SCENĘ" };

		generateDialog("OPCJE", items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				MenuBranch.this.parseHeadDecision(item);
			}
		}).show();
	}

	private AlertDialog generateDialog(String title, CharSequence[] items,
			DialogInterface.OnClickListener listener) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setTitle(title);
		builder.setItems(items, listener);
		AlertDialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(true);
		return dialog;
	}

	private AlertDialog generateSelectionDialog(String title, List<GlobalItem> items) {
		ListAdapter adapter = new MenuItemAdapter(activity, items);

		AlertDialog dialog = new AlertDialog.Builder(activity).setTitle(title)
				.setAdapter(adapter, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item ) {
						Toast.makeText(activity, "Item Selected: " + item, Toast.LENGTH_SHORT).show();
					}
				}).create();

		dialog.setCanceledOnTouchOutside(true);

		return dialog;
	}

	private void parseHeadDecision(int item) {
		switch (item) {
		case 0:
			MenuBranch.this.addDeviceList();
			break;
		case 1:
			MenuBranch.this.addGroupList();
			break;
		case 2:
			Common.notImplementedFunctionAlert(activity);
		}
	}

	private void addDeviceList() {
		final CharSequence[] items = { "OŚWIETLENIE", "ROLETY",
		"WYŚWIETL LISTĘ WSZYSTKICH URZĄDZEŃ" };

		generateDialog("DODAJ URZĄDZENIE", items,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				int type = 0;
				switch (item) {
				case 0:
					type = 1;
					break;
				case 1:
					type = 2;
					break;
				case 2:
					type = 3;
				}
				MenuBranch.this.manageSpecifiedDevices(type);
			}
		}).show();
	}
	
	private void addGroupList() {
		final CharSequence[] items = { "OŚWIETLENIE", "ROLETY"};

		generateDialog("DODAJ GRUPĘ URZĄDZEŃ", items,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				int type = 0;
				switch (item) {
				case 0:
					type = 4;
					break;
				case 1:
					type = 8;
					break;
				}
				MenuBranch.this.manageSpecifiedDevices(type);
			}
		}).show();
	}

	private void manageSpecifiedDevices(int type) {
		final List<GlobalItem> toShowItems = ((MyApplication)activity.getApplication()).getFromType(type);
		
		generateSelectionDialog(GlobalItem.getTypeName(type), toShowItems).show();
	}
}
