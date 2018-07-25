package com.example.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

@Repository
public class OthersDao extends baseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OthersDao.class);

    public void start() {
        String insertPath = "src/main/resources/insertNew.sql";
        String queryPath = "src/main/resources/queryNew.sql";
        Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
        //获取新增表的map 包含类型，查询语句，新增语句。
        getSqlMap(insertPath,queryPath,map);

        //遍历循环新增的表。
        for(Map.Entry<String,Map<String,String>> entry : map.entrySet()) {
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
            if(hasRowsInTable(tableName)){
                continue;
            }
//            clearTable(tableName);

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
                    msps.addValue(fieldName,fieldMap.get(fieldName));
//                    LOGGER.info(fieldName + "-->" + fieldMap.get(fieldName));
                }
                batchValues.add(msps.getValues());
            }
            int[] nums = mysqlTemplate.batchUpdate(insertSql,batchValues.toArray(new Map[queryMap.size()]));
            LOGGER.info(tableName + "完成迁库！");
//            for(int i = 0; i < nums.length; i++){
//                if(nums[i] != 1){
//                    LOGGER.error("第" + i + "个插入语句有问题，请核查");
//                }
//            }
//            ;
        }

       System.out.println("妈的智障！！！");
    }



    @Override
    public List<Map<String, Object>> executeQuerySql() {
        return null;
    }

    /**
     * 不使用该方法
     * @return
     */

    private void getSqlMap(String insertPath,String queryPath,Map<String,Map<String,String>> map){

        int lineNum = 0;
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
                lineNum++;
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
                }

            }
            //
            String whereClause = "";
            while((str = in2.readLine()) != null){
               if(str.startsWith("SELECT")) {
                   querySql = str + "\r\n";
               }else if(str.startsWith("\t")){
                  querySql = querySql + str + "\r\n";
               }else if(str.startsWith("FROM")) {
                  String[] strs = str.split("\\s+");
                  tableName = strs[1];
                  querySql = querySql + str;
                   map.get(tableName).put("querySql",querySql);
               }else{
                   continue;
               }
            }

            in2.close();
            in.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("第" + lineNum + "行出问题了");
        }
    }
}
