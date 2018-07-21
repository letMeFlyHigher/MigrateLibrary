package com.example.dao;

import com.sun.xml.internal.ws.util.QNameMap;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//酸雨站网的拆分与迁移
@Repository
public class ArStationNetShip extends baseDao {


    @Override
    public boolean start() {
        List<Map<String, Object>> listMap = executeQuerySql();
        String tableName = "TAB_OMIN_CM_CC_ARSTATIONNETSHIP";
        if(insertToPMCISTable(tableName,listMap) > 0){
            System.out.println("酸雨站网迁移成功");
        }else{
            System.out.println("酸雨站网迁移失败");
        }
        return false;
    }

    @Override
    protected void dealDiffTable(PreparedStatement ps, Iterator<Map.Entry<String, Object>> iter) throws SQLException {
        int j = 0;
        while(iter.hasNext()){
            j++;
            Map.Entry<String,Object> entry =  iter.next();
            String fieldName = entry.getKey();
            Object val = entry.getValue();
            if(",C_OBSVMODE,C_ONDUTY,".indexOf(fieldName) > -1){
                ps.setBigDecimal(j,(BigDecimal)val);
            }else{
                ps.setString(j,(String)val);
            }
        }
    }

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
