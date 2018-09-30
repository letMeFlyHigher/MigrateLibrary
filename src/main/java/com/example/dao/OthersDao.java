package com.example.dao;

import com.sun.xml.internal.ws.util.QNameMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

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
                for(Map.Entry<String,Object> en : fieldMap.entrySet()){
                   Object value =  en.getValue();
                   String key = en.getKey();

                   if("OBSVCRITPK,SUMMARYPK,WEATHERDESCPK,NOTEEVENTPK,OBSTPK,POLLUTEPK,NETWORKPK,OBSVRECDPK,HISLOGFROMPK,NETWORKSOPK,SOFTPK".contains(key)){
                       String oldNetPK = ((String)value).substring(0,36);
                        if(key.equals("NETWORKPK")){
                            value = netPKMap.get(oldNetPK);
                        }else{
                            String networkPK = netPKMap.get(oldNetPK);
                            String newPK = networkPK + ((String)value).substring(36);
                            value = newPK;
                        }
                   }else if(key.equals("C_STATIOIN_ID")){
                        String newPK = stationPKMap.get(value);
                        Assert.notNull(newPK,"未找到对应的台站主键：" + value);
                        value = newPK;
                   }
                    msps.addValue(key,value);
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


