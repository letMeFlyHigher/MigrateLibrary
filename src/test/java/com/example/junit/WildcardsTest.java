package com.example.junit;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WildcardsTest {

    @Test
    public void test1(){
        List<Object> intList = Arrays.asList(1,2,3);
        printlist(intList);
//        List<Object> strList = Arrays.asList("aa","bb","cc");
//        printlist(strList);
        List<String> strList2 = Arrays.asList("aa","bb","cc");
        printlist2(strList2);
    }

    public void printlist(List<Object> list){
        for(Object elem : list){
            System.out.println(elem + " ");
        }
        System.out.println();
    }

    public void printlist2(List<?> list){
        for(Object elem : list){
            System.out.println(elem + " ");
        }
        System.out.println();
    }
}
