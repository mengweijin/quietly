package com.github.mengweijin.quietly.step.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
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
public class SendHttpRequestAction implements Step {

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
        String headersJson = stepDefinition.getActionApiHeaders();
        if(StrUtil.isNotBlank(headersJson)) {
            JSONObject jsonObject = JSONUtil.parseObj(headersJson);

            HashMap<?, ?> argsMap = new HashMap<>();
            if(stepArgs.getStepData() != null && stepArgs.getStepDateType() == StepArgs.DataType.JSON_OBJECT) {
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
        String headersJson = stepDefinition.getActionApiHeaders();
        if(!JSONUtil.isJsonObj(headersJson)) {
            throw new QuickBootException("Can't parse header args, because it is not a correct JSON object, please check you step definition config.");
        }



        boolean contains = ReUtil.contains("\\$\\{\\S+}", headersJson);
        // contain ${\S+} ----> need to process header placeholder value.
        if(contains) {
            StepArgs.DataType stepDateType = stepArgs.getStepDateType();
            if(StepArgs.DataType.JSON_OBJECT != stepDateType) {
                throw new QuickBootException("Can't parse StepArgs to process header placeholder value, because the StepArgs is not a JSON object, please check you code.");
            }
            JSONObject jsonObject = JSONUtil.parseObj(stepArgs.getStepData());
        }

        JSONObject jsonObject = JSONUtil.parseObj(headersJson);
        HttpHeaders headers = new HttpHeaders();
        jsonObject.forEach((k, v) -> {

            boolean match = ReUtil.isMatch("^\\$\\{\\S+}$", StrUtil.toStringOrNull(v));
            if(match) {

            }
            headers.add(k, StrUtil.toStringOrNull(v));
        });







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
