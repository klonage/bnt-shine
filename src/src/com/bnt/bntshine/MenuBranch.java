package com.bnt.bntshine;

import java.util.ArrayList;
import java.util.List;

import com.bnt.bntshine.activities.MainActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ListAdapter;
import android.widget.Toast;

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

	private AlertDialog generateSelectionDialog(String title, List<GlobalItem> items) {

        ListAdapter adapter = new MenuItemAdapter(activity, items);

        return new AlertDialog.Builder(activity).setTitle(title)
            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int item ) {
                    Toast.makeText(activity, "Item Selected: " + item, Toast.LENGTH_SHORT).show();
                }
     }).create();
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
			activity.notImplementedFunctionAlert();
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
		final List<GlobalItem> toShowItems = getFromType(type);
		
		generateSelectionDialog(GlobalItem.getTypeName(type), toShowItems).show();
	}

	private List<GlobalItem> getFromType(int type) {
		List<GlobalItem> allItems = ((MyApplication) activity.getApplication()).getAllItems();
		List<GlobalItem> selected = new ArrayList<GlobalItem>();
		
		for (GlobalItem globalItem : allItems) {
			if ((globalItem.getType() & type) != 0)
				selected.add(globalItem);
		}
		
		return selected;
	}
}
