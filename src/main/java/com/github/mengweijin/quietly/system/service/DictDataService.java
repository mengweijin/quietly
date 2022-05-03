package com.github.mengweijin.quietly.system.service;

import lombok.extern.slf4j.Slf4j;
import com.github.mengweijin.quietly.system.entity.DictData;
import com.github.mengweijin.quietly.system.mapper.DictDataMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * QTL_DICT_DATA implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class DictDataService extends ServiceImpl<DictDataMapper, DictData> implements IService<DictData> {

    /**
     * <p>
     * DictDataMapper
     * </p>
     */
    @Autowired
    private DictDataMapper dictDataMapper;
}

