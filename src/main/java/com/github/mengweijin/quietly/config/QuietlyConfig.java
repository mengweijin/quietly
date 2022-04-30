package com.github.mengweijin.quietly.config;

import com.github.mengweijin.quietly.properties.QuietlyProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author mengweijin
 * @date 2022/4/30
 */
@Configuration
@EnableConfigurationProperties(QuietlyProperties.class)
public class QuietlyConfig {
}
