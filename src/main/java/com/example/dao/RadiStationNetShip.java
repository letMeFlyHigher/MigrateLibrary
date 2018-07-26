package com.example.dao;

import com.example.util.FieldHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

@Repository
public class RadiStationNetShip extends baseDao{
    private static final Logger LOGGER = LoggerFactory.getLogger(RadiStationNetShip.class);

    @Override
    @Async
    public void start() {
        String tableName = "TAB_OMIN_CM_CC_RADISTATIONNETSHIP";
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
            LOGGER.info("辐射站网迁移成功！");
        }else{
            LOGGER.error("辐射站网迁移失败！");
        }
    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT \n" +
                "\tTAB_OMIN_META_NETWORK.NETWORKPK AS C_SNETSHIP_ID," +
                "\tTAB_OMIN_CM_CC_STATION.C_STATION_ID AS C_SITEOPF_ID," +
                "'11' as C_SNET_ID," +
                "TAB_OMIN_META_NETWORK.NetWorkLevel as C_STATION_LEVEL,TAB_OMIN_META_NETWORK.StartTime as C_StartTime,TAB_OMIN_META_NETWORK.EndTime as C_EndTime,TAB_OMIN_META_NETWORK.TimeSystem as C_TimeSystem,TAB_OMIN_META_NETWORK.ObsvCount as C_ObsvCount,TAB_OMIN_META_NETWORK.ObsvTimes as C_ObsvTimes,TAB_OMIN_META_NETWORK.ExchangeCode as C_ExchangeCode,TAB_OMIN_META_NETWORK.ObsvMode as C_ObsvMode,TAB_OMIN_META_NETWORK.ShiftCase as C_ONDUTY,TAB_OMIN_META_NETWORK.HisLogDataFrom as C_HisLogDataFrom,TAB_OMIN_META_NETWORK.HisLogCompBy as C_HisLogCompBy,TAB_OMIN_META_NETWORK.HisLogAudtBy as C_HisLogAudtBy,TAB_OMIN_META_NETWORK.HISLOGCOMPDATE as C_HISLOGCOMPDATE \n" +
                "FROM TAB_OMIN_META_NETWORK,TAB_OMIN_CM_CC_STATION " +
                "WHERE SUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK(+),0,32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID " +
                "AND TAB_OMIN_META_NETWORK.NETWORKTYPE='11'";
        return queryMDOSForListMap(querySql);
    }
}
