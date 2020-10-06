package com.example.elnady.technical.config;

import com.example.elnady.technical.service.DefaultFrontLineService;
import com.example.elnady.technical.service.FrontLineService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FrontLineServiceConfiguration {

    @Value("${persons.endpoint.url}")
    private String personsEndpointUrl;

    @Bean
    public FrontLineService frontLineService(RestTemplate restTemplate){
        return new DefaultFrontLineService(restTemplate, personsEndpointUrl);
    }
}
