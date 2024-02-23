package com.github.ypiel.fastff3.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.github.ypiel.fastff3.spring", "com.github.ypiel.fastff3.service"})
public class FastFF3Config {
}
