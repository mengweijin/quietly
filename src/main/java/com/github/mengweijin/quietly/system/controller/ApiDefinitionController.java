package com.github.mengweijin.quietly.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.github.mengweijin.quietly.system.entity.ApiDefinition;
import com.github.mengweijin.quietly.system.service.ApiDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpMethod;
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
 * QTL_API_DEFINITION Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api-definition")
public class ApiDefinitionController  {

    /**
     * <p>
     * ApiDefinitionService
     * </p>
     */
    @Autowired
    private ApiDefinitionService apiDefinitionService;

    @GetMapping("/getHttpMethods")
    @Cacheable("API_DEFINITION_HTTP_METHOD_ARRAY")
    public HttpMethod[] getHttpMethods() {
        return HttpMethod.values();
    }

    @GetMapping("/getMediaTypes")
    @Cacheable("API_DEFINITION_MEDIA_TYPE_ARRAY")
    public String[] getMediaTypes() {
        return apiDefinitionService.getMediaTypes();
    }


    @GetMapping("/page")
    public PageDTO<ApiDefinition> page(PageDTO<ApiDefinition> page, ApiDefinition apiDefinition) {
        return apiDefinitionService.page(page, new QueryWrapper<>(apiDefinition));
    }

    /**
     * <p>
     * Get ApiDefinition by id
     * </p>
     * @param id id
     * @return ApiDefinition
     */
    @GetMapping("/{id}")
    public ApiDefinition getById(@PathVariable("id") Long id) {
        return apiDefinitionService.getById(id);
    }

    /**
     * <p>
     * Add ApiDefinition
     * </p>
     * @param apiDefinition apiDefinition
     */
    @PostMapping
    public void add(@Valid @RequestBody ApiDefinition apiDefinition) {
        apiDefinitionService.save(apiDefinition);
    }

    /**
     * <p>
     * Update ApiDefinition
     * </p>
     * @param apiDefinition apiDefinition
     */
    @PutMapping
    public void update(@Valid @RequestBody ApiDefinition apiDefinition) {
        apiDefinitionService.updateById(apiDefinition);
    }

    /**
     * <p>
     * Delete ApiDefinition by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        apiDefinitionService.removeById(id);
    }

}

