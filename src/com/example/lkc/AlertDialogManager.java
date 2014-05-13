package com.example.lkc;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertDialogManager {
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alerdialog = new AlertDialog.Builder(context).create();
		alerdialog.setTitle(title);
		alerdialog.setMessage(message);
		if (status != null) {
			alerdialog.setIcon((status) ? R.drawable.ic_launcher
					: R.drawable.ic_launcher);
			alerdialog.setButton("Ok", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {

				}
			});
			alerdialog.show();
		}
	}
}
