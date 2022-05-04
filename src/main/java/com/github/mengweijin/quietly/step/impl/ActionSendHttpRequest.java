package com.github.mengweijin.quietly.step.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.step.Step;
import com.github.mengweijin.quietly.step.StepArgs;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
@Service
public class ActionSendHttpRequest implements Step {

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public StepType stepType() {
        return StepType.ACTION_SEND_HTTP_REQUEST;
    }

    @Override
    public Object invoke(Long stepId, StepArgs stepArgs) {
        StepDefinition stepDefinition = stepDefinitionService.getById(stepId);

        // header
        HttpHeaders headers = new HttpHeaders();
        String headersJson = stepDefinition.getApiArgs();
        if(StrUtil.isNotBlank(headersJson)) {
            JSONObject jsonObject = JSONUtil.parseObj(headersJson);

            HashMap<?, ?> argsMap = new HashMap<>(16);
            if(StepArgs.DataType.JSON_OBJECT == StepArgs.getDataType(stepArgs.getStepData())) {
                argsMap = objectMapper.convertValue(stepArgs.getStepData(), HashMap.class);
            }

            for (Map.Entry<String, Object> entry: jsonObject.entrySet()) {
                String value = StrUtil.toStringOrNull(entry.getValue());
                boolean match = ReUtil.isMatch("^\\$\\{\\S*}$", value);
                if(match) {
                    String key = StrUtil.sub(value, 1, value.lastIndexOf("}"));
                    value = StrUtil.toStringOrNull(argsMap.get(key));
                }
                headers.add(entry.getKey(), value);
            }
        }






        return null;
    }

    public String headerProcess(StepArgs stepArgs, StepDefinition stepDefinition){


        return null;
    }

    public static void main(String[] args) {
        String v = "123123wr${aaabb123}";
        boolean match = ReUtil.isMatch("^\\$\\{\\S+}$", StrUtil.toStringOrNull(v));
        System.out.println(match);


        boolean contains = ReUtil.contains("\\$\\{\\S+}", v);
        System.out.println(contains);
    }
}
