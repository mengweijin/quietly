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
import com.github.mengweijin.quietly.system.entity.ProjectEnvironment;
import com.github.mengweijin.quietly.system.service.ProjectEnvironmentService;
import java.io.Serializable;

/**
 * <p>
 * QTL_PROJECT_ENVIRONMENT Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/project-environment")
public class ProjectEnvironmentController  {

    /**
     * <p>
     * ProjectEnvironmentService
     * </p>
     */
    @Autowired
    private ProjectEnvironmentService projectEnvironmentService;

    /**
     * <p>
     * Get ProjectEnvironment by id
     * </p>
     * @param id id
     * @return ProjectEnvironment
     */
    @GetMapping("/{id}")
    public ProjectEnvironment getById(@PathVariable("id") Serializable id) {
        return projectEnvironmentService.getById(id);
    }

    /**
     * <p>
     * Add ProjectEnvironment
     * </p>
     * @param projectEnvironment projectEnvironment
     */
    @PostMapping
    public void add(@Valid @RequestBody ProjectEnvironment projectEnvironment) {
        projectEnvironmentService.save(projectEnvironment);
    }

    /**
     * <p>
     * Update ProjectEnvironment
     * </p>
     * @param projectEnvironment projectEnvironment
     */
    @PutMapping
    public void update(@Valid @RequestBody ProjectEnvironment projectEnvironment) {
        projectEnvironmentService.updateById(projectEnvironment);
    }

    /**
     * <p>
     * Delete ProjectEnvironment by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Serializable id) {
        projectEnvironmentService.removeById(id);
    }

}

