package com.example.dao;

import com.example.util.FieldHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;
import java.util.Map;

//地面站网
@Repository
public class Awsstationnetship extends baseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(Awsstationnetship.class);

    @Override
    @Async
    public void start() {
        String tableName = "TAB_OMIN_CM_CC_AWSSTATIONNETSHIP";
        LOGGER.info(tableName + "开始迁库>>>>>>");
        List<Map<String,Object>> listMap = executeQuerySql();
        if(insertToPMCISTable(tableName, listMap, new FieldHelper() {
            @Override
            public int getFiledNameType(String fieldName) {
                if("C_ONDUTY,C_OBSVMODE,".contains(fieldName)){
                    return Types.NUMERIC;
                }else if("C_RAIN_STIME,C_RAIN_ETIME,C_SWITCH_SDATE,C_SWITCH_EDATE".contains(fieldName)){
                    return Types.TINYINT;
                }else{
                    return Types.VARCHAR;
                }
            }

            @Override
            public void editMapForUpdate(Map<String, Object> map) {
                String cIndexNbr = (String)map.get("C_INDEXNBR_QUERY");
                if(!cIndexNbr.matches("[a-z|A-Z]\\d\\d\\d\\d")){ //区域站
                    map.put("C_STARTTIME",null);
                }
                String rainFallSwitchQuery = (String)map.get("RAINFALLSWITCH_QUERY");
                String evaporationSwitchQuery = (String)map.get("EVAPORATIONSWITCH_QUERY");
                //C_RAIN_STIME,C_RAIN_ETIME,C_SWITCH_SDATE,C_SWITCH_EDATE
                if(rainFallSwitchQuery == null){
                    map.put("C_RAIN_STIME",null);
                    map.put("C_RAIN_ETIME",null);
                }else if(rainFallSwitchQuery.equals(",")){  //全年启用将开始启用，和开始停用月都设置为12月份儿。
                    map.put("C_RAIN_STIME",12);
                    map.put("C_RAIN_ETIME",12);
                }else{ // 切换时间有具体的数值
                    String[] vals = ((String)rainFallSwitchQuery).split(",");
                    map.put("C_RAIN_STIME",Integer.parseInt(vals[0]));
                    map.put("C_RAIN_ETIME",Integer.parseInt(vals[1]));
                }

                if(evaporationSwitchQuery == null){
                    map.put("C_SWITCH_SDATE",null);
                    map.put("C_SWITCH_EDATE",null);
                }else if(evaporationSwitchQuery.equals(",")){  //全年启用将开始启用，和开始停用月都设置为12月份儿。
                    map.put("C_SWITCH_SDATE",12);
                    map.put("C_SWITCH_EDATE",12);
                }else{ // 切换时间有具体的数值
                    String[] vals = ((String)evaporationSwitchQuery).split(",");
                    map.put("C_SWITCH_SDATE",Integer.parseInt(vals[0]));
                    map.put("C_SWITCH_EDATE",Integer.parseInt(vals[1]));
                }
//                map.put("C_OBSVMODE",(BigDecimal)map.get("C_OBSVMODE"));
//                map.put("C_ONDUTY",(BigDecimal)map.get("C_ONDUTY"));
                map.put("C_SNET_ID","01");

            }
        }) > 0){
            LOGGER.info(cnt++ + tableName + "完成迁库");
        }else{
           LOGGER.error(tableName + "迁移失败");
        }
    }

//    @Override
//    protected void editMapForUpdate(Map<String, Object> map) {
//
//
//
//    }

//    protected void dealDiffTable(PreparedStatement ps, String fieldName,Object val,Map<String,Object> map) throws SQLException {
        // 以下需要做特殊化处理的
        // 站网ID（C_SNET_ID) 处理为 ’01‘
        //主键，还是用mdos的主键，在公共元数据中新增的话，再说。
        //
        //启用时间(C_Starttime) 取用 区域站的建站时间,区域站通过台站号是否包含字母来确定。
        // 大型蒸发量，自记降水切换时间的处理。
