package com.github.mengweijin.quietly.cucumber.step;

import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quietly.cucumber.ScenarioThreadLocal;
import com.github.mengweijin.quietly.cucumber.StepArgs;
import com.github.mengweijin.quietly.cucumber.service.CucumberService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * database hooks
 * @author mengweijin
 */
public class DatabaseHooks {

    /**
     * init dataSource before each step when has @DataSource
     */
    @Before(value = "@DataSource", order = 1)
    public void jdbcTemplate() {
        StepArgs stepArgs = ScenarioThreadLocal.get();
        CucumberService cucumberService = SpringUtils.getBean(CucumberService.class);
        DataSource dataSource = cucumberService.getTestDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        stepArgs.setJdbcTemplate(jdbcTemplate);
    }

    @After
    public void doSomethingAfter(Scenario scenario){
        // Do something after scenario
    }

}
