package com.pathology.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ConfigServices {

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger("Running the logger");
    }
}