////        String cIndexNbr = "";
////        int rainSPos=-1,rainEPos=-1,switchSPos=-1,switchEPos = -1;
////        String C_SNETSHIP_ID = null;
////        int j=0;
////        while(iter.hasNext()){
////            j++;
////            Map.Entry<String,Object> entry = iter.next();
////            String fieldName = entry.getKey();
////            Object val = entry.getValue();
////            //处理启用时间（取用区域站的建站时间）
////            if(fieldName.equals("C_StartTime".toUpperCase())){
////                if(cIndexNbr.matches("[a-z|A-Z]\\d\\d\\d\\d")){ //区域站
////                   ps.setString(j, (String) val) ;
////                }else{
////                   ps.setString(j,null);
////                }
////            }else if(fieldName.equals("C_SNET_ID")){
////               ps.setString(j,"01");
////            }else if(fieldName.equals("c_indexnbr_Query".toUpperCase())){
////               cIndexNbr = (String)val;
////               j--;  //用来查询的字段，并不计入参数累加器中
////            }else if(fieldName.equals("RainfallSwitch_Query".toUpperCase())){ //自己降水切换时间的处理  ， 限制条件 Query是在后面。
////                //大型蒸发量切换时间的处理   值分三种情况， 一种是空值，此时表示改地面站网无改要素，第二种是‘,’,表示全年启用该要素，第三种是‘month1,month2’,表示month1停用，month2开始启用
////                if(val == null){
////                    ps.setNull(rainSPos,Types.TINYINT);
////                    ps.setNull(rainEPos,Types.TINYINT);
////                }else if(val.equals(",")){  //全年启用将开始启用，和开始停用月都设置为12月份儿。
////                    ps.setInt(rainSPos,12);
////                    ps.setInt(rainEPos,12);
////                }else{ // 切换时间有具体的数值
////                    String[] vals = ((String)val).split(",");
////                    ps.setInt(rainSPos,Integer.parseInt(vals[0]));
////                    ps.setInt(rainEPos,Integer.parseInt(vals[1]));
////                }
////                j--;
//
//            }else if(fieldName.equals("EvaporationSwitch_Query".toUpperCase())){
//                //大型蒸发量切换时间的处理   值分三种情况， 一种是空值，此时表示改地面站网无改要素，第二种是‘,’,表示全年启用该要素，第三种是‘month1,month2’,表示month1停用，month2开始启用
//                if(val == null){
//                    ps.setNull(switchSPos,Types.TINYINT);
//                    ps.setNull(switchEPos,Types.TINYINT);
//                }else if(val.equals(",")){  //全年启用将开始启用，和开始停用月都设置为12月份儿。
//                    ps.setInt(switchSPos,12);
//                    ps.setInt(switchEPos,12);
//                }else{ // 切换时间有具体的数值
//                    String[] vals = ((String)val).split(",");
//                    ps.setInt(switchSPos,Integer.parseInt(vals[0]));
//                    ps.setInt(switchEPos,Integer.parseInt(vals[1]));
//                }
//                j--;
//            }else if(",C_RAIN_STIME,C_RAIN_ETIME,C_SWITCH_SDATE,C_SWITCH_EDATE".indexOf(fieldName) > -1){
//                if(fieldName.equals("C_RAIN_STIME")){
//                    rainSPos = j;
//                }else if(fieldName.equals("C_RAIN_ETIME")){
//                    rainEPos = j;
//                }else if(fieldName.equals("C_SWITCH_SDATE")){
//                    switchSPos = j;
//                }else if(fieldName.equals("C_SWITCH_EDATE")){
//                    switchEPos = j;
//                }else{
//                    System.out.println("fieldName 出现问题了！");
//                    break;
//                }
//            }else{
//               //接下来处理剩下字段的类型问题
//                // bigDecimal  的字段：shiftcase,ObsvMode -- C_ONDUTY,C_ObsvMode
//                if("C_OBSVMODE".indexOf(fieldName) > -1){
//                    ps.setBigDecimal(j,(BigDecimal)val);
//                }else if("C_ONDUTY".indexOf(fieldName) > -1){
//                            ps.setBigDecimal(j,(BigDecimal) val);
//                }else{
//                    ps.setString(j,(String)val);
//                }
//            }
//        }
//    }

    @Override
    public List<Map<String, Object>> executeQuerySql() {
        String querySql = "SELECT\n" +
                "    TAB_OMIN_META_NETWORK.NETWORKPK                                                                      AS C_SNETSHIP_ID,\n" +
                "    TAB_OMIN_CM_CC_STATION.c_station_id                                                                  AS C_SITEOPF_ID,\n" +
                "    'tmpVal'                                                                  AS C_SNET_ID,\n" +
                "    TAB_OMIN_META_NETWORK.NetWorkLevel                                                                   AS C_STATION_LEVEL,\n" +
                "    TAB_OMIN_CM_CC_STATION.c_indexnbr                                                                    AS C_indexnbr_QUERY,\n" +
                "    TAB_OMIN_CM_CC_STATION.c_estadate                                                                    AS C_StartTime,\n" +
                "    TAB_OMIN_META_NETWORK.StartTime                                                                      AS C_netstarttime,\n" +
                "    TAB_OMIN_META_NETWORK.EndTime                                                                        AS C_netEndTime,\n" +
                "    TAB_OMIN_META_NETWORK.TimeSystem                                                                     AS C_TimeSystem,\n" +
                "    TAB_OMIN_META_NETWORK.ObsvCount                                                                      AS C_ObsvCount,\n" +
                "    TAB_OMIN_META_NETWORK.ObsvTimes                                                                      AS C_ObsvTimes,\n" +
                "    TAB_OMIN_META_NETWORK.ExchangeCode                                                                   AS C_ExchangeCode,\n" +
                "    TAB_OMIN_META_NETWORK.ObsvMode                                                                       AS C_ObsvMode,\n" +
                "    TAB_OMIN_META_NETWORK.ShiftCase                                                                      AS C_ONDUTY,\n" +
                "    TAB_OMIN_META_NETWORK.HisLogDataFrom                                                                 AS C_HisLogDataFrom,\n" +
                "    TAB_OMIN_META_NETWORK.HisLogCompBy                                                                   AS C_HisLogCompBy,\n" +
                "    TAB_OMIN_META_NETWORK.HisLogAudtBy                                                                   AS C_HisLogAudtBy,\n" +
                "    TAB_OMIN_META_NETWORK.HISLOGCOMPDATE                                                                 AS C_HISLOGCOMPDATE,\n" +
                "    TAB_OMIN_META_REGSTATION.AUTOSTATIONMODEL                                                            AS C_INSTR_MODEL,\n" +
                "    TAB_OMIN_META_REGSTATION.AUTOSTATIONMANU                                                             AS C_MF,\n" +
                "    TAB_OMIN_META_REGSTATION.POWERSUPPLYMODE                                                             AS C_POWER_TYPE,\n" +
                "    TAB_OMIN_META_REGSTATION.INSTALPOSITION                                                              AS C_INSTALL_POS,\n" +
                "    'tmpVal' AS C_RAIN_STIME," +
                "    'tmpVal' AS C_RAIN_ETIME," +
                "    'tmpVal' AS C_SWITCH_SDATE," +
                "    'tmpVal' AS C_SWITCH_EDATE,\n" +
                "    decode((select 1 from TAB_OMIN_META_OBSVELMT o\n" +
                "     WHERE substr(o.OBSVELMTPK, 0, 36) = TAB_OMIN_META_NETWORK.NETWORKPK AND o.OBSVELMTNAME ='自记降水'),1,(\n" +
                "     SELECT decode(o.switchdate,null,',',o.switchdate)\n" +
                "     FROM TAB_OMIN_META_OBSVELMT o\n" +
                "     WHERE substr(o.OBSVELMTPK, 0, 36) = TAB_OMIN_META_NETWORK.NETWORKPK AND o.OBSVELMTNAME ='自记降水'),null) AS RainfallSwitch_QUERY,\n" +
                "    decode((select 1 from TAB_OMIN_META_OBSVELMT o\n" +
                "     WHERE substr(o.OBSVELMTPK, 0, 36) = TAB_OMIN_META_NETWORK.NETWORKPK AND o.OBSVELMTNAME ='大型蒸发量'),1,(\n" +
                "     SELECT decode(o.switchdate,null,',',o.switchdate)\n" +
                "     FROM TAB_OMIN_META_OBSVELMT o\n" +
                "     WHERE substr(o.OBSVELMTPK, 0, 36) = TAB_OMIN_META_NETWORK.NETWORKPK AND o.OBSVELMTNAME ='大型蒸发量'),null) AS EvaporationSwitch_QUERY\n" +
                "  FROM TAB_OMIN_META_NETWORK, TAB_OMIN_CM_CC_STATION, TAB_OMIN_META_REGSTATION\n" +
                "  WHERE substr(TAB_OMIN_META_NETWORK.NETWORKPK(+), 0, 32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID AND\n" +
                "        TAB_OMIN_META_REGSTATION.REGSTATIONID (+) = TAB_OMIN_CM_CC_STATION.C_STATION_ID\n" +
                "        AND TAB_OMIN_META_NETWORK.NETWORKTYPE = '01' AND TAB_OMIN_CM_CC_STATION.C_INDEXNBR is not null";
        return queryMDOSForListMap(querySql);
    }



}
