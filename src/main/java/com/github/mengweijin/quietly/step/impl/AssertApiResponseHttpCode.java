package com.github.mengweijin.quietly.step.impl;

import cn.hutool.core.util.NumberUtil;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
@Service
public class AssertApiResponseHttpCode extends AbstractAssertApi {

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Override
    public StepType stepType() {
        return StepType.ASSERT_API_RESPONSE_HTTP_CODE;
    }

    @Override
    public Map<String, Object> doAssert(StepDefinition stepDefinition, ResponseEntity<Object> responseEntity) throws Exception {
        int expectValue = NumberUtil.parseInt(stepDefinition.getExpectValue());
        int actualValue = responseEntity.getStatusCode().value();
        log.debug("Expect value: {}", expectValue);
        log.debug("Actual value: {}", actualValue);
        stepDefinitionService.updateActualValueById(stepDefinition.getId(), String.valueOf(actualValue));

        Assertions.assertEquals(expectValue, actualValue);

        return null;
    }

}
