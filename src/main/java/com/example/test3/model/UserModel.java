package com.example.test3.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserModel implements Comparable<UserModel>{
    @Max(value = 99,message = "ID không quá 99")
    private long id;

    @Min(value = 1,message = "Tuổi không thể nhỏ hơn 1")
    @Max(value = 100,message = "Tuổi không thể lớn hơn 100")
    private int age;

    @Size(min = 2,max = 255)
    private String fullname;

    @Size(min = 2,max = 45)
    @NotBlank(message = "Vui lòng không để trống")
    private String address;

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

    @Override
    public int compareTo(UserModel o) {
            return this.getFullname().compareTo(o.getFullname());
    }
}
