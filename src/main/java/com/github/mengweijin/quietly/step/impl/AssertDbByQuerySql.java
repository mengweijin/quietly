package com.github.mengweijin.quietly.step.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mengweijin.quickboot.framework.exception.QuickBootClientException;
import com.github.mengweijin.quickboot.framework.jdbc.LowerColumnMapRowMapper;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.step.AbstractExecuteSql;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import com.github.mengweijin.quietly.system.service.StepDefinitionService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.junit.jupiter.api.Assertions;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
@Component
public class AssertDbByQuerySql extends AbstractExecuteSql {

    public static final String SELECT_SQL_TAG = "select";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StepDefinitionService stepDefinitionService;

    @Override
    public StepType stepType() {
        return StepType.ASSERT_DB_BY_QUERY_SQL;
    }

    @Override
    public Map<String, Object> executeSql(StepDefinition stepDefinition, JdbcTemplate jdbcTemplate) throws Exception {
        String sql = stepDefinition.getExpression();
        if(!sql.trim().toLowerCase(Locale.ROOT).startsWith(SELECT_SQL_TAG)) {
            String msg = "No select SQL was found for this step type:" + stepType()
                    + ", step id: " + stepDefinition.getId() + "; Please check your step config.";
            throw new QuickBootClientException(msg);
        }

        String expectValue = stepDefinition.getExpectValue();
        if(StrUtil.isBlank(expectValue)) {
            String msg = "No expect value was found for this step type:" + stepType()
                    + ", step id: " + stepDefinition.getId() + "; Please check your step config.";
            throw new QuickBootClientException(msg);
        }

        log.debug("Expect value: {}", expectValue);

        if(JSONUtil.isJsonObj(expectValue)) {
            Map expectMap = objectMapper.readValue(expectValue, HashMap.class);
            expectMap = this.keyToLowerCase(expectMap);
            expectValue = objectMapper.writeValueAsString(expectMap);
            log.debug("Expect value: {}", expectValue);

            Map<String, Object> map = jdbcTemplate.queryForObject(sql, new LowerColumnMapRowMapper());
            Map<String, Object> resultMap = this.keyToLowerCase(map);
            String actualValue = objectMapper.writeValueAsString(resultMap);
            log.debug("Actual value: {}", actualValue);

            stepDefinitionService.updateActualValueById(stepDefinition.getId(), actualValue);

            JSONAssert.assertEquals(expectValue, actualValue, false);

        } else if(JSONUtil.isJsonArray(expectValue)) {
            List expectList = objectMapper.readValue(expectValue, ArrayList.class);
            expectList = this.keyToLowerCase(expectList);
            expectValue = objectMapper.writeValueAsString(expectList);
            log.debug("Expect value: {}", expectValue);

            List<Map<String, Object>> list = jdbcTemplate.query(sql, new LowerColumnMapRowMapper());
            List<Map<String, Object>> mapList = this.keyToLowerCase(list);
            String actualValue = objectMapper.writeValueAsString(mapList);
            log.debug("Actual value: {}", actualValue);

            stepDefinitionService.updateActualValueById(stepDefinition.getId(), actualValue);

            JSONAssert.assertEquals(expectValue, new JSONArray(actualValue), false);

        } else {
            log.debug("Expect value: {}", expectValue);
            String actualValue = jdbcTemplate.queryForObject(sql, String.class);
            log.debug("Actual value: {}", actualValue);
            stepDefinitionService.updateActualValueById(stepDefinition.getId(), actualValue);

            Assertions.assertEquals(expectValue, actualValue);

        }
        return null;
    }

    public Map<String, Object> keyToLowerCase(Map<String, Object> map) {
        if(map == null) {
            return null;
        }

        Map<String, Object> resultMap = new HashMap<>(map.size());
        for (Map.Entry<String, Object> entry: map.entrySet()){
            resultMap.put(entry.getKey().toLowerCase(Locale.ROOT), entry.getValue());
        }
        return resultMap;
    }

    public List<Map<String, Object>> keyToLowerCase(List<Map<String, Object>> mapList) {
        if(mapList == null) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>(mapList.size());
        for (Map<String, Object> map: mapList){
            list.add(this.keyToLowerCase(map));
        }
        return list;
    }
}
