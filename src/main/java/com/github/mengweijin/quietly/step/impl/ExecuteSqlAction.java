package com.github.mengweijin.quietly.step.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.github.mengweijin.quickboot.framework.exception.QuickBootClientException;
import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
import com.github.mengweijin.quickboot.framework.jdbc.LowerColumnMapRowMapper;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.step.Step;
import com.github.mengweijin.quietly.step.StepArgs;
import com.github.mengweijin.quietly.system.entity.EnvironmentDatasource;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.EnvironmentDatasourceService;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
@Service
public class ExecuteSqlAction implements Step {

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Autowired
    private EnvironmentDatasourceService environmentDatasourceService;

    @Override
    public StepType stepType() {
        return StepType.ACTION_EXECUTE_SQL;
    }

    @Override
    public Object invoke(Long stepId, StepArgs stepArgs) {
        StepDefinition stepDefinition = stepDefinitionService.getById(stepId);
        EnvironmentDatasource ed = environmentDatasourceService.getById(stepDefinition.getActionSqlDatasourceId());

        this.checkDatasource(stepId, ed);

        DriverManagerDataSource dataSource = new DriverManagerDataSource(ed.getUrl(), ed.getUsername(), ed.getPassword());
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String sqls = stepDefinition.getActionExpression();
        if(StrUtil.isBlank(sqls)) {
            throw new QuickBootClientException("The sql is blank for this step id: " + stepId + "; Please check your step config.");
        }

        String[] sqlArray = sqls.split(Const.SEMICOLON);
        if(this.isAllUpdateSQL(sqlArray)) {
            jdbcTemplate.batchUpdate(sqlArray);
        } else if(sqlArray.length != 1) {
            throw new QuickBootClientException("Not support multiple select sql in one step. Because it's a bad practice.");
        } else {
            List<Map<String, Object>> mapList = jdbcTemplate.query(sqlArray[0], new LowerColumnMapRowMapper());
            return mapList;
        }
        return null;
    }

    public void checkDatasource(Long stepId, EnvironmentDatasource environmentDatasource) {
        if(environmentDatasource == null) {
            throw new QuickBootException("No environment datasource was found for stepId: " + stepId);
        }
        DbType dbType = DbType.getDbType(environmentDatasource.getDbType());
        if(dbType == DbType.OTHER) {
            throw new QuickBootClientException(stepType().name() + " does not support current database type: " + environmentDatasource.getDbType());
        }
    }

    public boolean isAllUpdateSQL(String[] sqlArray) {
        return Arrays.stream(sqlArray).noneMatch(sql -> sql.trim().startsWith("select"));
    }

    public boolean isSelectSQL(String sql) {
        return sql.trim().startsWith("select");
    }
}
