package com.github.mengweijin.quietly.cucumber.step.impl;

import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.github.mengweijin.quietly.cucumber.step.QuietlyStep.CALL_API;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Component(CALL_API)
public class CallApi implements QuietlyStep {

    @Override
    @When(CALL_API + " {long} {long}")
    public void execute(Long caseId, Long stepId){

    }
}
