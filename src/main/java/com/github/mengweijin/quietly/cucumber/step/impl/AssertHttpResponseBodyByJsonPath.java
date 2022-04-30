package com.github.mengweijin.quietly.cucumber.step.impl;

import cn.hutool.json.JSONUtil;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quietly.cucumber.ScenarioThreadLocal;
import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import com.github.mengweijin.quietly.system.entity.Step;
import com.github.mengweijin.quietly.system.service.StepService;
import com.jayway.jsonpath.JsonPath;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static com.github.mengweijin.quietly.cucumber.step.QuietlyStep.ASSERT_HTTP_RESPONSE_BODY_BY_JSON_PATH;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Component(ASSERT_HTTP_RESPONSE_BODY_BY_JSON_PATH)
public class AssertHttpResponseBodyByJsonPath implements QuietlyStep {

    @Override
    @Then(ASSERT_HTTP_RESPONSE_BODY_BY_JSON_PATH + " {long} {long}")
    public void execute(Long caseId, Long stepId) throws JSONException {
        StepService stepService = SpringUtils.getBean(StepService.class);
        ResponseEntity<Object> responseEntity = ScenarioThreadLocal.get().getResponseEntity();
        Step testStep = stepService.getById(stepId);
        String[] jsonPathArray = testStep.getActionOrAssertKey().split(Const.SEMICOLON);
        String expectValue = testStep.getExpectValue();

        String responseValue = JSONUtil.toJsonStr(responseEntity.getBody());

        JSONObject jsonObject = new JSONObject();
        for (String jsonPath : jsonPathArray) {
            Object object = JsonPath.read(responseValue, jsonPath);
            jsonObject.put(jsonPath, object);
        }

        log.debug("Expect value: {}", expectValue);
        log.debug("Actual value: {}", jsonObject);
        stepService.updateActualValueById(stepId, jsonObject.toString());

        JSONAssert.assertEquals(expectValue, jsonObject, false);
    }
}
