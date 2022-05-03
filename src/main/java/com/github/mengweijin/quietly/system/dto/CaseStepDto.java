package com.github.mengweijin.quietly.system.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Data
@Accessors(chain = true)
public class CaseStepDto {

    private String key;

    private String name;

}
