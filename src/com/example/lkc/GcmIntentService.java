package com.example.lkc;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import static com.example.lkc.CommonUtilities.displayMessage;
import static com.example.lkc.CommonUtilities.SENDER_ID;
import com.google.android.gcm.GCMBaseIntentService;

public class GcmIntentService extends GCMBaseIntentService {
	public static final String TAG = "GCMIntentService";

	public GcmIntentService() {
		super(SENDER_ID);
	}

	protected void onRegistered(Context context, String registrationId) {
		displayMessage(context, "Your device registered with GCM ");
		ServerUtilities.register(context, MainActivity.name,
				MainActivity.email, registrationId);
		 ServerUtilities.register(context, MainActivity.name, MainActivity.email, registrationId);
		ServerUtilities.unregister(GcmIntentService.this, registrationId);

	}

	protected void onUnregistered(Context context, String registrationId) {
		displayMessage(context, getString(R.string.app_name));
		ServerUtilities.unregister(context, registrationId);
	}

	protected void onMessage(Context context, Intent intent) {
		String message = intent.getExtras().getString("price");
		displayMessage(context, message);
		generateNotification(context, message);
	}

	protected void onDeleteMessages(Context context, int total) {
		String message = getString(R.string.app_name, total);
		displayMessage(context, message);
		generateNotification(context, message);
	}

	protected void onError(Context context, String errorId) {
		displayMessage(context, getString(R.string.app_name, errorId));
	}

	protected boolean onRecoverableError(Context context, String errorId) {
		displayMessage(context, getString(R.string.app_name, errorId));
		return super.onRecoverableError(context, errorId);
	}

	private static void generateNotification(Context context, String message) {
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		NotificationManager notificationmanager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(icon, message, when);
		String title = context.getString(R.string.app_name);
		Intent notificationIntent = new Intent(context, MainActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
				notificationIntent, 0);
		notification.setLatestEventInfo(context, title, message, intent);
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notificationmanager.notify(0, notification);
		notification.defaults |= Notification.DEFAULT_ALL;
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notificationmanager.notify(message, icon, notification);
		notificationmanager.notifyAll();
	}
}
