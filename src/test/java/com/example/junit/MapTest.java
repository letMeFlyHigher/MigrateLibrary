package com.example.junit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class MapTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapTest.class);

    public static void main(String[] args){

        Map<String,Object> map = new HashMap<>();
        map.put("1","aa");
        map.put("2","bb");
        addMap(map);

        LOGGER.info(map.toString());
    }

    /**
     * 此处可以证明，方法中传入的这个map并非是又copy了一份儿，而是还是原来的那一份儿；
     * 所以如果对map进行修改的话，原map对应的值也会变动。
     * @param map
     */
    public static void addMap(Map<String,Object> map){
       map.put("3","cc");
    }


}
