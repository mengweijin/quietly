package com.github.mengweijin.quietly.cucumber.step.impl;

import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quietly.cucumber.ScenarioThreadLocal;
import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import com.github.mengweijin.quietly.system.service.StepService;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.ResponseEntity;
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
        ResponseEntity<Object> responseEntity = ScenarioThreadLocal.get().getResponseEntity();
        StepService stepService = SpringUtils.getBean(StepService.class);

        String expectValue = stepService.getById(stepId).getExpectValue();
        String actualValue = String.valueOf(responseEntity.getStatusCode().value());
        log.debug("Expect value: {}", expectValue);
        log.debug("Actual value: {}", actualValue);
        stepService.updateActualValueById(stepId, actualValue);

        Assertions.assertEquals(expectValue, actualValue);
    }
}
