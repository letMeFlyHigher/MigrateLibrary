package com.example.dao;

import com.example.util.FieldHelper;
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
    public void start() {
        List<Map<String, Object>> listMap = executeQuerySql();
        String tableName = "TAB_OMIN_CM_CC_ARSTATIONNETSHIP";
        if(insertToPMCISTable(tableName, listMap, new FieldHelper() {
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

            }
        }) > 0){
            LOGGER.info("酸雨站网迁移成功");
        }else{
           LOGGER.error("酸雨站网迁移失败");
        }
    }

//    @Override
//    protected void editMapForUpdate(Map<String, Object> map) {
//        map.put("C_OBSVMODE",(BigDecimal)map.get("C_OBSVMODE"));
//        map.put("C_ONDUTY",(BigDecimal)map.get("C_ONDUTY"));
//    }

//    @Override
//    protected void dealDiffTable(PreparedStatement ps, String fieldName,Object val,Map<String,Object> map) throws SQLException {
//        int j = 0;
//        while(iter.hasNext()){
//            j++;
//            Map.Entry<String,Object> entry =  iter.next();
//            String fieldName = entry.getKey();
//            Object val = entry.getValue();
//            if(",C_OBSVMODE,C_ONDUTY,".indexOf(fieldName) > -1){
//                ps.setBigDecimal(j,(BigDecimal)val);
//            }else{
//                ps.setString(j,(String)val);
//            }
//        }
//    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT \n" +
                "\tTAB_OMIN_META_NETWORK.NETWORKPK as C_SNETSHIP_ID," +
                "\tSUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK,0,32) AS C_SITEOPF_ID,"+
                "'17' as C_SNET_ID," +
                "TAB_OMIN_META_NETWORK.NetWorkLevel as C_STATION_LEVEL,TAB_OMIN_META_NETWORK.StartTime as C_StartTime,TAB_OMIN_META_NETWORK.EndTime as C_EndTime,TAB_OMIN_META_NETWORK.TimeSystem as C_TimeSystem,TAB_OMIN_META_NETWORK.ShiftCase as C_ONDUTY,TAB_OMIN_META_NETWORK.ExchangeCode as C_ExchangeCode,TAB_OMIN_META_NETWORK.ObsvMode as C_ObsvMode,TAB_OMIN_META_NETWORK.ObsvCount as C_ObsvCount,TAB_OMIN_META_NETWORK.ObsvTimes as C_ObsvTimes,TAB_OMIN_META_NETWORK.AcidrainAcquMode as C_AcidrainAcquMode,TAB_OMIN_META_NETWORK.AcidrainAcquStyle as C_AcidrainAcquStyle,TAB_OMIN_META_NETWORK.HisLogDataFrom as C_HisLogDataFrom,TAB_OMIN_META_NETWORK.HisLogCompBy as C_HisLogCompBy,TAB_OMIN_META_NETWORK.HisLogAudtBy as C_HisLogAudtBy,TAB_OMIN_META_NETWORK.HISLOGCOMPDATE as C_HISLOGCOMPDATE \n" +
                "FROM TAB_OMIN_META_NETWORK " +
                "Where TAB_OMIN_META_NETWORK.NETWORKTYPE='17'";
        return queryMDOSForListMap(querySql);
    }
}
