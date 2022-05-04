package com.github.mengweijin.quietly.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quietly.system.entity.ProjectEnvironment;
import com.github.mengweijin.quietly.system.mapper.ProjectEnvironmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * QTL_PROJECT_ENVIRONMENT implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class ProjectEnvironmentService extends ServiceImpl<ProjectEnvironmentMapper, ProjectEnvironment> implements IService<ProjectEnvironment> {

    /**
     * <p>
     * ProjectEnvironmentMapper
     * </p>
     */
    @Autowired
    private ProjectEnvironmentMapper projectEnvironmentMapper;

    public void enableById(Long id) {
        this.lambdaUpdate()
                .set(ProjectEnvironment::getEnabled, Const.N)
                .eq(ProjectEnvironment::getEnabled, Const.Y)
                .update();
        this.lambdaUpdate()
                .set(ProjectEnvironment::getEnabled, Const.Y)
                .eq(ProjectEnvironment::getId, id)
                .update();
    }
}

