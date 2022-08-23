package com.gs.schedules.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:regex-config.properties")
public class RegexConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "schedule")
    public Map<String, String> regexConfigMap() {
        return new HashMap<>();
    }

}
