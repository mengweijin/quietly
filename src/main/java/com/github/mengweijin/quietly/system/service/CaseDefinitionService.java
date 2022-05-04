package com.github.mengweijin.quietly.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quietly.enums.CaseStepStatus;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.step.Step;
import com.github.mengweijin.quietly.system.entity.CaseDefinition;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.mapper.CaseDefinitionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * QTL_CASE_DEFINITION implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class CaseDefinitionService extends ServiceImpl<CaseDefinitionMapper, CaseDefinition> implements IService<CaseDefinition> {

    /**
     * <p>
     * CaseDefinitionMapper
     * </p>
     */
    @Autowired
    private CaseDefinitionMapper caseDefinitionMapper;

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Autowired
    private List<Step> stepList;

    public void runCase(Long id) {
        try {
            this.updateStatusById(id, CaseStepStatus.RUNNING);

            List<StepDefinition> stepDefinitionList = stepDefinitionService.getByCaseIdOrderBySeqAsc(id);
            stepDefinitionList.forEach(stepDefinition -> {
                Step step = this.getStepByStepType(stepDefinition.getStepType());
                step.run(stepDefinition.getId());
            });
            this.updateStatusById(id, CaseStepStatus.SUCCESS);
        } catch (Throwable t) {
            this.updateStatusById(id, CaseStepStatus.FAILED);
        }
    }

    private void updateStatusById(Long id, CaseStepStatus status) {
        CaseDefinition caseDefinition = new CaseDefinition();
        caseDefinition.setId(id);
        caseDefinition.setStatus(status);
        this.updateById(caseDefinition);
    }

    public Step getStepByStepType(StepType stepType) {
        return stepList.stream().filter(step-> step.stepType() == stepType).findFirst().get();
    }
}

