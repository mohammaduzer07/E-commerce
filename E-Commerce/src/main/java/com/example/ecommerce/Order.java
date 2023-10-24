package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {

    // this func going to create an order & place an order for us
    public static boolean placeOrder(Customer customer , Product product){
        String groupOrderId = "SELECT max(group_order_id) +1 id FROM orders";
        DbConnection dbConnection = new DbConnection();
        try {
            ResultSet res = dbConnection.getQueryTable(groupOrderId);
            if(res.next()){
                // inserting row into this order table
                String placeOrder = "INSERT INTO orders(group_order_id, customer_id, product_id) VALUES ("+res.getInt("id")+","+customer.getId()+","+product.getId()+")";
                return dbConnection.updateDatabase(placeOrder) != 0;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static int placeMulitpleOrder(Customer customer , ObservableList<Product> productList){
        String groupOrderId = "SELECT max(group_order_id) +1 id FROM orders";
        DbConnection dbConnection = new DbConnection();
        try {
            ResultSet res = dbConnection.getQueryTable(groupOrderId);
            int count = 0;
            if(res.next()){
                for(Product product : productList){
                    // inserting row into this order table
                    String placeOrder = "INSERT INTO orders(group_order_id, customer_id, product_id) VALUES ("+res.getInt("id")+","+customer.getId()+","+product.getId()+")";
                    count += dbConnection.updateDatabase(placeOrder);
                }
                return count;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
