package com.example.demo13;

public class StudentToJson {
    private int id;
    private String name;
    private String sex;
    private int age;
    private double weight;
    private double hight;
    
    public StudentToJson(int id, String name, String sex, int age, double weight, double hight) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.hight = hight;
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
