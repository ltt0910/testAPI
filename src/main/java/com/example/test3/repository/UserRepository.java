package com.example.test3.repository;

import com.example.test3.entity.UserEntity;
import com.example.test3.singleton.HikariConfigCustom;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements IUserRepository {

    private static HikariDataSource dataSource = new HikariDataSource(HikariConfigCustom.getInstance());
    @Override
    public UserEntity insertUser(UserEntity user) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            System.out.println("Thêm dữ liệu");
            conn = dataSource.getConnection();
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
            conn = dataSource.getConnection();
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
            conn = dataSource.getConnection();
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
    public List<UserEntity> findAll() {
        List<UserEntity> user = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String QUERY = "SELECT id, fullname, age, address FROM User";
            conn = dataSource.getConnection();
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
        return user;
    }

    @Override
    public boolean exsist(long id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String QUERY = "SELECT id, fullname, age, address FROM User WHERE  id = ?";
            conn = dataSource.getConnection();
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
    public UserEntity findById(long id) {
        UserEntity userEntity = new UserEntity();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String QUERY = "SELECT id, fullname, age, address FROM User WHERE  id = ?";
            conn = dataSource.getConnection();
            stmt = conn.prepareStatement(QUERY);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                userEntity.setId(rs.getInt("id"));
                userEntity.setAge(rs.getInt("age"));
                userEntity.setFullname(rs.getString("fullname"));
                userEntity.setAddress(rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String QUERY = "SELECT id, fullname, age, address FROM User WHERE  fullname like ?";
            conn = dataSource.getConnection();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        return user;
    }

    @Override
    public List<UserEntity> findByAddress(String address) {
        List<UserEntity> user = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String QUERY = "SELECT id, fullname, age, address FROM User WHERE  address like ?";
            conn = dataSource.getConnection();
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
        return user;
    }

}
