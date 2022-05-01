package com.github.mengweijin.quietly.cucumber.step.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.framework.jdbc.CamelColumnMapRowMapper;
import com.github.mengweijin.quickboot.framework.util.Const;
import com.github.mengweijin.quickboot.framework.util.SpringUtils;
import com.github.mengweijin.quietly.cucumber.ScenarioThreadLocal;
import com.github.mengweijin.quietly.cucumber.step.QuietlyStep;
import com.github.mengweijin.quietly.system.entity.Step;
import com.github.mengweijin.quietly.system.service.StepService;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

import static com.github.mengweijin.quietly.cucumber.step.QuietlyStep.ASSERT_DB_DATA;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Slf4j
@Component(ASSERT_DB_DATA)
public class AssertDbData implements QuietlyStep {

    @Override
    @Then(ASSERT_DB_DATA + " {long} {long}")
    public void execute(Long caseId, Long stepId) throws JsonProcessingException, JSONException {
        StepService stepService = SpringUtils.getBean(StepService.class);
        Step step = stepService.getById(stepId);
        String sqls = step.getActionOrAssertKey();
        Assert.hasText(sqls, "The assert sql is empty, please check your config for step " + step.getId());
        // 只处理第一条
        String firstSql = sqls.split(Const.SEMICOLON)[0];
        JdbcTemplate jdbcTemplate = ScenarioThreadLocal.get().getJdbcTemplate();
        List<Map<String, Object>> mapList = jdbcTemplate.query(firstSql, new CamelColumnMapRowMapper());
        ObjectMapper objectMapper = SpringUtil.getBean(ObjectMapper.class);

        String expectValue = step.getExpectValue();
        String actualValue = objectMapper.writeValueAsString(mapList);

        log.debug("Expect value: {}", expectValue);
        log.debug("Actual value: {}", actualValue);
        stepService.updateActualValueById(stepId, actualValue);

        if(JSONUtil.isJsonArray(expectValue)) {
            JSONAssert.assertEquals(expectValue, new JSONArray(actualValue), false);
        } else if(JSONUtil.isJsonObj(expectValue)) {
            String assertValue = objectMapper.writeValueAsString(CollUtil.getFirst(mapList));
            JSONAssert.assertEquals(expectValue, assertValue, false);
        } else {
            Assertions.assertEquals(expectValue, actualValue);
        }
    }
}
