package com.example.shell;

import com.example.work.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class StartShell {

    private Main main;

    @Autowired
    public void setMain(Main m){
       this.main = m;
    }

    @ShellMethod("start migrate ")
    public String startMigrate(){
        main.start();
        return "";
    }

    @ShellMethod("recreate pmcis table strucure")
    public String recreateTableStructure(){
       return "";
    }

    @ShellMethod("Hello Demo")
    public String hello(){
        return "hello this is demo, if you can see that,you start shell successfully";
    }

    @ShellMethod("truncate all pmcis observation tables")
    public String truncateAll(){
        return null;
    }
}
