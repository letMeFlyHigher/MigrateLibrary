package com.example.dao;

import com.example.util.FieldHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

//表名：自动土壤水分站
@Repository
public class AsmStationNetShip extends baseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AsmStationNetShip.class);

    @Override
    public void start() {
        String tableName = "TAB_OMIN_CM_CC_ASMSTATIONNETSHIP";
        List<Map<String,Object>> listMap = executeQuerySql();
        if(insertToPMCISTable(tableName, listMap, new FieldHelper() {
            @Override
            public void editMapForUpdate(Map<String, Object> map) {

            }

            @Override
            public int getFiledNameType(String fieldName) {
                //C_OBSVMODE,
                if("C_OBSVMODE".contains(fieldName)){
                    return Types.DECIMAL;
                }else{
                    return Types.VARCHAR;
                }
            }
        }) > 0){
            LOGGER.info("自动土壤水分站迁移成功！");
        }else{
            LOGGER.error("自动土壤水分站迁移失败！");
        }
    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT \n" +
                "\tTAB_OMIN_META_NETWORK.NETWORKPK as C_SNETSHIP_ID," +
                "\tTAB_OMIN_CM_CC_STATION.C_STATION_ID as C_SITEOPF_ID," +
                "'13' as C_SNET_ID,TAB_OMIN_META_NETWORK.NetWorkLevel as C_STATION_LEVEL,TAB_OMIN_META_NETWORK.StartTime as C_StartTime,TAB_OMIN_META_NETWORK.EndTime as C_EndTime,TAB_OMIN_META_NETWORK.TimeSystem as C_TimeSystem,TAB_OMIN_META_NETWORK.ObsvCount as C_ObsvCount,TAB_OMIN_META_NETWORK.ObsvTimes as C_ObsvTimes,TAB_OMIN_META_NETWORK.ShiftCase as C_ONDUTY,TAB_OMIN_META_NETWORK.ExchangeCode as C_ExchangeCode,TAB_OMIN_META_NETWORK.ObsvMode as C_ObsvMode,TAB_OMIN_META_NETWORK.FOODPRODUCTION as C_FOODPRODUCTION,TAB_OMIN_META_NETWORK.TestSite as C_TestSite,TAB_OMIN_META_NETWORK.Agricultural as C_Agricultural,TAB_OMIN_META_NETWORK.DepthOfSensor as C_DepthOfSensor,TAB_OMIN_META_NETWORK.tsoilInstrument as C_tsoilInstrument,\n" +
                "\tTAB_OMIN_META_REGSTATION.AUTOSTATIONMODEL as C_INSTR_MODEL,TAB_OMIN_META_REGSTATION.AUTOSTATIONMANU as C_MF,TAB_OMIN_META_REGSTATION.POWERSUPPLYMODE as C_POWER_TYPE,TAB_OMIN_META_REGSTATION.INSTALPOSITION as C_INSTALL_POS,"+
                "\tTAB_OMIN_META_NETWORK_SOIL.DISTRTYPE as C_DISTRTYPE,TAB_OMIN_META_NETWORK_SOIL.CROP as C_CROP,TAB_OMIN_META_NETWORK_SOIL.TEXTURE10 as C_TEXTURE10,TAB_OMIN_META_NETWORK_SOIL.CAPACITY10 as C_CAPACITY10,TAB_OMIN_META_NETWORK_SOIL.UNITWEIGHT10 as C_UNITWEIGHT10,TAB_OMIN_META_NETWORK_SOIL.WILTING_MOISTURE10 as C_WILTING_MOISTURE10,TAB_OMIN_META_NETWORK_SOIL.TEXTURE20 as C_TEXTURE20,TAB_OMIN_META_NETWORK_SOIL.CAPACITY20 as C_CAPACITY20,TAB_OMIN_META_NETWORK_SOIL.UNITWEIGHT20 as C_UNITWEIGHT20,TAB_OMIN_META_NETWORK_SOIL.WILTING_MOISTURE20 as C_WILTING_MOISTURE20,TAB_OMIN_META_NETWORK_SOIL.TEXTURE30 as C_TEXTURE30,TAB_OMIN_META_NETWORK_SOIL.CAPACITY30 as C_CAPACITY30,TAB_OMIN_META_NETWORK_SOIL.UNITWEIGHT30 as C_UNITWEIGHT30,TAB_OMIN_META_NETWORK_SOIL.WILTING_MOISTURE30 as C_WILTING_MOISTURE30,TAB_OMIN_META_NETWORK_SOIL.TEXTURE40 as C_TEXTURE40,TAB_OMIN_META_NETWORK_SOIL.CAPACITY40 as C_CAPACITY40,TAB_OMIN_META_NETWORK_SOIL.UNITWEIGHT40 as C_UNITWEIGHT40,TAB_OMIN_META_NETWORK_SOIL.WILTING_MOISTURE40 as C_WILTING_MOISTURE40,TAB_OMIN_META_NETWORK_SOIL.TEXTURE50 as C_TEXTURE50,TAB_OMIN_META_NETWORK_SOIL.CAPACITY50 as C_CAPACITY50,TAB_OMIN_META_NETWORK_SOIL.UNITWEIGHT50 as C_UNITWEIGHT50,TAB_OMIN_META_NETWORK_SOIL.WILTING_MOISTURE50 as C_WILTING_MOISTURE50,TAB_OMIN_META_NETWORK_SOIL.TEXTURE60 as C_TEXTURE60,TAB_OMIN_META_NETWORK_SOIL.CAPACITY60 as C_CAPACITY60,TAB_OMIN_META_NETWORK_SOIL.UNITWEIGHT60 as C_UNITWEIGHT60,TAB_OMIN_META_NETWORK_SOIL.WILTING_MOISTURE60 as C_WILTING_MOISTURE60,TAB_OMIN_META_NETWORK_SOIL.TEXTURE80 as C_TEXTURE80,TAB_OMIN_META_NETWORK_SOIL.CAPACITY80 as C_CAPACITY80,TAB_OMIN_META_NETWORK_SOIL.UNITWEIGHT80 as C_UNITWEIGHT80,TAB_OMIN_META_NETWORK_SOIL.WILTING_MOISTURE80 as C_WILTING_MOISTURE80,TAB_OMIN_META_NETWORK_SOIL.TEXTURE100 as C_TEXTURE100,TAB_OMIN_META_NETWORK_SOIL.CAPACITY100 as C_CAPACITY100,TAB_OMIN_META_NETWORK_SOIL.UNITWEIGHT100 as C_UNITWEIGHT100,TAB_OMIN_META_NETWORK_SOIL.WILTING_MOISTURE100 as C_WILTING_MOISTURE100,TAB_OMIN_META_NETWORK_SOIL.VIEWMODE10 as C_VIEWMODE10,TAB_OMIN_META_NETWORK_SOIL.CROPVARETYPE as C_CROPVARETYPE,TAB_OMIN_META_NETWORK_SOIL.RIPE as C_RIPE,TAB_OMIN_META_NETWORK_SOIL.CROPVARE as C_CROPVARE,TAB_OMIN_META_NETWORK_SOIL.CULTIVATION as C_CULTIVATION,TAB_OMIN_META_NETWORK_SOIL.VIEWMODE20 as C_VIEWMODE20,TAB_OMIN_META_NETWORK_SOIL.VIEWMODE30 as C_VIEWMODE30,TAB_OMIN_META_NETWORK_SOIL.VIEWMODE40 as C_VIEWMODE40,TAB_OMIN_META_NETWORK_SOIL.VIEWMODE50 as C_VIEWMODE50,TAB_OMIN_META_NETWORK_SOIL.VIEWMODE60 as C_VIEWMODE60,TAB_OMIN_META_NETWORK_SOIL.VIEWMODE80 as C_VIEWMODE80,TAB_OMIN_META_NETWORK_SOIL.VIEWMODE100 as C_VIEWMODE100,TAB_OMIN_META_NETWORK_SOIL.VIEWMODE70 as C_VIEWMODE70,TAB_OMIN_META_NETWORK_SOIL.VIEWMODE90 as C_VIEWMODE90,TAB_OMIN_META_NETWORK_SOIL.TEXTURE70 as C_TEXTURE70,TAB_OMIN_META_NETWORK_SOIL.CAPACITY70 as C_CAPACITY70,TAB_OMIN_META_NETWORK_SOIL.UNITWEIGHT70 as C_UNITWEIGHT70,TAB_OMIN_META_NETWORK_SOIL.WILTING_MOISTURE70 as C_WILTING_MOISTURE70,TAB_OMIN_META_NETWORK_SOIL.TEXTURE90 as C_TEXTURE90,TAB_OMIN_META_NETWORK_SOIL.CAPACITY90 as C_CAPACITY90,TAB_OMIN_META_NETWORK_SOIL.UNITWEIGHT90 as C_UNITWEIGHT90,TAB_OMIN_META_NETWORK_SOIL.WILTING_MOISTURE90 as C_WILTING_MOISTURE90 \n" +
                "FROM TAB_OMIN_META_NETWORK,TAB_OMIN_META_NETWORK_SOIL,TAB_OMIN_CM_CC_STATION,TAB_OMIN_META_REGSTATION " +
                "Where TAB_OMIN_META_NETWORK.NETWORKPK=TAB_OMIN_META_NETWORK_SOIL.NETWORKSOPK(+) \n" +
                " AND SUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK(+),0,32)=TAB_OMIN_CM_CC_STATION.C_STATION_ID  AND TAB_OMIN_CM_CC_STATION.C_STATION_ID = TAB_OMIN_META_REGSTATION.REGSTATIONID(+)" +
                " AND TAB_OMIN_META_NETWORK.NETWORKTYPE='13'";
        return queryMDOSForListMap(querySql);
    }
}