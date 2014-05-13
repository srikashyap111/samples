package com.example.lkc;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class call extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		final Button btn = (Button) findViewById(R.id.btn);
		final TextView txt = (TextView) findViewById(R.id.textView1);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				txt.setText("Requesting server");
				WebServiceCall web = new WebServiceCall();
				String weight = "18000";
				String fromUnit = "grams";
				String toUnit = "kilograms";
				String aResponse = web.getConvertedWeight("Convert Weight",
						weight, fromUnit, toUnit);
				Toast.makeText(getApplicationContext(),
						weight + "Gram=" + aResponse + "Kilograms",
						Toast.LENGTH_LONG).show();
				Log.i("Response", "------" + aResponse);
				txt.setText("Response" + aResponse);
			}
		});
	}

	public class WebServiceCall {
		String namespace = "http://www.WebServiceX.net";
		private String url = "http://webservicex.net/ConvertWeight.aspx";
		String SOAP_ACTION;
		private SoapObject request = null, ObjMessages = null;
		private SoapSerializationEnvelope envelope;
		private AndroidHttpTransport androidHttpTransport;

		WebServiceCall() {

		}

		protected void SetEnvelope() {
			try {
				envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
				envelope.dotNet = true;
				envelope.setOutputSoapObject(request);
				androidHttpTransport = new AndroidHttpTransport(url);
				androidHttpTransport.debug = true;
			} catch (Exception e) {
				Log.i("Soap Exception", "=======" + e.toString());
			}
		}

		public String getConvertedWeight(String MethodName, String weight,
				String fromUnit, String toUnit) {
			try {
				SOAP_ACTION = namespace + MethodName;
				request = new SoapObject(namespace, MethodName);
				PropertyInfo weightpro = new PropertyInfo();
				weightpro.setName("weight");
				weightpro.setValue(weight);
				weightpro.setType(double.class);
				request.addProperty(weightpro);
				request.addProperty("FromUnit", "" + fromUnit);
				request.addProperty("ToUnit", "" + toUnit);
				request.addAttribute(MethodName, weightpro);
				request.addProperty(fromUnit, toUnit);
				request.addAttribute(MethodName, weight);
				request.addProperty(fromUnit, weight);
				SetEnvelope();
				try {
					androidHttpTransport.call(SOAP_ACTION, envelope);
					String result = envelope.getResponse().toString();
					return result;
				} catch (Exception e) {
					return e.toString();
				}

			} catch (Exception e) {
				return e.toString();
			}
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}
}
