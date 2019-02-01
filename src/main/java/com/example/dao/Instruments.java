package com.example.dao;

import com.example.util.FieldHelper;
import com.example.util.MyUUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;


@Repository
public class Instruments extends baseDao {
    private static Logger LOGGER = LoggerFactory.getLogger(EnvironmentDao.class);

    @Override
    public int start() {
        String tableName = "TAB_OMIN_CM_CC_INSTRUMENTS";

        List<Map<String,Object>> listMap = executeQuerySql();
        insertToPMCISTable(tableName, listMap, new FieldHelper() {
            @Override
            public void editMapForUpdate(Map<String, Object> map) {
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
                "TAB_OMIN_META_INSTRUMENTS.APPLIANCE_ID AS APPLIANCE_ID," +
                "TAB_OMIN_META_INSTRUMENTS.APPLIANCE_NAME AS APPLIANCE_NAME\r\n" +
                "FROM TAB_OMIN_META_INSTRUMENTS\r\n";
        return queryMDOSForListMap(querySql);
    }
}
