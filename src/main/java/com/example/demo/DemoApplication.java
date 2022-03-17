package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MessageConverter;

import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootApplication
public class DemoApplication {
    Logger logger = LoggerFactory.getLogger("demo");

    @Bean
    public MessageConverter apRogueMessageConverter() {
        return new GPBMessageConverter();
    }

    @Bean
    public Function<PersonOuterClass.Person, PersonOuterClass.Person> function() {
        return payload -> payload;
    }

    @Bean
    public Consumer<PersonOuterClass.Person> sink() {
        return payload -> logger.info(payload.toString());
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
