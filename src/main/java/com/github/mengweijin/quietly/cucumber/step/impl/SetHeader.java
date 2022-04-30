package com.github.mengweijin.quietly.cucumber.step.impl;

import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Component("SET_HEADER")
public class SetHeader implements QuietlyStep {

    @Override
    @When("SET_HEADER {long} {long}")
    public void execute(Long caseId, Long stepId){

    }
}
