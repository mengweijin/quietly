package com.github.mengweijin.quietly.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * @author mengweijin
 */
public enum ResponseDataType implements IEnum<String> {
    /**
     * API 接口响应的数据类型
     */
    TEXT,

    ARRAY,

    JSON_OBJECT,

    JSON_ARRAY;

    @Override
    public String getValue() {
        return this.name();
    }
}
