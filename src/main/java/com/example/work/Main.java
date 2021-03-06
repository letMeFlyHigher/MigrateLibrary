package com.example.work;

import com.example.dao.ArStationNetShip;
import com.example.dao.Awsstationnetship;
import com.example.dao.UparStationNetShip;
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

    @Autowired
    private UparStationNetShip uparStationNetShip;

    @Autowired
    private ArStationNetShip arStationNetShip;


    public boolean start(){
//        Long starttime = System.currentTimeMillis();
//        awsstationnetship.start();
        long endTime = System.currentTimeMillis();
//        System.out.println("迁移地面站网表用时" + (endTime - starttime)/1000 + "s");

        uparStationNetShip.start();
        long dealUpar = System.currentTimeMillis() ;
        System.out.println("迁移高空站网表用时" + (dealUpar - endTime)/1000 + "s");

        arStationNetShip.start();
        long dealAr = System.currentTimeMillis() ;
        System.out.println("迁移酸雨站网表用时" + (dealAr - dealUpar)/1000 + "s");
        return false;
    }






}
