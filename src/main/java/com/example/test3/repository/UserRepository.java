package com.example.test3.repository;

import com.example.test3.entity.UserEntity;
import com.example.test3.singleton.HikariConfigCustom;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
public class UserRepository implements IUserRepository {

    @Override
    public UserEntity insertUser(UserEntity user) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            System.out.println("Thêm dữ liệu");
            conn = HikariConfigCustom.getInstance().getConnection();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO User(fullname,age,address) VALUES (?,?,?);";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getFullname());
            stmt.setInt(2, user.getAge());
            stmt.setString(3, user.getAddress());
            stmt.executeUpdate();
            conn.commit();
            // nen tra lai default mode cho JDBC khi thuc thi xong
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return user;
    }

    @Override
    public UserEntity updateUser(UserEntity user) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = HikariConfigCustom.getInstance().getConnection();
            conn.setAutoCommit(false);
            String sql = "UPDATE User SET fullname = ?,age = ?,address =? WHERE  id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getFullname());
            stmt.setInt(2, user.getAge());
            stmt.setString(3, user.getAddress());
            stmt.setLong(4, user.getId());
            stmt.executeUpdate();
            System.out.println("Sửa dữ liệu");
            //bat commit
            conn.commit();
            //set lại auto Commit con conn
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public void deleteUser(long id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = HikariConfigCustom.getInstance().getConnection();
            conn.setAutoCommit(false);
            String sql = "DELETE FROM User WHERE id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            System.out.println("Xóa dữ liệu");
            stmt.executeUpdate();

            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<UserEntity> findAll() throws SQLException {
        List<UserEntity> user = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String QUERY = "SELECT id, fullname, age, address FROM User";
        conn = HikariConfigCustom.getInstance().getConnection();
        stmt = conn.prepareStatement(QUERY);
        rs = stmt.executeQuery();
        while (rs.next()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(rs.getInt("id"));
            userEntity.setAge(rs.getInt("age"));
            userEntity.setFullname(rs.getString("fullname"));
            userEntity.setAddress(rs.getString("address"));
            user.add(userEntity);
        }
        if (conn != null) {
            conn.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (rs != null) {
            rs.close();
        }
        return user;
    }


    @Override
    public boolean exsist(long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String QUERY = "SELECT id, fullname, age, address FROM User WHERE  id = ?";
            conn = HikariConfigCustom.getInstance().getConnection();
            stmt = conn.prepareStatement(QUERY);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public UserEntity findById(long id) throws SQLException {
        UserEntity userEntity = new UserEntity();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String QUERY = "SELECT id, fullname, age, address FROM User WHERE  id = ?";
        conn = HikariConfigCustom.getInstance().getConnection();
        stmt = conn.prepareStatement(QUERY);
        stmt.setLong(1, id);
        rs = stmt.executeQuery();
        while (rs.next()) {
            userEntity.setId(rs.getInt("id"));
            userEntity.setAge(rs.getInt("age"));
            userEntity.setFullname(rs.getString("fullname"));
            userEntity.setAddress(rs.getString("address"));
        }
        if (conn != null) {
            conn.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        if (rs != null) {
            rs.close();
        }
        return userEntity;
    }

    @Override
    public List<UserEntity> findByName(String name) throws SQLException {
        List<UserEntity> user = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String QUERY = "SELECT id, fullname, age, address FROM User WHERE  fullname like ?";
        conn = HikariConfigCustom.getInstance().getConnection();
        stmt = conn.prepareStatement(QUERY);
        stmt.setString(1, name);
        rs = stmt.executeQuery();
        while (rs.next()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(rs.getInt("id"));
            userEntity.setAge(rs.getInt("age"));
            userEntity.setFullname(rs.getString("fullname"));
            userEntity.setAddress(rs.getString("address"));
            user.add(userEntity);
        }
        if (conn != null)
            conn.close();
        if (stmt != null)
            stmt.close();
        if (rs != null)
            rs.close();

        return user;
    }

    @Override
    public List<UserEntity> findByAddress(String address) throws SQLException {
        List<UserEntity> user = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String QUERY = "SELECT id, fullname, age, address FROM User WHERE  address like ?";
        conn = HikariConfigCustom.getInstance().getConnection();
        stmt = conn.prepareStatement(QUERY);
        stmt.setString(1, address);
        rs = stmt.executeQuery();
        while (rs.next()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(rs.getInt("id"));
            userEntity.setAge(rs.getInt("age"));
            userEntity.setFullname(rs.getString("fullname"));
            userEntity.setAddress(rs.getString("address"));
            user.add(userEntity);
        }
        if (conn != null)
            conn.close();
        if (stmt != null)
            stmt.close();
        if (rs != null)
            rs.close();

        return user;
    }

    public void add5MillionRecords() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = HikariConfigCustom.getInstance().getConnection();
            conn.setAutoCommit(false);
            Random rand = new Random();
            StringBuilder insertSql = new StringBuilder("INSERT INTO user VALUES");
            for (int i = 0; i < 10 - 1; i++) {
                insertSql.append("(" + (RandomStringUtils.randomAlphabetic(15)) + "," + rand.nextInt(100) + "," + RandomStringUtils.randomAlphabetic(10) + "),");
            }
            insertSql.append("(" + (RandomStringUtils.randomAlphabetic(15)) + "," + rand.nextInt(100) + "," + RandomStringUtils.randomAlphabetic(10) + ")");
            conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
            stmt = conn.prepareStatement(insertSql.toString());
            stmt.executeUpdate();
            conn.setAutoCommit(true);
        }catch (Exception e){
            conn.rollback();
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
