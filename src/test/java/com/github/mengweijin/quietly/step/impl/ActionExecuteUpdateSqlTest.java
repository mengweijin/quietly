package com.github.mengweijin.quietly.step.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author mengweijin
 * @date 2022/5/4
 */
class ActionExecuteUpdateSqlTest {

    @Test
    void filterUpdateSql() {
        ActionExecuteUpdateSql action = new ActionExecuteUpdateSql();
        String[] sqls = {
                "select * from table",
                "insert into table",
                "update table set",
                "delete from table where id=",
        };
        List<String> strings = action.filterUpdateSql(sqls);
        Assertions.assertEquals(3, strings.size());
    }
}