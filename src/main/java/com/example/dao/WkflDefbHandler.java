package com.example.dao;

import com.example.util.FieldHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;


@Repository
public class WkflDefbHandler extends baseDao {
    private static Logger LOGGER = LoggerFactory.getLogger(WkflDefbHandler.class);

    @Override
    public int start() {
        String tableName = "TAB_OMIN_CM_CC_WKFLDEFB";

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
        String querySql = "SELECT\n" +
                "\tWKFLBPK,WKFLSTEPB,WKFLQUEST,WKFLMESS,WKFLNEXTSTEP1,WKFLNEXTSTEP2,WKFLDEPTTYPE,WKFLSTAFFTYPE,ORGID\n" +
                "FROM TAB_OMIN_META_WKFLDEFB";
        return queryMDOSForListMap(querySql);
    }
}