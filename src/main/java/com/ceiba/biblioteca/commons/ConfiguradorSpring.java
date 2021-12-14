package com.ceiba.biblioteca.commons;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.ceiba.biblioteca.service")
@PropertySource("classpath:application.properties")
public class ConfiguradorSpring {

}
