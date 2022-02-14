package com.example.test3.repository;

import com.example.test3.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface IUserRepository {
    UserEntity insertUser(UserEntity user) throws SQLException;

    UserEntity updateUser(UserEntity user) throws SQLException;

    void deleteUser(long id) throws SQLException;

    boolean exsist(long id);

    List<UserEntity> findAll() throws SQLException;

    UserEntity findById(long id) throws SQLException;

    List<UserEntity> findByName(String name) throws SQLException;

    List<UserEntity> findByAddress(String address) throws SQLException;

    void add5MillionRecords() throws SQLException;

    List<UserEntity> findByNameStartWith(String name) throws SQLException;

    List<UserEntity> findByNameContain(String name) throws SQLException;

    List<UserEntity> findByNameEqual(String name) throws SQLException;

    long getMoney(long id) throws SQLException;

    UserEntity updateMoneyById(long id, long money) throws SQLException;

    List<UserEntity> transferById(long id1, long id2, long money) throws SQLException;

    long getMoneyById(long id) throws SQLException;


}
