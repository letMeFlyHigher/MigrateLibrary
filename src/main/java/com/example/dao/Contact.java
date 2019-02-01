package com.example.dao;

import com.example.util.FieldHelper;
import com.example.util.MyUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.Types;
import java.util.List;
import java.util.Map;




@Repository
public class Contact extends baseDao {
    private static Logger LOGGER = LoggerFactory.getLogger(EnvironmentDao.class);

    @Override
    public int start() {
        String tableName = "TAB_OMIN_CM_CC_CONTACT";

        List<Map<String,Object>> listMap = executeQuerySql();
        insertToPMCISTable(tableName, listMap, new FieldHelper() {
            @Override
            public void editMapForUpdate(Map<String, Object> map) {
                String uuid = MyUUID.getUUID36();
                map.put("C_CONTACT_ID",uuid);
                String c_siteopf_id = (String) map.get("C_SITEOPF_ID");
                String newStationPK = stationPKMap.get(c_siteopf_id);
                map.put("C_SITEOPF_ID", newStationPK);
            }

            @Override
            public int getFiledNameType(String fieldName) {
                return Types.VARCHAR;
            }
        });
        return 1;
    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT\r\n" +
                "'tmpVal' as C_CONTACT_ID,\r\n" +
                "TAB_OMIN_CM_CC_STATION.C_CNTPHONE AS C_CONTACT_PHONE," +
                "TAB_OMIN_CM_CC_STATION.C_RPINDNAME AS C_CONTACT_NAME," +
                "TAB_OMIN_CM_CC_STATION.C_STATION_ID AS C_SITEOPF_ID\r\n" +
                "FROM TAB_OMIN_CM_CC_STATION\r\n" +
                "WHERE TAB_OMIN_CM_CC_STATION.C_INDEXNBR < 'A'\r\n";
        return queryMDOSForListMap(querySql);
    }
}
