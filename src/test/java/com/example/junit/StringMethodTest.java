package com.example.junit;

public class StringMethodTest {
    public static void main(String[] args){
        String testStr1 = "A1232";
        String testStr2 = "54234";
        String testStr3 = "a1232";

//        match(testStr1);
//        match(testStr2);
//        match(testStr3);
//        split("2,4");
        indexOf("bb");
    }

    private static void match(String str){
       if(str.matches("[a-z|A-Z]\\d\\d\\d\\d") ){
           System.out.println(str + " begin with alphabet");
       }else{
           System.out.println(str + " does not start with alphabet");
       }
    }

    public static void split(String str){
        String[] vals = str.split(",");
        System.out.println("第一个val:" + vals[0]);
        System.out.println("第二个val:" + vals[1]);
    }

    public static void indexOf(String str){
//        if(str.indexOf(",rr,bb,cc") > -1){
//            System.out.println("exists");
//        }else{
//            System.out.println("not exists");
//        }
        if(",rr,bb,cc".indexOf("bb") > -1){
            System.out.println("exists");
        }else{
            System.out.println("not exists");
        }
    }
}
