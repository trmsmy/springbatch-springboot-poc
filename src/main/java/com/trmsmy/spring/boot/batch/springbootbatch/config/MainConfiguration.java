package com.trmsmy.spring.boot.batch.springbootbatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ ServletConfiguration.class, WebAppConfiguration.class })
public class MainConfiguration {

}
