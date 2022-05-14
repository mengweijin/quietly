package com.github.mengweijin.quietly.system.controller;

import com.github.mengweijin.quietly.system.entity.Datasource;
import com.github.mengweijin.quietly.system.service.EnvironmentDatasourceService;
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
public class DatasourceController {

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
    public Datasource getById(@PathVariable("id") Serializable id) {
        return environmentDatasourceService.getById(id);
    }

    /**
     * <p>
     * Add EnvironmentDatasource
     * </p>
     * @param datasource environmentDatasource
     */
    @PostMapping
    public void add(@Valid @RequestBody Datasource datasource) {
        environmentDatasourceService.save(datasource);
    }

    /**
     * <p>
     * Update EnvironmentDatasource
     * </p>
     * @param datasource environmentDatasource
     */
    @PutMapping
    public void update(@Valid @RequestBody Datasource datasource) {
        environmentDatasourceService.updateById(datasource);
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

