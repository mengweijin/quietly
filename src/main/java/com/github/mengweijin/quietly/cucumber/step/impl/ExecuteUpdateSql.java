package com.github.mengweijin.quietly.cucumber.step.impl;

import cn.hutool.core.util.StrUtil;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quietly.cucumber.ScenarioThreadLocal;
import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import com.github.mengweijin.quietly.system.entity.Step;
import com.github.mengweijin.quietly.system.service.StepService;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import static com.github.mengweijin.quietly.cucumber.step.QuietlyStep.EXECUTE_UPDATE_SQL;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Component(EXECUTE_UPDATE_SQL)
public class ExecuteUpdateSql implements QuietlyStep {

    @Override
    @When(EXECUTE_UPDATE_SQL + " {long} {long}")
    public void execute(Long caseId, Long stepId){
        StepService stepService = SpringUtils.getBean(StepService.class);
        Step step = stepService.getById(stepId);
        String sqls = step.getActionOrAssertKey();
        if (StrUtil.isNotBlank(sqls)) {
            String[] sqlArray = sqls.split(Const.SEMICOLON);
            JdbcTemplate jdbcTemplate = ScenarioThreadLocal.get().getJdbcTemplate();
            jdbcTemplate.batchUpdate(sqlArray);
        }
    }
}
