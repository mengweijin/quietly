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
import com.github.mengweijin.quietly.system.entity.DictData;
import com.github.mengweijin.quietly.system.service.DictDataService;
import java.io.Serializable;

/**
 * <p>
 * QTL_DICT_DATA Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/dict-data")
public class DictDataController  {

    /**
     * <p>
     * DictDataService
     * </p>
     */
    @Autowired
    private DictDataService dictDataService;

    /**
     * <p>
     * Get DictData by id
     * </p>
     * @param id id
     * @return DictData
     */
    @GetMapping("/{id}")
    public DictData getById(@PathVariable("id") Serializable id) {
        return dictDataService.getById(id);
    }

    /**
     * <p>
     * Add DictData
     * </p>
     * @param dictData dictData
     */
    @PostMapping
    public void add(@Valid @RequestBody DictData dictData) {
        dictDataService.save(dictData);
    }

    /**
     * <p>
     * Update DictData
     * </p>
     * @param dictData dictData
     */
    @PutMapping
    public void update(@Valid @RequestBody DictData dictData) {
        dictDataService.updateById(dictData);
    }

    /**
     * <p>
     * Delete DictData by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        dictDataService.removeById(id);
    }

}

