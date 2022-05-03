package com.github.mengweijin.quietly.system.service;

import lombok.extern.slf4j.Slf4j;
import com.github.mengweijin.quietly.system.entity.DictType;
import com.github.mengweijin.quietly.system.mapper.DictTypeMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * QTL_DICT_TYPE implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class DictTypeService extends ServiceImpl<DictTypeMapper, DictType> implements IService<DictType> {

    /**
     * <p>
     * DictTypeMapper
     * </p>
     */
    @Autowired
    private DictTypeMapper dictTypeMapper;
}

