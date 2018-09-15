package com.example.dao;

import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class RecreateObservationTables extends baseDao {

    @Override
    public int start() {
        String srcPath1 = "src/main/resources/sql/新增表.sql";
        String srcPath2 = "src/main/resources/sql/冲突表.sql";
//        StringBuilder sb = readFile(srcPath1);
//        sb.append(readFile(srcPath2));
//        System.out.println(sb.toString());
        return 1;

    }

    public String mig(){
        String srcPath1 = "src/main/resources/sql/新增表.sql";
        String srcPath2 = "src/main/resources/sql/冲突表.sql";
        List<String> ctList = readFile(srcPath1);
        ctList.addAll(readFile(srcPath2));
//        ctList.forEach(item ->  item.indexOf("\r\n"));
        int i = 0;
        for (String s : ctList) {
            i++;
           int pos = s.indexOf("\r\n");
           String dropIfExists = s.substring(0,pos);
           String create = s.substring(pos+2,s.length());
           System.out.println(dropIfExists);
           System.out.println(create);
           mysqlTemplate.getJdbcOperations().execute(dropIfExists);
           mysqlTemplate.getJdbcOperations().execute(create);
        }
        System.out.println("共创建" + i +  "个表");
//        ctList.forEach(item ->mysqlTemplate.getJdbcOperations().execute(item));
        return null;
    }



    public List<String> readFile(String srcPath){
        List<String> ctList = new ArrayList<String>();
        StringBuilder sb = null;
        try{
            sb = new StringBuilder();
            File fileDir = new File(srcPath);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));
            String str;
            boolean lastEmptyFlag = false;
            while ((str = in.readLine()) != null){
                if(str.trim().startsWith("#")||str.trim().startsWith("-")){
                    continue;
                }else if(str.isEmpty()){
                   if(lastEmptyFlag == true) {
                       continue;
                   }
                   ctList.add(sb.toString());
                   sb.setLength(0);
                   lastEmptyFlag = true;
                }else{
                    sb.append(str).append("\r\n");
                    lastEmptyFlag = false;
                }
            }
            if(sb.length() > 0 && !ctList.contains(sb.toString())){
                ctList.add(sb.toString());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ctList;
    }


    @Override
    public List<Map<String, Object>> executeQuerySql() {
        return null;
    }
}
