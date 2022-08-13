package com.github.mengweijin.quietly.step.impl;

import cn.hutool.core.collection.CollUtil;
import com.github.mengweijin.quickboot.exception.QuickBootClientException;
import com.github.mengweijin.quickboot.util.Const;
import com.github.mengweijin.quietly.enums.StepType;
import com.github.mengweijin.quietly.step.AbstractExecuteSql;
import com.github.mengweijin.quietly.system.entity.StepDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Slf4j
@Component
public class ActionExecuteSql extends AbstractExecuteSql {

    public static final String[] UPDATE_SQL_TAGS = {"insert", "update", "delete"};

    @Override
    public StepType stepType() {
        return StepType.ACTION_EXECUTE_SQL;
    }

    @Override
    public Map<String, Object> executeSql(StepDefinition stepDefinition, JdbcTemplate jdbcTemplate) {
        String sql = stepDefinition.getExpression();
        Long stepId = stepDefinition.getId();
        List<String> sqlList = this.filterUpdateSql(sql.split(Const.SEMICOLON));
        if(CollUtil.isEmpty(sqlList)) {
            String msg = "No insert/update/delete SQLs was found for this step type:" + stepType()
                    + ", step id: " + stepId + "; Please check your step config.";
            throw new QuickBootClientException(msg);
        }

        jdbcTemplate.batchUpdate(sqlList.toArray(new String[]{}));

        return null;
    }

    public List<String> filterUpdateSql(String[] sqlArray) {
        return Arrays.stream(sqlArray).
                filter(sql -> Arrays.stream(UPDATE_SQL_TAGS).anyMatch(tag -> sql.trim().toLowerCase(Locale.ROOT).startsWith(tag)))
                .collect(Collectors.toList());
    }

}
