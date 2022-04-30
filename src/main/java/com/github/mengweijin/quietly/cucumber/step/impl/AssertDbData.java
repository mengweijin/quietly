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
@Component("ASSERT_DB_DATA")
public class AssertDbData implements QuietlyStep {

    @Override
    @Then("ASSERT_DB_DATA {long} {long}")
    public void execute(Long caseId, Long stepId){

    }
}
