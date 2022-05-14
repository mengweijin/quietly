package com.github.mengweijin.quietly.listener;

import com.github.mengweijin.quietly.enums.CaseStepStatus;
import com.github.mengweijin.quietly.listener.event.CaseFailedEvent;
import com.github.mengweijin.quietly.listener.event.CaseStartEvent;
import com.github.mengweijin.quietly.listener.event.CaseSuccessEvent;
import com.github.mengweijin.quietly.listener.event.StepFailedEvent;
import com.github.mengweijin.quietly.listener.event.StepStartEvent;
import com.github.mengweijin.quietly.listener.event.StepSuccessEvent;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.CaseDefinitionService;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * application listener for run case and steps
 *
 * @author mengweijin
 * @date 2022/5/14
 */
@Component
public class RunCaseApplicationListener {

    @Autowired
    private CaseDefinitionService caseDefinitionService;

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @EventListener(value = CaseStartEvent.class)
    public void onApplicationEvent(CaseStartEvent event) {
        Long caseId = event.getCaseId();
        // update case status to running
        caseDefinitionService.updateStatusById(caseId, CaseStepStatus.RUNNING);
        // update steps status of case to queuing
        List<StepDefinition> stepDefinitionList = stepDefinitionService.getByCaseIdOrderBySeqAsc(caseId);
        stepDefinitionService.updateStatusBatchByIdsOfSteps(stepDefinitionList, CaseStepStatus.QUEUING);
    }

    @EventListener(value = StepStartEvent.class)
    public void onApplicationEvent(StepStartEvent event) {
        stepDefinitionService.updateStatusById(event.getStepId(), CaseStepStatus.RUNNING);
    }

    @EventListener(value = StepSuccessEvent.class)
    public void onApplicationEvent(StepSuccessEvent event) {
        Long stepId = event.getStepId();
        stepDefinitionService.updateStatusById(stepId, CaseStepStatus.SUCCESS);
        // clear error info
        stepDefinitionService.updateErrorInfoById(stepId, null);
    }

    @EventListener(value = StepFailedEvent.class)
    public void onApplicationEvent(StepFailedEvent event) {
        Long stepId = event.getStepId();
        StepDefinition stepDefinition = new StepDefinition();
        stepDefinition.setId(stepId);
        stepDefinition.setStatus(CaseStepStatus.FAILED);
        stepDefinition.setErrorInfo(event.getThrowable().getMessage());
        stepDefinitionService.updateById(stepDefinition);

        // Update status of the execution of subsequent steps to canceled
        StepDefinition step = stepDefinitionService.getById(stepId);
        List<StepDefinition> stepDefinitionList = stepDefinitionService.getByCaseIdOrderBySeqAsc(step.getCaseId());
        List<Long> stepIdList = stepDefinitionList.stream()
                .filter(item -> item.getSeq() > step.getSeq())
                .map(StepDefinition::getId)
                .collect(Collectors.toList());
        stepDefinitionService.updateStatusBatchByIds(stepIdList, CaseStepStatus.CANCELED);
    }

    @EventListener(value = CaseSuccessEvent.class)
    public void onApplicationEvent(CaseSuccessEvent event) {
        caseDefinitionService.updateStatusById(event.getCaseId(), CaseStepStatus.SUCCESS);
    }

    @EventListener(value = CaseFailedEvent.class)
    public void onApplicationEvent(CaseFailedEvent event) {
        caseDefinitionService.updateStatusById(event.getCaseId(), CaseStepStatus.FAILED);
    }
}
