package com.github.mengweijin.quietly.cucumber.step;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
public interface QuietlyStep {

    void execute(Long caseId, Long stepId);
}
