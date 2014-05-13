package com.example.lkc;

import java.util.ArrayList;

import android.app.Application;

public class Controller extends Application {
	private ArrayList<ModelProduct> myproduct = new ArrayList<ModelProduct>();
	private ModelCart modelcart = new ModelCart();

	public ModelProduct getProduct(int pPosition) {
		return myproduct.get(pPosition);
	}
	public void setProduct(ModelProduct product) {
		myproduct.add(product);
	}
	public ModelCart getCart() {
		return modelcart;
	}

public int getProductArrayListSize() {
	return myproduct.size();
}
}
