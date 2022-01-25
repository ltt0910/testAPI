package com.example.test3.controller;

import com.example.test3.model.UserModel;
import com.example.test3.response.ResponseCustom;
import com.example.test3.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<UserModel> userModels = new ArrayList<>();
        userModels = userService.findAll();
        if (userModels.size() > 0) {
            return ResponseCustom.response("Get data thành công", HttpStatus.OK, userModels, 1);
        }
        return ResponseCustom.response("Không có dữ liệu", HttpStatus.NOT_FOUND, null, 1);
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserModel user) {
        try {
            Object userModel = new UserModel();
            userModel = userService.addUser(user);
            if (userModel != null) {
                return ResponseCustom.response("Thêm thành công", HttpStatus.CREATED, userModel, 1);
            }
        } catch (Exception e) {
            return ResponseCustom.response(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null, 0);
        }
        return ResponseCustom.response("Lỗi", HttpStatus.INTERNAL_SERVER_ERROR, null, 0);
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserModel user) {
        Object userModel = new UserModel();
        userModel = userService.updateUser(user);
        if (userModel != null) {
            return ResponseCustom.response("Sửa thành công", HttpStatus.OK, userModel, 1);
        }
        return ResponseCustom.response("Lỗi", HttpStatus.INTERNAL_SERVER_ERROR, null, 0);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteBuildingById(@RequestParam long[] ids) throws Exception {
        try {
            for (long id : ids) {
                userService.deleteUser(id);
            }
            return ResponseCustom.response("Xóa thành công", HttpStatus.OK, "", 1);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseCustom.response("Lỗi", HttpStatus.MULTI_STATUS, "", 0);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id) {
        UserModel userModel = new UserModel();
        userModel = userService.findById(id);
        if (userModel != null) {
            return ResponseCustom.response("Get Data thành công", HttpStatus.OK, userModel, 1);
        }
        return ResponseCustom.response("Không có dữ liệu", HttpStatus.NOT_FOUND, null, 1);
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Object> findByAddress(@RequestParam(value = "address") String address) {
        List<UserModel> userModel = new ArrayList<>();
        userModel = userService.findByAddress(address);
        if (userModel.size() > 0) {
            return ResponseCustom.response("Get Data thành công", HttpStatus.OK, userModel, 1);
        }
        return ResponseCustom.response("Không có dữ liệu", HttpStatus.NOT_FOUND, null, 1);
    }

    @GetMapping(value = "/")
    public ResponseEntity<Object> findByName(@RequestParam(value = "name") String name) {
        List<UserModel> userModel = new ArrayList<>();
        userModel = userService.findByName(name);
        if (userModel.size() > 0) {
            return ResponseCustom.response("Get Data thành công", HttpStatus.OK, userModel, 1);
        }
        return ResponseCustom.response("Không có dữ liệu", HttpStatus.NOT_FOUND, null, 1);
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<Object> sortByName() {
        if (userService.sortByName() != null) {
            return ResponseCustom.response("Sắp xếp thành công", HttpStatus.OK, userService.sortByName(), 1);
        }
        return ResponseCustom.response("Lỗi", HttpStatus.MULTI_STATUS, null, 0);
    }
}
