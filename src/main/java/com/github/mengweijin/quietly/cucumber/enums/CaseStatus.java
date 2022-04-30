package com.github.mengweijin.quietly.cucumber.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author mengweijin
 */

public enum CaseStatus implements IEnum<String> {
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
