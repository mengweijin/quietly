package com.github.mengweijin.quietly.cucumber.step.impl;

import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Component("ASSERT_HTTP_RESPONSE_BODY_BY_JSON_PATH")
public class AssertHttpResponseBodyByJsonPath implements QuietlyStep {

    @Override
    @Then("ASSERT_HTTP_RESPONSE_BODY_BY_JSON_PATH {long} {long}")
    public void execute(Long caseId, Long stepId){

    }
}
