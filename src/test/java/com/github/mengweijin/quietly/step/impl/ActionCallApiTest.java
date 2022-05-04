package com.github.mengweijin.quietly.step.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author mengweijin
 * @date 2022/5/4
 */
class ActionCallApiTest {

    private ActionCallApi actionCallApi = new ActionCallApi();

    @BeforeEach
    void setUp() {
    }

    @Test
    void processPlaceholder() throws JsonProcessingException {
        Map<String, Object> headers = new HashMap<>();
        headers.put("username", "jack");
        headers.put("token", "${token}");
        ObjectMapper objectMapper = new ObjectMapper();
        String apiArgs = objectMapper.writeValueAsString(headers);

        Map<String, Object> argsMap = new HashMap<>();
        argsMap.put("token", "AAA");

        String value = actionCallApi.processPlaceholder(apiArgs, argsMap);

        Assertions.assertEquals("{\"username\":\"jack\",\"token\":\"AAA\"}", value);
    }

    @Test
    void placeholder() {
        String content = "111${token}222${token}333${username}444";
        boolean match = ReUtil.isMatch(ActionCallApi.REGEX_PLACEHOLDER_VALUE, StrUtil.toStringOrNull(content));
        assertFalse(match);

        match = ReUtil.isMatch(ActionCallApi.REGEX_PLACEHOLDER, StrUtil.toStringOrNull(content));
        assertFalse(match);

        boolean contains = ReUtil.contains(ActionCallApi.REGEX_PLACEHOLDER, content);
        assertTrue(contains);


        Map<String, Object> argsMap = new HashMap<>();
        argsMap.put("token", "AAA");
        for (Map.Entry<String, Object> entry: argsMap.entrySet()) {
            content = ReUtil.replaceAll(content, "\\$\\{" + entry.getKey() + "}", StrUtil.toStringOrNull(entry.getValue()));
        }

        assertEquals("111AAA222AAA333${username}444", content);
    }
}