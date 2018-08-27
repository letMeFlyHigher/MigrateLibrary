package com.example.junit;

import org.springframework.util.Assert;

public class AssertTest {

    public static void main(String[] args){
        Object obj = null;
        Assert.notNull(null,"对象不能为空");
        Assert.doesNotContain("woshicainiao","no","该参数不包含cai，参数非法");

    }
}
