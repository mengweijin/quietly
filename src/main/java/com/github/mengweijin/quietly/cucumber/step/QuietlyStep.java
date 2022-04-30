package com.github.mengweijin.quietly.cucumber.step;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
public interface QuietlyStep {

    String ASSERT_DB_DATA = "ASSERT_DB_DATA";
    String ASSERT_HTTP_RESPONSE_BODY = "ASSERT_HTTP_RESPONSE_BODY";
    String ASSERT_HTTP_RESPONSE_BODY_BY_JSON_PATH = "ASSERT_HTTP_RESPONSE_BODY_BY_JSON_PATH";
    String ASSERT_HTTP_RESPONSE_CODE = "ASSERT_HTTP_RESPONSE_CODE";
    String CALL_API = "CALL_API";
    String EXECUTE_UPDATE_SQL = "EXECUTE_UPDATE_SQL";
    String SET_HEADER = "SET_HEADER";

    /**
     * execute step
     * @param caseId caseId
     * @param stepId stepId
     */
    void execute(Long caseId, Long stepId) throws Exception;
}
