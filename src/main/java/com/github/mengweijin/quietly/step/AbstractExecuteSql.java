package com.github.mengweijin.quietly.step;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.github.mengweijin.quickboot.framework.exception.QuickBootClientException;
import com.github.mengweijin.quietly.system.entity.Datasource;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.DatasourceService;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
public abstract class AbstractExecuteSql extends AbstractStep {

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Autowired
    private DatasourceService datasourceService;

    @Override
    public Map<String, Object> invoke(Long stepId, StepArgs stepArgs) throws Exception {
        StepDefinition stepDefinition = stepDefinitionService.getById(stepId);

        this.checkSqlIsEmpty(stepDefinition);

        JdbcTemplate jdbcTemplate = this.getJdbcTemplate(stepDefinition);

        return executeSql(stepDefinition, jdbcTemplate);
    }

    /**
     * execute sql
     * @param stepDefinition stepDefinition
     * @param jdbcTemplate jdbcTemplate
     * @return Object
     * @throws Exception exception
     */
    public abstract Map<String, Object> executeSql(StepDefinition stepDefinition, JdbcTemplate jdbcTemplate) throws Exception;

    protected void checkSqlIsEmpty(StepDefinition stepDefinition) {
        Long stepId = stepDefinition.getId();
        String sql = stepDefinition.getExpression();
        if(StrUtil.isBlank(sql)) {
            String msg = "The sql is blank for this step type:" + stepType()
                    + ", step id: " + stepId + "; Please check your step config.";
            throw new QuickBootClientException(msg);
        }
    }

    protected JdbcTemplate getJdbcTemplate(StepDefinition stepDefinition) {
        Datasource ds = datasourceService.getByStepDefinition(stepDefinition);

        DbType dbType = DbType.getDbType(ds.getDbType());
        if(dbType == DbType.OTHER) {
            throw new QuickBootClientException(stepType().name() + " does not support current database type: " + ds.getDbType());
        }

        DriverManagerDataSource dataSource = new DriverManagerDataSource(ds.getUrl(), ds.getUsername(), ds.getPassword());
        return new JdbcTemplate(dataSource);
    }

}
