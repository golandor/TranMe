package com.example.golan.train.BL;

public class User {
    private String fullName;
    private String user_id;
    private String gender;
    private double weigh;
    private int age;
    private long userNumber;

    public User() {
    }

    public User(String fullName, String user_id, String gender, double weigh, int age,long userNumber) {
        this.fullName = fullName;
        this.user_id = user_id;
        this.gender = gender;
        this.weigh = weigh;
        this.age = age;
        this.userNumber = userNumber;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getWeigh() {
        return weigh;
    }

    public void setWeigh(double weigh) {
        this.weigh = weigh;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(long userNumber) {
        this.userNumber = userNumber;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", user_id='" + user_id + '\'' +
                ", gender='" + gender + '\'' +
                ", weigh=" + weigh +
                ", age=" + age +
                '}';
    }
}
