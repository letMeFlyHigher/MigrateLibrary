package com.example.util;

import java.io.*;

public class GenerateSQL {
    private static String getSQLResult(String pathName){
        try{
            File fileDir = new File(pathName);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));

            String str;
            String query_table_name = "";
            String col_name = "";
            String rename = "";
            StringBuffer sb = new StringBuffer();
            String queryStr = "";
            int lineNum = 0;
            String tables = "";
            String inserTable = "";
            while ((str = in.readLine()) != null) {
                lineNum++;
                //                System.out.println(str);
                if(str.startsWith("表名")) {
                    sb.append("待插入表：" + str.substring(3,str.length()) + "\r\n");
                    continue;
                }else if(str.startsWith("序号")){
                    continue;
                }else if(str.isEmpty()){ //Str为空，表示读到了空行，也表示一个表的sql语句已经完成。
                    col_name = "";
                    query_table_name = "";
                    rename = "";
                    sb.append(queryStr).deleteCharAt(sb.length() - 4).append("\r\nFROM " + tables).deleteCharAt(sb.length()-1).append("\r\n\r\n");
                    queryStr = "";
                    tables = "";
                }else{
                    String[] cols = str.split("\\s+");
                    if(cols.length < 7){
                        continue;
                    }else if(cols.length > 7){
                        System.out.println("第" + lineNum + "行有问题！");
                        break;
                    }
                    if(query_table_name.isEmpty() && col_name.isEmpty()){
                        queryStr = "SELECT \r\n\t";
                        col_name = cols[5];
                        query_table_name = cols[6].toUpperCase();
                    }else if(!query_table_name.isEmpty() && !col_name.isEmpty()){  //列和表都不为空
                        col_name = cols[5];
                        if(!query_table_name.equals(cols[6].toUpperCase())){
                            queryStr += "\r\n\t";
                        }
                        if(tables.indexOf(cols[6].toUpperCase()) < 0){
                            tables = tables + cols[6].toUpperCase() + ",";
                        }
                        query_table_name = cols[6].toUpperCase();
                        rename = cols[2];
                    }
                    queryStr = queryStr + query_table_name + "." + col_name + " as " + rename +",";
                }
            }

            in.close();
            return sb.toString();
        }catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "";
    }
    public static void main(String[] args){
        String pathName = "src/main/resources/冲突表0716.txt"; //冲突表0716.txt  C:\Users\99624\IdeaProjects\mdos2\MigrateLibrary\src\main\resources\冲突表0716.txt
        String SQLResult = getSQLResult(pathName);
        System.out.println(SQLResult);
        String newFilePath = "";

        File fileDir = new File("src\\main\\resources\\query.sql");
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
