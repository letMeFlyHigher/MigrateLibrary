package com.example.config;

import com.example.dao.Awsstationnetship;
import com.example.work.Main;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class MultipleDBConfig {
    @Bean(name="mysqlDb")
    @ConfigurationProperties(prefix="spring.mysql.datasource2")
    public DataSource mysqlDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="mysqlJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("mysqlDb") DataSource dsMySql){
        return new JdbcTemplate(dsMySql);
//        return new NamedParameterJdbcTemplate(dsMySql);
    }

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
