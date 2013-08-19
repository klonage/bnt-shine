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
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.bnt.bntshine.GlobalItem;
import com.bnt.bntshine.MyApplication;
import com.bnt.bntshine.R;

public class DeviceListActivity extends Activity {
	private List<GlobalItem> copy;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_list);
		
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.device_list, menu);
		return true;
	}
	
	private void init() {
		int type = getIntent().getIntExtra("type", 3);
		final String curName = (type == 3) ? "urządzenia" : "grupy";
		
		List<GlobalItem> allItems = ((MyApplication) getApplication()).getFromType(type);
		copy = new ArrayList<GlobalItem>(allItems.size());

		for (GlobalItem i : allItems) {
		  copy.add(i.clone());
		}
		ListView listView = (ListView) findViewById(R.id.devicesListView);
		final SimpleItemAdapter adapter = new SimpleItemAdapter(this, copy, false);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position,
					long arg3) {
				AlertDialog.Builder alert = new AlertDialog.Builder(DeviceListActivity.this);

				alert.setTitle("ZMIANA NAZWY");
				alert.setMessage("Wprowadź nową nazwę dla " + curName + " " + copy.get(position).getName());
				final EditText input = new EditText(DeviceListActivity.this);
				alert.setView(input);
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
				  String value = input.getText().toString();
				  
				  if (value.equals("")) {
					  Toast.makeText(DeviceListActivity.this, "Nazwa nie może pozostać pusta.", Toast.LENGTH_LONG).show();
					  return;
				  }
				  
				  for (int i = 0; i < copy.size(); i++) {
					  if (i != position && value.equals(copy.get(i).getName())) {
						  Toast.makeText(DeviceListActivity.this, "Nazwa " + curName + " musi być unikalna. " +
						  		"W systemie istnieje urządzenie o podanej nazwie.", Toast.LENGTH_LONG).show();
						  return;
					  }
				  }
				  
				  copy.get(position).setName(value);
				  }
				});

				alert.setNegativeButton("Cancel", null);

				alert.show().setCanceledOnTouchOutside(true);
				
			}
		});
	}
	
	public void saveToServerClick(View view) {
		Toast.makeText(this, "No dobra, niby zapisałem, ale dobrze wiesz, " +
				"że musisz najpierw zrobić ten serwer...", Toast.LENGTH_LONG).show();
	}
}
