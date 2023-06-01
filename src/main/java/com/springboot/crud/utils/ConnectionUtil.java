package com.springboot.crud.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionUtil {

    private static HikariDataSource dataSource;




    static {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://172.29.16.1:3307/employee_db");
        config.setUsername("root");
        config.setPassword("mypassword");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60_000);
        config.setMaxLifetime(10 * 60_000);
        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource(){
       return dataSource;
    }
}
