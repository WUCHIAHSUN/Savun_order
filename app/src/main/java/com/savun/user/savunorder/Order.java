package com.savun.user.savunorder;

/**
 * Created by user on 2017/1/3.
 */

public class Order {
    public  String client;
    public  String collectionDate;
    public  String collectionState;
    public  String deliveryState;
    public  String orderDate;
    public  String orderID;
    public  String recordedState;
    public  String salesman;
    public  String totalAmount;



    public Order(){

    }

    public Order( String client, String collectionDate, String collectionState, String deliveryState,
                  String orderDate, String orderID, String recordedState, String salesman, String totalAmount) {
        this.client = client;
        this.salesman = salesman;
        this.orderDate = orderDate;
        this.recordedState = recordedState;
        this.orderID = orderID;
        this.collectionDate = collectionDate;
        this.collectionState = collectionState;
        this.deliveryState = deliveryState;
        this.totalAmount = totalAmount;
    }


    public String getClient(){
        return client;
    }
    public String getSalesman(){
        return salesman;
    }
    public String getOrderDate(){
        return orderDate;
    }
    public String getRecordedState(){
        return recordedState;
    }
    public String getOrderID(){
        return orderID;
    }
    public String getCollectionDate(){
        return collectionDate;
    }
    public String getCollectionState(){
        return collectionState;
    }
    public String getDeliveryState(){
        return deliveryState;
    }
    public String gettotalAmount(){
        return totalAmount;
    }



    public void setClient(String client){
        this.client = client;
    }
    public void setSalesman(String salesman){
        this.salesman = salesman;
    }
    public void setOrderDate(String orderDate){
        this.orderDate = orderDate;
    }
    public void setRecordedState(String recordedState){
        this.recordedState = recordedState;
    }
    public void setOrderID(String orderID){
        this.orderID = orderID;
    }
    public void setCollectionDate(String collectionDate){
        this.collectionDate = collectionDate;
    }
    public void setCollectionState(String collectionState){
        this.collectionState = collectionState;
    }
    public void setDeliveryState(String deliveryState){
        this.deliveryState = deliveryState;
    }
    public void settotalAmount(String totalAmount){
        this.totalAmount = totalAmount;
    }

}
