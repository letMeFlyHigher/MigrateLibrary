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
public class ObsMethod extends baseDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(ObsMethod.class);
    @Override
    @Async
    public void start() {
        String tableName = "TAB_OMIN_CM_CC_OBSMETHOD";

        LOGGER.info(tableName + "开始迁库");
        List<Map<String,Object>> listMap = executeQuerySql();
        insertToPMCISTable(tableName, listMap, new FieldHelper() {
            @Override
            public void editMapForUpdate(Map<String, Object> map) {
                String instPK =(String) map.get("INSTPK_QUERY");
                //仪器和观测方法的字段处理
                //观测量id不作处理。
                //仪器和观测方法的id需要自动生成。
                String C_OBSVM_ID = MyUUID.getUUID36();
                String C_SNETSHIP_ID = instPK.substring(0,36);
                map.put("C_OBSVM_ID",C_OBSVM_ID);
                map.put("C_SNETSHIP_ID",C_SNETSHIP_ID);
            }

            @Override
            public int getFiledNameType(String fieldName) {
                return Types.VARCHAR;
            }
        });

        LOGGER.info(tableName + "迁库成功");
    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT \n" +
                "\t'tmpVal' AS C_OBSVM_ID,\n" +
                "\tTAB_OMIN_META_INSTRUMENT.INSTPK AS INSTPK_QUERY,\n" +
                "\t'tmpVal' AS C_SNETSHIP_ID,\n" +
                "\tTAB_OMIN_META_INSTRUMENT.InstName as C_INSTNAME,TAB_OMIN_META_INSTRUMENT.Manufactory as C_Manufactory,TAB_OMIN_META_INSTRUMENT.StopTime as C_StopTime,TAB_OMIN_META_INSTRUMENT.ResumeTime as C_ResumeTime,TAB_OMIN_META_INSTRUMENT.STOPREASON as C_STOPREASON,TAB_OMIN_META_INSTRUMENT.OverGrdHeight as C_OverGrdHeight,TAB_OMIN_META_INSTRUMENT.OBSVPLATHEIGHT as C_OBSVPLATHEIGHT,TAB_OMIN_META_INSTRUMENT.BuyTime as C_BuyTime,TAB_OMIN_META_INSTRUMENT.CHECKTIME as C_CHECKTIME,TAB_OMIN_META_INSTRUMENT.againtime as C_AgainTime,TAB_OMIN_META_INSTRUMENT.Vercer as C_Vercer,TAB_OMIN_META_INSTRUMENT.EndTime as C_endtime,TAB_OMIN_META_INSTRUMENT.InstModel as C_InstModel,TAB_OMIN_META_INSTRUMENT.ObsvElmtName as C_ObsvElmtName \n" +
                "FROM TAB_OMIN_META_INSTRUMENT,TAB_OMIN_META_NETWORK,TAB_OMIN_CM_CC_STATION \n" +
                "WHERE SUBSTR(TAB_OMIN_META_INSTRUMENT.instpk,0,36) = TAB_OMIN_META_NETWORK.NETWORKPK AND SUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK(+),0,32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID\n" +
                "AND TAB_OMIN_META_INSTRUMENT.INSTNAME IS NOT NULL AND TAB_OMIN_META_INSTRUMENT.OBSVELMTNAME IS NOT NULL";
        return queryMDOSForListMap(querySql);
    }
}
