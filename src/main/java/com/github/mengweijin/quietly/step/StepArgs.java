package com.github.mengweijin.quietly.step;

import cn.hutool.json.JSONUtil;
import com.github.mengweijin.quietly.enums.StepType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.assertj.core.util.Arrays;

import java.util.Collection;
import java.util.Map;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Data
@Accessors(chain = true)
public class StepArgs {

    private StepType stepType;

    private Object stepData;

    public enum DataType {
        /**
         * 数据类型
         */
        INTEGER,
        LONG,
        STRING,
        STRING_JSON_OBJECT,
        STRING_JSON_ARRAY,
        JSON_OBJECT,
        JSON_ARRAY,
        ARRAY
    }

    public static StepArgs.DataType getDataType(Object object) {
        if(object == null) {
            return null;
        }
        if(object instanceof Integer) {
            return StepArgs.DataType.INTEGER;
        }
        if(object instanceof Long) {
            return StepArgs.DataType.LONG;
        }
        if(object instanceof String) {
            String str = String.valueOf(object);
            if(JSONUtil.isJsonObj(str)){
                return StepArgs.DataType.STRING_JSON_OBJECT;
            }
            if(JSONUtil.isJsonArray(str)){
                return StepArgs.DataType.STRING_JSON_ARRAY;
            }
            return StepArgs.DataType.STRING;
        }
        if(object instanceof Collection) {
            return DataType.JSON_ARRAY;
        }
        if(object instanceof Map) {
            return DataType.JSON_OBJECT;
        }
        if(Arrays.isArray(object)){
            return DataType.ARRAY;
        }
        return DataType.JSON_OBJECT;
    }
}
