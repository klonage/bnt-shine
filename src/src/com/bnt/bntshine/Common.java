package com.bnt.bntshine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class Common {
	static public void notImplementedFunctionAlert(Activity activity) {
		AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(activity).setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		}).create();
		alertDialog.setTitle("Funkcja niedostępna");
		alertDialog.setMessage("Obecnie funkcjonalność nie jest dostępna.");
		alertDialog.show();
	}
}
