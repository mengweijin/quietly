package com.github.mengweijin.quietly.cucumber.plugin;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestCaseFinished;

/**
 * @author mengweijin
 */
public class ApiCasePlugin implements EventListener {

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseFinished.class, this::testCaseResult);
    }

    private void testCaseResult(TestCaseFinished event) {
        // TestCaseService testCaseService = SpringUtils.getBean(TestCaseService.class);
        // Long caseId = ScenarioThreadLocal.get().getCaseId();
        // TestCase testCase = new TestCase();
        // testCase.setId(caseId);
        //
        // Result result = event.getResult();
        // if (result.getStatus() == Status.PASSED) {
        //     testCase.setStatus(ECaseStatus.SUCCESS);
        // } else {
        //     testCase.setStatus(ECaseStatus.FAILED);
        //     testCase.setFailedMessage(result.getError().getMessage());
        // }
        // testCaseService.updateById(testCase);
    }

}
