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

            String table_name = "";
            String col_name = "";

            String col_type = "";
            String nullOrNot = "";
            String comment = "";

            String dropTemplete;
            String createTemplete;
            StringBuffer sb = new StringBuffer();
            String tableComment = "";
            String pk = "";
            int lineNum = 0;
            String primaryKey = "";
            while ((str = in.readLine()) != null) {
                lineNum++;
                //                System.out.println(str);
                if(str.startsWith("表名")) {
                    table_name = str.substring(3, str.length()).toUpperCase().trim();
                    dropTemplete = "DROP TABLE IF EXISTS `" + table_name + "`;\r\n";
                    createTemplete = "CREATE TABLE `" + table_name + "` (" + "\r\n";
                    sb.append(dropTemplete).append(createTemplete);
                }else if(str.startsWith("中文名")){
                    tableComment = str.substring(4,str.length());
                }else if(str.startsWith("主键")){
                    pk = str.substring(3,str.length());
                }else if(str.startsWith("序号")){
                    continue;
                }else if(str.isEmpty()){ //Str为空，表示读到了空行，也表示一个表的sql语句已经完成。
                    if(!pk.isEmpty()){//如果有主键
                        primaryKey = "       PRIMARY KEY (`" +pk.trim()+ "`)\n";
                    }else{  //没有主键
                        primaryKey = "";
                        sb.deleteCharAt(sb.length() - 3);
                    }
                    String end = ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='"+tableComment+"';" + "\r\n";
                    sb.append(primaryKey).append(end).append("\r\n");
                    pk = "";
                }else{
                    String[] cols = str.split("\\s+");
                    if(cols.length != 5){
                        System.out.println("ERROR: "+lineNum+"行空格数量不对.\r\n");
                        continue;
                    }
                    comment = cols[1];
                    col_name = cols[2];
                    col_type = cols[3];
                    if(col_name.equals("C_CREATE_DATE")){
                        nullOrNot = "NULL DEFAULT CURRENT_TIMESTAMP";
                    }else if(col_name.equals("C_UPDATED_DATE")){
                        nullOrNot = "NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP";
                    }else{
                        if (cols[4].toUpperCase().equals("M")) {
                            nullOrNot = "NOT NULL";
                        } else {
                            nullOrNot = "DEFAULT NULL";
                        }
                    }

                    String EachCol = "      `" + col_name + "` " + col_type + " " + nullOrNot + " COMMENT " + "'" + comment + "'," + "\r\n";
                    sb.append(EachCol.toUpperCase());
                }
            }

            primaryKey = "       PRIMARY KEY (`" +pk+"`)\r\n";
            String end = ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='"+tableComment+"';" + "\r\n";
            sb.append(primaryKey).append(end);
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
        String pathName = "out/production/castSQL/newTableCastText.txt";
        String SQLResult = getSQLResult(pathName);
        String newFilePath = "";
        File fileDir = new File("out\\production\\castSQL\\SQLResult2.sql");
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
