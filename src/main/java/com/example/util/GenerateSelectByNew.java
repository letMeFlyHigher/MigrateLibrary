package com.example.util;

import java.io.*;

public class GenerateSelectByNew {
    private static String getSQLResult(String pathName){
        try{
            File fileDir = new File(pathName);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(fileDir), "UTF8"));

            String str;
            StringBuffer sb = new StringBuffer();
            int lineNum = 0;
            String tableName = "";
            while ((str = in.readLine()) != null) {
                lineNum++;
                //                System.out.println(str);
                //SELECT ..,..,.. FROM TABLENAME
                //INSERT INTO TABLENAME(COL1,COL2,..,COLN) VALUES(:COL1,COL2,..COLN);
                if(str.startsWith("DROP")){ //直接跳过，弃表语句
                    sb.append("SELECT\r\n\t");
                }else if(str.startsWith("CREATE")){ //在此行获取表名
                    int pos1 = str.indexOf('`');
                    int pos2 = str.indexOf('`',pos1 + 1);
                    tableName = str.substring(pos1 + 1, pos2);
                    if(tableName.equals("TAB_OMIN_CM_CC_DOUCUMENT")){
                       tableName = tableName.replace("CM_CC","MATA") ;
                    }else{
                        tableName = tableName.replace("CM_CC","META");
                    }

                }else if(str.startsWith(")")){
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append("\r\n").append("FROM " + tableName).append("\r\n\r\n");
                }else if(str.isEmpty()){
                    continue;
                }else if(str.trim().startsWith("PRIMARY")){
                    continue;
                }else if(str.startsWith("#")){
                    continue;
                }

                else{
                    String[] cols = str.split("\\s+");
                    String colName = cols[1].substring(1,cols[1].length() - 1);
                    //`C_CREATE_DATE` //      `C_UPDATED_DATE` //      `C_MODIFIER` //      `C_OPT_TYPE`
                    if(!",C_CREATE_DATE,C_UPDATED_DATE,C_MODIFIER,C_OPT_TYPE,".contains(colName)){
                        // select col1,col2,col3,...,coln from tablename
                        sb.append(colName).append(",");
                    }
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
        String pathName = "src/main/resources/sql/新增表.sql"; //冲突表0716.txt  C:\Users\99624\IdeaProjects\mdos2\MigrateLibrary\src\main\resources\冲突表0716.txt
        String SQLResult = getSQLResult(pathName);
        System.out.println(SQLResult);
        String newFilePath = "";

        File fileDir = new File("src\\main\\resources\\queryNew.sql");
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
