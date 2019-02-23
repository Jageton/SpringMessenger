package com.jageton.server.configs;

import com.jageton.server.components.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:properties/token.properties")
public class TokenConfig {

    @Autowired
    private Environment environment;

    @Bean
    public TokenGenerator tokenGenerator(){
        return new TokenGenerator(
                Integer.valueOf(environment.getRequiredProperty("tokenLength")));
    }
}
