package com.example.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class GenerateInsertByNew {
    private static String getSQLResult(String pathName){

        int lineNum = 0;
        try{
            File fileDir = new File(pathName);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));

            String str;
            StringBuffer sb = new StringBuffer();
            String tableName = "";
            StringBuffer valSb = null;
            boolean flag = false;
            Map<String,String> map = new HashMap<String,String>();
            while ((str = in.readLine()) != null) {
                lineNum++;
                //                System.out.println(str);
               //
                //类型 - NUMERIC:col1,col3;    VARCHAR:col2,..,col5 .......
                //INSERT INTO TABLENAME(COL1,COL2,..,COLN) VALUES(:COL1,COL2,..COLN)
                if(str.startsWith("DROP")){ //直接跳过，弃表语句
                    sb.append("INSERT INTO ");
                }else if(str.startsWith("CREATE")){ //在此行获取表名
                    int pos1 = str.indexOf('`');
                    int pos2 = str.indexOf('`',pos1 + 1);
                    tableName = str.substring(pos1 + 1, pos2).replace("CM_CC","META");
                    sb.append(tableName).append("(");
                    valSb = new StringBuffer("VALUES(");
                }else if(str.startsWith(")")){
                    valSb.deleteCharAt(valSb.length() - 1).append(")");
                    sb.deleteCharAt(sb.length() - 1).append(")").append(" ").append(valSb);
                    sb.append("\r\n类型 - ");
                   map.forEach((k,v) -> sb.append(k).append(":").append(v).append(";\t"));
                   sb.append("\r\n\r\n");
                   map.clear();
                }else if(str.isEmpty()){
                    continue;
                }else if(str.trim().startsWith("PRIMARY")){
                    continue;
                }else if(str.startsWith("#")){
                    continue;
                }
                else{
                    //cols[1] 字段名， cols[2] 字段类型
                    String[] cols = str.split("\\s+");
                    String colName = cols[1].substring(1,cols[1].length() - 1);
                    //cols[2] VARCHAR(30)

                    //`C_CREATE_DATE` //      `C_UPDATED_DATE` //      `C_MODIFIER` //      `C_OPT_TYPE`
                    if(!",C_CREATE_DATE,C_UPDATED_DATE,C_MODIFIER,C_OPT_TYPE,".contains(colName)){
                        // select col1,col2,col3,...,coln from tablename
                        String type ="";
                        if(cols[2].contains("(")){
                            type = cols[2].substring(0,cols[2].indexOf('('));
                        }else{
                            type = cols[2];
                        }
                        if(map.containsKey(type)){
                            map.put(type,map.get(type).concat(",").concat(colName));
                        }else{
                            map.put(type,colName);
                        }
                        sb.append(colName).append(",");
                        valSb.append(":").append(colName).append(",");
                    }
                }
            }

            in.close();
            return sb.toString();
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("第" + lineNum + "行出问题了");
        }
        return "";
    }
    public static void main(String[] args){
        String pathName = "src/main/resources/sql/新增表.sql"; //冲突表0716.txt  C:\Users\99624\IdeaProjects\mdos2\MigrateLibrary\src\main\resources\冲突表0716.txt
        String SQLResult = getSQLResult(pathName);
        System.out.println(SQLResult);
        String newFilePath = "";

        File fileDir = new File("src\\main\\resources\\insertNew.sql");
        if(!fileDir.exists()){
            try {
                fileDir.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(fileDir), "UTF8"));
            out.append(SQLResult);
            out.flush();
            out.close();
            System.out.println("转换完成！");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
