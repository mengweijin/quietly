package com.github.mengweijin.quietly.system.controller;

import com.github.mengweijin.quietly.system.entity.TestCase;
import com.github.mengweijin.quietly.system.service.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.Serializable;

/**
 * <p>
 * test case Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api-case")
public class TestCaseController {

    /**
     * <p>
     * ApiCaseService
     * </p>
     */
    @Autowired
    private TestCaseService testCaseService;

    @GetMapping("/run/{caseId}")
    public void runCase(@PathVariable Long caseId) {
        testCaseService.runCase(caseId);
    }

    /**
     * <p>
     * Get ApiCase by id
     * </p>
     * @param id id
     * @return ApiCase
     */
    @GetMapping("/{id}")
    public TestCase getById(@PathVariable("id") Serializable id) {
        return testCaseService.getById(id);
    }

    /**
     * <p>
     * Add ApiCase
     * </p>
     * @param testCase apiCase
     */
    @PostMapping
    public void add(@Valid @RequestBody TestCase testCase) {
        testCaseService.save(testCase);
    }

    /**
     * <p>
     * Update ApiCase
     * </p>
     * @param testCase apiCase
     */
    @PutMapping
    public void update(@Valid @RequestBody TestCase testCase) {
        testCaseService.updateById(testCase);
    }

    /**
     * <p>
     * Delete ApiCase by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        testCaseService.removeById(id);
    }

}

