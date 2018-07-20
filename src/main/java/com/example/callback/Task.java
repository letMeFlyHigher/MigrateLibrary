package com.example.callback;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Template-method class for callback hook execution
 */
public class Task {

    /**
     * Execute with callback
     */
//    public final int executeWith(String tableName, List<Map<String,Object>> listMap, JdbcTemplate mysqlTemplate,Callback callback){
//        String[] array = new String[listMap.size()];
//        int i = 0 ;
//        Map<String,Object> fieldMap = listMap.get(i);
//        Iterator<String> iter = fieldMap.keySet().iterator();
//        StringBuffer insertSql = new StringBuffer("INSERT INTO " + tableName + "(");
//        StringBuffer values = new StringBuffer("VALUES(");
//        Object value;
//        //其中mdos tab_omin_cm_cc_station 表中的c_hp,c_hha 的字段类型为 NUMBER(22),c_longtitude_numb,c_latitude_numb 的字段类型为NUMBER(22,15);
//        //拼写插入语句
//        while(iter.hasNext()){
//            String fieldName = iter.next();
//            insertSql.append(fieldName).append(",");
//            values.append("?").append(",");
//        }
//        insertSql.deleteCharAt(insertSql.length() - 1).append(")");
//        values.deleteCharAt(values.length() - 1).append(")");
//        insertSql.append(" ").append(values);
//        //给插入语句的赋值。
//        try{
//            System.out.println(insertSql);
//            //获取准备语句对象。
//            Connection conn = mysqlTemplate.getDataSource().getConnection();
//            conn.setAutoCommit(false);
//            PreparedStatement ps = conn.prepareStatement(insertSql.toString());
//            for(i = 0; i < listMap.size(); i++){
//                Map<String,Object> map = listMap.get(i);
//                Iterator<Map.Entry<String,Object>> itera = map.entrySet().iterator();
//                int j = 0;
//                while(itera.hasNext()){   //对查出来的字段，做遍历
//                    j++;
//                    Map.Entry<String,Object> en = itera.next();
//                    String field = en.getKey();  //字段名字
//                    Object val = en.getValue();     //字段值
//                    //回调函数，设置参数
//                    if(callback != null){
//                        callback.call(field,val);
//                    }
//                    //回调函数，设置参数
//                }
//                ps.addBatch();
//            }
//            ps.executeBatch();
//            conn.commit();
//
//            return 1;
//        }catch(DataAccessException e){
//            e.printStackTrace();
////            clearTable("TRUNCATE TABLE TAB_OMIN_CM_CC_STATIONPLAT");
//            System.out.println("迁移失败 " + tableName);
//            return -1;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return -1;
//        }
//    }

}
