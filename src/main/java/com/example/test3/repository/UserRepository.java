package com.example.test3.repository;

import com.example.test3.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {

    static final String DB_URL = "jdbc:mysql://localhost:3306/vccorp";
    static final String USER = "root";
    static final String PASS = "123456";

    @Override
    public UserEntity insertUser(UserEntity user){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            System.out.println("Thêm dữ liệu");
            String sql = "INSERT INTO User(fullname,age,address) VALUES ('"+user.getFullname()+"', "+user.getAge()+", '"+user.getAddress()+"');";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public UserEntity updateUser(UserEntity user){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            System.out.println("Sửa dữ liệu");
            String sql = "UPDATE User SET fullname = '"+user.getFullname()+"',age = "+user.getAge()+",address = '"+user.getAddress()+"' WHERE  id = "+user.getId()+";";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void deleteUser(long id) {
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {

            System.out.println("Xóa dữ liệu");
            String sql = "DELETE FROM User WHERE id = "+id+";";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {

        }
    }

    @Override
    public List<UserEntity> findAll(){
        List<UserEntity> user = new ArrayList<>();
        String QUERY = "SELECT id, fullname, age, address FROM User";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            while(rs.next()){
                UserEntity userEntity = new UserEntity();
                userEntity.setId(rs.getInt("id"));
                userEntity.setAge(rs.getInt("age"));
                userEntity.setFullname(rs.getString("fullname"));
                userEntity.setAddress(rs.getString("address"));
                user.add(userEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean exsist(long id){
        String QUERY = "SELECT id, fullname, age, address FROM User WHERE  id = "+id+"";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            if (rs.next() ) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public UserEntity findById(long id){
        String QUERY = "SELECT id, fullname, age, address FROM User WHERE  id = "+id+"";
        UserEntity userEntity = new UserEntity();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(QUERY);
            while (rs.next() ) {
                userEntity.setId(rs.getInt("id"));
                userEntity.setAge(rs.getInt("age"));
                userEntity.setFullname(rs.getString("fullname"));
                userEntity.setAddress(rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null)
                    conn.close();
                if (stmt != null)
                    stmt.close();
                if (rs != null)
                    rs.close();
            } catch (SQLException se) {
                se.getMessage();
            }
        }
        return userEntity;
    }

    @Override
    public List<UserEntity> findByName(String name) {
        List<UserEntity> user = new ArrayList<>();
        String QUERY = "SELECT id, fullname, age, address FROM User WHERE fullname like '%"+name+"%'";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            while(rs.next()){
                UserEntity userEntity = new UserEntity();
                userEntity.setId(rs.getInt("id"));
                userEntity.setAge(rs.getInt("age"));
                userEntity.setFullname(rs.getString("fullname"));
                userEntity.setAddress(rs.getString("address"));
                user.add(userEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<UserEntity> findByAddress(String address) {
        List<UserEntity> user = new ArrayList<>();
        String QUERY = "SELECT id, fullname, age, address FROM User WHERE address like '%"+address+"%'";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
        ) {
            while(rs.next()){
                UserEntity userEntity = new UserEntity();
                userEntity.setId(rs.getInt("id"));
                userEntity.setAge(rs.getInt("age"));
                userEntity.setFullname(rs.getString("fullname"));
                userEntity.setAddress(rs.getString("address"));
                user.add(userEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


}
