package com.github.mengweijin.quietly.step.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.exception.QuickBootException;
import com.github.mengweijin.quickboot.util.Const;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.step.AbstractStep;
import com.github.mengweijin.quietly.step.StepArgs;
import com.github.mengweijin.quietly.system.dto.ApiRequestActualInfoDto;
import com.github.mengweijin.quietly.system.entity.ApiDefinition;
import com.github.mengweijin.quietly.system.entity.Project;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.ApiDefinitionService;
import com.github.mengweijin.quietly.system.service.ProjectService;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
@Component
public class ActionCallApi extends AbstractStep {

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

        String url = this.processPlaceholder(this.getApiUrl(apiDefinition), argsMap);

        // header
        String requestMediaType = apiDefinition.getRequestMediaType();
        HttpHeaders headers = new HttpHeaders();
        String apiHeaders = this.processPlaceholder(stepDefinition.getApiHeaders(), argsMap);
        if(StrUtil.isNotBlank(apiHeaders)) {
            headers = objectMapper.convertValue(apiHeaders, HttpHeaders.class);
            headers.setContentType(MediaType.parseMediaType(requestMediaType));
        }

        String requestBody =  this.processPlaceholder(stepDefinition.getApiBodyArgs(), argsMap);

        HttpEntity<Object> httpEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Object> responseEntity = null;
        try {
            ApiRequestActualInfoDto dto = new ApiRequestActualInfoDto();
            dto.setUrl(url);
            dto.setHeaders(headers);
            dto.setRequestBody(requestBody);
            stepDefinitionService.updateApiRequestActualInfoById(stepId, dto);

            responseEntity = restTemplate.exchange(url, apiDefinition.getHttpMethod(), httpEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            responseEntity = ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders()).body(e.getResponseBodyAsString());
        } finally {
            String body = this.getResponseEntityBody(responseEntity);
            stepDefinitionService.updateActualValueById(stepId, body);
        }

        Map<String, Object> map = new HashMap<>(1);
        map.put(StepArgs.KEY_API_RESPONSE_ENTITY, responseEntity);
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

    private String getApiUrl(ApiDefinition apiDefinition) {
        ProjectService projectService = SpringUtil.getBean(ProjectService.class);
        Project project = projectService.getById(apiDefinition.getProjectId());
        String baseUrl = project.getBaseUrl();
        String url = apiDefinition.getUrl();
        if(StrUtil.isBlank(baseUrl) || StrUtil.isBlank(url)) {
            return url;
        }

        if(HttpUtil.isHttp(url) || HttpUtil.isHttps(url)) {
            return url;
        }

        baseUrl = baseUrl.endsWith(Const.SLASH) ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        url = url.startsWith(Const.SLASH) ? url.substring(1) : url;
        return String.join(Const.SLASH, baseUrl, url);
    }

    private String getResponseEntityBody(ResponseEntity<Object> responseEntity) {
        if(responseEntity == null) {
            return Const.EMPTY;
        }
        Object body = responseEntity.getBody();
        if(body == null) {
            return Const.EMPTY;
        }
        try {
            return objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new QuickBootException(e);
        }
    }
}
