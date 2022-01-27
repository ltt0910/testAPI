package com.example.test3.service;

import com.example.test3.converter.UserConverter;
import com.example.test3.entity.UserEntity;
import com.example.test3.exception.FieldErrorException;
import com.example.test3.exception.UserExsitsException;
import com.example.test3.exception.UserInfoException;
import com.example.test3.global.ErrorCode;
import com.example.test3.model.UserModel;
import com.example.test3.repository.IUserRepository;
import com.example.test3.utils.CheckVaildate;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserModel addUser(UserModel userModel) throws Exception {
        if (userModel.getFullname() == null || userModel.getFullname().isEmpty() || userModel.getFullname().length() > 255) {
            throw new UserInfoException("invalid user info", ErrorCode.INVALID_USER_INFO);
        }
        if (!CheckVaildate.checkVaild(userModel.getFullname()) || !CheckVaildate.checkVaild(userModel.getAddress())) {
            throw new FieldErrorException("Field không được có kí tự đặc biệt", ErrorCode.CHARACTER_ERROR);
        }
        if (userRepository.exsist(userModel.getId())) {
            throw new UserExsitsException("Người dùng đã tồn tại", ErrorCode.USER_EXSIST);
        }
        UserEntity user = userRepository.insertUser(userConverter.convertToEntity(userModel));
        return userConverter.convertToModel(user);
    }

    @Override
    public UserModel updateUser(UserModel userModel) throws Exception {
        if (userModel.getFullname() == null || userModel.getFullname().isEmpty() || userModel.getFullname().length() > 255) {
            throw new UserInfoException("invalid user info", ErrorCode.INVALID_USER_INFO);
        }
        if (!CheckVaildate.checkVaild(userModel.getFullname()) || !CheckVaildate.checkVaild(userModel.getAddress())) {
            throw new FieldErrorException("Field không được có kí tự đặc biệt", ErrorCode.CHARACTER_ERROR);
        }
        if (!userRepository.exsist(userModel.getId())) {
            throw new FileNotFoundException("Người dùng đã tồn tại");
        }
        UserEntity user = userRepository.updateUser(userConverter.convertToEntity(userModel));
        return userConverter.convertToModel(user);
    }

    @Override
    public void deleteUser(long id) throws Exception {
        if (userRepository.exsist(id)) {
            throw new FileNotFoundException("Người dùng không tồn tại");
        }
        userRepository.deleteUser(id);
    }

    @Override
    public List<UserModel> findAll() throws SQLException {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities.size() > 0) {
            for (UserEntity item : userEntities) {
                userModels.add(userConverter.convertToModel(item));
            }
            return userModels;
        }
        return userModels;
    }

    @Override
    public UserModel findById(long id) throws SQLException {
        if (userRepository.exsist(id)) {
            return userConverter.convertToModel(userRepository.findById(id));
        }
        return null;
    }

    @Override
    public List<UserModel> findByName(String name) throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByName(name);
        if (!CheckVaildate.checkVaild(name)) {
            throw new FieldErrorException("Name không được chứa kí tự đặc biệt", ErrorCode.CHARACTER_ERROR);
        }
        for (UserEntity item : userEntities) {
            userModels.add(userConverter.convertToModel(item));
        }
        return userModels;
    }

    @Override
    public List<UserModel> findByAddress(String address) throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByAddress(address);
        if (!CheckVaildate.checkVaild(address)) {
            throw new FieldErrorException(" Address không được chứa kí tự đặc biệt", ErrorCode.CHARACTER_ERROR);
        }
        for (UserEntity item : userEntities) {
            userModels.add(userConverter.convertToModel(item));
        }
        return userModels;
    }

    public List<UserModel> sortByName() throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        if(userEntities.size() == 0){
            throw new FileNotFoundException("Không có đối tượng để sắp xếp");
        }
        for (UserEntity item : userEntities) {
            UserModel userModel = new UserModel();
            userModel = userConverter.convertToModel(item);
            userModels.add(userModel);
        }
        Collections.sort(userModels);
        return userModels;
    }

    public void add5MillionRecord() throws Exception {
        userRepository.add5MillionRecords();
    }

}
