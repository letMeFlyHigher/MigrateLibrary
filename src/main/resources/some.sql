SELECT
  TAB_OMIN_META_NETWORK.NETWORKPK as NETWORKPK,TAB_OMIN_META_NETWORK.NetWorkLevel as C_STATION_LEVEL,
  TAB_OMIN_CM_CC_STATION.c_estadate as C_StartTime,
  TAB_OMIN_META_NETWORK.StartTime as C_netstarttime,TAB_OMIN_META_NETWORK.EndTime as C_netEndTime,TAB_OMIN_META_NETWORK.TimeSystem as C_TimeSystem,TAB_OMIN_META_NETWORK.ObsvCount as C_ObsvCount,TAB_OMIN_META_NETWORK.ObsvTimes as C_ObsvTimes,TAB_OMIN_META_NETWORK.ExchangeCode as C_ExchangeCode,TAB_OMIN_META_NETWORK.ObsvMode as C_ObsvMode,TAB_OMIN_META_NETWORK.ShiftCase as C_ONDUTY,TAB_OMIN_META_NETWORK.HisLogDataFrom as C_HisLogDataFrom,TAB_OMIN_META_NETWORK.HisLogCompBy as C_HisLogCompBy,TAB_OMIN_META_NETWORK.HisLogAudtBy as C_HisLogAudtBy,TAB_OMIN_META_NETWORK.HISLOGCOMPDATE as C_HISLOGCOMPDATE,
  TAB_OMIN_META_REGSTATION.AUTOSTATIONMODEL as C_INSTR_MODEL,TAB_OMIN_META_REGSTATION.AUTOSTATIONMANU as C_MF,TAB_OMIN_META_REGSTATION.POWERSUPPLYMODE as C_POWER_TYPE,TAB_OMIN_META_REGSTATION.INSTALPOSITION as C_INSTALL_POS  --INSTALPOSITION
