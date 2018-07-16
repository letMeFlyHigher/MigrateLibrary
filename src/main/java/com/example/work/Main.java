package com.example.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

public class Main {

    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlTemplate;

    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate oracleTemplate;


    public boolean start(){

        oracleTemplate.queryForList("");
        return false;
    }

}
