package com.github.mengweijin.quietly.cucumber.step.impl;

import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.mengweijin.quietly.cucumber.step.QuietlyStep.ASSERT_HTTP_RESPONSE_CODE;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Component(ASSERT_HTTP_RESPONSE_CODE)
public class AssertHttpResponseCode implements QuietlyStep {

    @Override
    @Then(ASSERT_HTTP_RESPONSE_CODE + " {long} {long}")
    public void execute(Long caseId, Long stepId){

    }
}
