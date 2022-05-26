package com.github.mengweijin.quietly.system.controller;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.mengweijin.quietly.system.entity.Datasource;
import com.github.mengweijin.quietly.system.service.DatasourceService;
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
import java.util.List;

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
@RequestMapping("/datasource")
public class DatasourceController {

    /**
     * <p>
     * EnvironmentDatasourceService
     * </p>
     */
    @Autowired
    private DatasourceService datasourceService;

    @GetMapping("/getDbTypes")
    @Cacheable("DB_TYPE_ARRAY")
    public DbType[] getDbTypes() {
        return DbType.values();
    }

    @GetMapping("/list")
    public List<Datasource> list(Datasource datasource) {
        return datasourceService.list(new QueryWrapper<>(datasource));
    }

    /**
     * <p>
     * Get EnvironmentDatasource by id
     * </p>
     * @param id id
     * @return EnvironmentDatasource
     */
    @GetMapping("/{id}")
    public Datasource getById(@PathVariable("id") Long id) {
        return datasourceService.getById(id);
    }

    /**
     * <p>
     * Add EnvironmentDatasource
     * </p>
     * @param datasource environmentDatasource
     */
    @PostMapping
    public void add(@Valid @RequestBody Datasource datasource) {
        datasourceService.save(datasource);
    }

    /**
     * <p>
     * Update EnvironmentDatasource
     * </p>
     * @param datasource environmentDatasource
     */
    @PutMapping
    public void update(@Valid @RequestBody Datasource datasource) {
        datasourceService.updateById(datasource);
    }

    /**
     * <p>
     * Delete EnvironmentDatasource by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        datasourceService.removeById(id);
    }

}

