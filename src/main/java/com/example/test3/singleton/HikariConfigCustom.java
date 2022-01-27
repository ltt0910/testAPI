package com.example.test3.singleton;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConfigCustom {
    private static HikariDataSource dataSource;
    private static HikariConfig config = new HikariConfig();

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/vccorp");
        config.setUsername("root");
        config.setPassword("123456");
    }

    private HikariConfigCustom(){

    }
    public static HikariDataSource getInstance() {
        if(dataSource==null){
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }

}
