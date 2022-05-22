package com.github.mengweijin.quietly.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.mengweijin.quietly.system.entity.Project;
import com.github.mengweijin.quietly.system.service.ProjectService;
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
import java.util.List;

/**
 * <p>
 * project info Controller
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/project")
public class ProjectController  {

    /**
     * <p>
     * ProjectService
     * </p>
     */
    @Autowired
    private ProjectService projectService;

    @GetMapping("/list")
    public List<Project> list(Project project) {
        return projectService.list(new QueryWrapper<>(project));
    }

    /**
     * <p>
     * Get Project by id
     * </p>
     * @param id id
     * @return Project
     */
    @GetMapping("/{id}")
    public Project getById(@PathVariable("id") Serializable id) {
        return projectService.getById(id);
    }

    /**
     * <p>
     * Add Project
     * </p>
     * @param project project
     */
    @PostMapping
    public void add(@Valid @RequestBody Project project) {
        projectService.save(project);
    }

    /**
     * <p>
     * Update Project
     * </p>
     * @param project project
     */
    @PutMapping
    public void update(@Valid @RequestBody Project project) {
        projectService.updateById(project);
    }

    /**
     * <p>
     * Delete Project by id
     * </p>
     * @param id id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        projectService.removeById(id);
    }

}

