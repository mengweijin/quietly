package com.github.mengweijin.quietly.system.controller;

import com.github.mengweijin.quietly.system.entity.Step;
import com.github.mengweijin.quietly.system.service.StepService;
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
import java.util.Set;

/**
 * <p>
 * test step Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/step")
public class StepController  {

    /**
     * <p>
     * StepService
     * </p>
     */
    @Autowired
    private StepService stepService;

    @GetMapping("/definition/list")
    public Set<String> getStepDefinition(){
        return stepService.getStepDefinition();
    }

    /**
     * <p>
     * Get Step by id
     * </p>
     * @param id id
     * @return Step
     */
    @GetMapping("/{id}")
    public Step getById(@PathVariable("id") Serializable id) {
        return stepService.getById(id);
    }

    /**
     * <p>
     * Add Step
     * </p>
     * @param step step
     */
    @PostMapping
    public void add(@Valid @RequestBody Step step) {
        stepService.save(step);
    }

    /**
     * <p>
     * Update Step
     * </p>
     * @param step step
     */
    @PutMapping
    public void update(@Valid @RequestBody Step step) {
        stepService.updateById(step);
    }

    /**
     * <p>
     * Delete Step by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        stepService.removeById(id);
    }

}

