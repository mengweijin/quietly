package com.github.mengweijin.quietly.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author mengweijin
 */
public enum DictTypeCode implements IEnum<String> {
    /**
     * 字典类型编码，需要和数据库对应。
     */
    case_step_type;

    @Override
    public String getValue() {
        return this.name();
    }
}
