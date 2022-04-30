Feature: 测试Feature

@DataSource

    Scenario Outline: 测试参数长度验证
        GIVEN INIT_DB <caseId> <caseStepId>
        GIVEN GET_TOKEN <caseId> <caseStepId>
        GIVEN SET_HEADER <caseId> <caseStepId>
        WHEN CALL_API 1 <caseId> <caseStepId>
        WHEN CALL_API 2 <caseId> <caseStepId>
        THEN ASSERT_HTTP_CODE <caseId> <caseStepId>
        THEN ASSERT_RESPONSE_BODY <caseId> <caseStepId>
        THEN ASSERT_RESPONSE_BY_JSON_PATH <caseId> <caseStepId>
        THEN ASSERT_DB <caseId> <caseStepId>
        THEN CLEAN_DB <caseId> <caseStepId>
        Examples:
            |caseId|caseStepId|
            |1001  |2001|
