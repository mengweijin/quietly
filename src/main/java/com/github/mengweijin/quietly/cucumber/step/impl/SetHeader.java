package com.github.mengweijin.quietly.cucumber.step.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quietly.cucumber.ScenarioThreadLocal;
import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import com.github.mengweijin.quietly.system.entity.Step;
import com.github.mengweijin.quietly.system.service.StepService;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import static com.github.mengweijin.quietly.cucumber.step.QuietlyStep.SET_HEADER;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Component(SET_HEADER)
public class SetHeader implements QuietlyStep {

    @Override
    @When(SET_HEADER + " {long} {long}")
    public void execute(Long caseId, Long stepId){
        StepService stepService = SpringUtils.getBean(StepService.class);
        Step step = stepService.getById(stepId);
        String headersJson = step.getActionOrAssertKey();
        if(JSONUtil.isJsonObj(headersJson)) {
            HttpHeaders headers = ScenarioThreadLocal.get().getHeaders();
            JSONObject jsonObject = JSONUtil.parseObj(headersJson);
            jsonObject.forEach((k, v) -> headers.add(k, StrUtil.toStringOrNull(v)));
        }
    }
}
