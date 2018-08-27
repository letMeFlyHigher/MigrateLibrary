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
public class EnvironmentDao extends baseDao {
    private static Logger LOGGER = LoggerFactory.getLogger(EnvironmentDao.class);

    @Override
    public void start() {
        String tableName = "TAB_OMIN_CM_CC_ENVIRONMENT";

        List<Map<String,Object>> listMap = executeQuerySql();
        insertToPMCISTable(tableName, listMap, new FieldHelper() {
            @Override
            public void editMapForUpdate(Map<String, Object> map) {
                String uuid = MyUUID.getUUID36();
                map.put("C_ENV_ID",uuid);
            }

            @Override
            public int getFiledNameType(String fieldName) {
                return Types.VARCHAR;
            }
        });

    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT\r\n" +
                "'tmpVal' as C_ENV_ID,\r\n" +
                "TAB_OMIN_CM_CC_STATION.C_TRANSTIMES AS C_TRANSTIMES," +
                "TAB_OMIN_CM_CC_STATION.C_TRANSDATE AS C_TRANSDATE," +
                "TAB_OMIN_CM_CC_STATION.C_STATION_ID AS C_SITEOPF_ID\r\n" +
                "FROM TAB_OMIN_CM_CC_STATION\r\n" +
                "WHERE TAB_OMIN_CM_CC_STATION.C_INDEXNBR IS NOT NULL\r\n";
        return queryMDOSForListMap(querySql);
    }
}
