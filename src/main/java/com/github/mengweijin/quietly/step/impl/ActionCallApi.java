package com.github.mengweijin.quietly.step.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.framework.exception.QuickBootException;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.step.Step;
import com.github.mengweijin.quietly.step.StepArgs;
import com.github.mengweijin.quietly.system.dto.ApiArgsDto;
import com.github.mengweijin.quietly.system.dto.ApiRequestActualInfoDto;
import com.github.mengweijin.quietly.system.entity.ApiDefinition;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.ApiDefinitionService;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
@Service
public class ActionCallApi implements Step {

    public static final String REGEX_PLACEHOLDER = "\\$\\{\\S+}";

    public static final String REGEX_PLACEHOLDER_VALUE = "^\\$\\{\\S*}$";

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Autowired
    private ApiDefinitionService apiDefinitionService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public StepType stepType() {
        return StepType.ACTION_CALL_API;
    }

    @Override
    public Map<String, Object> invoke(Long stepId, StepArgs stepArgs) {
        StepDefinition stepDefinition = stepDefinitionService.getById(stepId);
        Map<String, Object> argsMap = stepArgs.getData();

        Long apiId = stepDefinition.getApiId();
        ApiDefinition apiDefinition = apiDefinitionService.getById(apiId);
        String url = this.processPlaceholder(apiDefinition.getUrl(), argsMap);

        String requestMediaType = apiDefinition.getRequestMediaType();

        String apiArgs =  this.processPlaceholder(stepDefinition.getApiArgs(), argsMap);
        // header
        HttpHeaders headers = new HttpHeaders();
        Object requestBody = new Object();
        if(StrUtil.isNotBlank(apiArgs)) {
            ApiArgsDto apiArgsDto = objectMapper.convertValue(apiArgs, ApiArgsDto.class);
            headers = apiArgsDto.getHeaders();
            headers.setContentType(MediaType.parseMediaType(requestMediaType));
            requestBody = apiArgsDto.getRequestBody();
        }

        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> responseEntity;
        try {
            ApiRequestActualInfoDto dto = new ApiRequestActualInfoDto();
            dto.setUrl(url);
            dto.setHeaders(headers);
            dto.setRequestBody(requestBody);
            String info = objectMapper.writeValueAsString(dto);
            stepDefinitionService.updateApiRequestActualInfoById(stepId, info);

            responseEntity = restTemplate.exchange(url, apiDefinition.getHttpMethod(), httpEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            responseEntity = ResponseEntity
                    .status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        } catch (JsonProcessingException e) {
            throw new QuickBootException(e.getMessage());
        }

        Map<String, Object> map = new HashMap<>(1);
        map.put(StepArgs.API_RESPONSE_ENTITY, responseEntity);
        return map;
    }

    public String processPlaceholder(String content, Map<String, Object> argsMap) {
        if(content != null && argsMap != null && ReUtil.contains(REGEX_PLACEHOLDER, content)) {
            for (Map.Entry<String, Object> entry: argsMap.entrySet()) {
                content = ReUtil.replaceAll(content, "\\$\\{" + entry.getKey() + "}", StrUtil.toStringOrNull(entry.getValue()));
            }
        }
        return content;
    }
}
