package com.example.config;

import com.example.dao.Awsstationnetship;
import com.example.work.Main;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
//@Import(AsyncConfig.class)
public class MultipleDBConfig {
//    @Bean(name="mysqlDb")
//    @ConfigurationProperties(prefix="spring.mysql.datasource2")
//    public DataSource mysqlDataSource(){
//        return DataSourceBuilder.create().build();
//    }

    @Bean(name="mysqlDb")
    @ConfigurationProperties(prefix="spring.mysql.datasource2")
    public DataSource mysqlDataSource(){

        HikariDataSource hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", "");
        hikari.addDataSourceProperty("port", getConfig().getString("mysql.port"));
        hikari.addDataSourceProperty("databaseName", getConfig().getString("mysql.database"));
        hikari.addDataSourceProperty("user", getConfig().getString("mysql.user"));
        hikari.addDataSourceProperty("password", getConfig().getString("mysql.password"));
        hikari.addDataSourceProperty("autoReconnect",true);
        hikari.addDataSourceProperty("cachePrepStmts",true);
        hikari.addDataSourceProperty("prepStmtCacheSize",250);
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit",2048);
        hikari.addDataSourceProperty("useServerPrepStmts",true);
        hikari.addDataSourceProperty("cacheResultSetMetadata",true);
        hikari.addDataSourceProperty("maxReconnects",5);
        hikari.addDataSourceProperty("tcpKeepAlive", true);
        hikari.setMaximumPoolSize(20);
        hikari.setMinimumIdle(0);
        hikari.setIdleTimeout(30000);
        return hikari;
    }

    @Bean(name="mysqlJdbcTemplate")
    public NamedParameterJdbcTemplate jdbcTemplate(@Qualifier("mysqlDb") DataSource dsMySql){
//        return new JdbcTemplate(dsMySql);
        return new NamedParameterJdbcTemplate(dsMySql);
    }

//    @Bean(name="oracleDb")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.oraclesql.datasource")
//    public DataSource oracleDataSource(){
//        return DataSourceBuilder.create().build();
//    }

    @Bean(name="oracleDb")
    @Primary
    @ConfigurationProperties(prefix = "spring.oraclesql.datasource")
    public DataSource oracleDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="oracleJdbcTemplate")
    public JdbcTemplate oracleJdbcTemplate(@Qualifier("oracleDb") DataSource dsOracle){
//        return new JdbcTemplate(dsOracle);
        return new JdbcTemplate(dsOracle);
    }

    @Bean(name="main")
    public Main getMain(){
        return new Main();
    }


}
