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
        main.migrate();
        return "Build Successfully!";
    }

    @ShellMethod("Hello Demo")
    public String hello(){
        return "hello this is demo, if you can see that,you start shell successfully";
    }

    @ShellMethod("truncate all pmcis observation tables")
    public String truncateAll(){
        return null;
    }

    @ShellMethod("count Table")
    public Long  tableCount(String tableName){
        return main.tableCount(tableName);
    }

    @ShellMethod("Help command")
    public String selfhelp(){
        String explain = "table-count 统计表中数据数量；\n" +
                "start-migrate 开始迁移; \n" +
                "recreate-table-structure 重新创建观测元数据表结构;\n";
        return explain;
    }
}
