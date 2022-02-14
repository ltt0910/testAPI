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

    ResponseCustom<String> add5MillionRecord() throws Exception;

    ResponseCustom<List<UserModel>> findByNameStartWith(String name) throws Exception;

    ResponseCustom<List<UserModel>> findByNameContain(String name) throws Exception;

    ResponseCustom<List<UserModel>> findByNameEqual(String name) throws Exception;

    ResponseCustom<UserModel> updateMoneyById(long id, long money) throws Exception;

    ResponseCustom<List<UserModel>> transferById(long id1, long id2, long money) throws Exception;


}
