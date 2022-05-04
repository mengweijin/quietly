package com.github.mengweijin.quietly.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quietly.system.entity.Datasource;
import com.github.mengweijin.quietly.system.mapper.DatasourceMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * QTL_ENVIRONMENT_DATASOURCE implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class EnvironmentDatasourceService extends ServiceImpl<DatasourceMapper, Datasource> implements IService<Datasource> {

    /**
     * <p>
     * EnvironmentDatasourceMapper
     * </p>
     */
    @Autowired
    private DatasourceMapper datasourceMapper;

}

