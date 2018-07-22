package com.example.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//高空气球探空仪探测
@Repository
public class UparStationNetShip extends baseDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(UparStationNetShip.class);

    @Override
    public boolean start() {
        String tableName = "tab_omin_cm_cc_uparstationnetship".toUpperCase();
        List<Map<String,Object>> listMap = executeQuerySql();
        if(insertToPMCISTable(tableName,listMap) > 0){
            LOGGER.info("高空站网迁移成功");
        }else{
            LOGGER.error("高空站网迁移失败");
        }
        return false;
    }

    @Override
    protected void dealDiffTable(PreparedStatement ps, String fieldName,Object val,Map<String,Object> map) throws SQLException {
        //高空站网的字段处理方式
        // ObsvMode,SondeObsvCount,AnemoObsvCount
        int j = 0;
        while(iter.hasNext()){
            j++;
            Map.Entry<String,Object> entry = iter.next();
            String fieldName = entry.getKey();
            Object val = entry.getValue();
            if(",C_OBSVMODE,C_SONDEOBSVCOUNT,C_ANEMOOBSVCOUNT,".indexOf(fieldName) > -1){
                ps.setBigDecimal(j, (BigDecimal)val);
            }else{
                ps.setString(j,(String)val);
            }
        }
    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT\n" +
                "  TAB_OMIN_META_NETWORK.NETWORKPK as C_SNETSHIP_ID,\n" +
                "  SUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK,0,32) as C_SITEOPF_ID,\n" +
                "  '04' as C_SNET_ID,\n" +
                "  TAB_OMIN_META_NETWORK.NetWorkLevel as C_STATION_LEVEL,TAB_OMIN_META_NETWORK.ISGCOS as C_ISGCOS,TAB_OMIN_META_NETWORK.StartTime as C_StartTime,TAB_OMIN_META_NETWORK.EndTime as C_EndTime,TAB_OMIN_META_NETWORK.TimeSystem as C_TimeSystem,TAB_OMIN_META_NETWORK.ExchangeCode as C_ExchangeCode,TAB_OMIN_META_NETWORK.ObsvMode as C_ObsvMode,TAB_OMIN_META_NETWORK.AnemoStartTime as C_AnemoStartTime,TAB_OMIN_META_NETWORK.AnemoEndTime as C_AnemoEndTime,TAB_OMIN_META_NETWORK.AnemoObsvTime as C_AnemoObsvTime,TAB_OMIN_META_NETWORK.AnemoObsvCount as C_AnemoObsvCount,TAB_OMIN_META_NETWORK.ANE_ALT as C_ANE_ALT,TAB_OMIN_META_NETWORK.SondeStartTime as C_SondeStartTime,TAB_OMIN_META_NETWORK.SondeEndTime as C_SondeEndTime,TAB_OMIN_META_NETWORK.SondeObsvTime as C_SondeObsvTime,TAB_OMIN_META_NETWORK.SondeObsvCount as C_SondeObsvCount,TAB_OMIN_META_NETWORK.Sound_Alt as C_Sound_Alt,TAB_OMIN_META_NETWORK.HisLogDataFrom as C_HisLogDataFrom,TAB_OMIN_META_NETWORK.HisLogCompBy as C_HisLogCompBy,TAB_OMIN_META_NETWORK.HisLogAudtBy as C_HisLogAudtBy,TAB_OMIN_META_NETWORK.HISLOGCOMPDATE as C_HISLOGCOMPDATE\n" +
                "FROM TAB_OMIN_META_NETWORK\n" +
                "WHERE TAB_OMIN_META_NETWORK.NETWORKTYPE = '04'";
        return queryMDOSForListMap(querySql);
    }
}
