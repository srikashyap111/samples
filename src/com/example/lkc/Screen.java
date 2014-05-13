package com.example.lkc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class Screen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		LinearLayout lm = (LinearLayout) findViewById(R.id.action_settings);
		Button btn = (Button) findViewById(R.id.btn);
		final Controller aController = (Controller) getApplicationContext();
		ModelProduct productobject = null;
		for (int i = 0; i < 4; i++) {
			int price = 10 + i;
			productobject = new ModelProduct("Product" + i, "Desription" + i,
					price);
			aController.setProduct(productobject);
		}
		int ProductSize = aController.getProductArrayListSize();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		for (int j = 0; j < ProductSize; j++) {
			String pName = aController.getProduct(j).getProductName();
			int pPrice = aController.getProduct(j).getProductPrice();
			LinearLayout ll = new LinearLayout(this);
			ll.setOrientation(LinearLayout.HORIZONTAL);
			TextView product = new TextView(this);
			product.setText(" " + pName + "");
			product.setText(" " + pPrice + "");
			ll.addView(product);
			final Button button = new Button(this);
			button.setId(j + 1);
			button.setText("Add to cart");
			button.setLayoutParams(params);
			final int index = j;
			button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.i("TAG", "index:" + index);
					ModelProduct tempProductOject = aController
							.getProduct(index);
					if (!aController.getCart().checkProductInCart(
							tempProductOject)) {
						button.setText("Added");
						aController.getCart().setProduct(tempProductOject);
						Toast.makeText(
								getApplicationContext(),
								"Now cart size"
										+ aController.getCart().getcartsize(),
								Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(),
								"product" + "(index+1)+", Toast.LENGTH_LONG)
								.show();
					}
				}
			});
			ll.addView(button);
			lm.addView(ll);
		}
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Screen.this, nextscreen.class);
				startActivity(intent);
			}
		});
	}

	public class nextscreen extends Activity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			TextView showcartContent = (TextView) findViewById(R.id.action_settings);
			Button btn = (Button) findViewById(R.id.btn);
			Controller aController = (Controller) getApplicationContext();
			int CartSize = aController.getCart().getcartsize();
			String showstring = "";
			if (CartSize > 0) {
				for (int i = 0; i < CartSize; i++) {
					String pName = aController.getCart().getProduct(i)
							.getProductName();
					int pPrice = aController.getCart().getProduct(i)
							.getProductPrice();
					String pDisc = aController.getCart().getProduct(i)
							.getProductDesc();
					showstring += " ProductName += " + pName + " " + "Price +="
							+ pPrice + " " + "Discription +=" + pDisc + " ";

				}
			} else {
				showstring = "Shopping casrt is empty";
				showcartContent.setText(showstring);
			}
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(getBaseContext(),
							lastscreen.class);
					startActivity(intent);
				}
			});
		}

		@Override
		protected void onDestroy() {
			super.onDestroy();
		}
	}

	public class lastscreen extends Activity {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			TextView txt = (TextView) findViewById(R.id.action_settings);
			final Controller acontroller = (Controller) getApplicationContext();
			int CartSize = acontroller.getCart().getcartsize();
			String showString = "";
			for (int i = 0; i < CartSize; i++) {
				String pName = acontroller.getCart().getProduct(i)
						.getProductName();
				int pPrice = acontroller.getCart().getProduct(i)
						.getProductPrice();
				String pDesc = acontroller.getCart().getProduct(i)
						.getProductDesc();
				showString = " Name += " + pName + "" + "Price += " + pPrice
						+ " " + "Description += " + pDesc + " ";
			}
			txt.setText(showString);
		}
	}
}
