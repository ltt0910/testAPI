package com.example.test3.service;

import com.example.test3.converter.UserConverter;
import com.example.test3.entity.UserEntity;
import com.example.test3.model.UserModel;
import com.example.test3.repository.IUserRepository;
import com.example.test3.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    @Override
    public UserModel addUser(UserModel userModel) {
        try {
            if(!userRepository.exsist(userModel.getId()) && checkValidate(userModel)){
                UserEntity user  = userRepository.insertUser(userConverter.convertToEntity(userModel));
                return userConverter.convertToModel(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserModel updateUser(UserModel userModel) {
        try {
            if(userRepository.exsist(userModel.getId()) && checkValidate(userModel)){
                UserEntity user  = userRepository.updateUser(userConverter.convertToEntity(userModel));
                return userConverter.convertToModel(user);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return null;
    }

    @Override
    public void deleteUser(long id){
        try {
            if(userRepository.exsist(id)){
                userRepository.deleteUser(id);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<UserModel> findAll(){
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        if(userEntities.size()>0){
            for (UserEntity item:userEntities) {
                userModels.add(userConverter.convertToModel(item));
            }
            return userModels;
        }
       return userModels;
    }

    @Override
    public UserModel findById(long id) {
        if(userRepository.exsist(id)){
            return userConverter.convertToModel(userRepository.findById(id));
        }
        return null;
    }

    @Override
    public List<UserModel> findByName(String name) {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByName(name);
        if(userEntities.size()>0){
            for (UserEntity item:userEntities) {
                userModels.add(userConverter.convertToModel(item));
            }
            return userModels;
        }
        return userModels;
    }

    @Override
    public List<UserModel> findByAddress(String address) {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByAddress(address);
        if(userEntities.size()>0){
            for (UserEntity item:userEntities) {
                userModels.add(userConverter.convertToModel(item));
            }
            return userModels;
        }
        return userModels;
    }

    public boolean checkValidate(UserModel userModel){
        if (!StringUtils.isEmpty(userModel.getFullname()) && !StringUtils.isEmpty(userModel.getAddress())){
            return true;
        }
        return false;
    }

    public List<UserModel> sortByName(){
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities =userRepository.findAll();
        if(userEntities.size()>0){
            for (UserEntity item:userEntities) {
                UserModel userModel = new UserModel();
                userModel = userConverter.convertToModel(item);
                userModels.add(userModel);
            }
        }
        Collections.sort(userModels);
        return userModels;
    }
}
