package com.github.mengweijin.quietly.step.impl;

import com.github.mengweijin.quietly.step.ActionStep;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Service
public class ExecuteSqlAction implements ActionStep {

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Override
    public void doAction(Long stepId) {
        StepDefinition stepDefinition = stepDefinitionService.getById(stepId);

    }
}
