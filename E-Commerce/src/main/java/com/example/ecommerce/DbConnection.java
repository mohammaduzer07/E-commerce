package com.example.ecommerce;
import java.sql.*;

public class DbConnection {

    private final String DBurl = "jdbc:mysql://localhost:3306/e-commerce";
    private final String userName = "root";
    private final String password = "Uzer^1997";
    private Statement getStatement(){
        try{
            Connection connection = DriverManager.getConnection(DBurl, userName, password);
            return connection.createStatement();
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet getQueryTable(String query){
        try{
            Statement statement = getStatement();
            return statement.executeQuery(query);
        }catch(Exception e){
            e.printStackTrace();;
        }
        return null;
    }

    public int updateDatabase(String query){
        try{
            Statement statement = getStatement();
            return statement.executeUpdate(query); //executeDatabase means we can update the database, insert , delete and update
        }catch(Exception e){
            e.printStackTrace();;
        }
        return 0; // here it will give how many rows are affected
    }

    public static void main(String[] args) {
        DbConnection conn = new DbConnection();
        ResultSet res = conn.getQueryTable("SELECT * FROM customer");
        if (res!= null){
            System.out.println("Connection Successful");
        }
        else System.out.println("Connection Failed.");
    }
}
