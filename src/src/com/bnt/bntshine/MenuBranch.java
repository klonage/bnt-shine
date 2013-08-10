package com.bnt.bntshine;

import android.app.AlertDialog;
import android.content.DialogInterface;

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

		return builder.create();
	}

	private void parseHeadDecision(int item) {
		switch (item) {
		case 0:
			MenuBranch.this.addDeviceList();
			break;
		case 1:
			MenuBranch.this.addDeviceGroupList();
			break;
		case 2:
			activity.notImplementedFunctionAlert();
		}
	}

	private void addDeviceList() {
		final CharSequence[] items = { "OŚWIETLENIE", "ROLETY",
		"WYŚWIETL LISTĘ WSZYSTKICH URZĄDZEŃ" };

		generateDialog("DODAJ URZĄDZENIE", items,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				MenuBranch.this.addDeviceList();
			}
		}).show();
	}

	private void addDeviceGroupList() {
		final CharSequence[] items = { "OŚWIETLENIE", "ROLETY" };

		generateDialog("DODAJ URZĄDZENIE", items,
				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {
				MenuBranch.this.addDeviceList();
			}
		}).show();
	}
}
