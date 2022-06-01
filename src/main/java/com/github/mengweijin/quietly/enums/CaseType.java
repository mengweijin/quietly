package com.github.mengweijin.quietly.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author mengweijin
 */
public enum CaseType implements IEnum<String> {
    /**
     * 测试用例类型
     */
    API,

    E2E_FLOW,

    WEB_UI,

    MOBILE_UI;

    @Override
    public String getValue() {
        return this.name();
    }
}
