package com.example.golan.train.BL;

public class User {
    private String fullName;
    private String user_id;
    private String gender;
    private double weigh, height;


    public User(String fullName, String user_id, String gender, double weigh, double height) {
        this.fullName = fullName;
        this.user_id = user_id;
        this.gender = gender;
        this.weigh = weigh;
        this.height = height;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", user_id='" + user_id + '\'' +
                ", gender='" + gender + '\'' +
                ", weigh=" + weigh +
                ", height=" + height +
                '}';
    }
}
