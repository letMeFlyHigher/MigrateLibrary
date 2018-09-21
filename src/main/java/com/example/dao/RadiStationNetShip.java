package com.example.dao;

import com.example.util.FieldHelper;
import com.example.util.MyUUID;
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
    public int start() {
        String tableName = "TAB_OMIN_CM_CC_RADISTATIONNETSHIP";
        LOGGER.info(tableName + "开始迁库>>>>>>");
        List<Map<String,Object>> listMap = executeQuerySql();
        //由于没有该表
        if(insertToPMCISForNetShip("", listMap, new FieldHelper() {
            @Override
            public void editMapForUpdate(Map<String, Object> map) {
                String old_NetPK = (String)map.get("C_SNETSHIP_ID");
                String new_NetPK = MyUUID.getUUID36();
                String old_stationPK = (String) map.get("C_SITEOPF_ID");
                String new_stationPK = stationPKMap.get(old_stationPK);
                map.put("C_SNETSHIP_ID",new_NetPK);
                map.put("C_SITEOPF_ID",new_stationPK);
                netPKMap.put(old_NetPK,new_NetPK);
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
            LOGGER.info(cnt++ + tableName + "完成迁库============");
        }else{
            LOGGER.error(tableName + "迁库失败！！！！！！");
        }
        return 1;
    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT \n" +
                "\tTAB_OMIN_META_NETWORK.NETWORKPK AS C_SNETSHIP_ID," +
                "\tTAB_OMIN_CM_CC_STATION.C_STATION_ID AS C_SITEOPF_ID," +
                "'11' as C_SNET_ID," +
                "TAB_OMIN_META_NETWORK.NetWorkLevel as C_STATION_LEVEL,TAB_OMIN_META_NETWORK.StartTime as C_StartTime,TAB_OMIN_META_NETWORK.EndTime as C_EndTime,TAB_OMIN_META_NETWORK.TimeSystem as C_TimeSystem,TAB_OMIN_META_NETWORK.ObsvCount as C_ObsvCount,TAB_OMIN_META_NETWORK.ObsvTimes as C_ObsvTimes,TAB_OMIN_META_NETWORK.ExchangeCode as C_ExchangeCode,TAB_OMIN_META_NETWORK.ObsvMode as C_ObsvMode,TAB_OMIN_META_NETWORK.ShiftCase as C_ONDUTY\n" +
                "FROM TAB_OMIN_META_NETWORK,TAB_OMIN_CM_CC_STATION " +
                "WHERE SUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK(+),0,32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID " +
                "AND TAB_OMIN_META_NETWORK.NETWORKTYPE='11'";
        return queryMDOSForListMap(querySql);
    }
}
