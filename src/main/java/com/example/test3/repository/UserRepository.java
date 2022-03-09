package com.example.test3.repository;

import com.example.test3.entity.UserEntity;
import com.example.test3.model.UserModel;
import com.example.test3.singleton.HikariConfigCustom;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Transactional
@Repository
public class UserRepository implements IUserRepository {


    @Override
    public UserEntity insertUser(UserEntity user) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
            conn.setAutoCommit(false);
            String sql = "INSERT INTO User(fullname,age,address) VALUES (?,?,?);";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getFullname());
            stmt.setInt(2, user.getAge());
            stmt.setString(3, user.getAddress());
            stmt.executeUpdate();
            conn.commit();
            // nên trả lại default mode cho JDBC khi thực thi xong
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
            conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
            conn.setAutoCommit(false);
            String sql = "UPDATE User SET fullname = ?,age = ?,address =?,money = ? WHERE  id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getFullname());
            stmt.setInt(2, user.getAge());
            stmt.setString(3, user.getAddress());
            stmt.setLong(4, user.getId());
            stmt.executeUpdate();
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
            conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
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
        Connection conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT id, fullname, age, address FROM User";
        stmt = conn.prepareStatement(query);
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
            conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
            String query = "SELECT id, fullname, age, address FROM User WHERE  id = ?";
            stmt = conn.prepareStatement(query);
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
        conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
        String query = "SELECT id, fullname, age, address,money, version FROM User WHERE  id = ?";
        stmt = conn.prepareStatement(query);
        stmt.setLong(1, id);
        rs = stmt.executeQuery();
        while (rs.next()) {
            userEntity.setId(rs.getInt("id"));
            userEntity.setAge(rs.getInt("age"));
            userEntity.setFullname(rs.getString("fullname"));
            userEntity.setAddress(rs.getString("address"));
            userEntity.setMoney(rs.getLong("money"));
            userEntity.setVersion(rs.getLong("version"));
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
        conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
        String query = "SELECT id, fullname, age, address FROM User WHERE  fullname LIKE CONCAT('%',?,'%')";
        stmt = conn.prepareStatement(query);
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
    public List<UserEntity> findByAddress(String address) throws SQLException {
        List<UserEntity> user = new ArrayList<>();
        Connection conn = null;
        conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String query = "SELECT id, fullname, age, address FROM User WHERE  address LIKE CONCAT('%',?,'%')?";
        stmt = conn.prepareStatement(query);
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

    public void add5MillionRecords() throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        Random random = new Random();
        try {
            conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
            conn.setAutoCommit(false);
            String sqlInsert = "INSERT INTO user(fullname,age,address) VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsert);
            for (int i = 1; i < 5000000; i++) {
                preparedStatement.setString(1, "fullname" + i);
                preparedStatement.setInt(2, random.nextInt(100));
                preparedStatement.setString(3, "address" + i);
                preparedStatement.addBatch();
                if (i % 1000 == 0) {
                    preparedStatement.executeBatch();
                    conn.commit();
                }
            }
            conn.setAutoCommit(true);
        } catch (Exception e) {
            conn.rollback();
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
    }

    @Override
    public List<UserEntity> findByNameStartWith(String name) throws SQLException {
        List<UserEntity> userEntities = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
        String query = "SELECT id,fullname,age,address FROM user WHERE fullname LIKE CONCAT(?,'%')";
        statement = conn.prepareStatement(query);
        statement.setString(1, name);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(resultSet.getInt("id"));
            userEntity.setAge(resultSet.getInt("age"));
            userEntity.setFullname(resultSet.getString("fullname"));
            userEntity.setAddress(resultSet.getString("address"));
            userEntities.add(userEntity);
        }
        conn.setAutoCommit(true);
        if (conn != null) {
            conn.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
        return userEntities;
    }

    @Override
    public List<UserEntity> findByNameContain(String name) throws SQLException {
        List<UserEntity> userEntities = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
        String query = "SELECT id,fullname,age,address FROM user WHERE fullname LIKE CONCAT('%',?,'%')";
        statement = conn.prepareStatement(query);
        statement.setString(1, name);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(resultSet.getInt("id"));
            userEntity.setAge(resultSet.getInt("age"));
            userEntity.setFullname(resultSet.getString("fullname"));
            userEntity.setAddress(resultSet.getString("address"));
            userEntities.add(userEntity);
        }
        conn.setAutoCommit(true);
        if (conn != null) {
            conn.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
        return userEntities;
    }

    @Override
    public List<UserEntity> findByNameEqual(String name) throws SQLException {
        List<UserEntity> userEntities = new ArrayList<>();
        Connection conn = null;
        conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT id,fullname,age,address FROM user WHERE fullname LIKE ?";
        statement = conn.prepareStatement(query);
        statement.setString(1, name);
        resultSet = statement.executeQuery();
        while (resultSet.next()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(resultSet.getInt("id"));
            userEntity.setAge(resultSet.getInt("age"));
            userEntity.setFullname(resultSet.getString("fullname"));
            userEntity.setAddress(resultSet.getString("address"));
            userEntities.add(userEntity);
        }
        if (conn != null) {
            conn.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
        return userEntities;
    }

    @Override
    public long getMoney(long id) throws SQLException {
        long money = 0;
        List<UserEntity> userEntities = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
        String query = "SELECT money FROM user WHERE id = ?";
        statement = conn.prepareStatement(query);
        statement.setLong(1, id);
        resultSet = statement.executeQuery();
        if (resultSet.next()) {
            money = resultSet.getLong("money");
        }
        if (conn != null) {
            conn.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (resultSet != null) {
            resultSet.close();
        }
        return money;
    }

    //Get money of user  have id like this.
    @Override
    public long getMoneyById(long id) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        long moneyA = 0;
        String sqlGetMoneyA = "SELECT money FROM user WHERE id = ?";
        conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
        stmt = conn.prepareStatement(sqlGetMoneyA);
        stmt.setLong(1, id);
        rs = stmt.executeQuery();
        if (rs.next()) {
            moneyA = rs.getLong("money");
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
        return moneyA;
    }

    //add money of user have id like this.
    @Override
    public UserEntity addMoneyById(long id, long moneyAdd) throws SQLException {
        PreparedStatement stmt = null;
        Connection conn = null;
        try {
            conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
            conn.setAutoCommit(false);
            String sql = "UPDATE User SET money = money + ? WHERE  id = ?;";
            stmt = conn.prepareStatement(sql);
            stmt.setLong(1, moneyAdd);
            stmt.setLong(2, id);
            stmt.executeUpdate();
            //bat commit
            conn.commit();
            //set lại auto Commit con conn
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
            conn.rollback();
            return null;
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
        return findById(id);
    }

    //subtrastion money of user have id like this.
    @Override
    public List<UserEntity> tranferMoneyById(long id1, long versionA, long id2, long versionB, long money) throws SQLException, NumberFormatException {
        List<UserEntity> ressult = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection conn = null;
        ResultSet rs = null;
        try {
            UserEntity userA = findById(id1);
            UserEntity userB = findById(id2);
            long moneyNewOfUserA = userA.getMoney() - money;
            long moneyNewOfUserB = userB.getMoney() + money;
            conn = HikariConfigCustom.getInstance().getDataSource().getConnection();
            conn.setAutoCommit(false);
            String sql1 = "UPDATE User SET money = " + moneyNewOfUserA + ",version =  version + 1  WHERE  id = ? and version = ?;";
            stmt = conn.prepareStatement(sql1);
            stmt.setLong(1, id1);
            stmt.setLong(2, versionA);
            int update1 = stmt.executeUpdate();
            int update2 = 0;
            if (update1 != 0) {
                String sql2 = "UPDATE User SET money = " + moneyNewOfUserB + ",version =  version + 1  WHERE  id = ? and version = ?;";
                stmt = conn.prepareStatement(sql2);
                stmt.setLong(1, id2);
                stmt.setLong(2, versionB);
                update2 = stmt.executeUpdate();
            }
            if (update2 != 0) {
                conn.commit();
            } else {
                conn.rollback();
                return ressult;
            }
            ressult.add(findById(id1));
            ressult.add(findById(id2));
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
        return ressult;
    }

}
