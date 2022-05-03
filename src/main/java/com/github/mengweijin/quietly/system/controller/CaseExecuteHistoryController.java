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
import com.github.mengweijin.quietly.system.entity.CaseExecuteHistory;
import com.github.mengweijin.quietly.system.service.CaseExecuteHistoryService;
import java.io.Serializable;

/**
 * <p>
 * QTL_CASE_EXECUTE_HISTORY Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/case-execute-history")
public class CaseExecuteHistoryController  {

    /**
     * <p>
     * CaseExecuteHistoryService
     * </p>
     */
    @Autowired
    private CaseExecuteHistoryService caseExecuteHistoryService;

    /**
     * <p>
     * Get CaseExecuteHistory by id
     * </p>
     * @param id id
     * @return CaseExecuteHistory
     */
    @GetMapping("/{id}")
    public CaseExecuteHistory getById(@PathVariable("id") Serializable id) {
        return caseExecuteHistoryService.getById(id);
    }

    /**
     * <p>
     * Add CaseExecuteHistory
     * </p>
     * @param caseExecuteHistory caseExecuteHistory
     */
    @PostMapping
    public void add(@Valid @RequestBody CaseExecuteHistory caseExecuteHistory) {
        caseExecuteHistoryService.save(caseExecuteHistory);
    }

    /**
     * <p>
     * Update CaseExecuteHistory
     * </p>
     * @param caseExecuteHistory caseExecuteHistory
     */
    @PutMapping
    public void update(@Valid @RequestBody CaseExecuteHistory caseExecuteHistory) {
        caseExecuteHistoryService.updateById(caseExecuteHistory);
    }

    /**
     * <p>
     * Delete CaseExecuteHistory by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        caseExecuteHistoryService.removeById(id);
    }

}

