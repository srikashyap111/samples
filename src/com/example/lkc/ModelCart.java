package com.example.lkc;

import java.util.ArrayList;

public class ModelCart {
private ArrayList<ModelProduct>cartproduct = new ArrayList<ModelProduct>();
public ModelProduct getProduct(int pPosition) {
	return cartproduct.get(pPosition);
}
public void setProduct(ModelProduct product){
	cartproduct.add(product);
}
public int getcartsize(){
	return cartproduct.size();
}
public boolean checkProductInCart(ModelProduct aproduct){
	return cartproduct.contains(aproduct);
}
}
