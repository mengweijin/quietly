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
import com.github.mengweijin.quietly.system.entity.DictType;
import com.github.mengweijin.quietly.system.service.DictTypeService;
import java.io.Serializable;

/**
 * <p>
 * QTL_DICT_TYPE Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/dict-type")
public class DictTypeController  {

    /**
     * <p>
     * DictTypeService
     * </p>
     */
    @Autowired
    private DictTypeService dictTypeService;

    /**
     * <p>
     * Get DictType by id
     * </p>
     * @param id id
     * @return DictType
     */
    @GetMapping("/{id}")
    public DictType getById(@PathVariable("id") Serializable id) {
        return dictTypeService.getById(id);
    }

    /**
     * <p>
     * Add DictType
     * </p>
     * @param dictType dictType
     */
    @PostMapping
    public void add(@Valid @RequestBody DictType dictType) {
        dictTypeService.save(dictType);
    }

    /**
     * <p>
     * Update DictType
     * </p>
     * @param dictType dictType
     */
    @PutMapping
    public void update(@Valid @RequestBody DictType dictType) {
        dictTypeService.updateById(dictType);
    }

    /**
     * <p>
     * Delete DictType by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        dictTypeService.removeById(id);
    }

}

