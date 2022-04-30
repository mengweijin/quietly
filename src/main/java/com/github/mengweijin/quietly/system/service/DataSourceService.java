package com.github.mengweijin.quietly.system.service;

import lombok.extern.slf4j.Slf4j;
import com.github.mengweijin.quietly.system.entity.DataSource;
import com.github.mengweijin.quietly.system.mapper.DataSourceMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Data source info implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Slf4j
@Service
public class DataSourceService extends ServiceImpl<DataSourceMapper, DataSource> implements IService<DataSource> {

    /**
     * <p>
     * DataSourceMapper
     * </p>
     */
    @Autowired
    private DataSourceMapper dataSourceMapper;
}

