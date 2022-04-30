package com.github.mengweijin.quietly.system.controller;

import com.github.mengweijin.quietly.system.entity.ApiCase;
import com.github.mengweijin.quietly.system.service.ApiCaseService;
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
public class ApiCaseController  {

    /**
     * <p>
     * ApiCaseService
     * </p>
     */
    @Autowired
    private ApiCaseService apiCaseService;

    /**
     * <p>
     * Get ApiCase by id
     * </p>
     * @param id id
     * @return ApiCase
     */
    @GetMapping("/{id}")
    public ApiCase getById(@PathVariable("id") Serializable id) {
        return apiCaseService.getById(id);
    }

    /**
     * <p>
     * Add ApiCase
     * </p>
     * @param apiCase apiCase
     */
    @PostMapping
    public void add(@Valid @RequestBody ApiCase apiCase) {
        apiCaseService.save(apiCase);
    }

    /**
     * <p>
     * Update ApiCase
     * </p>
     * @param apiCase apiCase
     */
    @PutMapping
    public void update(@Valid @RequestBody ApiCase apiCase) {
        apiCaseService.updateById(apiCase);
    }

    /**
     * <p>
     * Delete ApiCase by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        apiCaseService.removeById(id);
    }

}

