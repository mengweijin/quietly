package com.github.mengweijin.quietly.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quietly.system.entity.Project;
import com.github.mengweijin.quietly.system.mapper.ProjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * project info implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class ProjectService extends ServiceImpl<ProjectMapper, Project> implements IService<Project> {

    /**
     * <p>
     * ProjectMapper
     * </p>
     */
    @Autowired
    private ProjectMapper projectMapper;

}

