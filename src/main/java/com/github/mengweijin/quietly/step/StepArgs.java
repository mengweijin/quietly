package com.github.mengweijin.quietly.step;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Data
@Accessors(chain = true)
public class StepArgs {

    public static final String API_RESPONSE_ENTITY = "API_RESPONSE_ENTITY";

    private Map<String, Object> data;

}
