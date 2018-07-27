package com.example.dao;


import com.example.util.FieldHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class Stationplat extends baseDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(Stationplat.class);

    @Override
    @Async
    public void start(){
        List<Map<String,Object>> stationList = new ArrayList<Map<String,Object>>();
        stationList = executeQuerySql();
//        clearTable("TRUNCATE TABLE TAB_OMIN_CM_CC_STATIONPLAT");
        String tableName = "TAB_OMIN_CM_CC_STATIONPLAT";

        LOGGER.info(tableName + "开始迁库>>>>");
        if(insertToPMCISTable(tableName, stationList, new FieldHelper() {
            @Override
            public int getFiledNameType(String fieldName) {
                if(",C_HP,C_HHA,C_LONGITUDE_NUMB,C_LATITUDE_NUMB".indexOf(fieldName) > -1){
                    return Types.DECIMAL;
                }else{
                    return Types.VARCHAR;
                }
            }

            @Override
            public void editMapForUpdate(Map<String, Object> map) {

            }
        }) > 0){
            LOGGER.info(cnt++ + tableName + "完成迁库");
        }else{
            LOGGER.error(tableName + "迁库失败");
        }
    }

//    @Override
//    protected void editMapForUpdate(Map<String, Object> map) {
//        map.put("C_HP",(BigDecimal)map.get("C_HP"));
//        map.put("C_HHA",(BigDecimal)map.get("C_HHA"));
//        map.put("C_LONGITUDE_NUMB",(BigDecimal)map.get("C_LONGITUDE_NUMB"));
//        map.put("C_LATITUDE_NUMB",(BigDecimal)map.get("C_LATITUDE_NUMB"));
//    }

