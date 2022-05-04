package com.github.mengweijin.quietly.enums;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IEnum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用例步骤枚举
 * @author mengweijin
 */
public enum StepType implements IEnum<String> {
    /**
     * 行为：执行一条或多条 insert/update/delete SQL 语句，多个语句以 ; 分割开。
     */
    ACTION_EXECUTE_UPDATE_SQL("动作-执行更新SQL语句"),

    /**
     * 行为：发送一个 http 请求
     */
    ACTION_CALL_API("动作-调用接口"),

    ASSERT_DB_BY_QUERY_SQL("断言-数据库-根据一个查询SQL"),

    /**
     * 断言：上一个Action执行后，对其返回的结果进行HTTP 状态码断言。如：200，400，404，500
     */
    ASSERT_API_RESPONSE_HTTP_CODE("断言-API响应状态码"),

    /**
     * 断言：上一个Action执行后，对其返回的结果进行字符串断言。expectValue.equals(responseActualValue)
     */
    ASSERT_API_RESPONSE_BY_TEXT("断言-字符串文本"),

    /**
     * 断言：上一个Action执行后，对其返回的结果进行JSON断言。断言期待的JSON值和 response 中响应的实际JSON值是否相匹配。
     */
    ASSERT_API_RESPONSE_BY_JSON_OBJECT("断言-JSON-OBJECT"),

    /**
     * 断言：上一个Action执行后，对其返回的结果进行JSON断言。断言期待的JSON值和 response 中响应的实际JSON值是否相匹配。
     */
    ASSERT_API_RESPONSE_BY_JSON_ARRAY("断言-JSON-ARRAY"),

    /**
     * 断言：上一个Action执行后，对其返回的结果进行JSON-Path断言。用户提供一个 JSON-Path 表达式和期待值，以此和 response 响应的值做比对。
     */
    ASSERT_API_RESPONSE_BY_JSON_PATH("断言-根据 JSON-Path 表达式"),

    /**
     * 断言：上一个Action执行后，对其返回的结果进行XML-Path断言。用户提供一个 XML-path 表达式和期待值，以此和 response 响应的值做比对。
     */
    ASSERT_API_RESPONSE_BY_XML_PATH("断言-根据 XML-Path 表达式");

    private String label;

    StepType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static List<String> getStepTypes() {
        return Arrays.stream(StepType.values())
                .map(step -> StrUtil.subBefore(step.name(), '_', false))
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<StepType> getStepByPrefix(String prefix) {
        return Arrays.stream(StepType.values())
                .filter(step -> step.name().startsWith(prefix))
                .collect(Collectors.toList());
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
