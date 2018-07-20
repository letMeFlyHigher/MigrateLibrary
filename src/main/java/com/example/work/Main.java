package com.example.work;

import com.example.dao.Awsstationnetship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Main {

    @Autowired
    @Qualifier("awsstationnetship")
    private Awsstationnetship awsstationnetship;


    public boolean start(){
        awsstationnetship.start();
        return false;
    }






}
