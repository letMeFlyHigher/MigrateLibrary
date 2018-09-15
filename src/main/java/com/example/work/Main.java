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

    public boolean start(){

        awsstationnetship.clearTable("TAB_OMIN_CM_CC_STATIONNETSHIP");
        if(stationplat.start() < 0){
            return false;
        }
//        Long starttime = System.currentTimeMillis();
        awsstationnetship.start();
//        long endTime = System.currentTimeMillis();
//        System.out.println("迁移地面站网表用时" + (endTime - starttime)/1000 + "s");
//
        uparStationNetShip.start();
//        long dealUpar = System.currentTimeMillis() ;
//        System.out.println("迁移高空站网表用时" + (dealUpar - endTime)/1000 + "s");
//
        arStationNetShip.start();
//        long dealAr = System.currentTimeMillis() ;
//        System.out.println("迁移酸雨站网表用时" + (dealAr - dealUpar)/1000 + "s");

        asmStationNetShip.start();
//        long dealAsm = System.currentTimeMillis() ;
//        System.out.println("迁移酸雨站网表用时" + (dealAsm - dealAr)/1000 + "s");

        cawnStationNetShip.start();

        radiStationNetShip.start();

        obsQuantity.start();

        obsMethod.start();

        othersFactory.start();

        environmentDao.start();

        return false;
    }

    public String migrate(){
        return recreateObservationTables.mig();
    }





}
