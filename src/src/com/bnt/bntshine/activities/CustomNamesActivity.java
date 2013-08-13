package com.bnt.bntshine.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bnt.bntshine.GlobalItem;
import com.bnt.bntshine.MyApplication;
import com.bnt.bntshine.Page;
import com.bnt.bntshine.R;

public class CustomNamesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_names);

		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.custom_names, menu);
		return true;
	}

	private void init() {
		List<Page> pages = ((MyApplication) getApplication()).getPages();
		final List<GlobalItem> items = new ArrayList<GlobalItem>();

		for (Page p : pages) {
			for (int i = 0; i < p.itemsCount(); i++) {
				items.add(p.getItem(i));
			}
		}

		ListView listView = (ListView) findViewById(R.id.iconsListView);
		final SimpleItemAdapter adapter = new SimpleItemAdapter(this, items, true);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position,
					long arg3) {
				AlertDialog.Builder alert = new AlertDialog.Builder(CustomNamesActivity.this);

				alert.setTitle("ZMIANA NAZWY");
				alert.setMessage("Wprowadź nazwę użytkownika dla " + items.get(position).getUserName() + " (" + items.get(position).getName() + ")");
				final EditText input = new EditText(CustomNamesActivity.this);
				alert.setView(input);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						String value = input.getText().toString();

						if (!value.equals("")) {
							for (int i = 0; i < items.size(); i++) {
								if (i != position && value.equals(items.get(i).getUserName())) {
									Toast.makeText(CustomNamesActivity.this, "Nazwa elementu musi być unikalna. " +
											"Na pulpicie istnieje urządzenie o podanej nazwie.", Toast.LENGTH_LONG).show();
									return;
								}
							}}
						items.get(position).setUserName(value);
						adapter.notifyDataSetChanged();
						items.get(position).notifyAdapter();
						((MyApplication) CustomNamesActivity.this.getApplication()).getProfileManager().saveToConfigFile();
					}
				});

				alert.setNegativeButton("Cancel", null);

				alert.show();

			}
		});
	}

}
