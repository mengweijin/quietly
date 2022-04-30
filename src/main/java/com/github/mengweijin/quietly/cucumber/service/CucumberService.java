package com.github.mengweijin.quietly.cucumber.service;

import com.github.mengweijin.quietly.properties.QuietlyProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Service
public class CucumberService {

    @Autowired
    private QuietlyProperties quietlyProperties;

    public DataSource getTestDataSource() {
        QuietlyProperties.Datasource datasource = quietlyProperties.getTest().getDatasource();
        return new DriverManagerDataSource(datasource.getUrl(), datasource.getUsername(), datasource.getPassword());
    }



}
