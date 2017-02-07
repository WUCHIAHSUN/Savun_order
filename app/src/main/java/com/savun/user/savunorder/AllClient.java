package com.savun.user.savunorder;

/**
 * Created by user on 2017/1/10.
 */

public class AllClient {
    public  String address;
    public  String clientID;
    public  String clientLv;
    public  String clientName;
    public  String clientPassword;
    public  String tel;

    public AllClient(){

    }

    public AllClient(String address, String clientID, String clientLv, String clientName,String clientPassword, String tel) {
        this.address = address;
        this.clientID = clientID;
        this.clientLv = clientLv;
        this.clientName = clientName;
        this.clientPassword = clientPassword;
        this.tel = tel;

    }

    public String getAddress(){
        return address;
    }
    public String getClientID(){
        return clientID;
    }
    public String getClientLv(){
        return clientLv;
    }
    public String getClientName(){
        return clientName;
    }
    public String getClientPassword(){
        return clientPassword;
    }
    public String getTel(){
        return tel;
    }


    public void setAddress(String address){
        this.address = address;
    }
    public void setClientID(String clientID){
        this.clientID = clientID;
    }
    public void setClientLv(String clientLv){
        this.clientLv = clientLv;
    }
    public void setClientName(String clientName){
        this.clientName = clientName;
    }
    public void setClientPassword(String clientPassword){
        this.clientPassword = clientPassword;
    }
    public void setTel(String tel){
        this.tel = tel;
    }



}