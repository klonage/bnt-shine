package com.bnt.bntshine;

import com.bnt.bntshine.activities.MainActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GlobalOffAction {
	private Activity parentActivity;
	private Context appContext;
	private String password;
	private int selectedMethod;

	public GlobalOffAction(Context applicationContext, Activity parentActivity) {
		appContext = applicationContext;
		this.parentActivity = parentActivity;

		reloadConfig();
	}

	public void doGlobalOff() {
		reloadConfig(); // TODO: should it be here? probably no, and should be
						// moved to globaloffsettings
		authorize();
	}

	private void reloadConfig() {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(appContext);

		selectedMethod = preferences.getInt("settings.global_off.method",
				AppConfiguration.getDefaultGlobalOffMethod());
		password = preferences.getString("settings.global_off.password", "");
	}

	private void authorize() {
		((MainActivity) parentActivity).setGlobalOffTouchListener(null);

		switch (selectedMethod) {
		case 0: // confirmation window
			showConfirmationWindow();
			break;
		case 1:
			showSymbolReader();
			break;
		case 2:
			changeButtonAction();
			break;
		case 3:
			showPasswordPrompt();
			break;
		default:
			break;
		}
	}

	private void showConfirmationWindow() {
		AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
		builder.setMessage("Na pewno chcesz wykonać operację GLOBAL OFF?")
				.setPositiveButton("TAK", dialogClickListener)
				.setNegativeButton("NIE", dialogClickListener).show().setCanceledOnTouchOutside(true);
	}

	private void showSymbolReader() {
		Toast.makeText(
				parentActivity,
				"Wybrano niezaimplementowaną metodę autoryzacji. "
						+ "Zmień metodę w ustawieniach programu.",
				Toast.LENGTH_LONG).show();
	}

	private void showPasswordPrompt() {
		final AlertDialog.Builder alert = new AlertDialog.Builder(
				parentActivity);

		alert.setTitle("URUCHAMIANIE GLOBAL OFF");
		alert.setMessage("Wprowadź hasło:");

		final EditText input = new EditText(parentActivity);
		alert.setView(input);

		alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				if (value.equals(password)) {
					executeGlobalOff();
				} else {
					Toast.makeText(parentActivity,
							"Wprowadzono niepoprawne hasło", Toast.LENGTH_LONG)
							.show();
				}
			}

		});

		alert.setNegativeButton("Anuluj", null);
		alert.show().setCanceledOnTouchOutside(true);
	}

	private void changeButtonAction() {
		MainActivity mact = (MainActivity) parentActivity;

		mact.setGlobalOffTouchListener(new View.OnTouchListener() {
			private float mDownX;
			private float mDownY;
			private final float SCROLL_THRESHOLD = 30;
			private long time;
			private boolean isOnClick;

			@Override
			public boolean onTouch(View v, MotionEvent ev) {
				switch (ev.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					mDownX = ev.getX();
					mDownY = ev.getY();
					isOnClick = true;
					time = System.currentTimeMillis();
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
					if (isOnClick && System.currentTimeMillis() - time > 2000) { // FIXME:
																					// I really don't  know why
																					// I need to use 2000 value,
																					// when I'm going to wait 3000ms...
																					// maybe it's due inaccurate
																					// currentTimeMillis function
						executeGlobalOff();
					}
					break;
				case MotionEvent.ACTION_MOVE:
					if (isOnClick
							&& (Math.abs(mDownX - ev.getX()) > SCROLL_THRESHOLD || Math
									.abs(mDownY - ev.getY()) > SCROLL_THRESHOLD)) {
						isOnClick = false;
					}
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	private void executeGlobalOff() {
		// TODO: not implementet, because of no data frame...
		Toast.makeText(parentActivity, "i cały świat gaśnie...",
				Toast.LENGTH_SHORT).show();
	}

	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which) {
			case DialogInterface.BUTTON_POSITIVE:
				executeGlobalOff();
				break;
			case DialogInterface.BUTTON_NEGATIVE:
				// do nothing
				break;
			}
		}
	};
}
