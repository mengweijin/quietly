package com.github.mengweijin.quietly.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author mengweijin
 */
public enum CaseStepStatus implements IEnum<String> {
    /**
     * 用例执行状态
     */
    CREATED,

    QUEUING,

    RUNNING,

    SUCCESS,

    FAILED;

    @Override
    public String getValue() {
        return this.name();
    }
}
