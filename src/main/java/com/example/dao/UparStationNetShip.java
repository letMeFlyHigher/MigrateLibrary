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

//高空气球探空仪探测
@Repository
public class UparStationNetShip extends baseDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(UparStationNetShip.class);

    @Override
    public int start() {
        String tableName = "tab_omin_cm_cc_uparstationnetship".toUpperCase();
        LOGGER.info(tableName + "开始迁库>>>>>>");
        List<Map<String,Object>> listMap = executeQuerySql();
        if(insertToPMCISForNetShip(tableName, listMap, new FieldHelper() {
            @Override
            public int getFiledNameType(String fieldName) {
                if(",C_OBSVMODE,C_SONDEOBSVCOUNT,C_ANEMOOBSVCOUNT,".contains(fieldName)){
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
            LOGGER.info(cnt++ + tableName + "完成迁库");
        }else{
            LOGGER.error(tableName + "迁移失败");
        }
        return 1;
    }

//    @Override
//    protected void editMapForUpdate(Map<String, Object> map) {
//        map.put("C_OBSVMODE",(BigDecimal)map.get("C_OBSVMODE"));
//        map.put("C_SONDEOBSVCOUNT",(BigDecimal)map.get("C_SONDEOBSVCOUNT"));
//        map.put("C_ANEMOOBSVCOUNT",(BigDecimal)map.get("C_ANEMOOBSVCOUNT"));
//    }

//    @Override
//    protected void dealDiffTable(PreparedStatement ps, String fieldName,Object val,Map<String,Object> map) throws SQLException {
//        //高空站网的字段处理方式
//        // ObsvMode,SondeObsvCount,AnemoObsvCount
//        int j = 0;
//        while(iter.hasNext()){
//            j++;
//            Map.Entry<String,Object> entry = iter.next();
//            String fieldName = entry.getKey();
//            Object val = entry.getValue();
//            if(",C_OBSVMODE,C_SONDEOBSVCOUNT,C_ANEMOOBSVCOUNT,".indexOf(fieldName) > -1){
//                ps.setBigDecimal(j, (BigDecimal)val);
//            }else{
//                ps.setString(j,(String)val);
//            }
//        }
//    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        //守班情况，人工观测次数，人工观测时间在高空站网中都是没有该字段的，但是目前基本关系表里这三个字段，所以将这三个字段的值赋值为空。
        String querySql = "SELECT\n" +
                "  TAB_OMIN_META_NETWORK.NETWORKPK as C_SNETSHIP_ID,\n" +
                "  SUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK,0,32) as C_SITEOPF_ID,\n" +
                "  '04' as C_SNET_ID,\n" +
                " null as C_OBSVCOUNT,\n" +
                " null as C_OBSVTIMES,\n" +
                " null as C_ONDUTY,\n" +
                "  TAB_OMIN_META_NETWORK.NetWorkLevel as C_STATION_LEVEL,TAB_OMIN_META_NETWORK.ISGCOS as C_ISGCOS,TAB_OMIN_META_NETWORK.StartTime as C_StartTime,TAB_OMIN_META_NETWORK.EndTime as C_EndTime,TAB_OMIN_META_NETWORK.TimeSystem as C_TimeSystem,TAB_OMIN_META_NETWORK.ExchangeCode as C_ExchangeCode,TAB_OMIN_META_NETWORK.ObsvMode as C_ObsvMode,TAB_OMIN_META_NETWORK.AnemoStartTime as C_AnemoStartTime,TAB_OMIN_META_NETWORK.AnemoEndTime as C_AnemoEndTime,TAB_OMIN_META_NETWORK.AnemoObsvTime as C_AnemoObsvTime,TAB_OMIN_META_NETWORK.AnemoObsvCount as C_AnemoObsvCount,TAB_OMIN_META_NETWORK.ANE_ALT as C_ANE_ALT,TAB_OMIN_META_NETWORK.SondeStartTime as C_SondeStartTime,TAB_OMIN_META_NETWORK.SondeEndTime as C_SondeEndTime,TAB_OMIN_META_NETWORK.SondeObsvTime as C_SondeObsvTime,TAB_OMIN_META_NETWORK.SondeObsvCount as C_SondeObsvCount,TAB_OMIN_META_NETWORK.Sound_Alt as C_Sound_Alt \n" +
                "FROM TAB_OMIN_META_NETWORK\n" +
                "WHERE TAB_OMIN_META_NETWORK.NETWORKTYPE = '04'";
        return queryMDOSForListMap(querySql);
    }


}
