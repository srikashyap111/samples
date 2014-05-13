package com.example.lkc;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import com.google.android.gcm.GCMRegistrar;

import android.content.Context;
import static com.example.lkc.CommonUtilities.displayMessage;

public class ServerUtilities {
	private static final int MAX_ATTEMPTS = 5;
	private static final int BACKOFF_MILLI_SECONDS = 2000;
	private static final Random random = new Random();
	private static String SERVER_URL;

	static void register(final Context context, String name, String email,
			final String regId) {
		String serverUrl = SERVER_URL;
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", name);
		params.put("email", email);
		params.put("regId", regId);
		long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(2000);
		for (int i = 1; i <= MAX_ATTEMPTS; i++) {
			try {
				displayMessage(context,
						context.getString(R.string.app_name, i, MAX_ATTEMPTS));
				post(serverUrl, params);
				GCMRegistrar.setRegisteredOnServer(context, true);
				String message = context.getString(R.string.app_name);
				CommonUtilities.displayMessage(context, message);
				return;
			} catch (IOException e) {
				e.printStackTrace();
				if (i == MAX_ATTEMPTS) {
					break;
				}
				try {
					Thread.sleep(backoff);
				} catch (InterruptedException e1) {
					Thread.currentThread().interrupt();
					return;
				}
				backoff *= 2;
			}
		}
		String message = context.getString(R.string.app_name, MAX_ATTEMPTS);
		CommonUtilities.displayMessage(context, message);
	}

	static void unregister(final Context context, final String regId) {
		String serverUrl = SERVER_URL + "/unregister";
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regId);
		try {
			post(serverUrl, params);
			GCMRegistrar.setRegisteredOnServer(context, false);
			String message = context.getString(R.string.app_name);
			CommonUtilities.displayMessage(context, message);
		} catch (IOException e) {
			String message = context.getString(R.string.app_name,
					e.getMessage());
			CommonUtilities.displayMessage(context, message);
			context.getApplicationContext();
			context.checkUriPermission(null, serverUrl, message, MAX_ATTEMPTS,
					BACKOFF_MILLI_SECONDS, 0);

		}
	}

	private static void post(String endpoint, Map<String, String> params)
			throws IOException {
		URL url;
		try {
			url = new URL(endpoint);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("invalid url" + endpoint);
		}
		StringBuilder Bodybuilder = new StringBuilder();
		Iterator<Entry<String, String>> iterator = params.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, String> param = iterator.next();
			Bodybuilder.append(param.getKey()).append("=")
					.append(param.getValue());
			if (iterator.hasNext()) {
				Bodybuilder.append('&');
				String body = Bodybuilder.toString();
				byte[] bytes = body.getBytes();
				HttpURLConnection conn = null;
				try {
					conn = (HttpURLConnection) url.openConnection();
					conn.setDoOutput(true);
					conn.setUseCaches(false);
					conn.setFixedLengthStreamingMode(bytes.length);
					conn.setRequestMethod("POST");
					conn.setRequestProperty("content-type",
							"application/x-www-form-urlencoded;charset=UTF-8");
					OutputStream out = conn.getOutputStream();
					out.write(bytes);
					out.close();
					int status = conn.getResponseCode();
					if (status != 200) {
						throw new IOException("post failed wit error code"
								+ status);
					}
				} finally {
					if (conn != null) {
						conn.disconnect();
					}
				}
			}
		}
	}
}
