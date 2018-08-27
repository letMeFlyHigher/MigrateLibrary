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
public class CawnStationNetShip extends baseDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(CawnStationNetShip.class);

    @Override
    public void start() {
        List<Map<String,Object>> listMap = executeQuerySql();
        String tableName = "TAB_OMIN_CM_CC_CAWNSTATIONNETSHIP";
        if(insertToPMCISTable(tableName, listMap, new FieldHelper() {
            @Override
            public void editMapForUpdate(Map<String, Object> map) {

            }

            @Override
            public int getFiledNameType(String fieldName) {
                if("C_OBSVMODE".contains(fieldName)){
                    return Types.NUMERIC;
                }else{
                    return Types.VARCHAR;
                }
            }
        })> 0){
           LOGGER.info(cnt++ + tableName + "开始迁库>>>>");
        }else{
            LOGGER.error(tableName + "迁库完成！");
        }

    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT \n" +
                "\tTAB_OMIN_META_NETWORK.NETWORKPK AS C_SNETSHIP_ID," +
                "\tTAB_OMIN_CM_CC_STATION.C_STATION_ID AS C_SITEOPF_ID," +
                "\t'16' as C_SNET_ID,TAB_OMIN_META_NETWORK.NetWorkLevel as C_STATION_LEVEL,TAB_OMIN_META_NETWORK.StartTime as C_StartTime,TAB_OMIN_META_NETWORK.EndTime as C_EndTime,TAB_OMIN_META_NETWORK.TimeSystem as C_TimeSystem,TAB_OMIN_META_NETWORK.ObsvCount as C_ObsvCount,TAB_OMIN_META_NETWORK.ObsvTimes as C_ObsvTimes,TAB_OMIN_META_NETWORK.ExchangeCode as C_ExchangeCode,TAB_OMIN_META_NETWORK.ObsvMode as C_ObsvMode,TAB_OMIN_META_NETWORK.ShiftCase as C_ONDUTY,\n" +
                "\tTAB_OMIN_META_NETWORK_ATMOS.have_minutedata as C_HAVE_MINUTEDATA,TAB_OMIN_META_NETWORK_ATMOS.file_format as C_FILE_FORMAT,TAB_OMIN_META_NETWORK_ATMOS.have_hourdata as C_HAVE_HOURDATA,TAB_OMIN_META_NETWORK_ATMOS.dev_type as C_DEV_TYPE,TAB_OMIN_META_NETWORK_ATMOS.observ_frequency as C_OBSERV_FREQUENCY\n" +
                "FROM TAB_OMIN_META_NETWORK,TAB_OMIN_META_NETWORK_ATMOS,TAB_OMIN_CM_CC_STATION " +
                "WHERE SUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK(+),0,32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID AND TAB_OMIN_META_NETWORK.NETWORKPK = TAB_OMIN_META_NETWORK_ATMOS.NETWORKPK(+) " +
                " AND TAB_OMIN_META_NETWORK.NETWORKTYPE='16'";
        return queryMDOSForListMap(querySql);
    }
}
