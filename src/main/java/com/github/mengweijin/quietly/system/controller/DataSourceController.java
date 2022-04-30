package com.github.mengweijin.quietly.system.controller;

import com.github.mengweijin.quietly.system.entity.DataSource;
import com.github.mengweijin.quietly.system.service.DataSourceService;
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
 * Data source info Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/data-source")
public class DataSourceController  {

    /**
     * <p>
     * DataSourceService
     * </p>
     */
    @Autowired
    private DataSourceService dataSourceService;

    /**
     * <p>
     * Get DataSource by id
     * </p>
     * @param id id
     * @return DataSource
     */
    @GetMapping("/{id}")
    public DataSource getById(@PathVariable("id") Serializable id) {
        return dataSourceService.getById(id);
    }

    /**
     * <p>
     * Add DataSource
     * </p>
     * @param dataSource dataSource
     */
    @PostMapping
    public void add(@Valid @RequestBody DataSource dataSource) {
        dataSourceService.save(dataSource);
    }

    /**
     * <p>
     * Update DataSource
     * </p>
     * @param dataSource dataSource
     */
    @PutMapping
    public void update(@Valid @RequestBody DataSource dataSource) {
        dataSourceService.updateById(dataSource);
    }

    /**
     * <p>
     * Delete DataSource by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        dataSourceService.removeById(id);
    }

}

