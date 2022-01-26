package com.example.test3.random;

import com.example.test3.model.UserModel;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class UserRandom {
    public UserModel userRandom(){
        UserModel userModel = new UserModel();
        userModel.setAge( new Random().nextInt(100));
        userModel.setFullname(RandomStringUtils.randomAlphabetic(15));
        userModel.setAddress(RandomStringUtils.randomAlphabetic(10));
        return userModel;
    }

}
