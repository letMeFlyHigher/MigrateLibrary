package com.example.work;

import com.example.dao.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Main {

    @Autowired
    private Stationplat stationplat;

    @Autowired
    @Qualifier("awsstationnetship")
    private Awsstationnetship awsstationnetship;

    @Autowired
    private UparStationNetShip uparStationNetShip;

    @Autowired
    private ArStationNetShip arStationNetShip;

    @Autowired
    private AsmStationNetShip asmStationNetShip;

    @Autowired
    private CawnStationNetShip cawnStationNetShip;

    @Autowired
    private RadiStationNetShip radiStationNetShip;

    @Autowired
    private ObsQuantity obsQuantity;

    @Autowired
    private ObsMethod obsMethod;

    @Autowired
    private OthersFactory othersFactory;

    @Autowired
    private RecreateObservationTables recreateObservationTables;

    @Autowired
    private EnvironmentDao environmentDao;

    @Autowired
    private Contact contact;

    @Autowired
    private WkflDefaHandler wkflDefaHandler;

    @Autowired
    private WkflDefbHandler wkflDefbHandler;

    public boolean start(){

        int size = 30;
        for(int i = 0; i < size; i++){
            if(stationplat.start() < 0){
                return false;
            }
            awsstationnetship.start();
            uparStationNetShip.start();
            arStationNetShip.start();
            asmStationNetShip.start();
            cawnStationNetShip.start();
            radiStationNetShip.start();
            obsQuantity.start();
            obsMethod.start();
            othersFactory.start();
            environmentDao.start();
            contact.start();
            clearPKMap();
        }
        wkflDefaHandler.start();
        wkflDefbHandler.start();
//        awsstationnetship.clearTable("TAB_OMIN_CM_CC_STATIONNETSHIP");
        return false;
    }

    public String getNetPK(){
        return baseDao.netPKMap.toString();
    }

    public String getStationPK(){
        return baseDao.stationPKMap.toString();
    }

    public String getNetPKValue(String key){
        return baseDao.netPKMap.get(key);
    }

    public String getStationPKValue(String key){
        return baseDao.stationPKMap.get(key);
    }

    public String clearPKMap(){
        baseDao.stationPKMap.clear();
        baseDao.netPKMap.clear();
        return "Okay";
    }


    public String migrate(){
        return recreateObservationTables.mig();
    }

    public String clearTable(){
        stationplat.clearTable("tab_omin_cm_cc_stationplat");
        awsstationnetship.clearTable("tab_omin_cm_cc_awsstationnetship");
        uparStationNetShip.clearTable("tab_omin_cm_cc_uparstationnetship");
        arStationNetShip.clearTable("tab_omin_cm_cc_arstationnetship");
        asmStationNetShip.clearTable("tab_omin_cm_cc_asmstationnetship");
        cawnStationNetShip.clearTable("tab_omin_cm_cc_cawnstationnetship");
        radiStationNetShip.clearTable("tab_omin_cm_cc_radistationnetship");
        obsQuantity.clearTable("tab_omin_cm_cc_obsquantity");
        obsMethod.clearTable("tab_omin_cm_cc_obsmethod");
        environmentDao.clearTable("tab_omin_cm_cc_environment");
        contact.clearTable("tab_omin_cm_cc_contact");
        return "Okay";
    }

    public Long tableCount(String tableName){
       return stationplat.countRows(tableName) ;
    }




}
