package com.github.mengweijin.quietly.system.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quickboot.mybatis.entity.BaseEntity;
import com.github.mengweijin.quietly.enums.CaseStepStatus;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.mapper.StepDefinitionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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

    public void updateStatusById(Long stepId, CaseStepStatus caseStepStatus) {
        StepDefinition stepDefinition = new StepDefinition();
        stepDefinition.setId(stepId);
        stepDefinition.setStatus(caseStepStatus);
        stepDefinitionMapper.updateById(stepDefinition);
    }


    public void updateActualValueById(Long stepId, String actualValue) {
        StepDefinition stepDefinition = new StepDefinition();
        stepDefinition.setId(stepId);
        stepDefinition.setActualValue(actualValue);
        stepDefinitionMapper.updateById(stepDefinition);
    }

    public List<StepDefinition> getByCaseIdOrderBySeqAsc(Serializable caseId) {
        return this.lambdaQuery().eq(StepDefinition::getCaseId, caseId).orderByAsc(StepDefinition::getSeq).list();
    }

    public void updateApiRequestActualInfoById(Long stepId, String info) {
        StepDefinition stepDefinition = new StepDefinition();
        stepDefinition.setId(stepId);
        stepDefinition.setApiRequestActualInfo(info);
        stepDefinitionMapper.updateById(stepDefinition);
    }

    public void updateStatusBatchByIds(List<Long> idList, CaseStepStatus status) {
        if(CollUtil.isEmpty(idList)) {
            return;
        }
        this.lambdaUpdate()
                .set(StepDefinition::getStatus, status)
                .in(StepDefinition::getId, idList)
                .update();
    }

    public void updateStatusBatchByIdsOfSteps(List<StepDefinition> stepDefinitionList, CaseStepStatus status) {
        if(CollUtil.isEmpty(stepDefinitionList)) {
            return;
        }
        List<Long> stepIdList = stepDefinitionList.stream().map(BaseEntity::getId).collect(Collectors.toList());
        this.updateStatusBatchByIds(stepIdList, status);
    }

    public void updateErrorInfoById(Long stepId, String errorInfo) {
        this.lambdaUpdate().set(StepDefinition::getErrorInfo, errorInfo).eq(StepDefinition::getId, stepId).update();
    }

}

