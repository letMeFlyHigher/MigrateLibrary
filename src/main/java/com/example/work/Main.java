package com.example.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Main {

    @Autowired
    @Qualifier("mysqlJdbcTemplate")
    private JdbcTemplate mysqlTemplate;

    @Autowired
    @Qualifier("oracleJdbcTemplate")
    private JdbcTemplate oracleTemplate;


    public boolean start(){
        List<Map<String,Object>> stationList = new ArrayList<Map<String,Object>>();
        stationList = queryStationForList();
//        clearTable("TRUNCATE TABLE TAB_OMIN_CM_CC_STATIONPLAT");
        insertIntoStation(stationList);
        return false;
    }

    public void clearTable(String sql){
       mysqlTemplate.execute(sql);
    }

    //使用最原始的statement，然后手动管理事务。
    public void insertIntoStation3(List<Map<String,Object>> stationList){
        String[] array = new String[stationList.size()];
        int i = 0 ;
        Map<String,Object> stationMap = stationList.get(i);
        Iterator<String> iter = stationMap.keySet().iterator();
        StringBuffer insertSql = new StringBuffer("INSERT INTO TAB_OMIN_CM_CC_STATIONPLAT(");
        StringBuffer values = new StringBuffer("VALUES(");
        Object value;
        //其中mdos tab_omin_cm_cc_station 表中的c_hp,c_hha 的字段类型为 NUMBER(22),c_longtitude_numb,c_latitude_numb 的字段类型为NUMBER(22,15);
        //拼写插入语句
        while(iter.hasNext()){
            String fieldName = iter.next();
            insertSql.append(fieldName).append(",");
            values.append("?").append(",");
        }
        insertSql.deleteCharAt(insertSql.length() - 1).append(")");
        values.deleteCharAt(values.length() - 1).append(")");
        insertSql.append(" ").append(values);
       //给插入语句的赋值。
        try{
            System.out.println(insertSql);
            //获取准备语句对象。
            Connection conn = mysqlTemplate.getDataSource().getConnection();
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(insertSql.toString());
            for(i = 0; i < stationList.size(); i++){
                Map<String,Object> map = stationList.get(i);
                Iterator<Map.Entry<String,Object>> itera = map.entrySet().iterator();
                int j = 0;
                while(itera.hasNext()){
                    j++;
                    Map.Entry<String,Object> en = itera.next();
                    String field = en.getKey();
                    Object val = en.getValue();
                    if(",C_HP,C_HHA,".indexOf("," + field + ",") > -1){
                        ps.setBigDecimal(j,(BigDecimal)val);
                    }else if(",C_LONGITUDE_NUMB,C_LATITUDE_NUMB,".indexOf("," + field + ",") > -1){
                        ps.setBigDecimal(j,(BigDecimal)val);
                    }else{
                        ps.setString(j,(String)val);
                    }
                }
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();

        }catch(DataAccessException e){
            e.printStackTrace();
//            clearTable("TRUNCATE TABLE TAB_OMIN_CM_CC_STATIONPLAT");
            System.out.println("迁移失败 TAB_OMIN_CM_CC_STATIONPLAT");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    //insert into tab_omin_cm_cc_stationplat(.....) values(?,?,?....);
    public void insertIntoStation2(List<Map<String,Object>> stationList){
        String[] array = new String[stationList.size()];
        int i = 0 ;
            Map<String,Object> stationMap = stationList.get(i);
            Iterator<String> iter = stationMap.keySet().iterator();
            StringBuffer insertSql = new StringBuffer("INSERT INTO TAB_OMIN_CM_CC_STATIONPLAT(");
            StringBuffer values = new StringBuffer("VALUES(");
            Object value;
            //其中mdos tab_omin_cm_cc_station 表中的c_hp,c_hha 的字段类型为 NUMBER(22),c_longtitude_numb,c_latitude_numb 的字段类型为NUMBER(22,15);
            while(iter.hasNext()){
                String fieldName = iter.next();
                insertSql.append(fieldName).append(",");
                values.append("?").append(",");
            }
            insertSql.deleteCharAt(insertSql.length() - 1).append(")");
            values.deleteCharAt(values.length() - 1).append(")");
            insertSql.append(" ").append(values);
        try{
        }catch(DataAccessException e){
            e.printStackTrace();
            clearTable("TRUNCATE TABLE TAB_OMIN_CM_CC_STATIONPLAT");
            System.out.println("迁移失败 TAB_OMIN_CM_CC_STATIONPLAT");
        }

    }

    //insert into tab_omin_cm_cc_stationplat(.....) values('','','',....);
    //缺点batchupdate 执行已调sql语句失败后，就会抛出异常不再继续执行。
    public void insertIntoStation(List<Map<String,Object>> stationList){
        String[] array = new String[stationList.size()];
        for(int i = 0; i < stationList.size(); i++){
            Map<String,Object> stationMap = stationList.get(i);
            Iterator<Map.Entry<String,Object>> iter = stationMap.entrySet().iterator();
            StringBuffer insertSql = new StringBuffer("INSERT INTO TAB_OMIN_CM_CC_STATIONPLAT(");
            StringBuffer values = new StringBuffer("VALUES(");
            Object value;
            //其中mdos tab_omin_cm_cc_station 表中的c_hp,c_hha 的字段类型为 NUMBER(22),c_longtitude_numb,c_latitude_numb 的字段类型为NUMBER(22,15);
            while(iter.hasNext()){
                Map.Entry<String,Object> entry = iter.next();
                String fieldName = entry.getKey();
                if(",C_HP,C_HHA,".indexOf("," + fieldName + ",") > -1){
                    value = (BigDecimal) entry.getValue();
                    values.append(value).append(",");
                }else if(",C_LONGITUDE_NUMB,C_LATITUDE_NUMB,".indexOf("," + fieldName + ",") > -1){
                    value = (BigDecimal)entry.getValue();
                    values.append(value).append(",");
                }else{
                    value = (String)entry.getValue();
                    if(value == null){
                        values.append(value).append(",");
                    }else{
                        values.append("'") .append(value).append("',");
                    }
                }
                insertSql.append(fieldName).append(",");

            }
            insertSql.deleteCharAt(insertSql.length() - 1).append(")");
            values.deleteCharAt(values.length() - 1).append(")");
            insertSql.append(" ").append(values);
            array[i] = insertSql.toString();
            System.out.println(array[i]);
        }
        try{
            mysqlTemplate.batchUpdate(array);
        }catch(DataAccessException e){
            e.printStackTrace();
//            clearTable("TRUNCATE TABLE TAB_OMIN_CM_CC_STATIONPLAT");
            System.out.println("迁移失败 TAB_OMIN_CM_CC_STATIONPLAT");
        }
    }
    public List<Map<String,Object>> queryStationForList(){

        String queryStation = "SELECT " +
                "  TAB_OMIN_CM_CC_STATION.C_STATION_ID as C_SITEOPF_ID,TAB_OMIN_CM_CC_STATION.C_MDLANG as C_MDLANG,TAB_OMIN_CM_CC_STATION.C_MDCHAR as C_MDCHAR,TAB_OMIN_CM_CC_STATION.C_MDDATEST as C_MDDATEST,TAB_OMIN_CM_CC_STATION.C_MDSTANNAME as C_MDSTANNAME,TAB_OMIN_CM_CC_STATION.C_MDSTANVER as C_MDSTANVER,TAB_OMIN_CM_CC_STATION.C_INDEXNBR as C_INDEXNBR,TAB_OMIN_CM_CC_STATION.C_INDEXSUBNBR as C_INDEXSUBNBR,TAB_OMIN_CM_CC_STATION.C_ARCHIVEINBR as C_ARCHIVEINBR,TAB_OMIN_CM_CC_STATION.C_STATIONNC as C_STATIONNC,TAB_OMIN_CM_CC_STATION.C_STATIONNE as C_STATIONNE,TAB_OMIN_CM_CC_STATION.C_LONGITUDE as C_LONGITUDE,TAB_OMIN_CM_CC_STATION.C_LATITUDE as C_LATITUDE,TAB_OMIN_CM_CC_STATION.C_HP as C_HP,TAB_OMIN_CM_CC_STATION.C_HHA as C_HHA,TAB_OMIN_CM_CC_STATION.C_ENVIRONMENT as C_ENVIRONMENT,TAB_OMIN_CM_CC_STATION.C_REGIONID as C_REGIONID,TAB_OMIN_CM_CC_STATION.C_COUNTRYID as C_COUNTRYID,TAB_OMIN_CM_CC_STATION.C_REGIONALHC as C_REGIONALHC,TAB_OMIN_CM_CC_STATION.C_ADMINISTRATIVEDC as C_ADMINISTRATIVEDC,TAB_OMIN_CM_CC_STATION.C_ADDRESS as C_ADDRESS,TAB_OMIN_CM_CC_STATION.C_ESTADATE as C_ESTADATE,TAB_OMIN_CM_CC_STATION.C_REPDATE as C_REPDATE,TAB_OMIN_META_REGSTATION.TRANSMITMODE as C_CMETHOD,TAB_OMIN_CM_CC_STATION.C_STATUS as C_STATUS,TAB_OMIN_CM_CC_STATION.C_RPINDNAME as C_CHARGEPERSON,TAB_OMIN_CM_CC_STATION.C_APPENDIX as C_APPENDIX," +
                "  TAB_OMIN_META_REGSTATION.ISEXAM as C_ISEXAM,TAB_OMIN_META_REGSTATION.TOWNSHIP as C_TOWNSHIP," +
                "  TAB_OMIN_CM_CC_STATION.C_ORGANIZATION as C_ORGANIZATION,TAB_OMIN_CM_CC_STATION.C_CONTINENT as C_CONTINENT,TAB_OMIN_CM_CC_STATION.C_OCEAN as C_OCEAN,TAB_OMIN_CM_CC_STATION.C_LONGITUDE_NUMB as C_LONGITUDE_NUMB,TAB_OMIN_CM_CC_STATION.C_LATITUDE_NUMB as C_LATITUDE_NUMB," +
                "  TAB_OMIN_META_REGSTATION.MANASTATION as C_MANASTATION,TAB_OMIN_META_REGSTATION.FUNDSSOURCES as C_FUNDSSOURCES,TAB_OMIN_META_REGSTATION.SPECIFICTYPE as C_SPECIFICTYPE,TAB_OMIN_META_REGSTATION.INFOFEEDBACKDEP as C_INFOFEEDBACKDEP,TAB_OMIN_META_REGSTATION.MODULEID as C_MODULEID,TAB_OMIN_META_REGSTATION.WINDSENSORHEIGHT as C_WINDSENSORHEIGHT,TAB_OMIN_META_REGSTATION.STATIONTYPE as C_STATIONTYPE,TAB_OMIN_META_REGSTATION.ISUPLOADREGHOURDATA as C_ISUPLOADREGHOURDATA,TAB_OMIN_META_REGSTATION.ISUPLOADMINUTEDATA as C_ISUPLOADMINUTEDATA,TAB_OMIN_META_REGSTATION.ISUPLOADHOURDATA as C_ISUPLOADHOURDATA,TAB_OMIN_META_REGSTATION.ISUPLOADDAYDATA as C_ISUPLOADDAYDATA,TAB_OMIN_META_REGSTATION.ISUPLOADSUNSHINEDATA as C_ISUPLOADSUNSHINEDATA,TAB_OMIN_META_REGSTATION.ISUPLOADRADIATEHOURDATA as C_ISUPLOADRADIATEHOURDATA,TAB_OMIN_META_REGSTATION.TELDATAPROCESSER as C_TELDATAPROCESSER,TAB_OMIN_META_REGSTATION.TELMANAGER as C_TELMANAGER,TAB_OMIN_META_REGSTATION.REMARKS as C_REMARKS,TAB_OMIN_META_REGSTATION.SHORT_NAME as C_SHORT_NAME,TAB_OMIN_META_REGSTATION.WIND_SENSOR_HEIGHT as C_WIND_SENSOR_HEIGHT,TAB_OMIN_META_REGSTATION.PROVINCE_SHORT as C_PROVINCE_SHORT,TAB_OMIN_META_REGSTATION.CITY_SHORT as C_CITY_SHORT,TAB_OMIN_META_REGSTATION.COUNTY_SHORT as C_COUNTY_SHORT,TAB_OMIN_META_REGSTATION.EQU_ID as C_EQU_ID,TAB_OMIN_META_REGSTATION.OBG_NUM as C_OBG_NUM,TAB_OMIN_META_REGSTATION.ISUPLOADACIDRAINDAYDATA as C_ISUPLOADACIDRAINDAYDATA,TAB_OMIN_META_REGSTATION.ISWEATHERSTATION as C_ISWEATHERSTATION,TAB_OMIN_META_REGSTATION.DEPT as C_DEPT,TAB_OMIN_META_REGSTATION.IF_MOUNTAIN as C_IF_MOUNTAIN," +
                "  TAB_OMIN_CM_CC_STATION.C_MDUPDATE as C_MDUPDATE," +
                "  TAB_OMIN_META_REGSTATION.ISTRAFFICSTATION as C_ISTRAFFICSTATION,TAB_OMIN_META_REGSTATION.ISTOURISTSTATION as C_ISTOURISTSTATION " +
                "FROM TAB_OMIN_CM_CC_STATION left outer join TAB_OMIN_META_REGSTATION on TAB_OMIN_CM_CC_STATION.C_STATION_ID = TAB_OMIN_META_REGSTATION.REGSTATIONID";
        oracleTemplate.setFetchSize(800);
        return oracleTemplate.queryForList(queryStation);
    }

}
