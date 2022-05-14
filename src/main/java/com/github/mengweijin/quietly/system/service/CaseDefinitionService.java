package com.github.mengweijin.quietly.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quietly.enums.CaseStepStatus;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.listener.event.CaseFailedEvent;
import com.github.mengweijin.quietly.listener.event.CaseStartEvent;
import com.github.mengweijin.quietly.listener.event.CaseSuccessEvent;
import com.github.mengweijin.quietly.step.AbstractStep;
import com.github.mengweijin.quietly.system.entity.CaseDefinition;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.mapper.CaseDefinitionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
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
    private List<AbstractStep> AbstractStepList;

    /**
     * publish an application event of spring application.
     */
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void runCase(Long id) {
        try {
            applicationEventPublisher.publishEvent(new CaseStartEvent(this, id));

            List<StepDefinition> stepDefinitionList = stepDefinitionService.getByCaseIdOrderBySeqAsc(id);
            stepDefinitionList.forEach(stepDefinition -> {
                AbstractStep step = this.getStepByStepType(stepDefinition.getStepType());
                step.run(stepDefinition.getId());
            });

            applicationEventPublisher.publishEvent(new CaseSuccessEvent(this, id));
        } catch (Throwable t) {
            applicationEventPublisher.publishEvent(new CaseFailedEvent(this, id));
        }
    }

    public void updateStatusById(Long id, CaseStepStatus status) {
        CaseDefinition caseDefinition = new CaseDefinition();
        caseDefinition.setId(id);
        caseDefinition.setStatus(status);
        this.updateById(caseDefinition);
    }

    public AbstractStep getStepByStepType(StepType stepType) {
        return AbstractStepList.stream().filter(AbstractStep -> AbstractStep.stepType() == stepType).findFirst().get();
    }
}

