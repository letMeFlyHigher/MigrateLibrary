package com.example.dao;

import com.example.callback.Callback;
import com.example.callback.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class baseDao extends Task {
    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    protected NamedParameterJdbcTemplate mysqlTemplate;

    @Autowired
    @Qualifier("oracleJdbcTemplate")
    protected JdbcTemplate oracleTemplate;

    public void clearTable(String sql){
        mysqlTemplate.execute(sql);
    }

    public abstract boolean start();
    /**
     * 用来处理不同表下的字段类型
     * @param ps  准备语句
     * @throws SQLException
     */
    protected abstract void dealDiffTable(PreparedStatement ps, String fieldName, Object val, Map<String, Object> fieldMap) throws SQLException;

    public abstract List<Map<String,Object>> executeQuerySql();

    public List<Map<String,Object>> queryMDOSForListMap(String querySql){
        oracleTemplate.setFetchSize(800);
        return oracleTemplate.queryForList(querySql);
    }


    /**
     * 目前还没有对，违反约束条件等异常，做出处理。
     * 使用最原始的statement，然后手动管理事务。
     * @param tableName 公共元数据中对应查询结果的表名
     * @param listMap 从mdos数据库中查出来的list<Map<>> 形式的结果集。
     * @return
     */
    public  int insertToPMCISTable(String tableName,List<Map<String,Object>> listMap){
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
                values.append("?").append(",");
            }
        }
        insertSql.deleteCharAt(insertSql.length() - 1).append(")");
        values.deleteCharAt(values.length() - 1).append(")");
        insertSql.append(" ").append(values);
        //给插入语句的赋值。
        Connection conn =null;
        try{
//            System.out.println(insertSql);
            //获取准备语句对象。
            Map<String,?>[] objs = (Map<String, ?>[]) listMap.toArray();
            mysqlTemplate.batchUpdate(insertSql.toString(),objs);
            for(i = 0; i < listMap.size(); i++){
                Map<String,Object> map = listMap.get(i);
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
            }
            return 1;
        }
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
        catch (SQLException e) {
            e.printStackTrace();
//            clearTable("TRUNCATE TABLE TAB_OMIN_CM_CC_STATIONPLAT");
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            System.out.println("迁移失败 " + tableName);

            e.printStackTrace();
            return -1;
        }

    }

}
