package com.github.mengweijin.quietly.cucumber.step.impl;

import cn.hutool.json.JSONUtil;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quietly.cucumber.ScenarioThreadLocal;
import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import com.github.mengweijin.quietly.system.service.StepService;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

import static com.github.mengweijin.quietly.cucumber.step.QuietlyStep.ASSERT_HTTP_RESPONSE_BODY;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Component(ASSERT_HTTP_RESPONSE_BODY)
public class AssertHttpResponseBody implements QuietlyStep {

    @Override
    @Then(ASSERT_HTTP_RESPONSE_BODY + " {long} {long}")
    public void execute(Long caseId, Long stepId) throws JSONException {
        StepService stepService = SpringUtils.getBean(StepService.class);
        ResponseEntity<Object> responseEntity = ScenarioThreadLocal.get().getResponseEntity();
        Object body = responseEntity.getBody();
        String expectValue = stepService.getById(stepId).getExpectValue();
        String actualValue = JSONUtil.toJsonStr(body);
        log.debug("Expect value: {}", expectValue);
        log.debug("Actual value: {}", actualValue);
        stepService.updateActualValueById(stepId, actualValue);

        if (body instanceof Map) {
            JSONAssert.assertEquals(expectValue, new JSONObject(actualValue), false);
        } else if (body instanceof Collection) {
            JSONAssert.assertEquals(expectValue, new JSONArray(actualValue), false);
        } else {
            Assertions.assertEquals(expectValue, String.valueOf(body));
        }
    }
}
