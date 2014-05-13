package com.example.lkc;

public class ModelProduct {
 public String productName;
 public String productDesc;
 public int productPrice;
 public ModelProduct(String productName,String productDesc,int productPrice) {
	 this.productName = productName;
	 this.productDesc = productDesc;
	 this.productPrice = productPrice;	 
 }
 public String getProductName() {
	 return productName;
 }
 public String getProductDesc() {
	 return productDesc;
 }
 public int getProductPrice() {
	 return productPrice;
 }
}
