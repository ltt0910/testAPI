package com.example.test3.controller;

import com.example.test3.exception.FieldNoVaildException;
import com.example.test3.model.UserModel;
import com.example.test3.response.ResponseCustom;
import com.example.test3.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        try {
            List<UserModel> userModels = new ArrayList<>();
            userModels = userService.findAll();
            return ResponseCustom.response("Get data thành công", HttpStatus.OK, userModels, 1);
        } catch (Exception e) {
            if (e instanceof SQLSyntaxErrorException) {
                return ResponseCustom.response("Lỗi Truy Vấn", HttpStatus.BAD_REQUEST, null, 0);
            } else if (e instanceof SQLException) {
                return ResponseCustom.response("Lỗi CSDL", HttpStatus.BAD_REQUEST, null, 0);
            } else {
                return ResponseCustom.response("Lỗi hệ thống", HttpStatus.INTERNAL_SERVER_ERROR, null, 0);
            }
        }
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserModel user) {
        try {
            Object userModel = new UserModel();
            userModel = userService.addUser(user);
            return (userModel != null) ? ResponseCustom.response("Thêm thành công", HttpStatus.CREATED, userModel, 1)
                    : ResponseCustom.response("Đã tồn tại User", HttpStatus.FOUND, null, 0);
        } catch (Exception e) {
            if (e instanceof ValidationException) {
                return ResponseCustom.response("Các trường không hợp lệ", HttpStatus.MULTI_STATUS, null, 0);
            } else if (e instanceof SQLException || e instanceof SQLSyntaxErrorException) {
                return ResponseCustom.response("Lỗi CSDL", HttpStatus.BAD_REQUEST, null, 0);
            } else {
                return ResponseCustom.response("Lỗi", HttpStatus.INTERNAL_SERVER_ERROR, null, 0);
            }
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserModel user) {
        try {
            Object userModel = new UserModel();
            userModel = userService.updateUser(user);
            return (userModel != null) ? ResponseCustom.response("Sửa thành công", HttpStatus.OK, userModel, 1)
                    : ResponseCustom.response("User không tồn tại", HttpStatus.NOT_FOUND, null, 0);
        } catch (Exception e) {
            if (e instanceof ValidationException) {
                return ResponseCustom.response("Các trường không hợp lệ", HttpStatus.MULTI_STATUS, null, 0);
            } else if (e instanceof SQLException || e instanceof SQLSyntaxErrorException) {
                return ResponseCustom.response("Lỗi CSDL", HttpStatus.BAD_REQUEST, null, 0);
            } else {
                return ResponseCustom.response("Lỗi", HttpStatus.INTERNAL_SERVER_ERROR, null, 0);
            }
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteBuildingById(@Valid @RequestParam long[] ids) {
        try {
            for (long id : ids) {
                userService.deleteUser(id);
            }
            return ResponseCustom.response("Xóa thành công", HttpStatus.OK, "", 1);
        } catch (Exception e) {
            if (e instanceof ValidationException) {
                return ResponseCustom.response("Các trường không hợp lệ", HttpStatus.MULTI_STATUS, null, 0);
            } else if (e instanceof SQLException || e instanceof SQLSyntaxErrorException) {
                return ResponseCustom.response("Lỗi CSDL", HttpStatus.BAD_REQUEST, null, 0);
            } else {
                return ResponseCustom.response("Lỗi", HttpStatus.INTERNAL_SERVER_ERROR, null, 0);
            }
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findById(@PathVariable long id) {
        try {
            UserModel userModel = new UserModel();
            userModel = userService.findById(id);
            return (userModel != null) ? ResponseCustom.response("Get Data thành công", HttpStatus.OK, userModel, 1)
                    : ResponseCustom.response("Không có dữ liệu", HttpStatus.NOT_FOUND, null, 1);
        } catch (Exception e) {
            if (e instanceof SQLSyntaxErrorException) {
                return ResponseCustom.response("Lỗi Truy Vấn", HttpStatus.BAD_REQUEST, null, 0);
            } else if (e instanceof SQLException) {
                return ResponseCustom.response("Lỗi CSDL", HttpStatus.BAD_REQUEST, null, 0);
            } else {
                return ResponseCustom.response("Lỗi hệ thống", HttpStatus.INTERNAL_SERVER_ERROR, null, 0);
            }
        }
    }

    @GetMapping(value = "/user")
    public ResponseEntity<Object> findByAddress(@RequestParam(value = "address") String address) {
        try {
            List<UserModel> userModel = new ArrayList<>();
            userModel = userService.findByAddress(address);
            FieldNoVaildException.validate(address);
            return (userModel.size() > 0) ? ResponseCustom.response("Get Data thành công", HttpStatus.OK, userModel, 1)
                    : ResponseCustom.response("Không có dữ liệu", HttpStatus.NOT_FOUND, null, 1);
        } catch (Exception e) {
            if (e instanceof SQLSyntaxErrorException) {
                return ResponseCustom.response("Lỗi Truy Vấn", HttpStatus.BAD_REQUEST, null, 0);
            } else if (e instanceof SQLException) {
                return ResponseCustom.response("Lỗi CSDL", HttpStatus.BAD_REQUEST, null, 0);
            } else if (e instanceof FieldNoVaildException) {
                return ResponseCustom.response("Dữ liệu vừa nhập không hợp lệ", HttpStatus.BAD_REQUEST, null, 0);
            } else {
                return ResponseCustom.response("Lỗi hệ thống", HttpStatus.INTERNAL_SERVER_ERROR, null, 0);
            }
        }

    }

    @GetMapping(value = "/")
    public ResponseEntity<Object> findByName(@RequestParam(value = "name") String name){
        try {
            FieldNoVaildException.validate(name);
            List<UserModel> userModel = new ArrayList<>();
            userModel = userService.findByName(name);
            return (userModel.size() > 0) ? ResponseCustom.response("Get Data thành công", HttpStatus.OK, userModel, 1)
                    : ResponseCustom.response("Không có dữ liệu", HttpStatus.NOT_FOUND, null, 1);
        } catch (Exception e) {
            if (e instanceof SQLSyntaxErrorException) {
                return ResponseCustom.response("Lỗi Truy Vấn", HttpStatus.BAD_REQUEST, null, 0);
            } else if (e instanceof SQLException) {
                return ResponseCustom.response("Lỗi CSDL", HttpStatus.BAD_REQUEST, null, 0);
            }else if (e instanceof FieldNoVaildException) {
                return ResponseCustom.response("Dữ liệu vừa nhập không hợp lệ", HttpStatus.BAD_REQUEST, null, 0);
            }
            else {
                return ResponseCustom.response("Lỗi hệ thống", HttpStatus.INTERNAL_SERVER_ERROR, null, 0);
            }
        }
    }

    @GetMapping(value = "/sort")
    public ResponseEntity<Object> sortByName() throws SQLException {
        return (userService.sortByName() != null) ? ResponseCustom.response("Sắp xếp thành công", HttpStatus.OK, userService.sortByName(), 1)
                : ResponseCustom.response("Lỗi", HttpStatus.MULTI_STATUS, null, 0);
    }
}
