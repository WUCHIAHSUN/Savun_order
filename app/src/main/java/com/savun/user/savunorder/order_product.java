package com.savun.user.savunorder;

/**
 * Created by user on 2017/1/30.
 */

public class order_product {
    private String name ;
    private int count ;
    private int rice ;

    public order_product() {
    }

    public order_product(String name, int count, int rice) {
        this.name = name;
        this.count = count;
        this.rice = rice;
    }

    public int getcount() {
        return count;
    }

    public void setcount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRice() {
        return rice;
    }

    public void setRice(int rice) {
        this.rice = rice;
    }
}