package com.github.mengweijin.quietly.cucumber.service;

import cn.hutool.core.io.FileUtil;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quietly.properties.QuietlyProperties;
import com.github.mengweijin.quietly.system.entity.Step;
import com.github.mengweijin.quietly.system.entity.TestCase;
import com.github.mengweijin.quietly.system.service.StepService;
import com.github.mengweijin.quietly.system.service.TestCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Service
public class CucumberService {

    @Autowired
    private QuietlyProperties quietlyProperties;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private StepService stepService;

    public DataSource getTestDataSource() {
        QuietlyProperties.Datasource datasource = quietlyProperties.getTest().getDatasource();
        return new DriverManagerDataSource(datasource.getUrl(), datasource.getUsername(), datasource.getPassword());
    }

    public File generateFeature(Long caseId) {
        TestCase testCase = testCaseService.getById(caseId);
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

        testCaseService.updateFeatureContentById(caseId, content);

        File feature = FileUtil.file(Const.PROJECT_PATH + "features" + File.separatorChar + caseId + ".feature");
        FileUtil.mkdir(feature.getParentFile());
        return FileUtil.writeLines(contentList, feature, StandardCharsets.UTF_8);
    }

}
