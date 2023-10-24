package com.example.ecommerce;

import java.sql.ResultSet;

public class Login {

    public Customer customerLogin(String userName, String password){
        String query = "SELECT * FROM customer WHERE email = '"+userName+"' AND password = '"+password+"'";
        DbConnection connection = new DbConnection();
        try{
            ResultSet res = connection.getQueryTable(query);
            if (res.next()){
                return new Customer(res.getInt("id"), res.getString("name"), res.getString("email"), res.getString("mobile"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        Login login = new Login();
        Customer customer = login.customerLogin("uzer@gmail.com", "abc123");
        System.out.println("Welcome : " + customer.getName());
//        System.out.println(login.customerLogin("uzer@gmail.com", "abc1234"));
    }

}
