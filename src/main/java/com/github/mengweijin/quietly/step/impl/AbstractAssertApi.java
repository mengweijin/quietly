package com.github.mengweijin.quietly.step.impl;

import com.github.mengweijin.quietly.step.Step;
import com.github.mengweijin.quietly.step.StepArgs;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
public abstract class AbstractAssertApi implements Step {

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Override
    public Map<String, Object> invoke(Long stepId, StepArgs stepArgs) throws Exception {
        StepDefinition stepDefinition = stepDefinitionService.getById(stepId);

        Optional<ResponseEntity<Object>> optional = Optional.ofNullable(stepArgs)
                .map(StepArgs::getData)
                .map(map -> map.get(StepArgs.API_RESPONSE_ENTITY))
                .map(entity -> (ResponseEntity<Object>) entity);

        ResponseEntity<Object> responseEntity = null;
        if(optional.isPresent()) {
            responseEntity = optional.get();
        }

        Map<String, Object> objectMap = doAssert(stepDefinition, responseEntity);
        if(objectMap == null) {
            objectMap = new HashMap<>();
        }

        // 参数传递
        objectMap.put(StepArgs.API_RESPONSE_ENTITY, responseEntity);
        return objectMap;
    }

    /**
     * do assert
     * @param stepDefinition stepDefinition
     * @param responseEntity responseEntity
     * @return Map<String, Object>
     * @throws Exception exception
     */
    public abstract Map<String, Object> doAssert(StepDefinition stepDefinition, final ResponseEntity<Object> responseEntity) throws Exception;

}
