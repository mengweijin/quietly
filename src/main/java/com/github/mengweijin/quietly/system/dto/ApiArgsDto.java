package com.github.mengweijin.quietly.system.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;

/**
 * @author mengweijin
 * @date 2022/5/3
 */
@Data
@Accessors(chain = true)
public class ApiArgsDto {

    private HttpHeaders headers;

    private Object requestBody;

}
