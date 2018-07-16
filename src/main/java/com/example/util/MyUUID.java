package com.example.util;

import java.util.UUID;

public class MyUUID {

    public static String getUUID32(){
       return UUID.randomUUID().toString().replace("-","");
    }
    public static String getUUID36(){
        return UUID.randomUUID().toString();
    }
    public static void main(String[] args){
        System.out.println("32 bit UUID: " + getUUID32());
        System.out.println("36 bit UUID: " + getUUID36());
    }
}
