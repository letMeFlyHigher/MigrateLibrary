package com.example.junit;

import org.junit.Test;

import javax.validation.constraints.NotNull;

public class NotNullAnnotationTest {
    
    @Test
    public void test1(){
        strnot(null);
    }

    //这个非空的注解，好像只有一个提示的作用啊，并没有发现有检验是否为空，并提示的功能。
    private void strnot(@NotNull String str1){
       System.out.println(str1) ;
    }
}
