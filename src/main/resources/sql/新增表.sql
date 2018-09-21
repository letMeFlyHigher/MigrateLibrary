DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_OBSVCRIT`;
CREATE TABLE `TAB_OMIN_CM_CC_OBSVCRIT` (
      `OBSVCRITPK` VARCHAR(50) NOT NULL COMMENT '观测规范ID',
      `STARTTIME` VARCHAR(20) DEFAULT NULL COMMENT '开始时间',
      `ENDTIME` VARCHAR(20) DEFAULT NULL COMMENT '终止时间',
      `NAMEANDVER` VARCHAR(100) DEFAULT NULL COMMENT '规范名称和版本',
      `ISSUEORG` VARCHAR(50) DEFAULT NULL COMMENT '发布单位',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`ObsvCritPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='观测规范表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_SUMMARY`;
CREATE TABLE `TAB_OMIN_CM_CC_SUMMARY` (
      `SUMMARYPK` VARCHAR(50) NOT NULL COMMENT '纪要ID',
      `SUMMARYEVENTSN` NUMERIC(10) DEFAULT NULL COMMENT '纪要编号',
      `STARTTIME` VARCHAR(20) DEFAULT NULL COMMENT '纪要起始时间',
      `ENDTIME` VARCHAR(20) DEFAULT NULL COMMENT '纪要起始时间',
      `SUMMARY_EVENT` VARCHAR(2000) DEFAULT NULL COMMENT '纪要内容',
      `SUMMARYEVENTID` NUMERIC(5) DEFAULT NULL COMMENT '纪要事件类型标识',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`SummaryPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='纪要表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_WEATHERDESC`;
CREATE TABLE `TAB_OMIN_CM_CC_WEATHERDESC` (
      `WEATHERDESCPK` VARCHAR(50) NOT NULL COMMENT '概况PK',
      `RECDTIME` VARCHAR(30) DEFAULT NULL COMMENT '记录时间',
      `FEATURE` VARCHAR(2000) DEFAULT NULL COMMENT '气候特点',
      `MAJORDESC` VARCHAR(2000) DEFAULT NULL COMMENT '主要天气过程',
      `KEYDESC` VARCHAR(2000) DEFAULT NULL COMMENT '关键性天气',
      `LONGADVERSEWEATHER` VARCHAR(2000) DEFAULT NULL COMMENT '较长时间不利天气',
      `OVERALLEVALUATION` VARCHAR(2000) DEFAULT NULL COMMENT '综合评价',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`WeatherDescPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='本月天气气候概况表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_NOTEEVENT`;
CREATE TABLE `TAB_OMIN_CM_CC_NOTEEVENT` (
      `NOTEEVENTPK` VARCHAR(50) NOT NULL COMMENT '备注事件ID',
      `GENERALEVENTID` NUMERIC(5) DEFAULT NULL COMMENT '备注项一般事件ID',
      `STARTTIME` VARCHAR(20) DEFAULT NULL COMMENT '起始时间',
      `ENDTIME` VARCHAR(20) DEFAULT NULL COMMENT '终止时间',
      `GENERALEVENTDESC` VARCHAR(2000) DEFAULT NULL COMMENT '备注项一般事件',
      `EVENTTYPE` NUMERIC(5) DEFAULT NULL COMMENT '备注事件类型',
      `GIFNAME` VARCHAR(20) DEFAULT NULL COMMENT '图像文件名',
      `PRONAME` VARCHAR(20) DEFAULT NULL COMMENT '报告书文件名',
      `N_FUNCLAYER` VARCHAR(30) DEFAULT NULL COMMENT '作用层性质',
      `N_CONDILAYER` VARCHAR(30) DEFAULT NULL COMMENT '作用层状况',
      `N_PROVICECODE` VARCHAR(30) DEFAULT NULL COMMENT '省质控码',
      `N_CITYCODE` VARCHAR(30) DEFAULT NULL COMMENT '市质控码',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`NoteEventPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='	备注项一般事件表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_ATTACHMENT`;
CREATE TABLE `TAB_OMIN_CM_CC_ATTACHMENT` (
      `TABLENAME` VARCHAR(50) DEFAULT NULL COMMENT '表名',
      `OBJPK` VARCHAR(2000) DEFAULT NULL COMMENT '记录ID',
      `ATTACHPK` VARCHAR(50) DEFAULT NULL COMMENT '附件ID',
      `ATTACHTYPE` NUMERIC(5) DEFAULT NULL COMMENT '文档类型',
      `ATTACHNAME` VARCHAR(50) DEFAULT NULL COMMENT '文档名称',
      `ATTACHDESC` VARCHAR(255) DEFAULT NULL COMMENT '文档说明',
      `CREATEDATE` VARCHAR(20) DEFAULT NULL COMMENT '创建日期',
      `UPLOADDATE` VARCHAR(20) DEFAULT NULL COMMENT '上传日期',
      `ATTACHUPDATE` VARCHAR(20) DEFAULT NULL COMMENT '更新日期',
      `UPLOADBY` VARCHAR(50) DEFAULT NULL COMMENT '上传者ID',
      `EXTNAME` VARCHAR(10) DEFAULT NULL COMMENT '扩展名',
      `IMPORTSTATE` VARCHAR(50) DEFAULT NULL COMMENT '导入状态',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`AttachPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附件表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_OBSTACLE`;
CREATE TABLE `TAB_OMIN_CM_CC_OBSTACLE` (
      `OBSTPK` VARCHAR(50) NOT NULL COMMENT '障碍物PK',
      `OBSTSN` VARCHAR(20) DEFAULT NULL COMMENT '障碍物编号',
      `OBSTCLASS` VARCHAR(20) NOT NULL COMMENT '障碍物名称',
      `DIRECTION` VARCHAR(20) DEFAULT NULL COMMENT '方位',
      `ELEVANGLE` VARCHAR(20) DEFAULT NULL COMMENT '仰角',
      `WIDTHANGLE` VARCHAR(20) DEFAULT NULL COMMENT '宽度角',
      `DISTANCE` VARCHAR(20) DEFAULT NULL COMMENT '距离',
      `STARTTIME` VARCHAR(20) DEFAULT NULL COMMENT '开始时间',
      `ENDTIME` VARCHAR(20) DEFAULT NULL COMMENT '结束时间',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`ObstPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='障碍物表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_POLLUTE`;
CREATE TABLE `TAB_OMIN_CM_CC_POLLUTE` (
      `POLLUTEPK` VARCHAR(50) NOT NULL COMMENT '污染物ID',
      `POLLUTESN` VARCHAR(20) DEFAULT NULL COMMENT '污染物编号',
      `POLLUTECLASS` VARCHAR(100) NOT NULL COMMENT '污染物名称',
      `DIRECTION` VARCHAR(20) DEFAULT NULL COMMENT '方位',
      `DISTANCE` VARCHAR(20) DEFAULT NULL COMMENT '距离',
      `STARTTIME` VARCHAR(20) DEFAULT NULL COMMENT '开始时间',
      `ENDTIME` VARCHAR(20) DEFAULT NULL COMMENT '结束时间',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`PollutePK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='污染源表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_CHGINFO`;
CREATE TABLE `TAB_OMIN_CM_CC_CHGINFO` (
      `CHGINFOTYPE` VARCHAR(1) NOT NULL COMMENT '变动项类型',
      `CHGINFOPK` VARCHAR(32) NOT NULL COMMENT '变动项PK',
      `NETWORKPK` VARCHAR(36) DEFAULT NULL COMMENT '站网PK',
      `OBJPK` VARCHAR(50) DEFAULT NULL COMMENT '对象PK',
      `CHGITEMID` VARCHAR(20) DEFAULT NULL COMMENT '变动ID',
      `ITEMLINENO` NUMERIC(10) DEFAULT NULL COMMENT '行号',
      `CHGTAG` VARCHAR(50) DEFAULT NULL COMMENT '变动标记',
      `VALUE01` VARCHAR(2000) DEFAULT NULL COMMENT '扩展字段1',
      `VALUE02` VARCHAR(2000) DEFAULT NULL COMMENT '扩展字段2',
      `VALUE03` VARCHAR(2000) DEFAULT NULL COMMENT '扩展字段3',
      `VALUE04` VARCHAR(2000) DEFAULT NULL COMMENT '扩展字段4',
      `VALUE05` VARCHAR(2000) DEFAULT NULL COMMENT '扩展字段5',
      `VALUE06` VARCHAR(2000) DEFAULT NULL COMMENT '扩展字段6',
      `VALUE07` VARCHAR(2000) DEFAULT NULL COMMENT '扩展字段7',
      `VALUE08` VARCHAR(2000) DEFAULT NULL COMMENT '扩展字段8',
      `VALUE09` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段9',
      `VALUE10` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段10',
      `VALUE11` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段11',
      `VALUE12` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段12',
      `VALUE13` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段13',
      `VALUE14` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段14',
      `VALUE15` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段15',
      `VALUE16` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段16',
      `VALUE17` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段17',
      `VALUE18` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段18',
      `VALUE19` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段19',
      `VALUE20` VARCHAR(50) DEFAULT NULL COMMENT '扩展字段20',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`ChgInfoPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='变动信息表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_WKFLDEFA`;
CREATE TABLE `TAB_OMIN_CM_CC_WKFLDEFA` (
      `WKFLAPK` VARCHAR(32) NOT NULL COMMENT '工作流ID',
      `WKFLNAME` VARCHAR(50) NOT NULL COMMENT '工作流名称及版本',
      `WKFLPICT` VARCHAR(50) DEFAULT NULL COMMENT '工作流图片',
      `WKFLVER` VARCHAR(10) DEFAULT NULL COMMENT '工作流版本',
      `WKFLTYPEA` NUMERIC(5) DEFAULT NULL COMMENT '工作流类型',
      `WKFLTYPEB` NUMERIC(5) DEFAULT NULL COMMENT '变动类型',
      `WKFLFUNCID` NUMERIC(10) DEFAULT NULL COMMENT '功能ID',
      `INUSE` VARCHAR(1) DEFAULT NULL COMMENT '是否在用',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`WkflAPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流目录表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_WKFLDEFB`;
CREATE TABLE `TAB_OMIN_CM_CC_WKFLDEFB` (
      `WKFLBPK` VARCHAR(40) NOT NULL COMMENT '工作流步骤PK',
      `WKFLSTEPB` NUMERIC(5) DEFAULT NULL COMMENT '工作流步骤编号',
      `WKFLQUEST` NUMERIC(5) DEFAULT NULL COMMENT '用户确认',
      `WKFLMESS` VARCHAR(50) DEFAULT NULL COMMENT '提示文字',
      `WKFLNEXTSTEP1` NUMERIC(5) DEFAULT NULL COMMENT '为真时的下一步',
      `WKFLNEXTSTEP2` NUMERIC(5) DEFAULT NULL COMMENT '为假时的下一步',
      `WKFLDEPTTYPE` NUMERIC(5) DEFAULT NULL COMMENT '部门类型',
      `WKFLSTAFFTYPE` NUMERIC(5) DEFAULT NULL COMMENT '责任人类型',
      `ORGID` VARCHAR(50) DEFAULT NULL COMMENT '部门编号',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`WkflBPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流定义表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_WKFLRUNA`;
CREATE TABLE `TAB_OMIN_CM_CC_WKFLRUNA` (
      `WKFLRUNAPK` VARCHAR(40) NOT NULL COMMENT '工作流实例PK',
      `WKFLDESC` VARCHAR(50) DEFAULT NULL COMMENT '工作流名称及版本',
      `WKFLSTATUS` VARCHAR(4) DEFAULT NULL COMMENT '工作流',
      `RESULT` NUMERIC(5) DEFAULT NULL COMMENT '处理结果',
      `SUGGESTION` VARCHAR(2000) DEFAULT NULL COMMENT '处理意见',
      `WKFLTYPEA` NUMERIC(5) DEFAULT NULL COMMENT '区分变动、疑问类型',
      `WKFLTYPEB` NUMERIC(5) DEFAULT NULL COMMENT '变动项',
      `WKFLTYPEC` VARCHAR(4) DEFAULT NULL COMMENT '操作类型',
      `SUBMITBY` VARCHAR(50) DEFAULT NULL COMMENT '提交人',
      `SUBMITDATE` VARCHAR(20) DEFAULT NULL COMMENT '提交时间',
      `APPRBY` VARCHAR(50) DEFAULT NULL COMMENT '审核人',
      `APPRDATE` VARCHAR(20) DEFAULT NULL COMMENT '审核时间',
      `NETWORKPK` VARCHAR(50) DEFAULT NULL COMMENT '站网主键',
      `WKFLORIGTABLE` VARCHAR(20) DEFAULT NULL COMMENT '数据原表',
      `WKFLMODITABLE` VARCHAR(20) DEFAULT NULL COMMENT '修改后数据保存的表',
      `WKFLORIGRECDID` VARCHAR(50) DEFAULT NULL COMMENT '要修改的数据主键',
      `WKFLMODIRECDID` VARCHAR(50) DEFAULT NULL COMMENT '修改后的数据主键',
      `ORGID` VARCHAR(50) DEFAULT NULL COMMENT '提交信息的部门ID',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`WkflRunAPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流实例表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_WKFLRUNB`;
CREATE TABLE `TAB_OMIN_CM_CC_WKFLRUNB` (
      `WKFLRUNBPK` VARCHAR(48) NOT NULL COMMENT '工作流实例PK',
      `WKFLSTEPB` NUMERIC(5) DEFAULT NULL COMMENT '工作流步骤编号',
      `ORGID` VARCHAR(50) DEFAULT NULL COMMENT '操作人部门编号',
      `USERID` VARCHAR(50) DEFAULT NULL COMMENT '执行者',
      `WKFLQUEST` NUMERIC(5) DEFAULT NULL COMMENT '用户确认',
      `WKFLSTEPDESC` VARCHAR(100) DEFAULT NULL COMMENT '说明',
      `WKFLNEXTSTEP1` NUMERIC(5) DEFAULT NULL COMMENT '通过时的下一步',
      `WKFLNEXTSTEP2` NUMERIC(5) DEFAULT NULL COMMENT '驳回时的下一步',
      `WKFLDEPTTYPE` NUMERIC(5) DEFAULT NULL COMMENT '部门类型',
      `WKFLSTAFFTYPE` NUMERIC(5) DEFAULT NULL COMMENT '责任人类型',
      `WKFLDATE0` VARCHAR(20) DEFAULT NULL COMMENT '审核任务分派日期',
      `WKFLDATE1` VARCHAR(20) DEFAULT NULL COMMENT '审核接受日期',
      `WKFLDATE2` VARCHAR(20) DEFAULT NULL COMMENT '审核完成日期',
      `WKFLBSTATUS` VARCHAR(1) DEFAULT NULL COMMENT '审批状态',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`WkflRunBPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='工作流运行表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_OBSVRECD`;
CREATE TABLE `TAB_OMIN_CM_CC_OBSVRECD` (
      `OBSVRECDPK` VARCHAR(50) NOT NULL COMMENT '观测记录ID',
      `STARTTIME` VARCHAR(20) DEFAULT NULL COMMENT '开始时间',
      `ENDTIME` VARCHAR(20) DEFAULT NULL COMMENT '终止时间',
      `CARRIERNAME` VARCHAR(50) DEFAULT NULL COMMENT '观测记录名称',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`ObsvRecdPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='观测记录表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_HISLOGFROM`;
CREATE TABLE `TAB_OMIN_CM_CC_HISLOGFROM` (
      `HISLOGFROMPK` VARCHAR(50) NOT NULL COMMENT '主键',
      `STARTTIME` VARCHAR(20) DEFAULT NULL COMMENT '开始时间',
      `ENDTIME` VARCHAR(20) DEFAULT NULL COMMENT '结束时间',
      `HISLOGDATAFROM` VARCHAR(500) DEFAULT NULL COMMENT '沿革数据来源',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`HISLOGFROMPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='沿革数据来源表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_DOUCUMENT`;
CREATE TABLE `TAB_OMIN_CM_CC_DOUCUMENT` (
      `PDOCUMENT` VARCHAR(4) DEFAULT NULL COMMENT '文件类型',
      `INPUT` VARCHAR(50) DEFAULT NULL COMMENT '输入人',
      `PROOFREAD` VARCHAR(50) DEFAULT NULL COMMENT '校对人',
      `PRELIMINARY` VARCHAR(50) DEFAULT NULL COMMENT '预审人',
      `AUDI` VARCHAR(50) DEFAULT NULL COMMENT '审核人',
      `TRANSFER` VARCHAR(50) DEFAULT NULL COMMENT '传输人',
      `STATION` VARCHAR(50) NOT NULL COMMENT '台站号',
      `PRINTER` VARCHAR(50) DEFAULT NULL COMMENT '打印人',
      `TRANSFERDATE` VARCHAR(50) DEFAULT NULL COMMENT '开始时间',
      `MAKETIME` VARCHAR(50) DEFAULT NULL COMMENT '结束时间',
      `SONDECODE` VARCHAR(30) DEFAULT NULL COMMENT '探空仪型号代码',
      `DETECTIONCODE` VARCHAR(30) DEFAULT NULL COMMENT '探测系统型号代码',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='附加信息人员信息表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_INSTMODEL`;
CREATE TABLE `TAB_OMIN_CM_CC_INSTMODEL` (
      `INSTMOPK` VARCHAR(50) NOT NULL COMMENT '仪器型号主键',
      `INSTMODEL` VARCHAR(50) DEFAULT NULL COMMENT '仪器型号',
      `INSTNAME` VARCHAR(50) DEFAULT NULL COMMENT '仪器名称',
      `INSTCOMP` VARCHAR(60) DEFAULT NULL COMMENT '生产厂家',
      `INSTNUM` VARCHAR(30) DEFAULT NULL COMMENT '号码组',
      `VALUE01` VARCHAR(30) DEFAULT NULL COMMENT '扩展字段',
      `VALUE02` VARCHAR(30) DEFAULT NULL COMMENT '扩展字段',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`INSTMOPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='大气成分仪器型号表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_INSTRUMENTS`;
CREATE TABLE `TAB_OMIN_CM_CC_INSTRUMENTS` (
      `APPLIANCE_ID` VARCHAR(20) NOT NULL COMMENT '仪器编码',
      `APPLIANCE_NAME` VARCHAR(100) NOT NULL COMMENT '仪器名称',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`APPLIANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='年报仪器编码表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_NETWORK_SOIL_BAK`;
CREATE TABLE `TAB_OMIN_CM_CC_NETWORK_SOIL_BAK` (
      `NETWORKSOPK` VARCHAR(44) NOT NULL COMMENT '站网主键',
      `DISTRTYPE` VARCHAR(50) DEFAULT NULL COMMENT '观测地段',
      `CROP` VARCHAR(50) DEFAULT NULL COMMENT '地表覆盖物',
      `TEXTURE10` VARCHAR(50) DEFAULT NULL COMMENT '10CM土壤质地',
      `CAPACITY10` VARCHAR(20) DEFAULT NULL COMMENT '10CM田间持水量',
      `UNITWEIGHT10` VARCHAR(20) DEFAULT NULL COMMENT '10CM土壤容重',
      `WILTING_MOISTURE10` VARCHAR(20) DEFAULT NULL COMMENT '10CM凋萎湿度',
      `TEXTURE20` VARCHAR(50) DEFAULT NULL COMMENT '20CM土壤质地',
      `CAPACITY20` VARCHAR(20) DEFAULT NULL COMMENT '20CM田间持水量',
      `UNITWEIGHT20` VARCHAR(20) DEFAULT NULL COMMENT '20CM土壤容重',
      `WILTING_MOISTURE20` VARCHAR(20) DEFAULT NULL COMMENT '20CM凋萎湿度',
      `TEXTURE30` VARCHAR(50) DEFAULT NULL COMMENT '30CM土壤质地',
      `CAPACITY30` VARCHAR(20) DEFAULT NULL COMMENT '30CM田间持水量',
      `UNITWEIGHT30` VARCHAR(20) DEFAULT NULL COMMENT '30CM土壤容重',
      `WILTING_MOISTURE30` VARCHAR(20) DEFAULT NULL COMMENT '30CM凋萎湿度',
      `TEXTURE40` VARCHAR(50) DEFAULT NULL COMMENT '40CM土壤质地',
      `CAPACITY40` VARCHAR(20) DEFAULT NULL COMMENT '40CM田间持水量',
      `UNITWEIGHT40` VARCHAR(20) DEFAULT NULL COMMENT '40CM土壤容重',
      `WILTING_MOISTURE40` VARCHAR(20) DEFAULT NULL COMMENT '40CM凋萎湿度',
      `TEXTURE50` VARCHAR(50) DEFAULT NULL COMMENT '50CM土壤质地',
      `CAPACITY50` VARCHAR(20) DEFAULT NULL COMMENT '50CM田间持水量',
      `UNITWEIGHT50` VARCHAR(20) DEFAULT NULL COMMENT '50CM土壤容重',
      `WILTING_MOISTURE50` VARCHAR(20) DEFAULT NULL COMMENT '50CM凋萎湿度',
      `TEXTURE60` VARCHAR(50) DEFAULT NULL COMMENT '60CM土壤质地',
      `CAPACITY60` VARCHAR(20) DEFAULT NULL COMMENT '60CM田间持水量',
      `UNITWEIGHT60` VARCHAR(20) DEFAULT NULL COMMENT '60CM土壤容重',
      `WILTING_MOISTURE60` VARCHAR(20) DEFAULT NULL COMMENT '60CM凋萎湿度',
      `TEXTURE80` VARCHAR(50) DEFAULT NULL COMMENT '80CM土壤质地',
      `CAPACITY80` VARCHAR(20) DEFAULT NULL COMMENT '80CM田间持水量',
      `UNITWEIGHT80` VARCHAR(20) DEFAULT NULL COMMENT '80CM土壤容重',
      `WILTING_MOISTURE80` VARCHAR(20) DEFAULT NULL COMMENT '80CM凋萎湿度',
      `TEXTURE100` VARCHAR(50) DEFAULT NULL COMMENT '100CM土壤质地',
      `CAPACITY100` VARCHAR(20) DEFAULT NULL COMMENT '100CM田间持水量',
      `UNITWEIGHT100` VARCHAR(20) DEFAULT NULL COMMENT '100CM土壤容重',
      `WILTING_MOISTURE100` VARCHAR(20) DEFAULT NULL COMMENT '100CM凋萎湿度',
      `VIEWMODE10` VARCHAR(20) DEFAULT NULL COMMENT '10CM观测方式',
      `CROPVARETYPE` VARCHAR(20) DEFAULT NULL COMMENT '覆盖物品种类型',
      `RIPE` VARCHAR(20) DEFAULT NULL COMMENT '熟性',
      `CROPVARE` VARCHAR(20) DEFAULT NULL COMMENT '覆盖物品种',
      `CULTIVATION` VARCHAR(20) DEFAULT NULL COMMENT '栽培方式',
      `VIEWMODE20` VARCHAR(20) DEFAULT NULL COMMENT '20CM观测方式',
      `VIEWMODE30` VARCHAR(20) DEFAULT NULL COMMENT '30CM观测方式',
      `VIEWMODE40` VARCHAR(20) DEFAULT NULL COMMENT '40CM观测方式',
      `VIEWMODE50` VARCHAR(20) DEFAULT NULL COMMENT '50CM观测方式',
      `VIEWMODE60` VARCHAR(20) DEFAULT NULL COMMENT '60CM观测方式',
      `VIEWMODE80` VARCHAR(20) DEFAULT NULL COMMENT '80CM观测方式',
      `VIEWMODE100` VARCHAR(20) DEFAULT NULL COMMENT '100CM观测方式',
      `VIEWMODE70` VARCHAR(20) DEFAULT NULL COMMENT '70CM观测方式',
      `VIEWMODE90` VARCHAR(20) DEFAULT NULL COMMENT '90CM观测方式',
      `TEXTURE70` VARCHAR(50) DEFAULT NULL COMMENT '70CM土壤质地',
      `CAPACITY70` VARCHAR(50) DEFAULT NULL COMMENT '70CM田间持水量',
      `UNITWEIGHT70` VARCHAR(50) DEFAULT NULL COMMENT '70CM土壤容重',
      `WILTING_MOISTURE70` VARCHAR(50) DEFAULT NULL COMMENT '70CM凋萎湿度',
      `TEXTURE90` VARCHAR(50) DEFAULT NULL COMMENT '90CM土壤质地',
      `CAPACITY90` VARCHAR(50) DEFAULT NULL COMMENT '90CM田间持水量',
      `UNITWEIGHT90` VARCHAR(50) DEFAULT NULL COMMENT '90CM土壤容重',
      `WILTING_MOISTURE90` VARCHAR(50) DEFAULT NULL COMMENT '90CM凋萎湿度',
      `STARTTIME` VARCHAR(50) DEFAULT NULL COMMENT '开始时间',
      `ENDTIME` VARCHAR(50) DEFAULT NULL COMMENT '结束时间',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`NETWORKSOPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='土壤水分属性信息变动表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_YEAR_CLIMATE`;
CREATE TABLE `TAB_OMIN_CM_CC_YEAR_CLIMATE` (
      `C_ID` NUMERIC NOT NULL COMMENT '主键',
      `STATION_ID` VARCHAR(100) NOT NULL COMMENT '台站主键',
      `C_TIME` VARCHAR(30) DEFAULT NULL COMMENT '年报年份',
      `C_MAIN` VARCHAR(2000) DEFAULT NULL COMMENT '主要天气气候特征',
      `C_ABNORMAL` VARCHAR(2000) DEFAULT NULL COMMENT '异常气候现象',
      `C_DAMAGE` VARCHAR(2000) DEFAULT NULL COMMENT '重要灾害性、关键性天气及其影响',
      `C_CONTINUED` VARCHAR(2000) DEFAULT NULL COMMENT '持续天气的不利影响',
      `C_SYNTHETICAL` VARCHAR(2000) DEFAULT NULL COMMENT '本年天气气候综合评价',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`C_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='台站年报气候概况表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_YEAR_INSTRUMENTS`;
CREATE TABLE `TAB_OMIN_CM_CC_YEAR_INSTRUMENTS` (
      `I_ID` NUMERIC NOT NULL COMMENT '主键',
      `STATION_ID` VARCHAR(100) NOT NULL COMMENT '台站主键',
      `APPLIANCE_ID` VARCHAR(20) DEFAULT NULL COMMENT '仪器代码',
      `APPLIANCE_NAME` VARCHAR(100) DEFAULT NULL COMMENT '仪器名称',
      `I_FACTORY` VARCHAR(200) DEFAULT NULL COMMENT '厂名',
      `I_TIME` VARCHAR(30) DEFAULT NULL COMMENT '检定时间',
      `I_MODELS` VARCHAR(500) DEFAULT NULL COMMENT '仪器型号组',
      `I_NUMBERS` VARCHAR(500) DEFAULT NULL COMMENT '号码组',
      `N_TIME` VARCHAR(30) DEFAULT NULL COMMENT '年报日期',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`I_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='台站年报仪器表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_YEAR_REMARK`;
CREATE TABLE `TAB_OMIN_CM_CC_YEAR_REMARK` (
      `R_ID` NUMERIC NOT NULL COMMENT '主键',
      `STATION_ID` VARCHAR(100) NOT NULL COMMENT '台站主键',
      `R_TIME` VARCHAR(30) DEFAULT NULL COMMENT '备注起止时间',
      `R_CONTENT` VARCHAR(2000) DEFAULT NULL COMMENT '备注内容',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`R_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='年报备注表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_REVIEWED`;
CREATE TABLE `TAB_OMIN_CM_CC_REVIEWED` (
      `RD_ID` NUMERIC NOT NULL COMMENT '主键',
      `HANDLE_TYPE` VARCHAR(20) NOT NULL COMMENT '流程类型',
      `PROCESS_STATE` VARCHAR(20) NOT NULL COMMENT '流程状态',
      `HANDLE_CONTENT` VARCHAR(500) NOT NULL COMMENT '流程内容',
      `SUBMITTER` VARCHAR(200) DEFAULT NULL COMMENT '提交人',
      `SUBMIT_TIME` VARCHAR(30) DEFAULT NULL COMMENT '提交时间',
      `APPROVER` VARCHAR(200) DEFAULT NULL COMMENT '审核人',
      `APPROVAL_TIME` VARCHAR(30) DEFAULT NULL COMMENT '审核时间',
      `VALUE01` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE02` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE03` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE04` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE05` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE06` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE07` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE08` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE09` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE10` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE11` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE12` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE13` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE14` TEXT DEFAULT NULL COMMENT '扩展字段',
      `VALUE15` TEXT DEFAULT NULL COMMENT '扩展字段',
      `PROCESS` LONGTEXT DEFAULT NULL COMMENT '流程说明值',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`RD_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='台站年报流程表';

DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_SOFT`;
CREATE TABLE `TAB_OMIN_CM_CC_SOFT` (
      `SOFTPK` VARCHAR(44) NOT NULL COMMENT '主键',
      `STARTTIME` VARCHAR(20) NOT NULL COMMENT '开始时间',
      `ENDTIME` VARCHAR(20) NOT NULL COMMENT '结束时间',
      `SOFTNAME` VARCHAR(200) DEFAULT NULL COMMENT '软件名称',
      `SOFTVER` VARCHAR(200) DEFAULT NULL COMMENT '软件版本号',
      `SOFTCORP` VARCHAR(200) DEFAULT NULL COMMENT '研制单位',
      `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
      `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
      `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
       PRIMARY KEY (`SOFTPK`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='高空探测软件信息表';

# DROP TABLE IF EXISTS `TAB_OMIN_CM_CC_RADIATION`;
# CREATE TABLE `TAB_OMIN_CM_CC_RADIATION` (
#       `RADIATIONID` VARCHAR(50) NOT NULL COMMENT '主键',
#       `N_FUNCLAYER` VARCHAR(200) NOT NULL COMMENT '作用层性质',
#       `N_CONDILAYER` VARCHAR(200) NOT NULL COMMENT '作用层状态',
#       `N_CONTENT` VARCHAR(2000) NOT NULL COMMENT '要素开始时间',
#       `C_CREATE_DATE` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
#       `C_UPDATED_DATE` TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
#       `C_MODIFIER` VARCHAR(36) DEFAULT NULL COMMENT '最近更新人',
#       `C_OPT_TYPE` VARCHAR(8) DEFAULT NULL COMMENT '操作状态/操作类型add、update、delete',
#        PRIMARY KEY (`RADIATIONID`)
# ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='辐射站网最新作用层状态';

