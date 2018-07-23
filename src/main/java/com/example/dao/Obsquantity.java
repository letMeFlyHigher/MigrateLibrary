package com.example.dao;

import com.example.util.FieldHelper;
import com.example.util.MyUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class Obsquantity extends baseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RadiStationNetShip.class);

    @Override
    public void start() {
        String tableName = "TAB_OMIN_CM_CC_OBSQUANTITY";
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
        List<Map<String,Object>> batchValues = new ArrayList<>(listMap.size());
        for(i = 0; i < listMap.size(); i++){
            Map<String,Object> map = listMap.get(i);
            String uuid = MyUUID.getUUID36();
            String obsvelmtPK = (String)map.get("C_OBSVELMTPK_QUERY");

//                int j = 0;
//                while(itera.hasNext()){   //对查出来的字段，做遍历
//                    j++;
//                    Map.Entry<String,Object> en = itera.next();
//                    String field = en.getKey();  //字段名字
//                    Object val = en.getValue();     //字段值
            //回调函数，设置参数
//                    if(callback != null){
//                        callback.call(ps,j,field,val);
//                    }
//                    dealDiffTable(ps,j,field,iter);
            //回调函数，设置参数
//                }
//            "mysql,oracle","bigDecimal",default,"string"
            MapSqlParameterSource msps = new MapSqlParameterSource();
            Iterator<String> fieldIter = map.keySet().iterator();
            while(fieldIter.hasNext()){
                String fieldName  = fieldIter.next();
                if(!fieldName.endsWith("_QUERY")){
                    msps.addValue(fieldName,map.get(fieldName), fieldHelper.getFiledNameType(fieldName));
                }
            }
            batchValues.add(msps.getValues());
        }

        int[] nums = mysqlTemplate.batchUpdate(insertSql.toString(),batchValues.toArray(new Map[listMap.size()]));
//        System.out.println(nums);

    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT \n" +
                "\tTAB_OMIN_META_OBSVELMT.OBSVELMTPK AS C_OBSVELMTPK_QUERY," +
                "\t'tempVal' AS C_OBSQ_ID " +
                "\tTAB_OMIN_META_OBSVELMT.ObsvElmtName as C_OBSQ_NAME,TAB_OMIN_META_OBSVELMT.ObsvMode as C_OBS_SOURCE,TAB_OMIN_META_OBSVELMT.StartTime as C_OBSQ_STARTTIME,TAB_OMIN_META_OBSVELMT.EndTime as C_OBSQ_ENDTIME,TAB_OMIN_META_OBSVELMT.TimeSystem as C_TimeSystem,TAB_OMIN_META_OBSVELMT.ObsvTimes as C_ObsvTimes,TAB_OMIN_META_OBSVELMT.ObsvCount as C_ObsvCount,TAB_OMIN_META_OBSVELMT.ShiftCase as C_ONDUTY,TAB_OMIN_META_OBSVELMT.AQTMETHOD as C_AQTMETHOD,TAB_OMIN_META_OBSVELMT.AQTFREQUENCY as C_AQTFREQUENCY,TAB_OMIN_META_OBSVELMT.StopTime as C_StopTime,TAB_OMIN_META_OBSVELMT.ResumeTime as C_ResumeTime,TAB_OMIN_META_OBSVELMT.StopReason as C_StopReaon \n" +
                "FROM TAB_OMIN_META_OBSVELMT,TAB_OMIN_META_NETWORK,TAB_OMIN_CM_CC_STATION\n" +
                "WHERE SUBSTR(TAB_OMIN_META_OBSVELMT.OBSVELMTPK(+),0,36) = TAB_OMIN_META_NETWORK.NETWORKPK " +
                " AND SUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK(+),0,32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID";
        return queryMDOSForListMap(querySql);
    }
}