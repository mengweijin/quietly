package com.github.mengweijin.quietly.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quickboot.util.Const;
import com.github.mengweijin.quietly.config.thread.ProjectThreadPoolFactory;
import com.github.mengweijin.quietly.config.thread.RunCaseTask;
import com.github.mengweijin.quietly.enums.CaseStepStatus;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.step.AbstractStep;
import com.github.mengweijin.quietly.system.entity.CaseDefinition;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.mapper.CaseDefinitionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

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

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void runCase(Long id) {
        this.updateStatusById(id, CaseStepStatus.QUEUING);
        CaseDefinition caseDefinition = caseDefinitionMapper.selectById(id);
        ThreadPoolExecutor executor = ProjectThreadPoolFactory.getThreadPool(caseDefinition.getProjectId());
        executor.submit(new RunCaseTask(id, applicationEventPublisher));
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

    public void changeEnabled(Long id, String enabled) {
        this.lambdaUpdate().set(CaseDefinition::getEnabled, enabled).eq(CaseDefinition::getId, id).update();
    }

    public void copy(Long id) {
        CaseDefinition caseDefinition = caseDefinitionMapper.selectById(id);
        caseDefinition.setId(null);
        caseDefinition.setName(caseDefinition.getName() + "(duplicate)");
        caseDefinition.setEnabled(Const.N);
        // 会自动回填 ID
        caseDefinitionMapper.insert(caseDefinition);

        List<StepDefinition> stepDefinitionList = stepDefinitionService.getByCaseIdOrderBySeqAsc(id);
        stepDefinitionList = stepDefinitionList.stream().peek(step -> {
            step.setId(null);
            step.setCaseId(caseDefinition.getId());
        }).collect(Collectors.toList());
        stepDefinitionService.saveBatch(stepDefinitionList);
    }
}

