package com.example.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class OthersDao extends baseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OthersDao.class);

    public void start(Map.Entry<String,Map<String,String>> entry ) {

        //遍历循环新增的表。
//        for(Map.Entry<String,Map<String,String>> entry : map.entrySet()) {
            String tableName = entry.getKey();
            String querySql = entry.getValue().get("querySql");
            String insertSql = entry.getValue().get("insertSql");
            if(insertSql.contains("TAB_OMIN_MATA_DOUCUMENT")){
                insertSql = insertSql.replace("_MATA_","_CM_CC_");
            }else{
                insertSql = insertSql.replace("_META_","_CM_CC_");
            }
            if(tableName.contains("TAB_OMIN_MATA_DOUCUMENT") ){
                tableName = tableName.replace("_MATA_","_CM_CC_");
            }else{
                tableName = tableName.replace("_META_","_CM_CC_");
            }

        //准备工作
//            if(hasRowsInTable(tableName)){
//                continue;
//            }
            clearTable(tableName);
            LOGGER.info(tableName + "开始迁库");
            //查询mdos
            List<Map<String,Object>> queryMap = queryMDOSForListMap(querySql) ;
            List<Map<String,Object>> batchValues = new ArrayList<>(queryMap.size());

            for(int i = 0; i < queryMap.size(); i++){
                Map<String,Object> fieldMap = queryMap.get(i);
                MapSqlParameterSource msps = new MapSqlParameterSource();
                Iterator<String> fieldIter = fieldMap.keySet().iterator();
                while(fieldIter.hasNext()){
                    String fieldName  = fieldIter.next();
                    //使用新的主键。
                    if("OBSVCRITPK,SUMMARYPK,WEATHERDESCPK,NOTEEVENTPK,OBSTPK,POLLUTEPK,NETWORKPK,OBSVRECDPK,HISLOGFROMPK,NETWORKSOPK,SOFTPK".contains(fieldName)){
                        if(fieldName.equals("NETWORKPK")){
                            fieldMap.put(fieldName,netPKMap.get(fieldName));
                        }else{
                            String value = netPKMap.get(fieldName);
                            String pk = value.substring(0,36) + (String)((String) fieldMap.get(fieldName)).substring(36);
                            fieldMap.put(fieldName,pk);
                        }
                    }
                    msps.addValue(fieldName,fieldMap.get(fieldName));
                }
                batchValues.add(msps.getValues());
            }
            int[] nums = mysqlTemplate.batchUpdate(insertSql,batchValues.toArray(new Map[queryMap.size()]));
            LOGGER.info(cnt++ + tableName + "完成迁库！");
        }


    @Override
    public int start() {
        return 1;
    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        return null;
    }
}


