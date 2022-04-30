package com.github.mengweijin.quietly.cucumber.step.impl;

import cn.hutool.json.JSONUtil;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quietly.cucumber.ScenarioThreadLocal;
import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import com.github.mengweijin.quietly.system.entity.Api;
import com.github.mengweijin.quietly.system.entity.TestCase;
import com.github.mengweijin.quietly.system.service.ApiService;
import com.github.mengweijin.quietly.system.service.TestCaseService;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
        RestTemplate restTemplate = SpringUtils.getBean(RestTemplate.class);
        TestCaseService testCaseService = SpringUtils.getBean(TestCaseService.class);
        TestCase testCase = testCaseService.getById(caseId);

        Long apiId = testCase.getApiId();
        ApiService apiService = SpringUtils.getBean(ApiService.class);
        Api api = apiService.getById(apiId);

        HttpHeaders headers = ScenarioThreadLocal.get().getHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(testCase.getRequestArgs(), headers);
        Map<String, ?> uriVariables = new HashMap<>();
        if(JSONUtil.isJsonObj(testCase.getUrlArgs())) {
            uriVariables = JSONUtil.parseObj(testCase.getUrlArgs());
        }

        ResponseEntity<Object> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    api.getUrl(),
                    Objects.requireNonNull(HttpMethod.resolve(api.getRequestType())),
                    httpEntity,
                    Object.class,
                    uriVariables);
        } catch (HttpStatusCodeException e) {
            responseEntity = ResponseEntity
                    .status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }

        ScenarioThreadLocal.get().setResponseEntity(responseEntity);
    }
}
