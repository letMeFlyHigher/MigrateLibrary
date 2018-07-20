package com.example.dao;

import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Repository
public class Awsstationnetship extends baseDao {

    @Override
    public boolean start() {
        List<Map<String,Object>> listMap = queryMDOSTableForListMap();
        if(insertToPMCISTable("TAB_OMIN_CM_CC_AWSSTATIONNETSHIP",listMap) > 0){
            System.out.println("成功迁移地面关系站网");
        }else{
            System.out.println("迁移地面关系站网失败");
        }
        return false;
    }

    @Override
    protected void dealDiffTable(PreparedStatement ps, Iterator<Map.Entry<String, Object>> iter) throws SQLException {
        // 以下需要做特殊化处理的
        // 站网ID（C_SNET_ID) 处理为 ’01‘
        //主键，还是用mdos的主键，在公共元数据中新增的话，再说。
        //启用时间(C_Starttime) 取用 区域站的建站时间,区域站通过台站号是否包含字母来确定。
        // 大型蒸发量，自记降水切换时间的处理。
        String cIndexNbr = "";
        int rainSPos=-1,rainEPos=-1,switchSPos=-1,switchEPos = -1;
        int j=0;
        while(iter.hasNext()){
            j++;
            Map.Entry<String,Object> entry = iter.next();
            String fieldName = entry.getKey();
            Object val = entry.getValue();
            //处理启用时间（取用区域站的建站时间）
            if(fieldName.equals("C_StartTime")){
                if(cIndexNbr.matches("[a-z|A-Z]\\d\\d\\d\\d")){ //区域站
                   ps.setString(j, (String) val) ;
                }else{
                   ps.setString(j,null);
                }
            }else if(fieldName.equals("C_SNET_ID")){
               ps.setString(j,"01");
            }else if(fieldName.equals("c_indexnbr_Query")){
               cIndexNbr = (String)val;
               j--;  //用来查询的字段，并不计入参数累加器中
            }else if(fieldName.equals("RainfallSwitch_Query")){ //自己降水切换时间的处理  ， 限制条件 Query是在后面。
                //大型蒸发量切换时间的处理   值分三种情况， 一种是空值，此时表示改地面站网无改要素，第二种是‘,’,表示全年启用该要素，第三种是‘month1,month2’,表示month1停用，month2开始启用
                if(val == null){
                    ps.setString(rainSPos,null);
                    ps.setString(rainEPos,null);
                }else if(val.equals(",")){  //全年启用将开始启用，和开始停用月都设置为12月份儿。
                    ps.setString(rainSPos,"12");
                    ps.setString(rainEPos,"12");
                }else{ // 切换时间有具体的数值
                    String[] vals = ((String)val).split(",");
                    ps.setString(rainSPos,vals[0]);
                    ps.setString(rainEPos,vals[1]);
                }
                j--;

            }else if(fieldName.equals("EvaporationSwitch_Query")){
                //大型蒸发量切换时间的处理   值分三种情况， 一种是空值，此时表示改地面站网无改要素，第二种是‘,’,表示全年启用该要素，第三种是‘month1,month2’,表示month1停用，month2开始启用
                if(val == null){
                    ps.setString(switchSPos,null);
                    ps.setString(switchEPos,null);
                }else if(val.equals(",")){  //全年启用将开始启用，和开始停用月都设置为12月份儿。
                    ps.setString(switchSPos,"12");
                    ps.setString(switchEPos,"12");
                }else{ // 切换时间有具体的数值
                    String[] vals = ((String)val).split(",");
                    ps.setString(switchSPos,vals[0]);
                    ps.setString(switchEPos,vals[1]);
                }
                j--;
            }else if(fieldName.indexOf(",C_RAIN_STIME,C_RAIN_ETIME,C_SWITCH_SDATE,C_SWITCH_EDATE") > -1){
                if(fieldName.equals("C_RAIN_STIME")){
                    rainSPos = j;
                }else if(fieldName.equals("C_RAIN_ETIME")){
                    rainEPos = j;
                }else if(fieldName.equals("C_SWITCH_SDATE")){
                    switchSPos = j;
                }else if(fieldName.equals("C_SWITCH_EDATE")){
                    switchEPos = j;
                }else{
                    System.out.println("fieldName 出现问题了！");
                    break;
                }
            }else{
                ps.setString(j,(String)val);
            }
        }
    }

    @Override
    public List<Map<String, Object>> queryMDOSTableForListMap() {
        String querySql = "SELECT\n" +
                "    TAB_OMIN_META_NETWORK.NETWORKPK                                                                      AS C_SNETSHIP_ID,\n" +
                "    TAB_OMIN_CM_CC_STATION.c_station_id                                                                  AS C_SITEOPF_ID,\n" +
                "    'tmpVal'                                                                  AS C_SNET_ID,\n" +
                "    TAB_OMIN_META_NETWORK.NetWorkLevel                                                                   AS C_STATION_LEVEL,\n" +
                "    TAB_OMIN_CM_CC_STATION.c_estadate                                                                    AS C_StartTime,\n" +
                "    TAB_OMIN_CM_CC_STATION.c_indexnbr                                                                    AS C_indexnbr_QUERY,\n" +
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
                "  WHERE substr(TAB_OMIN_META_NETWORK.NETWORKPK, 0, 32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID (+) AND\n" +
                "        TAB_OMIN_META_REGSTATION.REGSTATIONID (+) = TAB_OMIN_CM_CC_STATION.C_STATION_ID\n" +
                "        AND TAB_OMIN_META_NETWORK.NETWORKTYPE = '01'";
        return queryMDOSForListMap(querySql);
    }

}
