package com.trmsmy.spring.boot.batch.springbootbatch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/org/springframework/batch/admin/web/resources/webapp-config.xml")
public class WebAppConfiguration {

}
