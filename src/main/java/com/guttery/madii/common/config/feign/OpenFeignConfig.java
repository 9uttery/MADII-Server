package com.guttery.madii.common.config.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.guttery.madii")
public class OpenFeignConfig {


}

