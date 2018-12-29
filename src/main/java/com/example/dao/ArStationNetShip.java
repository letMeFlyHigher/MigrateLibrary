package com.example.dao;

import com.example.util.FieldHelper;
import com.example.util.MyUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

//酸雨站网的拆分与迁移
@Repository
public class ArStationNetShip extends baseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArStationNetShip.class);

    @Override
    public int start() {

        String tableName = "TAB_OMIN_CM_CC_ARSTATIONNETSHIP";
        LOGGER.info(tableName + "开始迁库>>>>>>");
        List<Map<String, Object>> listMap = executeQuerySql();
        if(insertToPMCISForNetShip(tableName, listMap, new FieldHelper() {
            @Override
            public int getFiledNameType(String fieldName) {
                if(",C_OBSVMODE,C_ONDUTY,".contains(fieldName)){
                    return Types.DECIMAL;
                }else{
                    return Types.VARCHAR;
                }
            }
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
        }) > 0){
            LOGGER.info(tableName + "完成迁库");
        }else{
           LOGGER.error( tableName + "迁移失败");
        }
        return 1;
    }



    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT \n" +
                "\tTAB_OMIN_META_NETWORK.NETWORKPK as C_SNETSHIP_ID," +
                "\tSUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK,0,32) AS C_SITEOPF_ID,"+
                "'17' as C_SNET_ID," +
                "TAB_OMIN_META_NETWORK.NetWorkLevel as C_STATION_LEVEL,TAB_OMIN_META_NETWORK.StartTime as C_StartTime,TAB_OMIN_META_NETWORK.EndTime as C_EndTime,TAB_OMIN_META_NETWORK.TimeSystem as C_TimeSystem,TAB_OMIN_META_NETWORK.ShiftCase as C_ONDUTY,TAB_OMIN_META_NETWORK.ExchangeCode as C_ExchangeCode,TAB_OMIN_META_NETWORK.ObsvMode as C_ObsvMode,TAB_OMIN_META_NETWORK.ObsvCount as C_ObsvCount,TAB_OMIN_META_NETWORK.ObsvTimes as C_ObsvTimes,TAB_OMIN_META_NETWORK.AcidrainAcquMode as C_AcidrainAcquMode,TAB_OMIN_META_NETWORK.AcidrainAcquStyle as C_AcidrainAcquStyle\n" +
                "FROM TAB_OMIN_META_NETWORK " +
                "Where TAB_OMIN_META_NETWORK.NETWORKTYPE='17'";
        return queryMDOSForListMap(querySql);
    }
}
