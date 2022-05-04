package com.github.mengweijin.quietly.system.dto;

import lombok.Data;
import org.springframework.util.MultiValueMap;

/**
 * @author mengweijin
 * @date 2022/5/4
 */
@Data
public class ApiRequestActualInfoDto {

    private String url;

    private MultiValueMap<String, String> headers;

    private Object requestBody;
}
