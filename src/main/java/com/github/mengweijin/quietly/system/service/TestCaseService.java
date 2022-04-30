package com.github.mengweijin.quietly.system.service;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quietly.cucumber.CucumberRunner;
import com.github.mengweijin.quietly.cucumber.ScenarioThreadLocal;
import com.github.mengweijin.quietly.cucumber.StepArgs;
import com.github.mengweijin.quietly.cucumber.enums.CaseStatus;
import com.github.mengweijin.quietly.cucumber.service.CucumberService;
import com.github.mengweijin.quietly.system.entity.Step;
import com.github.mengweijin.quietly.system.entity.TestCase;
import com.github.mengweijin.quietly.system.mapper.TestCaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    private CucumberService cucumberService;

    @Autowired
    private StepService stepService;

    public void runCase(Long caseId) {
        this.updateStatusById(caseId, CaseStatus.RUNNING);

        try {
            ScenarioThreadLocal.set(new StepArgs().setCaseId(caseId));
            File feature = this.generateFeature(caseId);
            CucumberRunner.runCase(feature);
        } catch (Throwable throwable) {
            log.error("Run case [" + caseId + "] error!", throwable);
            this.setExceptionById(caseId, throwable.getMessage());
        } finally {
            ScenarioThreadLocal.clear();
        }
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

    public File generateFeature(Long caseId) {
        TestCase testCase = this.getById(caseId);
        List<Step> stepList = stepService.lambdaQuery().eq(Step::getCaseId, caseId).orderByAsc(Step::getSeq).list();

        List<String> contentList = new ArrayList<>();
        contentList.add("Feature: " + testCase.getCaseName());
        contentList.add("");
        contentList.add("    @DataSource");
        contentList.add("    Scenario Outline: " + testCase.getDescription());
        stepList.forEach(step ->
                contentList.add("        " + step.getStepDefinition() + " <testCaseId> " + step.getId()));
        contentList.add("");
        contentList.add("        Examples:");
        contentList.add("            |testCaseId|");
        contentList.add("            |" + caseId + "|");

        String content = String.join("\r\n", contentList);
        log.debug("Feature content: \n{}", content);

        this.updateFeatureContentById(caseId, content);

        File feature = FileUtil.file(Const.PROJECT_PATH + "features" + File.separatorChar + caseId + ".feature");
        FileUtil.mkdir(feature.getParentFile());
        return FileUtil.writeLines(contentList, feature, StandardCharsets.UTF_8);
    }
}

