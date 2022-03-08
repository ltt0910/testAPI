package com.example.test3.service;

import com.example.test3.converter.UserConverter;
import com.example.test3.entity.UserEntity;
import com.example.test3.exception.*;
import com.example.test3.global.ErrorCode;
import com.example.test3.model.UserModel;
import com.example.test3.repository.IUserRepository;
import com.example.test3.response.ResponseCustom;
import com.example.test3.utils.CheckVaildate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserConverter userConverter;

    private ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public ResponseCustom<UserModel> addUser(UserModel userModel) throws Exception {
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
        return new ResponseCustom<UserModel>(1, HttpStatus.OK.value(), user, "Thêm thành công");
    }

    @Override
    public ResponseCustom<UserModel> updateUser(UserModel userModel) throws Exception {
        if (userModel.getFullname() == null || userModel.getFullname().isEmpty() || userModel.getFullname().length() > 255) {
            throw new UserInfoException("invalid user info", ErrorCode.INVALID_USER_INFO);
        }
        if (!CheckVaildate.checkVaild(userModel.getFullname()) || !CheckVaildate.checkVaild(userModel.getAddress())) {
            throw new FieldErrorException("Field không được có kí tự đặc biệt", ErrorCode.CHARACTER_ERROR);
        }
        if (!userRepository.exsist(userModel.getId())) {
            throw new FileNotFoundException("Người dùng không tồn tại");
        }
        UserEntity user = userRepository.updateUser(userConverter.convertToEntity(userModel));
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userConverter.convertToModel(user), "Update thành công");
    }

    @Override
    public ResponseCustom<String> deleteUser(long[] ids) throws Exception {
        for (long id : ids) {
            if (!userRepository.exsist(id)) {
                throw new FileNotFoundException("Người dùng không tồn tại");
            }
            userRepository.deleteUser(id);
        }
        return new ResponseCustom<>(1, HttpStatus.OK.value(), "Xóa thành công", "Xóa thành công");
    }

    @Override
    public ResponseCustom<List<UserModel>> findAll() throws SQLException {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        for (UserEntity item : userEntities) {
            userModels.add(userConverter.convertToModel(item));
        }
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userModels, "Get data success");

    }

    @Override
    public ResponseCustom<UserModel> findById(long id) throws Exception {
        if (!userRepository.exsist(id)) {
            throw new FileNotFoundException("Người dùng không tồn tại");
        }
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userConverter.convertToModel(userRepository.findById(id)), "Tìm kiếm thành công");
    }

    @Override
    public ResponseCustom<List<UserModel>> findByName(String name) throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByName(name);
        if (!CheckVaildate.checkVaild(name)) {
            throw new FieldErrorException("Name không được chứa kí tự đặc biệt", ErrorCode.CHARACTER_ERROR);
        }
        for (UserEntity item : userEntities) {
            userModels.add(userConverter.convertToModel(item));
        }
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userModels, "Tìm kiếm thành công");
    }

    @Override
    public ResponseCustom<List<UserModel>> findByAddress(String address) throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByAddress(address);
        if (!CheckVaildate.checkVaild(address)) {
            throw new FieldErrorException(" Address không được chứa kí tự đặc biệt", ErrorCode.CHARACTER_ERROR);
        }
        for (UserEntity item : userEntities) {
            userModels.add(userConverter.convertToModel(item));
        }
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userModels, "Tìm kiếm thành công");
    }

    public ResponseCustom<List<UserModel>> sortByName() throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findAll();
        if (userEntities.size() == 0) {
            throw new FileNotFoundException("Không có đối tượng để sắp xếp");
        }
        for (UserEntity item : userEntities) {
            UserModel userModel = userConverter.convertToModel(item);
            userModels.add(userModel);
        }
        Collections.sort(userModels);
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userModels, "Sắp xếp thành công");
    }

    @Override
    public ResponseCustom<String> add5MillionRecord() throws Exception {
        userRepository.add5MillionRecords();
        return new ResponseCustom<>(1, HttpStatus.OK.value(), "Thêm thành công", "Thêm thành công");
    }

    @Override
    public ResponseCustom<List<UserModel>> findByNameStartWith(String name) throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByNameStartWith(name);
        if (!CheckVaildate.checkVaild(name)) {
            throw new FieldErrorException("Name không được chứa kí tự đặc biệt", ErrorCode.CHARACTER_ERROR);
        }
        for (UserEntity item : userEntities) {
            userModels.add(userConverter.convertToModel(item));
        }
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userModels, "Tìm kiếm thành công");
    }

    @Override
    public ResponseCustom<List<UserModel>> findByNameContain(String name) throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByNameContain(name);
        if (!CheckVaildate.checkVaild(name)) {
            throw new FieldErrorException("Name không được chứa kí tự đặc biệt", ErrorCode.CHARACTER_ERROR);
        }
        for (UserEntity item : userEntities) {
            userModels.add(userConverter.convertToModel(item));
        }
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userModels, "Tìm kiếm thành công");
    }

    @Override
    public ResponseCustom<List<UserModel>> findByNameEqual(String name) throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> userEntities = userRepository.findByNameEqual(name);
        if (!CheckVaildate.checkVaild(name)) {
            throw new FieldErrorException("Name không được chứa kí tự đặc biệt", ErrorCode.CHARACTER_ERROR);
        }
        for (UserEntity item : userEntities) {
            userModels.add(userConverter.convertToModel(item));
        }
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userModels, "Tìm kiếm thành công");
    }

    @Override
    public ResponseCustom<UserModel> updateMoneyById(long id, long money) throws Exception {
        if (!userRepository.exsist(id)) {
            throw new FileNotFoundException("Người dùng không  tồn tại");
        }
        if (money < 0) {
            throw new MoneyNotValid("Số tiền không hợp lệ", ErrorCode.MONEY_NOT_VALID);
        }
        UserEntity userEntity = userRepository.addMoneyById(id, money);
        UserModel userModel = userConverter.convertToModel(userEntity);
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userModel, "Update thành công");

    }

    @Override
    public ResponseCustom<List<UserModel>> transferById(long id1, Long versionA, long id2, Long versionB, long money) throws Exception {
        List<UserModel> userModels = new ArrayList<>();
        UserEntity userA = userRepository.findById(id1);
        UserEntity userB = userRepository.findById(id2);

        if (!userRepository.exsist(id1) || !userRepository.exsist(id2)) {
            throw new FileNotFoundException("Người dùng không  tồn tại");
        }
        if (money < 0 || userRepository.findById(id1).getMoney() < money) {
            if (money < 0) {
                throw new MoneyNotValid("Số tiền không hợp lệ", ErrorCode.MONEY_NOT_VALID);
            } else {
                throw new MoneyNotValid("Tài khoản không đủ", ErrorCode.MONEY_NOT_ENOUGH);
            }
        }
        if (versionA == null || versionB == null || versionA < userA.getVersion() || versionB < userB.getVersion()) {
            throw new OptimisticLockingExeption("Lỗi version không hợp lệ");
        }
        List<UserEntity> userEntities = userRepository.tranferMoneyById(id1, versionA, id2, versionB, money);
        for (UserEntity item : userEntities) {
            UserModel userModel = userConverter.convertToModel(item);
            userModels.add(userModel);
        }
        return new ResponseCustom<>(1, HttpStatus.OK.value(), userEntities, "Chuyển thành công");
    }

}
