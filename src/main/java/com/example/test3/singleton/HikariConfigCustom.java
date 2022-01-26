package com.example.test3.singleton;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConfigCustom {
    private static HikariConfig instance;
    private static HikariDataSource dataSource = new HikariDataSource(HikariConfigCustom.getInstance());
    private HikariConfigCustom(){

    }
    public static HikariConfig getInstance(){
        if(instance == null){
            instance = new HikariConfig();
            instance.setJdbcUrl("jdbc:mysql://localhost:3306/vccorp");
            instance.setUsername("root");
            instance.setPassword("123456");
        }
        return instance;
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
