package com.github.mengweijin.quietly.step.impl;

import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.step.AbstractAssertApi;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
@Component
public class AssertApiResponseByText extends AbstractAssertApi {

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Override
    public StepType stepType() {
        return StepType.ASSERT_API_RESPONSE_BY_TEXT;
    }

    @Override
    public Map<String, Object> doAssert(StepDefinition stepDefinition, ResponseEntity<Object> responseEntity) throws Exception {
        String expectValue = stepDefinition.getExpectValue();
        String actualValue = String.valueOf(responseEntity.getBody());
        log.debug("Expect value: {}", expectValue);
        log.debug("Actual value: {}", actualValue);
        stepDefinitionService.updateActualValueById(stepDefinition.getId(), actualValue);

        Assertions.assertEquals(expectValue, actualValue);

        return null;
    }

}
