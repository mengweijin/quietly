package com.github.mengweijin.quietly.system.controller;

import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.system.dto.CaseStepDto;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * QTL_STEP_DEFINITION Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/step-definition")
public class StepDefinitionController  {

    /**
     * <p>
     * StepDefinitionService
     * </p>
     */
    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Cacheable("stepDefinition")
    @GetMapping("/getStepTypeList")
    public List<String> getStepTypeList() {
        return StepType.getStepTypes();
    }

    @Cacheable("stepDefinition")
    @GetMapping("/getStepListByType")
    public List<CaseStepDto> getStepListByType(@RequestParam @NotBlank String stepType) {
        return StepType.getStepByPrefix(stepType).stream()
                .map(step -> new CaseStepDto().setKey(step.name()).setName(step.getLabel()))
                .collect(Collectors.toList());
    }

    /**
     * <p>
     * Get StepDefinition by id
     * </p>
     * @param id id
     * @return StepDefinition
     */
    @GetMapping("/{id}")
    public StepDefinition getById(@PathVariable("id") Serializable id) {
        return stepDefinitionService.getById(id);
    }

    /**
     * <p>
     * Add StepDefinition
     * </p>
     * @param stepDefinition stepDefinition
     */
    @PostMapping
    public void add(@Valid @RequestBody StepDefinition stepDefinition) {
        stepDefinitionService.save(stepDefinition);
    }

    /**
     * <p>
     * Update StepDefinition
     * </p>
     * @param stepDefinition stepDefinition
     */
    @PutMapping
    public void update(@Valid @RequestBody StepDefinition stepDefinition) {
        stepDefinitionService.updateById(stepDefinition);
    }

    /**
     * <p>
     * Delete StepDefinition by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        stepDefinitionService.removeById(id);
    }

}

