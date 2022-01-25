package com.example.test3.entity;

public class UserEntity {
    private long id;
    private int age;
    private String fullname;
    private String address;

    public UserEntity() {
    }

    public UserEntity(long id, int age, String fullname, String address) {
        this.id = id;
        this.age = age;
        this.fullname = fullname;
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
