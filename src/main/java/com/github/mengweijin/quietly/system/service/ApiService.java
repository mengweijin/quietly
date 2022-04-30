package com.github.mengweijin.quietly.system.service;

import lombok.extern.slf4j.Slf4j;
import com.github.mengweijin.quietly.system.entity.Api;
import com.github.mengweijin.quietly.system.mapper.ApiMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * application program interface implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Slf4j
@Service
public class ApiService extends ServiceImpl<ApiMapper, Api> implements IService<Api> {

    /**
     * <p>
     * ApiMapper
     * </p>
     */
    @Autowired
    private ApiMapper apiMapper;
}

