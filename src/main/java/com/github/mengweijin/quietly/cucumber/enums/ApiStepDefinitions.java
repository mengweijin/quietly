package com.github.mengweijin.quietly.cucumber.enums;

import com.baomidou.mybatisplus.annotation.IEnum;
import lombok.Getter;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Getter
public enum ApiStepDefinitions implements IEnum<String> {

    WHEN_EXECUTE_SQL("When EXECUTE_SQL"),

    GIVEN_GET_TOKEN("Given GET_TOKEN"),

    GIVEN_SET_HEADER("Given SET_HEADER"),

    WHEN_CALL_API("When CALL_API"),

    THEN_ASSERT_HTTP_CODE("Then ASSERT_HTTP_CODE"),

    THEN_ASSERT_RESPONSE_BODY("Then ASSERT_RESPONSE_BODY"),

    THEN_ASSERT_RESPONSE_BY_JSON_PATH("Then ASSERT_RESPONSE_BY_JSON_PATH"),

    THEN_ASSERT_DB_DATA("Then ASSERT_DB_DATA"),

    THEN_CLEAN_DB_TEST_DATA("Then CLEAN_DB_TEST_DATA");

    private String desc;

    ApiStepDefinitions(String desc){
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.name();
    }

}
