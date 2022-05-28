package com.github.mengweijin.quietly.config.thread;

import cn.hutool.extra.spring.SpringUtil;
import com.github.mengweijin.quietly.listener.event.CaseFailedEvent;
import com.github.mengweijin.quietly.listener.event.CaseStartEvent;
import com.github.mengweijin.quietly.listener.event.CaseSuccessEvent;
import com.github.mengweijin.quietly.step.AbstractStep;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.CaseDefinitionService;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/5/28
 */
@AllArgsConstructor
public class RunCaseTask implements Runnable {
    private Long id;

    private ApplicationEventPublisher publisher;

    @Override
    public void run() {
        CaseDefinitionService caseService = SpringUtil.getBean(CaseDefinitionService.class);
        StepDefinitionService stepService = SpringUtil.getBean(StepDefinitionService.class);

        try {
            publisher.publishEvent(new CaseStartEvent(this, id));

            List<StepDefinition> stepDefinitionList = stepService.getByCaseIdOrderBySeqAsc(id);
            stepDefinitionList.forEach(stepDefinition -> {
                AbstractStep step = caseService.getStepByStepType(stepDefinition.getStepType());
                step.run(stepDefinition.getId());
            });

            publisher.publishEvent(new CaseSuccessEvent(this, id));
        } catch (Throwable t) {
            publisher.publishEvent(new CaseFailedEvent(this, id));
        }
    }
}
