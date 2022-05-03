package com.github.mengweijin.quietly.system.service;

import com.github.mengweijin.quietly.enums.DictTypeCode;
import com.github.mengweijin.quietly.system.entity.DictData;
import lombok.extern.slf4j.Slf4j;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.mapper.StepDefinitionMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * QTL_STEP_DEFINITION implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-05-03
 */
@Slf4j
@Service
public class StepDefinitionService extends ServiceImpl<StepDefinitionMapper, StepDefinition> implements IService<StepDefinition> {

    /**
     * <p>
     * StepDefinitionMapper
     * </p>
     */
    @Autowired
    private StepDefinitionMapper stepDefinitionMapper;

    @Autowired
    private DictDataService dictDataService;

    public List<DictData> getCaseStepTypeList() {
        return dictDataService.getByDictTypeCode(DictTypeCode.case_step_type);
    }
}

