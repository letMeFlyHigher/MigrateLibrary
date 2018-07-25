package com.example.dao;

import com.example.util.FieldHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class baseDao {
    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    protected NamedParameterJdbcTemplate mysqlTemplate;

    @Autowired
    @Qualifier("oracleJdbcTemplate")
    protected JdbcTemplate oracleTemplate;


    public abstract void start();

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
        return 1;
//        catch(DataAccessException e){
//            e.printStackTrace();
////            clearTable("TRUNCATE TABLE TAB_OMIN_CM_CC_STATIONPLAT");
//            try {
//                conn.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            System.out.println("迁移失败 " + tableName);
//
//            return -1;
//        }

    }

//    protected abstract int getFiledNameType(String fieldName);


}
