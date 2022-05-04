package com.github.mengweijin.quietly.step;

import cn.hutool.extra.spring.SpringUtil;
import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
import com.github.mengweijin.quietly.enums.CaseStatus;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
public interface Step {

    /**
     * 支持的 StepType 类型
     * @return StepType
     */
    StepType stepType();

    /**
     * 子类实现方法
     * @param stepId stepId
     * @param stepArgs stepArgs
     */
    Object invoke(Long stepId, StepArgs stepArgs) throws Exception;

    /**
     * 入口方法
     * @param stepId stepId
     */
    default void run(Long stepId) {
        StepDefinitionService stepDefinitionService = SpringUtil.getBean(StepDefinitionService.class);
        try{
            stepDefinitionService.updateStatusById(stepId, CaseStatus.RUNNING);

            // 临时保存 StepArgs
            StepArgs stepArgs = StepContextHolder.get();

            // 重置 StepContextHolder
            StepContextHolder.get().setStepType(stepType()).setStepData(null);

            // 执行逻辑
            Object object = this.invoke(stepId, stepArgs);

            // 设置  StepContextHolder
            StepContextHolder.get().setStepType(stepType()).setStepData(object);

            stepDefinitionService.updateStatusById(stepId, CaseStatus.SUCCESS);
        } catch (Throwable t) {
            StepDefinition stepDefinition = new StepDefinition();
            stepDefinition.setId(stepId);
            stepDefinition.setStatus(CaseStatus.FAILED);
            stepDefinition.setErrorInfo(t.getMessage());
            stepDefinitionService.updateById(stepDefinition);
            throw new QuickBootException(t);
        }
    }
}
