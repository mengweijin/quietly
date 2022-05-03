package com.github.mengweijin.quietly.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quietly.system.entity.TestCase;
import com.github.mengweijin.quietly.system.enums.CaseStatus;
import com.github.mengweijin.quietly.system.mapper.TestCaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * test case implement
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2022-04-30
 */
@Slf4j
@Service
public class TestCaseService extends ServiceImpl<TestCaseMapper, TestCase> implements IService<TestCase> {

    /**
     * <p>
     * ApiCaseMapper
     * </p>
     */
    @Autowired
    private TestCaseMapper testCaseMapper;

    @Autowired
    private StepService stepService;

    public void runCase(Long caseId) {

    }

    public boolean updateStatusById(Long id, CaseStatus status) {
        TestCase testCase = new TestCase();
        testCase.setId(id);
        testCase.setStatus(status);
        return this.updateById(testCase);
    }

    public boolean setExceptionById(Long id, String exception) {
        TestCase testCase = new TestCase();
        testCase.setId(id);
        testCase.setStatus(CaseStatus.FAILED);
        testCase.setException(exception);
        return this.updateById(testCase);
    }

    public boolean updateFeatureContentById(Long id, String content) {
        TestCase testCase = new TestCase();
        testCase.setId(id);
        testCase.setFeatureContent(content);
        return this.updateById(testCase);
    }
    
}