//    @Override
//    protected void dealDiffTable(PreparedStatement ps, String fieldName,Object val,Map<String,Object> map) throws SQLException {
//        map.
//        int j = 0;
//        while(iter.hasNext()){
//            j++;
//            Map.Entry<String,Object> entry = iter.next();
//            //站点平台表
//        if(",C_HP,C_HHA,".indexOf("," + fieldName + ",") > -1){
//            ps.setBigDecimal(j,(BigDecimal)val);
//        }else if(",C_LONGITUDE_NUMB,C_LATITUDE_NUMB,".indexOf("," + fieldName + ",") > -1){
//            ps.setBigDecimal(j,(BigDecimal)val);
//        }else{
//            ps.setString(j,(String)val);
//        }
//        }
//    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String queryStation = "SELECT " +
                "  TAB_OMIN_CM_CC_STATION.C_STATION_ID as C_SITEOPF_ID,TAB_OMIN_CM_CC_STATION.C_MDLANG as C_MDLANG,TAB_OMIN_CM_CC_STATION.C_MDCHAR as C_MDCHAR,TAB_OMIN_CM_CC_STATION.C_MDDATEST as C_MDDATEST,TAB_OMIN_CM_CC_STATION.C_MDSTANNAME as C_MDSTANNAME,TAB_OMIN_CM_CC_STATION.C_MDSTANVER as C_MDSTANVER,TAB_OMIN_CM_CC_STATION.C_INDEXNBR as C_INDEXNBR,TAB_OMIN_CM_CC_STATION.C_INDEXSUBNBR as C_INDEXSUBNBR,TAB_OMIN_CM_CC_STATION.C_ARCHIVEINBR as C_ARCHIVEINBR,TAB_OMIN_CM_CC_STATION.C_STATIONNC as C_STATIONNC,TAB_OMIN_CM_CC_STATION.C_STATIONNE as C_STATIONNE,TAB_OMIN_CM_CC_STATION.C_LONGITUDE as C_LONGITUDE,TAB_OMIN_CM_CC_STATION.C_LATITUDE as C_LATITUDE,TAB_OMIN_CM_CC_STATION.C_HP as C_HP,TAB_OMIN_CM_CC_STATION.C_HHA as C_HHA,TAB_OMIN_CM_CC_STATION.C_ENVIRONMENT as C_ENVIRONMENT,TAB_OMIN_CM_CC_STATION.C_REGIONID as C_REGIONID,TAB_OMIN_CM_CC_STATION.C_COUNTRYID as C_COUNTRYID,TAB_OMIN_CM_CC_STATION.C_REGIONALHC as C_REGIONALHC,TAB_OMIN_CM_CC_STATION.C_ADMINISTRATIVEDC as C_ADMINISTRATIVEDC,TAB_OMIN_CM_CC_STATION.C_ADDRESS as C_ADDRESS,TAB_OMIN_CM_CC_STATION.C_ESTADATE as C_ESTADATE,TAB_OMIN_CM_CC_STATION.C_REPDATE as C_REPDATE,TAB_OMIN_META_REGSTATION.TRANSMITMODE as C_CMETHOD,TAB_OMIN_CM_CC_STATION.C_STATUS as C_STATUS,TAB_OMIN_CM_CC_STATION.C_RPINDNAME as C_CHARGEPERSON,TAB_OMIN_CM_CC_STATION.C_APPENDIX as C_APPENDIX," +
                "  TAB_OMIN_META_REGSTATION.ISEXAM as C_ISEXAM,TAB_OMIN_META_REGSTATION.TOWNSHIP as C_TOWNSHIP," +
                "  TAB_OMIN_CM_CC_STATION.C_ORGANIZATION as C_ORGANIZATION,TAB_OMIN_CM_CC_STATION.C_CONTINENT as C_CONTINENT,TAB_OMIN_CM_CC_STATION.C_OCEAN as C_OCEAN,TAB_OMIN_CM_CC_STATION.C_LONGITUDE_NUMB as C_LONGITUDE_NUMB,TAB_OMIN_CM_CC_STATION.C_LATITUDE_NUMB as C_LATITUDE_NUMB," +
                "  TAB_OMIN_META_REGSTATION.MANASTATION as C_MANASTATION,TAB_OMIN_META_REGSTATION.FUNDSSOURCES as C_FUNDSSOURCES,TAB_OMIN_META_REGSTATION.SPECIFICTYPE as C_SPECIFICTYPE,TAB_OMIN_META_REGSTATION.INFOFEEDBACKDEP as C_INFOFEEDBACKDEP,TAB_OMIN_META_REGSTATION.MODULEID as C_MODULEID,TAB_OMIN_META_REGSTATION.WINDSENSORHEIGHT as C_WINDSENSORHEIGHT,TAB_OMIN_META_REGSTATION.STATIONTYPE as C_STATIONTYPE,TAB_OMIN_META_REGSTATION.ISUPLOADREGHOURDATA as C_ISUPLOADREGHOURDATA,TAB_OMIN_META_REGSTATION.ISUPLOADMINUTEDATA as C_ISUPLOADMINUTEDATA,TAB_OMIN_META_REGSTATION.ISUPLOADHOURDATA as C_ISUPLOADHOURDATA,TAB_OMIN_META_REGSTATION.ISUPLOADDAYDATA as C_ISUPLOADDAYDATA,TAB_OMIN_META_REGSTATION.ISUPLOADSUNSHINEDATA as C_ISUPLOADSUNSHINEDATA,TAB_OMIN_META_REGSTATION.ISUPLOADRADIATEHOURDATA as C_ISUPLOADRADIATEHOURDATA,TAB_OMIN_META_REGSTATION.TELDATAPROCESSER as C_TELDATAPROCESSER,TAB_OMIN_META_REGSTATION.TELMANAGER as C_TELMANAGER,TAB_OMIN_META_REGSTATION.REMARKS as C_REMARKS,TAB_OMIN_META_REGSTATION.SHORT_NAME as C_SHORT_NAME,TAB_OMIN_META_REGSTATION.WIND_SENSOR_HEIGHT as C_WIND_SENSOR_HEIGHT,TAB_OMIN_META_REGSTATION.PROVINCE_SHORT as C_PROVINCE_SHORT,TAB_OMIN_META_REGSTATION.CITY_SHORT as C_CITY_SHORT,TAB_OMIN_META_REGSTATION.COUNTY_SHORT as C_COUNTY_SHORT,TAB_OMIN_META_REGSTATION.EQU_ID as C_EQU_ID,TAB_OMIN_META_REGSTATION.OBG_NUM as C_OBG_NUM,TAB_OMIN_META_REGSTATION.ISUPLOADACIDRAINDAYDATA as C_ISUPLOADACIDRAINDAYDATA,TAB_OMIN_META_REGSTATION.ISWEATHERSTATION as C_ISWEATHERSTATION,TAB_OMIN_META_REGSTATION.DEPT as C_DEPT,TAB_OMIN_META_REGSTATION.IF_MOUNTAIN as C_IF_MOUNTAIN," +
                "  TAB_OMIN_CM_CC_STATION.C_MDUPDATE as C_MDUPDATE," +
                "  TAB_OMIN_META_REGSTATION.ISTRAFFICSTATION as C_ISTRAFFICSTATION,TAB_OMIN_META_REGSTATION.ISTOURISTSTATION as C_ISTOURISTSTATION " +
                "FROM TAB_OMIN_CM_CC_STATION left outer join TAB_OMIN_META_REGSTATION on TAB_OMIN_CM_CC_STATION.C_STATION_ID = TAB_OMIN_META_REGSTATION.REGSTATIONID " +
                "WHERE TAB_OMIN_CM_CC_STATION.C_INDEXNBR IS NOT NULL";  //过滤掉台站号为空的记录
        return queryMDOSForListMap(queryStation);
    }

}
