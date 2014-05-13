package com.example.lkc;

import android.content.Context;
import android.content.Intent;

public class CommonUtilities {
	static final String SERVER_URL = "htp://google.com";
	static final String SENDER_ID = "";
	static final String TAG = "AndroidHive Gcm";
	static final String DISPLAY_MESSAGE_ACTION = "com.example.lkc.DISPLAY_MESSAGE";
	static final String EXTRA_MESSAGE = "message";

	static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

}
