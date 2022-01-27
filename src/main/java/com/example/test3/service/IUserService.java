package com.example.test3.service;

import com.example.test3.model.UserModel;
import com.example.test3.response.ResponseCustom;

import java.sql.SQLException;
import java.util.List;

public interface IUserService {
    ResponseCustom<UserModel> addUser(UserModel userModel) throws Exception;

    ResponseCustom<UserModel> updateUser(UserModel userModel) throws Exception;

    ResponseCustom<String> deleteUser(long[] id) throws Exception;

    ResponseCustom<List<UserModel>> findAll() throws SQLException;

    ResponseCustom<UserModel> findById(long id) throws Exception;

    ResponseCustom<List<UserModel>> findByName(String name) throws Exception;

    ResponseCustom<List<UserModel>> findByAddress(String address) throws Exception;

    ResponseCustom<List<UserModel>> sortByName() throws Exception;

    void add5MillionRecord() throws Exception;
}
