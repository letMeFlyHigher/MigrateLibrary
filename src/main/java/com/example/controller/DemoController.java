package com.example.controller;

import com.example.work.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DemoController {
//    @Autowired
//    @Qualifier("oracleJdbcTemplate")
//    private JdbcTemplate oracleTemplate;
//
//    @Autowired
//    @Qualifier("mysqlJdbcTemplate")
//    private JdbcTemplate mysqlTemplate;
    @Autowired
    private Main main;

    /**
     * 迁移mdos元数据
     * @return
     */
    @RequestMapping("/startMigrate")
    public String getStarted(){
        main.start();
        return null;
    }

    /**
     * 重新创建观测元数据的表结构
     * @return
     */
    @RequestMapping("/RecreateObservation")
    public String createObs(){
        return main.migrate();
    }

    @RequestMapping("/clear")
    public String clear(){

        return null;
    }

//    @RequestMapping("/getArea1")
//    public String getUser1(){
//        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        String query = "select * from tab_omin_cm_cc_station where c_indexnbr < 'A'";
//        list = oracleTemplate.queryForList(query);
//        return "Oracle AreaData: " + list.toString();
//    }

//    @RequestMapping("/getArea2")
//    public String getArea2(){
//        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
//        String query = "select * from tab_omin_cm_cc_area";
//        list = mysqlTemplate.queryForList(query);
//        mysqlTemplate.batchUpdate("");
//
//        return "mysql AreaData: " + list.toString();
//    }

}
