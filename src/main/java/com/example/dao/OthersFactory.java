package com.example.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

@Component
public class OthersFactory   {

    private static final Logger LOGGER = LoggerFactory.getLogger(OthersFactory.class);

    @Autowired
    private OthersDao othersDao;

    public void start() {
        String insertPath = "src/main/resources/insertNew.sql";
        String queryPath = "src/main/resources/queryNew.sql";
        Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
        //获取新增表的map 包含类型，查询语句，新增语句。
        getSqlMap(insertPath,queryPath,map);
        int i = 0;
        for(Map.Entry<String,Map<String,String>> entry :map.entrySet()){
            i++;
            othersDao.start(entry);
        }
        LOGGER.info("OthersDao 迁移" + i + "个表");

    }




    /**
     * 不使用该方法
     * @return
     */

    private void getSqlMap(String insertPath,String queryPath,Map<String,Map<String,String>> map){

        int queryLineNum = 0;
        int insertLineNum = 0;
        try{
            File insertFile = new File(insertPath);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(insertFile), "UTF8"));
            File queryFile = new File(queryPath);
            BufferedReader in2 = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(queryFile),"UTF8"
                    )
            );


            String str;
            String tableName = "";
            String querySql = "";
            while ((str = in.readLine()) != null) {
                insertLineNum++;
                if(str.startsWith("INSERT")){
                    int pos2 = str.indexOf('(');
                    int pos1 = str.indexOf("TAB_OMIN_");
                    tableName = str.substring(pos1,pos2);
                    Map<String,String> tableMap = new HashMap<String,String>();
                    tableMap.put("insertSql",str);
                    map.put(tableName,tableMap);
                }else if(str.startsWith("类型")){
                    String[] strs = str.split("-") ;
                    map.get(tableName).put("type",strs[1].trim());
                }else if(str.startsWith("--")){
                    continue;
                }

            }
            //
            String whereClause = "";
            while((str = in2.readLine()) != null){
                queryLineNum ++;
                if(str.startsWith("SELECT")) {
                    querySql = str + "\r\n";
                }else if(str.startsWith("\t")){
                    querySql = querySql + str + "\r\n";
                }else if(str.startsWith("FROM")) {
                    String[] strs = str.split("\\s+");
                    if(strs[1].contains(",")){ //如果多个表关联查询获取第一个表名。表之间用‘,’分隔。
                        int position = strs[1].indexOf(',');
                        tableName = strs[1].substring(0,position);
                    }else{
                        tableName = strs[1];
                    }
                    querySql = querySql + str;
                    map.get(tableName).put("querySql",querySql)                  ;
                }else if(str.startsWith("--")){
                    continue;
                }else{
                    if(str.startsWith("WHERE")){
                        map.get(tableName).put("querySql", map.get(tableName).get("querySql") + "\r\n" + str + "\r\n");
                    }
                }
            }

            in2.close();
            in.close();
        }catch (Exception e){
            LOGGER.error(e.getMessage());
            LOGGER.error("insertNew文件走到" + insertLineNum + "行！");
            LOGGER.error("queryNew文件走到" + queryLineNum + "行!");

        }
    }
}
