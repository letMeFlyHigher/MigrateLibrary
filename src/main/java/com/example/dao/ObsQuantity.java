package com.example.dao;

import com.example.util.MyUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class ObsQuantity extends baseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObsQuantity.class);

    @Override
    @Async
    public void start() {
        String tableName = "TAB_OMIN_CM_CC_OBSQUANTITY";
        LOGGER.info(tableName + "开始迁库>>>>>>");
        List<Map<String,Object>> listMap = executeQuerySql();
        //因为观测量，查出来之后，不只是要插入到一个表中，还有一个观测量与站网和台站关系表

        //怎么去做主键，怎么去更新关系表。

        //关系表 ： TAB_OMIN_CM_CC_OBSQSTATIONNETSHIP

        // 一个是怎么替代原来的主键，另一个是怎么把现在的主键和站网的id对应起来。

        //insert into (C_OBSQSN_ID,C_SNETSHIP_ID,C_OBSQ_ID) values('','','');

        //取要素主键的36位，即为站网主键，然后生成要素主键，并把这两个主键存到一个键值对中。

        String queryIfExists = "select count(*) as num from " + tableName;
        Map<String,Object> numMap = mysqlTemplate.getJdbcOperations().queryForMap(queryIfExists);
        if((Long)numMap.get("num") != null && (Long)numMap.get("num") > 0){
            mysqlTemplate.getJdbcOperations().execute("TRUNCATE TABLE " + tableName);
            mysqlTemplate.getJdbcOperations().execute("TRUNCATE TABLE TAB_OMIN_CM_CC_OBSQSTATIONNETSHIP");
        }
        int i = 0 ;
        Map<String,Object> fieldMap = listMap.get(i);
        Iterator<String> iter = fieldMap.keySet().iterator();
        StringBuffer insertSql = new StringBuffer("INSERT INTO " + tableName + "(");
        StringBuffer values = new StringBuffer("VALUES(");
        Object value;
        //拼写插入语句,后缀有QUERY表示，用到该值，但并不将该值直接插入。
        while(iter.hasNext()){
            String fieldName = iter.next();
            if(!fieldName.endsWith("_QUERY")){//过滤掉后缀为‘_QUERY'的字段。
                insertSql.append(fieldName).append(",");
                values.append(":").append(fieldName).append(",");
            }
        }
        insertSql.deleteCharAt(insertSql.length() - 1).append(")");
        values.deleteCharAt(values.length() - 1).append(")");
        insertSql.append(" ").append(values);
        //给插入语句的赋值。
//            System.out.println(insertSql);
        //获取准备语句对象。
        List<Map<String,Object>> netShipBatchValues = new ArrayList<>(listMap.size());

        List<Map<String,Object>> batchValues = new ArrayList<>(listMap.size());
        for(i = 0; i < listMap.size(); i++){

            MapSqlParameterSource mspsNetShip = new MapSqlParameterSource();
            Map<String,Object> map = listMap.get(i);
            String C_OBSQSN_ID = MyUUID.getUUID36();
            String obsvelmtPK = (String)map.get("C_OBSVELMTPK_QUERY");
            String C_SNETSHIP_ID = obsvelmtPK.substring(0,36);
            String C_OBSQ_ID = MyUUID.getUUID36();
            mspsNetShip.addValue("C_OBSQSN_ID",C_OBSQSN_ID)
                    .addValue("C_SNETSHIP_ID",C_SNETSHIP_ID)
                    .addValue("C_OBSQ_ID",C_OBSQ_ID);


            map.put("C_OBSQ_ID",C_OBSQ_ID);

            MapSqlParameterSource msps = new MapSqlParameterSource();
            Iterator<String> fieldIter = map.keySet().iterator();
            while(fieldIter.hasNext()){
                String fieldName  = fieldIter.next();
                if(!fieldName.endsWith("_QUERY")){
                    msps.addValue(fieldName,map.get(fieldName));
                }
            }
            batchValues.add(msps.getValues());
            netShipBatchValues.add(mspsNetShip.getValues());
        }

        int[] nums = mysqlTemplate.batchUpdate(insertSql.toString(),batchValues.toArray(new Map[listMap.size()]));
        mysqlTemplate.batchUpdate("INSERT INTO TAB_OMIN_CM_CC_OBSQSTATIONNETSHIP(C_OBSQSN_ID,C_SNETSHIP_ID,C_OBSQ_ID) VALUES(:C_OBSQSN_ID,:C_SNETSHIP_ID,:C_OBSQ_ID)",netShipBatchValues.toArray(new Map[listMap.size()]));
//        System.out.println(nums);
        LOGGER.info(tableName + "迁库完成======");

    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        //以台站为基准，观测要素名不为空。
        String querySql = "SELECT \n" +
                "\tTAB_OMIN_META_OBSVELMT.OBSVELMTPK AS C_OBSVELMTPK_QUERY,\n" +
                "\t'tempVal' AS C_OBSQ_ID,\n" +
                "\tTAB_OMIN_META_OBSVELMT.ObsvElmtName as C_OBSQ_NAME,TAB_OMIN_META_OBSVELMT.ObsvMode as C_OBS_SOURCE,TAB_OMIN_META_OBSVELMT.StartTime as C_OBSQ_STARTTIME,TAB_OMIN_META_OBSVELMT.EndTime as C_OBSQ_ENDTIME,TAB_OMIN_META_OBSVELMT.TimeSystem as C_TimeSystem,TAB_OMIN_META_OBSVELMT.ObsvTimes as C_ObsvTimes,TAB_OMIN_META_OBSVELMT.ObsvCount as C_ObsvCount,TAB_OMIN_META_OBSVELMT.ShiftCase as C_ONDUTY,TAB_OMIN_META_OBSVELMT.AQTMETHOD as C_AQTMETHOD,TAB_OMIN_META_OBSVELMT.AQTFREQUENCY as C_AQTFREQUENCY,TAB_OMIN_META_OBSVELMT.StopTime as C_StopTime,TAB_OMIN_META_OBSVELMT.ResumeTime as C_ResumeTime,TAB_OMIN_META_OBSVELMT.StopReason as C_StopReason \n" +
                "FROM TAB_OMIN_META_OBSVELMT,TAB_OMIN_META_NETWORK,TAB_OMIN_CM_CC_STATION\n" +
                "WHERE SUBSTR(TAB_OMIN_META_OBSVELMT.OBSVELMTPK,0,36) = TAB_OMIN_META_NETWORK.NETWORKPK \n" +
                " AND SUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK(+),0,32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID \n" +
                "AND TAB_OMIN_META_OBSVELMT.OBSVELMTNAME IS NOT NULL";
        return queryMDOSForListMap(querySql);
    }
}
