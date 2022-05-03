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
     * 执行
     * @param stepId stepId
     */
    void invoke(Long stepId);
}
