package com.github.mengweijin.quietly.step;

import cn.hutool.extra.spring.SpringUtil;
import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
import com.github.mengweijin.quietly.enums.CaseStepStatus;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import java.util.Map;

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
     * @return
     */
    Map<String, Object> invoke(Long stepId, StepArgs stepArgs) throws Exception;

    /**
     * 入口方法
     * @param stepId stepId
     */
    default void run(Long stepId) {
        StepDefinitionService stepDefinitionService = SpringUtil.getBean(StepDefinitionService.class);
        try{
            stepDefinitionService.updateStatusById(stepId, CaseStepStatus.RUNNING);

            // 临时保存 StepArgs
            StepArgs stepArgs = StepContextHolder.get();

            // 重置 StepContextHolder
            StepContextHolder.get().setData(null);

            // 执行逻辑
            Map<String, Object> map = this.invoke(stepId, stepArgs);

            // 设置  StepContextHolder
            StepContextHolder.get().setData(map);

            stepDefinitionService.updateStatusById(stepId, CaseStepStatus.SUCCESS);
        } catch (Throwable t) {
            StepDefinition stepDefinition = new StepDefinition();
            stepDefinition.setId(stepId);
            stepDefinition.setStatus(CaseStepStatus.FAILED);
            stepDefinition.setErrorInfo(t.getMessage());
            stepDefinitionService.updateById(stepDefinition);
            throw new QuickBootException(t);
        }
    }
}
