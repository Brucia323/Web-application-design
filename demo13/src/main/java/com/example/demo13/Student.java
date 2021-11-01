package com.example.demo13;

import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private String sex;
    private int age;
    private double weight;
    private double hight;
    private String url = "jdbc:mysql://localhost:3306/students";
    private String user = "root";
    private String password = "20010323";
    
    public Student() {
    }
    
    public Student(String name, String sex, int age, double weight, double hight) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.hight = hight;
    }
    
    public void add() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO stu_info (name, sex, age, " +
                "weight, hight) VALUES ('" + name + "', '" + sex + "', '" + age + "', '" + weight + "', '" + hight + "')");
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
    
    public String inquire() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM stu_info");
        ResultSet resultSet = preparedStatement.executeQuery();
        List list=new ArrayList();
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            name = resultSet.getString("name");
            sex = resultSet.getString("sex");
            age = resultSet.getInt("age");
            weight = resultSet.getDouble("weight");
            hight = resultSet.getDouble("hight");
            StudentToJson studentToJson=new StudentToJson(id,name,sex,age,weight,hight);
            list.add(studentToJson);
        }
        resultSet.close();
        preparedStatement.close();
        connection.close();
        Gson gson=new Gson();
        return gson.toJson(list);
    }
    
    public void delete(int id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM stu_info WHERE id = '" + id + "'");
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
    
    public void revise(int id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(url, user, password);
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE stu_info SET name = '" + name + "', sex =" +
                " '" + sex + "', age = '" + age + "', weight = '" + weight + "', hight = '" + hight + "' WHERE id = " +
                "'"+id+"'");
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSex() {
        return sex;
    }
    
    public void setSex(String sex) {
        this.sex = sex;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    public double getHight() {
        return hight;
    }
    
    public void setHight(double hight) {
        this.hight = hight;
    }
}
