package com.example.test3.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserModel implements Comparable<UserModel> {
    @Max(value = 99, message = "ID không quá 99")
    private long id;

    @Min(value = 1, message = "Tuổi không thể nhỏ hơn 1")
    @Max(value = 100, message = "Tuổi không thể lớn hơn 100")
    private int age;

    @Size(min = 2, max = 255)
    private String fullname;

    @Max(value = 99999999, message = "Money không quá 99999999")
    private Long money;


    @Size(min = 2, max = 45)
    @NotBlank(message = "Vui lòng không để trống")
    private String address;

    private Long version;

    @Override
    public int compareTo(UserModel o) {
        return this.getFullname().compareTo(o.getFullname());
    }
}
