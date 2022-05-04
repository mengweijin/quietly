package com.github.mengweijin.quietly.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quietly.enums.CaseStatus;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.mapper.StepDefinitionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * QTL_STEP_DEFINITION implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class StepDefinitionService extends ServiceImpl<StepDefinitionMapper, StepDefinition> implements IService<StepDefinition> {

    /**
     * <p>
     * StepDefinitionMapper
     * </p>
     */
    @Autowired
    private StepDefinitionMapper stepDefinitionMapper;

    public void updateStatusById(Long stepId, CaseStatus caseStatus) {
        StepDefinition stepDefinition = new StepDefinition();
        stepDefinition.setId(stepId);
        stepDefinition.setStatus(caseStatus);
        stepDefinitionMapper.updateById(stepDefinition);
    }


    public void updateActualValueById(Long stepId, String actualValue) {
        StepDefinition stepDefinition = new StepDefinition();
        stepDefinition.setId(stepId);
        stepDefinition.setActualValue(actualValue);
        stepDefinitionMapper.updateById(stepDefinition);
    }
}

