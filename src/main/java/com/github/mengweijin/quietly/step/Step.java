package com.github.mengweijin.quietly.step;

import com.github.mengweijin.quietly.enums.StepType;

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
    Object invoke(Long stepId, StepArgs stepArgs);

    /**
     * 入口方法
     * @param stepId stepId
     */
    default void run(Long stepId) {
        // 临时保存 StepArgs
        StepArgs stepArgs = StepContextHolder.get();

        // 重置 StepContextHolder
        StepContextHolder.get().setStepType(stepType()).setStepData(null).setStepDateType(null);

        // 执行逻辑
        Object object = this.invoke(stepId, stepArgs);

        // 设置  StepContextHolder
        StepContextHolder.get().setStepType(stepType()).setStepData(object).setStepDateType(StepArgs.selectDataType(object));
    }
}
