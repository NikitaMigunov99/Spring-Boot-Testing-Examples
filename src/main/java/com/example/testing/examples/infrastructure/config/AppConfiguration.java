package com.example.testing.examples.infrastructure.config;

import com.example.testing.examples.model.JokeModel;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableFeignClients(basePackages = "com.example.testing.examples")
@ComponentScan(basePackages = "com.example.testing.examples")
public class AppConfiguration {

    @Bean
    public RestTemplate jokeModelRestTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

}
