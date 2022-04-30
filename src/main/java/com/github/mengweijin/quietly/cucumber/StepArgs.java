package com.github.mengweijin.quietly.cucumber;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Data
@Accessors(chain = true)
public class StepArgs {

    private Long caseId;

    private String token;

    private HttpHeaders headers = new HttpHeaders();

    private JdbcTemplate jdbcTemplate;

    private ResponseEntity<Object> responseEntity;
}
