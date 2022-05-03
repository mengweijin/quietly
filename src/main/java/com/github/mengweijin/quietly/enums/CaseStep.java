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
public enum CaseStep implements IEnum<String> {
    /**
     * 行为：执行一条 SQL 语句
     */
    ACTION_EXECUTE_SQL("执行 SQL"),

    /**
     * 行为：发送一个 http 请求
     */
    ACTION_SEND_HTTP_REQUEST("发送 HTTP 请求"),

    /**
     * 断言：上一个Action执行后，对其返回的结果进行字符串断言。expectValue.equals(responseActualValue)
     */
    ASSERT_STRING("字符串"),

    /**
     * 断言：上一个Action执行后，对其返回的结果进行JSON断言。断言期待的JSON值和 response 中响应的实际JSON值是否相匹配。
     */
    ASSERT_JSON("JSON"),

    /**
     * 断言：上一个Action执行后，对其返回的结果进行JSON-Path断言。用户提供一个 JSON-Path 表达式和期待值，以此和 response 响应的值做比对。
     */
    ASSERT_JSON_PATH("JSON-Path"),

    /**
     * 断言：上一个Action执行后，对其返回的结果进行XML-Path断言。用户提供一个 XML-path 表达式和期待值，以此和 response 响应的值做比对。
     */
    ASSERT_XML_PATH("XML-Path");

    private String label;

    CaseStep(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }

    public static List<String> getCaseStepTypes() {
        return Arrays.stream(CaseStep.values())
                .map(step -> StrUtil.subBefore(step.name(), '_', false))
                .distinct()
                .collect(Collectors.toList());
    }

    public static List<CaseStep> getCaseStepByPrefix(String prefix) {
        return Arrays.stream(CaseStep.values())
                .filter(step -> step.name().startsWith(prefix))
                .collect(Collectors.toList());
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
