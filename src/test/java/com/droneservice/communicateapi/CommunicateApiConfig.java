package com.droneservice.communicateapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@ComponentScan({"com.droneservice.communicateapi.entity", "com.droneservice.communicateapi.controller", "com.droneservice.communicateapi.service"})
@EnableJpaRepositories("com.droneservice.communicateapi.repository")
@EnableAspectJAutoProxy
@Profile({"test"})
public class CommunicateApiConfig {
}
