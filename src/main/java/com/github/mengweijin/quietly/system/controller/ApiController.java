package com.github.mengweijin.quietly.system.controller;

import com.github.mengweijin.quietly.system.entity.Api;
import com.github.mengweijin.quietly.system.service.ApiService;
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
 * application program interface Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api")
public class ApiController  {

    /**
     * <p>
     * ApiService
     * </p>
     */
    @Autowired
    private ApiService apiService;

    /**
     * <p>
     * Get Api by id
     * </p>
     * @param id id
     * @return Api
     */
    @GetMapping("/{id}")
    public Api getById(@PathVariable("id") Serializable id) {
        return apiService.getById(id);
    }

    /**
     * <p>
     * Add Api
     * </p>
     * @param api api
     */
    @PostMapping
    public void add(@Valid @RequestBody Api api) {
        apiService.save(api);
    }

    /**
     * <p>
     * Update Api
     * </p>
     * @param api api
     */
    @PutMapping
    public void update(@Valid @RequestBody Api api) {
        apiService.updateById(api);
    }

    /**
     * <p>
     * Delete Api by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        apiService.removeById(id);
    }

}

