package com.github.mengweijin.quietly.system.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Data
@Accessors(chain = true)
public class ApiArgsDto implements Serializable {

    private HttpHeaders headers;

    /**
     * json
     */
    private String requestBody;

}
