package com.github.mengweijin.quietly.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import com.github.mengweijin.quietly.system.entity.Step;
import com.github.mengweijin.quietly.system.mapper.StepMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

    @Autowired
    private Map<String, QuietlyStep> stepDefinition;

    /**
     * <p>
     * StepMapper
     * </p>
     */
    @Autowired
    private StepMapper stepMapper;

    public Set<String> getStepDefinition(){
        return new TreeSet<>(stepDefinition.keySet());
    }

    public void updateActualValueById(Long stepId, String actualValue) {
        Step step = new Step();
        step.setId(stepId);
        step.setActualValue(actualValue);
        this.updateById(step);
    }
}

