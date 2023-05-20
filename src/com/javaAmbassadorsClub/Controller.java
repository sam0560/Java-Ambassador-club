package com.javaAmbassadorsClub;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import  java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {
    public Connection myConn;

//    Create method to connect to database
    public Connection getMyConn() {
        String url = "jdbc:mysql://localhost:3306/javaAmbassadorsClubDb";
        String user = "root";
        String password = "";

        try {
            myConn = DriverManager.getConnection(url,user,password);
//            JOptionPane.showMessageDialog(null,"Program connected successfully");
        } catch (Exception e){
            e.printStackTrace();
        }
        return myConn;
    }

//    Create a method to fill an arraylist by getting all members from database
    public ArrayList<Members> getMembers() {
        ArrayList<Members> list = new ArrayList<>();//interested in seve column without password

        Members fromDatabase;

        try {
//            String sQl = "SELECT serialNumber,firstName,lastName,userName,mobile,email,date_created FROM registration;
            String sQL = "SELECT * FROM registration";
            PreparedStatement statement = getMyConn().prepareStatement(sQL);
            ResultSet resultSet = statement.executeQuery(); // Use resultset to retriec from database

            while (resultSet.next()){
                fromDatabase = new Members(resultSet.getInt("serialNumber"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("userName"),
                        resultSet.getString("mobile"),
                        resultSet.getString("email"),
                        resultSet.getString("date_created"));

                list.add(fromDatabase);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  list;
    }
}
