package com.github.mengweijin.quietly.step.impl;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import com.jayway.jsonpath.JsonPath;
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
public class AssertApiResponseByJsonPath extends AbstractAssertApi {

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public StepType stepType() {
        return StepType.ASSERT_API_RESPONSE_BY_JSON_PATH;
    }

    @Override
    public Map<String, Object> doAssert(StepDefinition stepDefinition, ResponseEntity<Object> responseEntity) throws Exception {
        String expression = stepDefinition.getExpression();
        String responseBody = objectMapper.writeValueAsString(responseEntity.getBody());

        String expectValue = stepDefinition.getExpectValue();
        String actualValue = StrUtil.toStringOrNull(JsonPath.read(responseBody, expression));

        log.debug("Expect value: {}", expectValue);
        log.debug("Actual value: {}", actualValue);
        stepDefinitionService.updateActualValueById(stepDefinition.getId(), actualValue);

        Assertions.assertEquals(expectValue, actualValue);

        return null;
    }

}
