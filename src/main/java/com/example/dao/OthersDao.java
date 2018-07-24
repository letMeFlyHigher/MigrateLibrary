package com.example.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

@Repository
public class OthersDao extends baseDao {


    public static void main(String[] args){
        OthersDao oo = new OthersDao();
        oo.start();
    }
    public void start() {
        String insertPath = "src/main/resources/insertNew.sql";
        String queryPath = "src/main/resources/queryNew.sql";
        Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
        //获取新增表的map 包含类型，查询语句，新增语句。
        getSqlMap(insertPath,queryPath,map);

        for(Map.Entry<String,Map<String,String>> entry : map.entrySet()) {
            String tableName = entry.getKey();
            Map<String,String> tableMap= entry.getValue();

           List<Map<String,Object>> listMap = queryMDOSForListMap(tableMap.get("querySql")) ;

            List<Map<String,Object>> batchValues = new ArrayList<>(listMap.size());
            for(int i = 0; i < listMap.size(); i++){
                Map<String,Object> map2 = listMap.get(i);
                MapSqlParameterSource msps = new MapSqlParameterSource();
                Iterator<String> fieldIter = map.keySet().iterator();
                while(fieldIter.hasNext()){
                    String fieldName  = fieldIter.next();
                    if(!fieldName.endsWith("_QUERY")){
                        msps.addValue(fieldName,map.get(fieldName));
                    }
                }
                batchValues.add(msps.getValues());
            }
            int[] nums = mysqlTemplate.batchUpdate(insertSql.toString(),batchValues.toArray(new Map[listMap.size()]));
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
                   int pos1 = str.indexOf("TAB_OMIN_META");
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
