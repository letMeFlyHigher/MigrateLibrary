package com.example.dao;

import com.example.util.FieldHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public abstract class baseDao {
    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    protected NamedParameterJdbcTemplate mysqlTemplate;

    @Autowired
    @Qualifier("oracleJdbcTemplate")
    protected JdbcTemplate oracleTemplate;

    protected static int cnt = 0;

    public static Map<String,String> netPKMap = new HashMap<String,String>();
    public static Map<String,String> stationPKMap = new HashMap<String,String>();

    public abstract int start();

//    protected abstract void editMapForUpdate(Map<String, Object> map);
    public abstract List<Map<String,Object>> executeQuerySql();

    public List<Map<String,Object>> queryMDOSForListMap(String querySql){

        oracleTemplate.setFetchSize(3000);
        return oracleTemplate.queryForList(querySql);
    }

    public  void clearTable(String tableName){
        String executeSql = "TRUNCATE TABLE " + tableName;
        mysqlTemplate.getJdbcOperations().execute(executeSql);
    }

    public boolean hasRowsInTable(String tableName){
       String hasSql = "SELECT COUNT(*) AS NUM FROM " + tableName;
       Map<String,Object> map = mysqlTemplate.getJdbcOperations().queryForMap(hasSql);
       if(map.size() > 0 &&(Long) map.get("NUM") > 0){
          return true;
       }else{
           return false;
       }
    }



    /**
     * 目前还没有对，违反约束条件等异常，做出处理。
     * 使用最原始的statement，然后手动管理事务。
     * @param tableName 公共元数据中对应查询结果的表名
     * @param listMap 从mdos数据库中查出来的list<Map<>> 形式的结果集。
     * @return
     */
    public  int insertToPMCISTable(String tableName, List<Map<String,Object>> listMap, FieldHelper fieldHelper){
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
            fieldHelper.editMapForUpdate(map);
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
        return 1;
    }

    /**
     *  专门针对站网的迁移到PMCIS库的代码
     * @param tableName 如果表名为""表示，不需要拆分查询结果，直接插入基本关系表中即可。
     * @param listMap
     * @param fieldHelper
     * @return
     */
    public int insertToPMCISForNetShip(String tableName, List<Map<String,Object>> listMap, FieldHelper fieldHelper){
        if(!tableName.isEmpty()){
            String queryIfExists = "select count(*) as num from " + tableName;
            Map<String,Object> numMap = mysqlTemplate.getJdbcOperations().queryForMap(queryIfExists);
            if((Long)numMap.get("num") != null && (Long)numMap.get("num") > 0){
                mysqlTemplate.getJdbcOperations().execute("TRUNCATE TABLE " + tableName);
            }
        }

        //这程序该怎么写呢。。。
        //先要看我们有什么？
        //一个不会变的是查询！我们查出来的东西，还是一样的，
        //区别是在现在查出来的东西，需要插入到两个表中，一个基本表中一个扩展表中，在这些站网中，基本表就一个，所以个这个基本表的
        //名字给定一个固定的值，
        //现在需要考虑的问题是，如何把公共的字段从listMap中摘出来
        // String baseName = "TAB_OMIN_CM_CC_BASENETSHIP";
        //肯定要做的有遍历listMap
        //基本表的语句是不会变的，先写基本表的语句
        // INSERT INTO TAB_OMIN_CM_CC_BASESTATIONNETSHIP(C_SNETSHIP_ID,C_SITEOPF_ID,C_SNET_ID,C_STATION_LEVEL,C_STARTTIME,C_ENDTIME,C_TIMESYSTEM,C_EXCHANGECODE,C_OBSVMODE,C_OBSVCOUNT,C_OBSVTIMES,C_ONDUTY)
        // VALUES(:C_SNETSHIP_ID,:C_SITEOPF_ID,:C_SNET_ID,:C_STATION_LEVEL,:C_STARTTIME,:C_ENDTIME,:C_TIMESYSTEM,:C_EXCHANGECODE,:C_OBSVMODE,:C_OBSVCOUNT,:C_OBSVTIMES,:C_ONDUTY)
        int i ;
        //othersDao insert sql statements
        //INSERT INTO TABLENAME(C_SNETSHIP_ID,...SET(LISTMAP -> MAP.KEYSET) - BASESET) VALUES (:C_SNETSHIP_ID,...SET(....));
        //坚信怎么赋值会根据SQL语句来，多余的字段不会影响sql语句的执行，在此前提下的程序,、、无情代码打破你的幻想

        String baseSql = "INSERT INTO TAB_OMIN_CM_CC_STATIONNETSHIP(C_SNETSHIP_ID,C_SITEOPF_ID,C_SNET_ID,C_STATION_LEVEL,C_STARTTIME,C_ENDTIME,C_TIMESYSTEM,C_EXCHANGECODE,C_OBSVMODE,C_OBSVCOUNT,C_OBSVTIMES,C_ONDUTY)" +
                                                                " VALUES(:C_SNETSHIP_ID,:C_SITEOPF_ID,:C_SNET_ID,:C_STATION_LEVEL,:C_STARTTIME,:C_ENDTIME,:C_TIMESYSTEM,:C_EXCHANGECODE,:C_OBSVMODE,:C_OBSVCOUNT,:C_OBSVTIMES,:C_ONDUTY)";
        List<String> baseList = Arrays.asList("C_SNETSHIP_ID","C_SITEOPF_ID","C_SNET_ID","C_STATION_LEVEL","C_STARTTIME","C_ENDTIME","C_TIMESYSTEM","C_EXCHANGECODE","C_OBSVMODE","C_OBSVCOUNT","C_OBSVTIMES","C_ONDUTY");
        List<String> otherList = new ArrayList<String>();
        Set<String> set = listMap.get(0).keySet();
        otherList.add("C_SNETSHIP_ID");
        for(String s : set){
           if(!baseList.contains(s) && !s.endsWith("QUERY")) {
                   otherList.add(s) ;
           }
        }
        String otherSql = "INSERT INTO " + tableName + "(";
        StringBuilder sb1 = new StringBuilder(otherSql);
        StringBuilder sb2 = new StringBuilder("VALUES(");
        for(String s : otherList){
            sb1.append(s).append(",");
            sb2.append(":").append(s).append(",");
        }
        otherSql = sb1.deleteCharAt(sb1.length() - 1).append(")").append(" ").append(sb2.deleteCharAt(sb2.length() - 1).append(")")).toString();
        List<Map<String,Object>> batchValues = new ArrayList<Map<String,Object>>(listMap.size());
        List<Map<String,Object>> otherBatchValues = new ArrayList<Map<String,Object>>(listMap.size());
        for(i = 0; i < listMap.size(); i++){
            Map<String,Object> map = listMap.get(i);
            fieldHelper.editMapForUpdate(map);
            MapSqlParameterSource msps = new MapSqlParameterSource();
            MapSqlParameterSource otherMsps = new MapSqlParameterSource();
            for(Map.Entry<String,Object> entry :map.entrySet()){
                String key = entry.getKey();
                Object val = entry.getValue();
                if(!key.endsWith("QUERY")){
                    if(baseList.contains(key)) {
                        msps.addValue(key,val);
                    }
                    if(otherList.contains(key)){
                       otherMsps.addValue(key, val);
                    }
                }
            }
            batchValues.add(msps.getValues());
            otherBatchValues.add(otherMsps.getValues());
        }
        //TODO 需要去测试一下，这个batchupdate方法的返回结构。
        int[] baseResult = mysqlTemplate.batchUpdate(baseSql,batchValues.toArray(new Map[listMap.size()]));
        if(!tableName.isEmpty()){
            int[] otherResult = mysqlTemplate.batchUpdate(otherSql,otherBatchValues.toArray(new Map[listMap.size()]));
        }
        return 1;
    }

    public Long countRows(String tableName){
       Map<String, Object> result = mysqlTemplate.getJdbcOperations().queryForMap("SELECT COUNT(*) as total FROM " + tableName);
       Long rs = (Long) result.get("total");
       return rs;
    }


}
