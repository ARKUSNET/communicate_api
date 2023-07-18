package com.droneservice.communicateapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
@EnableWebSecurity
@ComponentScan("com.droneservice.communicateapi.*")
@EnableJpaRepositories("com.droneservice.communicateapi.repository")
@EnableAspectJAutoProxy
public class CommunicateApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommunicateApiApplication.class, args);
    }
}
