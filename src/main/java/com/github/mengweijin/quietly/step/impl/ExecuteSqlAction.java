package com.github.mengweijin.quietly.step.impl;

import com.baomidou.mybatisplus.annotation.DbType;
import com.github.mengweijin.quickboot.framework.exception.QuickBootClientException;
import com.github.mengweijin.quietly.step.ActionStep;
import com.github.mengweijin.quietly.system.entity.EnvironmentDatasource;
import com.github.mengweijin.quietly.system.service.EnvironmentDatasourceService;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
@Service
public class ExecuteSqlAction implements ActionStep {

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Autowired
    private EnvironmentDatasourceService environmentDatasourceService;

    @Override
    public void doAction(Long stepId) {
        EnvironmentDatasource datasource = environmentDatasourceService.getByStepDefinitionId(stepId);
        if(datasource != null) {
            DbType dbType = DbType.getDbType(datasource.getDbType());
            if(dbType == DbType.OTHER) {
                throw new QuickBootClientException("ACTION_EXECUTE_SQL does not support current database type: " + datasource.getDbType());
            }
        }




    }
}
