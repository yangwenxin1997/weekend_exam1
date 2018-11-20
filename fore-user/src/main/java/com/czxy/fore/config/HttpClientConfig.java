package com.czxy.fore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


/**
 * Created by liangtong on 2018/9/10.
 */
@Configuration          //配置类必须有的注解
public class HttpClientConfig {


    @Bean
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        return template;
    }

}
