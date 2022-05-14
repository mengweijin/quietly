package com.github.mengweijin.quietly.system.dto;

import lombok.Data;
import org.springframework.util.MultiValueMap;

import java.io.Serializable;

/**
 * @author mengweijin
 * @date 2022/5/4
 */
@Data
public class ApiRequestActualInfoDto implements Serializable {

    private String url;

    private MultiValueMap<String, String> headers;

    /**
     * json
     */
    private String requestBody;
}
