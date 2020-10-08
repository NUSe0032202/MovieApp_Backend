package com.ben.backend.movieapp.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

@Configuration
public class Config {
	
	
	@Bean
    public DataSource getDatasource() throws SQLException {
		SimpleDriverDataSource datasource = new SimpleDriverDataSource();
        datasource.setDriver(new com.mysql.cj.jdbc.Driver());
        
        return datasource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
