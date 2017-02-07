package com.savun.user.savunorder;

import android.text.Editable;

/**
 * Created by user on 2016/12/30.
 */
 public class AllProduct {
    public  String productID;
    public  String productName;
    public  String retailPrice;
    public  String salonPrice;
    public  String stockNumber;

    public AllProduct(){

    }

    public AllProduct(String productID, String productName, String retailPrice, String salonPrice, String stockNumber) {
        this.productID = productID;
        this.productName = productName;
        this.retailPrice = retailPrice;
        this.salonPrice = salonPrice;
        this.stockNumber = stockNumber;

    }



    public String getproductID(){
        return productID;
    }
    public String getproductName(){
        return productName;
    }
    public String retailPrice(){
        return retailPrice;
    }
    public String getsalonPrice(){
        return salonPrice;
    }
    public String getstockNumber(){
        return stockNumber;
    }


    public void setproductID(String productID){
        this.productID = productID;
    }
    public void setproductName(String productName){
        this.productName = productName;
    }
    public void setretailPrice(String retailPrice){
        this.retailPrice = retailPrice;
    }
    public void setsalonPrice(String salonPrice){
        this.salonPrice = salonPrice;
    }
    public void setstockNumber(String stockNumber){
        this.stockNumber = stockNumber;
    }



}
