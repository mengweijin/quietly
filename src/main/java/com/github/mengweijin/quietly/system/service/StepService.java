package com.github.mengweijin.quietly.system.service;

import lombok.extern.slf4j.Slf4j;
import com.github.mengweijin.quietly.system.entity.Step;
import com.github.mengweijin.quietly.system.mapper.StepMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * test step implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Slf4j
@Service
public class StepService extends ServiceImpl<StepMapper, Step> implements IService<Step> {

    /**
     * <p>
     * StepMapper
     * </p>
     */
    @Autowired
    private StepMapper stepMapper;
}