--TAB_OMIN_META_OBSVELMT.自己降水SwitchDate as C_RAIN_STIME,TAB_OMIN_META_OBSVELMT.自己降水SWITCHDATE as C_RAIN_ETIME,TAB_OMIN_META_OBSVELMT.大型蒸发量的SWITCHDATE as C_SWITCH_SDATE,TAB_OMIN_META_OBSVELMT.大型蒸发量SWITCHDATE as C_SWITCH_EDTE
FROM TAB_OMIN_META_NETWORK,TAB_OMIN_CM_CC_STATION,TAB_OMIN_META_REGSTATION,TAB_OMIN_META_OBSVELMT
WHERE SUBSTR(TAB_OMIN_META_NETWORK.NETWORKPK(+),0,32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID AND TAB_OMIN_CM_CC_STATION.C_STATION_ID = TAB_OMIN_META_REGSTATION.REGSTATIONID(+)
      AND SUBSTR(TAB_OMIN_META_OBSVELMT.OBSVELMTPK(+),0,36) = TAB_OMIN_META_NETWORK.NETWORKPK
      AND TAB_OMIN_META_NETWORK.NETWORKTYPE = '01' AND TAB_OMIN_CM_CC_STATION.C_INDEXNBR IS NOT NULL;

-- 对应关系
-- 如果自记降水，有两条呢？？

SELECT decode(o.SWITCHDATE,null,',',o.SWITCHDATE) FROM TAB_OMIN_META_OBSVELMT O
WHERE O.OBSVELMTNAME = '自记降水' and o.SWITCHDATE is null;

SELECT decode(o.switchDate,null,',',o.SWITCHDATE) FROM TAB_OMIN_META_OBSVELMT O
WHERE O.OBSVELMTNAME = '大型蒸发量';

select * from (
  SELECT
    s.C_INDEXNBR,
    s.C_INDEXSUBNBR,
    n.NETWORKTYPE,
    n.networklevel,
    o.OBSVELMTNAME,
    o.SWITCHDATE
  FROM tab_omin_cm_cc_station s, tab_omin_meta_network n, TAB_OMIN_META_OBSVELMT o
  WHERE substr(n.NETWORKPK, 0, 32) = s.c_station_id AND substr(o.OBSVELMTPK, 0, 36) = n.NETWORKPK
        AND o.OBSVELMTNAME IN ('自记降水', '大型蒸发量')
) tmp;


select * from TAB_OMIN_META_RADIATION;


select count(*) from (
  SELECT
    TAB_OMIN_META_NETWORK.NETWORKPK                                                                      AS C_SITEOPF_ID,
    TAB_OMIN_META_NETWORK.NetWorkLevel                                                                   AS C_STATION_LEVEL,
    TAB_OMIN_CM_CC_STATION.c_estadate                                                                    AS C_StartTime,
    TAB_OMIN_META_NETWORK.StartTime                                                                      AS C_netstarttime,
    TAB_OMIN_META_NETWORK.EndTime                                                                        AS C_netEndTime,
    TAB_OMIN_META_NETWORK.TimeSystem                                                                     AS C_TimeSystem,
    TAB_OMIN_META_NETWORK.ObsvCount                                                                      AS C_ObsvCount,
    TAB_OMIN_META_NETWORK.ObsvTimes                                                                      AS C_ObsvTimes,
    TAB_OMIN_META_NETWORK.ExchangeCode                                                                   AS C_ExchangeCode,
    TAB_OMIN_META_NETWORK.ObsvMode                                                                       AS C_ObsvMode,
    TAB_OMIN_META_NETWORK.ShiftCase                                                                      AS C_ONDUTY,
    TAB_OMIN_META_NETWORK.HisLogDataFrom                                                                 AS C_HisLogDataFrom,
    TAB_OMIN_META_NETWORK.HisLogCompBy                                                                   AS C_HisLogCompBy,
    TAB_OMIN_META_NETWORK.HisLogAudtBy                                                                   AS C_HisLogAudtBy,
    TAB_OMIN_META_NETWORK.HISLOGCOMPDATE                                                                 AS C_HISLOGCOMPDATE,
    TAB_OMIN_META_REGSTATION.AUTOSTATIONMODEL                                                            AS C_INSTR_MODEL,
    TAB_OMIN_META_REGSTATION.AUTOSTATIONMANU                                                             AS C_MF,
    TAB_OMIN_META_REGSTATION.POWERSUPPLYMODE                                                             AS C_POWER_TYPE,
    TAB_OMIN_META_REGSTATION.INSTALPOSITION                                                              AS C_INSTALL_POS,
    -- TAB_OMIN_META_OBSVELMT.自己降水SwitchDate as C_RAIN_STIME,TAB_OMIN_META_OBSVELMT.自己降水SWITCHDATE as C_RAIN_ETIME,TAB_OMIN_META_OBSVELMT.大型蒸发量的SWITCHDATE as C_SWITCH_SDATE,TAB_OMIN_META_OBSVELMT.大型蒸发量SWITCHDATE as C_SWITCH_EDTE
    (SELECT switchdate
     FROM TAB_OMIN_META_OBSVELMT o
     WHERE substr(o.OBSVELMTPK, 0, 36) = TAB_OMIN_META_NETWORK.NETWORKPK AND o.OBSVELMTNAME =
                                                                             '自记降水')                     AS switchdaterain,
    (SELECT switchdate
     FROM tab_omin_meta_obsvelmt o2
     WHERE substr(o2.OBSVELMTPK, 0, 36) = TAB_OMIN_META_NETWORK.NETWORKPK AND o2.OBSVELMTNAME =
                                                                              '大型蒸发量')                   AS switchdatebig
  FROM TAB_OMIN_META_NETWORK, TAB_OMIN_CM_CC_STATION, TAB_OMIN_META_REGSTATION
  WHERE substr(TAB_OMIN_META_NETWORK.NETWORKPK, 0, 32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID (+) AND
        TAB_OMIN_META_REGSTATION.REGSTATIONID (+) = TAB_OMIN_CM_CC_STATION.C_STATION_ID
        AND TAB_OMIN_META_NETWORK.NETWORKTYPE = '01'
)tmp where tmp.switchdatebig != NULL;

select s.C_INDEXNBR,s.C_INDEXSUBNBR,o.* from tab_omin_meta_obsvelmt o,tab_omin_cm_cc_station s
where  o.OBSVELMTNAME = '大型蒸发量'
       and substr(o.OBSVELMTPK,0,32) = s.C_STATION_ID and s.C_INDEXNBR < 'A';

select Count(*) from TAB_OMIN_META_OBSVELMT o
where o.OBSVELMTNAME = '大型蒸发量';

select s.*,n.* from tab_omin_meta_network n,tab_omin_cm_cc_station s
where n.NETWORKPK = '216bef0b45a5412bbe9342875c4ccdbf0001' and substr(n.NETWORKPK,0,32) = s.C_STATION_ID;




select * from TAB_OMIN_META_OBSVELMT OO
where substr(OO.OBSVELMTPK,0,36) in (
  SELECT TAB_OMIN_META_NETWORK.NETWORKPK AS C_SITEOPF_ID
  --   (select switchdate from TAB_OMIN_META_OBSVELMT o
  --   where substr(o.OBSVELMTPK,0,36) = TAB_OMIN_META_NETWORK.NETWORKPK AND o.OBSVELMTNAME = '自记降水') as switchdaterain,
  --   (select switchdate from tab_omin_meta_obsvelmt o2
  --   where substr(o2.OBSVELMTPK,0,36) = TAB_OMIN_META_NETWORK.NETWORKPK AND o2.OBSVELMTNAME = '大型蒸发量') as switchdatebig
  FROM TAB_OMIN_META_NETWORK, TAB_OMIN_CM_CC_STATION, TAB_OMIN_META_REGSTATION
  WHERE substr(TAB_OMIN_META_NETWORK.NETWORKPK, 0, 32) = TAB_OMIN_CM_CC_STATION.C_STATION_ID (+) AND
        TAB_OMIN_META_REGSTATION.REGSTATIONID (+) = TAB_OMIN_CM_CC_STATION.C_STATION_ID
        AND TAB_OMIN_META_NETWORK.NETWORKTYPE = '01'
) and oo.OBSVELMTNAME = '大型蒸发量'