package com.example.test3.singleton;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConfigCustom {
    private static HikariDataSource dataSource;
    private static HikariConfigCustom hikariConfigCustom;
    private static HikariConfig config = new HikariConfig();

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/vccorp?useSSL=false");
        config.setUsername("root");
        config.setPassword("123456");
        config.setMaximumPoolSize(40);
        config.setMinimumIdle(30);
        dataSource = new HikariDataSource(config);
    }
    private HikariConfigCustom(){

    }
    public static HikariConfigCustom getInstance() {
        if(hikariConfigCustom==null){
            hikariConfigCustom = new HikariConfigCustom();
        }
        return hikariConfigCustom;
    }

    public HikariDataSource getDataSource(){
        return dataSource;
    }

}
