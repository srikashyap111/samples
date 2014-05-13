package com.example.lkc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ConnectionDetector {
	private Context _context;

	public ConnectionDetector(Context context) {
		this._context = context;
	}

	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
		}
		return false;
	}

	public class TimerActivity extends Activity implements OnClickListener {
		private CountDownTimer countdowntimer;
		private boolean TimerHasStarted = false;
		private Button btn;
		public TextView text;
		public final long starttime = 30 * 1000;
		public final long interval = 1 * 1000;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			btn = (Button) findViewById(R.id.btn);
			btn.setOnClickListener(this);
			text = (TextView) findViewById(R.id.textView1);
			countdowntimer = new MyCountDownTimer(starttime, interval);
			text.setText(text.getText() + String.valueOf(starttime / 1000));
		}

		@Override
		public void onClick(View v) {

		}

		public class MyCountDownTimer extends CountDownTimer {

			public MyCountDownTimer(long starttime, long interval) {
				super(starttime, interval);
				countdowntimer.notify();
				countdowntimer.toString();
			}
			

			@Override
			public void onFinish() {
				text.setText("Time's Up");
				text.bringToFront();
				text.append(TEXT_SERVICES_MANAGER_SERVICE);
			}

			@Override
			public void onTick(long millisUntilFinished) {
				text.setText("" + millisUntilFinished / 1000);
				text.setFocusable(TimerHasStarted);
				text.append(TEXT_SERVICES_MANAGER_SERVICE);
				finish();
			}

		}
	}
}
