package com.example.lkc;

import static com.example.lkc.CommonUtilities.SENDER_ID;
import static com.example.lkc.CommonUtilities.SERVER_URL;
import static com.example.lkc.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.example.lkc.CommonUtilities.EXTRA_MESSAGE;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class RegisterActivity extends Activity {
	AlertDialogManager alert = new AlertDialogManager();
	ConnectionDetector cd;
	public static String name;
	public static String email;
	EditText txtName;
	EditText txtMail;
	Button register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		cd = new ConnectionDetector(getApplicationContext());
		if (!cd.isConnectingToInternet()) {
			alert.showAlertDialog(RegisterActivity.this,
					"Internet Connection Error", "Please connect to Internet",
					false);
			return;
		}
		if (SERVER_URL == null || SENDER_ID == null || SERVER_URL.length() == 0
				|| SENDER_ID.length() == 0) {
			alert.showAlertDialog(RegisterActivity.this,
					"Configuration Error !",
					"Please set ur SERVER Url and GCM Sender ID", false);
			return;
		}
		txtName = (EditText) findViewById(R.id.action_settings);
		txtMail = (EditText) findViewById(R.id.textView1);
		register = (Button) findViewById(R.id.btn);
		register.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String name = txtName.getText().toString();
				String email = txtMail.getText().toString();
				if (name.trim().length() > 0 && email.trim().length() > 0) {
					Intent i = new Intent(getApplicationContext(),
							MainActivity.class);
					i.putExtra("name", name);
					i.putExtra("email", email);
					startActivity(i);
					finish();
				} else {
					alert.showAlertDialog(RegisterActivity.this,
							"Registration Error !",
							"Please Enter Your Details", false);
				}
			}
		});
	}

	public class MainActivity extends Activity {
		TextView lblView;
		AsyncTask<Void, Void, Void> mTask;
		AlertDialogManager alert = new AlertDialogManager();
		ConnectionDetector cd;

		// public static String name;
		// public static String email;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			cd = new ConnectionDetector(getApplicationContext());
			if (!cd.isConnectingToInternet()) {
				alert.showAlertDialog(MainActivity.this,
						"Internet ConnectionError",
						"Please connect to Internet", false);
				return;
			}
			Intent i = getIntent();
			name = i.getStringExtra("name");
			email = i.getStringExtra("email");
			GCMRegistrar.checkDevice(this);
			GCMRegistrar.checkManifest(this);
			lblView = (TextView) findViewById(R.id.textView1);
			registerReceiver(mHandleMessageReceiver, new IntentFilter(
					DISPLAY_MESSAGE_ACTION));
			final String regId = GCMRegistrar.getRegistrationId(this);
			if (regId.equals("")) {
				GCMRegistrar.register(this, SENDER_ID);
			} else {
				if (GCMRegistrar.isRegisteredOnServer(this)) {
					Toast.makeText(MainActivity.this,
							"Already registerd with GCM", Toast.LENGTH_LONG)
							.show();
					registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_SERVICE));
					
				} else {
					final Context context = null;
					mTask = new AsyncTask<Void, Void, Void>() {
						protected Void doInBackground(Void[] params) {
							ServerUtilities.register(context, name, email,
									regId);
							return null;
						}

						@Override
						protected void onPostExecute(Void result) {
							mTask = null;
							super.onPostExecute(result);
						}
					};
					mTask.execute(null, null, null);
				}
			}
		}

		private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
				wakelocker.acquire(getApplicationContext());
				lblView.append(newMessage + "\n");
				Toast.makeText(getApplicationContext(),
						"New Message" + newMessage, Toast.LENGTH_LONG).show();
				wakelocker.release();
			}
		};

		protected void onDestroy() {
			if (mTask != null) {
				mTask.cancel(true);
			}
			try {
				unregisterReceiver(mHandleMessageReceiver);
				GCMRegistrar.onDestroy(this);
			} catch (Exception e) {
			}
			super.onDestroy();
		};
	}
}
