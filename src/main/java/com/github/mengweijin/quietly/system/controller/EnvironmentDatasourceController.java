package com.github.mengweijin.quietly.system.controller;

import lombok.extern.slf4j.Slf4j;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.github.mengweijin.quietly.system.entity.EnvironmentDatasource;
import com.github.mengweijin.quietly.system.service.EnvironmentDatasourceService;
import java.io.Serializable;

/**
 * <p>
 * QTL_ENVIRONMENT_DATASOURCE Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/environment-datasource")
public class EnvironmentDatasourceController  {

    /**
     * <p>
     * EnvironmentDatasourceService
     * </p>
     */
    @Autowired
    private EnvironmentDatasourceService environmentDatasourceService;

    /**
     * <p>
     * Get EnvironmentDatasource by id
     * </p>
     * @param id id
     * @return EnvironmentDatasource
     */
    @GetMapping("/{id}")
    public EnvironmentDatasource getById(@PathVariable("id") Serializable id) {
        return environmentDatasourceService.getById(id);
    }

    /**
     * <p>
     * Add EnvironmentDatasource
     * </p>
     * @param environmentDatasource environmentDatasource
     */
    @PostMapping
    public void add(@Valid @RequestBody EnvironmentDatasource environmentDatasource) {
        environmentDatasourceService.save(environmentDatasource);
    }

    /**
     * <p>
     * Update EnvironmentDatasource
     * </p>
     * @param environmentDatasource environmentDatasource
     */
    @PutMapping
    public void update(@Valid @RequestBody EnvironmentDatasource environmentDatasource) {
        environmentDatasourceService.updateById(environmentDatasource);
    }

    /**
     * <p>
     * Delete EnvironmentDatasource by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        environmentDatasourceService.removeById(id);
    }

}

