package com.bnt.bntshine.activities;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.bnt.bntshine.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FileBrowserActivity extends ListActivity {

	private List<String> item = null;
	private List<String> path = null;
	private String root;
	private TextView myPath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_browser);
		myPath = (TextView)findViewById(R.id.path);

		root = Environment.getExternalStorageDirectory().getPath();

		boolean shouldLock = getIntent().getExtras().getBoolean("lock");
		
		((EditText) findViewById(R.id.editText1)).setEnabled(shouldLock);
		
		getDir(root);
	}

	private void getDir(String dirPath)
	{
		myPath.setText("Folder: " + dirPath);
		item = new ArrayList<String>();
		path = new ArrayList<String>();
		File f = new File(dirPath);
		File[] files = f.listFiles();

		if(!dirPath.equals(root))
		{
			item.add(root);
			path.add(root);
			item.add("../");
			path.add(f.getParent()); 
		}

		for(int i=0; i < files.length; i++)
		{
			File file = files[i];

			if(!file.isHidden() && file.canRead()){
				path.add(file.getPath());
				if(file.isDirectory()){
					item.add(file.getName() + "/");
				}else{
					item.add(file.getName());
				}
			} 
		}

		ArrayAdapter<String> fileList =
				new ArrayAdapter<String>(this, R.layout.row, item);
		setListAdapter(fileList); 
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File file = new File(path.get(position));

		if (file.isDirectory())
		{
			if(file.canRead()){
				getDir(path.get(position));
			}else{
				new AlertDialog.Builder(this)
				.setIcon(R.drawable.ic_launcher)
				.setTitle("[" + file.getName() + "] folder nie może zostać otworzony!")
				.setPositiveButton("OK", null).show(); 
			} 
		}else {
			((EditText) findViewById(R.id.editText1)).setText(file.getName());
		}
	}

	public void cancelClick(View view) {
		Intent resultIntent = new Intent();
		resultIntent.putExtra("ok_save", false);
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}

	public void okClick(View view) {
		Intent resultIntent = new Intent();
		resultIntent.putExtra("ok_save", true);
		resultIntent.putExtra("filename", myPath.getText().toString().substring(8) + "/" +
				((EditText) findViewById(R.id.editText1)).getText().toString());
		setResult(Activity.RESULT_OK, resultIntent);
		finish();
	}
}