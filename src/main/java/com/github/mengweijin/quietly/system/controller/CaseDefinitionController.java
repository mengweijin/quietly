package com.github.mengweijin.quietly.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quietly.enums.CaseStepStatus;
import com.github.mengweijin.quietly.system.entity.CaseDefinition;
import com.github.mengweijin.quietly.system.service.CaseDefinitionService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * QTL_CASE_DEFINITION Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/case-definition")
public class CaseDefinitionController  {

    /**
     * <p>
     * CaseDefinitionService
     * </p>
     */
    @Autowired
    private CaseDefinitionService caseDefinitionService;

    @GetMapping("/getCaseStepStatusEnums")
    @Cacheable("CASE_DEFINITION_STATUS_ARRAY")
    public CaseStepStatus[] getCaseStepStatusEnums() {
        return CaseStepStatus.values();
    }

    @PostMapping("/changeEnabled/{id}")
    public void changeEnabled(@PathVariable("id") Long id, String enabled) {
        caseDefinitionService.changeEnabled(id, enabled);
    }
    @GetMapping("/page")
    public PageDTO<CaseDefinition> page(PageDTO<CaseDefinition> page, CaseDefinition caseDefinition) {
        return caseDefinitionService.page(page, new QueryWrapper<>(caseDefinition));
    }

    @GetMapping("/runCase/{id}")
    public void runCase(@PathVariable("id") Long id) {
        caseDefinitionService.runCase(id);
    }

    /**
     * <p>
     * Get CaseDefinition by id
     * </p>
     * @param id id
     * @return CaseDefinition
     */
    @GetMapping("/{id}")
    public CaseDefinition getById(@PathVariable("id") Long id) {
        return caseDefinitionService.getById(id);
    }

    /**
     * <p>
     * Add CaseDefinition
     * </p>
     * @param caseDefinition caseDefinition
     */
    @PostMapping
    public void add(@Valid @RequestBody CaseDefinition caseDefinition) {
        caseDefinition.setStatus(CaseStepStatus.CREATED);
        caseDefinition.setEnabled(Const.N);
        caseDefinitionService.save(caseDefinition);
    }

    @PostMapping("/copy/{id}")
    public void copy(@PathVariable("id") Long id) {
        caseDefinitionService.copy(id);
    }

    /**
     * <p>
     * Update CaseDefinition
     * </p>
     * @param caseDefinition caseDefinition
     */
    @PutMapping
    public void update(@Valid @RequestBody CaseDefinition caseDefinition) {
        caseDefinitionService.updateById(caseDefinition);
    }

    /**
     * <p>
     * Delete CaseDefinition by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        caseDefinitionService.removeById(id);
    }

}

