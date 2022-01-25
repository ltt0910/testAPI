package com.example.test3.singleton;

import com.zaxxer.hikari.HikariConfig;

public class HikariConfigCustom {
    private static HikariConfig instance;
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

}
